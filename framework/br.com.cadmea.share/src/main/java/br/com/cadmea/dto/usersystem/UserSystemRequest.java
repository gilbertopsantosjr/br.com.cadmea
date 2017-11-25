/**
 *
 */
package br.com.cadmea.dto.user;

import br.com.cadmea.comuns.dto.Request;
import br.com.cadmea.comuns.validator.Validator;
import br.com.cadmea.model.orm.UserSystem;
import lombok.Data;

/**
 * @author Gilberto Santos
 */
@Data
public class UserSystemRequest extends Request<UserSystem> {

    public static final String USER_SYSTEM_REQUEST_SYSTEM_NAME_REQUIRED = "userSystemRequest.systemName.required";
    public static final String USER_SYSTEM_REQUEST_PASSWORD_REQUIRED = "userSystemRequest.password.required";
    public static final String USER_SYSTEM_PASSWORD_MIN_SIZE = "userSystem.password.min.size";
    public static final String USER_SYSTEM_REQUEST_PASSWORD_NOTMATCH = "userSystemRequest.password.notmatch";

    private String systemName;
    private String url;
    private String email; // must be a valid email
    private String password;
    private String repeatPassword;
    private String pictureProfile;
    private String nickname;
    private Boolean readTerms;

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
