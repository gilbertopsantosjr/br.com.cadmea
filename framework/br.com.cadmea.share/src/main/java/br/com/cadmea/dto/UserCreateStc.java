/**
 *
 */
package br.com.cadmea.dto;

import br.com.cadmea.comuns.dto.Structurable;
import br.com.cadmea.comuns.validator.Validator;
import br.com.cadmea.model.orm.UserSystem;
import lombok.Data;

/**
 * @author Gilberto Santos
 */
@Data
public class UserCreateStc implements Structurable<UserSystem> {

    private String systemName;
    private String url;
    private String email; // must be a valid email
    private String password;
    private String repeatPassword;
    private String pictureProfile;
    private String nickname;
    private Boolean readTerms;

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
        return userSystem;
    }

    /**
     * check if the {@link UserCreateStc} is a valid on request
     */
    @Override
    public void validate() {
        Validator.assertNotBlank(getSystemName(), "System name is required !");

        Validator.assertEmailValid(getEmail());

        Validator.assertNotBlank(getPassword(), "Password is required !");

        Validator.assertEquals(getPassword(), getRepeatPassword(), "Password's not match !");

        Validator.failIfAnyExceptionsFound();
    }


}
