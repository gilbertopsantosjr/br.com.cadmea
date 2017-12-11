/**
 *
 */
package br.com.cadmea.dto.usersystem;

import br.com.cadmea.comuns.dto.Request;
import br.com.cadmea.comuns.orm.enums.Gender;
import br.com.cadmea.comuns.orm.enums.Relationship;
import br.com.cadmea.comuns.util.DateUtil;
import br.com.cadmea.comuns.validator.Validator;
import br.com.cadmea.model.orm.Person;
import br.com.cadmea.model.orm.UserSystem;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Gilberto Santos
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserSystemRequest extends Request<UserSystem> {

    private String systemName;
    private String url;
    private String email;
    private String password;
    private String repeatPassword;
    private String pictureProfile;
    private String nickname;
    private Boolean readTerms;

    private String personName;
    private String personSurname;
    private String personDateOfBirth;
    private String personRegister;
    private Gender personGender;
    private Relationship personRelationship;

    private String locale;


    /**
     * here we set the values defines by the user
     *
     * @return
     */
    @Override
    public UserSystem getEntity() {
        final UserSystem userSystem = new UserSystem();
        userSystem.setEmail(getEmail());
        userSystem.setNickname(getNickname());
        userSystem.setReadTerms(getReadTerms());

        Person person = new Person();
        person.setName(personName);
        person.setSurname(personName);
        person.setDateOfBirth( DateUtil.getDate(personDateOfBirth) );
        person.setGender(personGender);
        person.setRegister(personRegister);

        userSystem.setPerson(person);

        return userSystem;
    }

    /**
     * check if the {@link UserSystemRequest} is a valid on request
     */
    @Override
    public void validate() {
        //CREATE, UPDATE, REQUEST
        getState().doAction(this);
        Validator.failIfAnyExceptionsFound();
    }


}
