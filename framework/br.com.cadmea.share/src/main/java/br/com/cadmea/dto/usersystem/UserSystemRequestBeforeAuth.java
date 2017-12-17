package br.com.cadmea.dto.usersystem;

import br.com.cadmea.comuns.dto.Request;
import br.com.cadmea.comuns.dto.State;
import br.com.cadmea.comuns.validator.Validator;
import br.com.cadmea.model.orm.UserSystem;


public class UserSystemRequestBeforeAuth implements State<UserSystem> {

    @Override
    public void doAction(final Request struct) {
        final UserSystemRequest userSystemRequest = (UserSystemRequest) struct;

        Validator.assertNotBlank(userSystemRequest.getSystemName(), UserSystemMessages.USER_SYSTEM_REQUEST_SYSTEM_NAME_REQUIRED);

        Validator.assertEmailValid(userSystemRequest.getEmail());

        Validator.assertNotBlank(userSystemRequest.getPassword(), UserSystemMessages.USER_SYSTEM_REQUEST_PASSWORD_REQUIRED);

        if (userSystemRequest.getPassword() != null) {
            Validator.assertFalse(userSystemRequest.getPassword().length() < UserSystem.MIM_LENGTH_PASSWORD, UserSystemMessages.USER_SYSTEM_PASSWORD_MIN_SIZE);
        }

    }
}
