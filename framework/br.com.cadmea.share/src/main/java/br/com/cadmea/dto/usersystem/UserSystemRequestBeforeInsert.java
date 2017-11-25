package br.com.cadmea.dto.user.states;

import br.com.cadmea.comuns.dto.Request;
import br.com.cadmea.comuns.dto.State;
import br.com.cadmea.comuns.validator.Validator;
import br.com.cadmea.dto.user.UserSystemRequest;
import br.com.cadmea.model.orm.UserSystem;


public class UserSystemRequestBeforeInsert implements State<UserSystem> {

    @Override
    public void doAction(final Request struct) {
        final UserSystemRequest userSystemRequest = (UserSystemRequest) struct;

        Validator.assertNotBlank(userSystemRequest.getSystemName(), UserSystemRequest.USER_SYSTEM_REQUEST_SYSTEM_NAME_REQUIRED);

        Validator.assertEmailValid(userSystemRequest.getEmail());

        Validator.assertNotBlank(userSystemRequest.getPassword(), UserSystemRequest.USER_SYSTEM_REQUEST_PASSWORD_REQUIRED);

        if (userSystemRequest.getPassword() != null) {
            Validator.assertTrue(userSystemRequest.getPassword().length() > UserSystem.MIM_LENGTH_PASSWORD, UserSystemRequest.USER_SYSTEM_PASSWORD_MIN_SIZE);
        }

        if (userSystemRequest.getRepeatPassword() != null) {
            Validator.assertEquals(userSystemRequest.getPassword(), userSystemRequest.getRepeatPassword(), UserSystemRequest.USER_SYSTEM_REQUEST_PASSWORD_NOTMATCH);
        }
    }
}
