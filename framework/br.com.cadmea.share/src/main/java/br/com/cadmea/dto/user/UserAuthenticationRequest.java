package br.com.cadmea.dto.user;

import br.com.cadmea.comuns.dto.Request;
import br.com.cadmea.comuns.validator.Validator;
import br.com.cadmea.model.orm.UserSystem;
import lombok.Data;

@Data
public class UserAuthenticationRequest implements Request<UserSystem> {

    private String systemName;
    private String username;
    private String password;

    @Override
    public UserSystem getEntity() {
        return null;
    }

    @Override
    public void validate() {
        Validator.assertNotBlank(getSystemName(), "system name is required !");
        Validator.assertNotBlank(getUsername(), "username is required !");
        Validator.assertNotBlank(getPassword(), "password is required !");
        Validator.failIfAnyExceptionsFound();
    }

    @Override
    public String getLocale() {
        return null;
    }
}
