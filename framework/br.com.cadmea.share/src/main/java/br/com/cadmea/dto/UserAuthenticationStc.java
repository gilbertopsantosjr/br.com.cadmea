package br.com.cadmea.dto;

import br.com.cadmea.comuns.dto.Structurable;
import br.com.cadmea.comuns.validator.Validator;
import br.com.cadmea.model.orm.UserSystem;
import lombok.Data;

@Data
public class UserAuthenticationStc implements Structurable<UserSystem> {

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
}
