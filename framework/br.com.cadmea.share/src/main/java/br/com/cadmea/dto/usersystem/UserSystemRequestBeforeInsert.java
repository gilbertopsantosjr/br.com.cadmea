package br.com.cadmea.dto.usersystem;

import br.com.cadmea.comuns.dto.Request;
import br.com.cadmea.comuns.dto.State;
import br.com.cadmea.comuns.validator.Validator;
import br.com.cadmea.dto.person.PersonMessages;
import br.com.cadmea.model.orm.UserSystem;


public class UserSystemRequestBeforeInsert implements State<UserSystem> {

    @Override
    public void doAction(final Request struct) {
        final UserSystemRequest userSystemRequest = (UserSystemRequest) struct;

        Validator.assertNotNull(userSystemRequest.getReadTerms(), UserSystemMessages.USER_SYSTEM_REQUEST_READ_TERMS);

        Validator.assertNotBlank(userSystemRequest.getNickname(), UserSystemMessages.USER_SYSTEM_REQUEST_NICKNAME);

        Validator.assertNotBlank(userSystemRequest.getSystemName(), UserSystemMessages.USER_SYSTEM_REQUEST_SYSTEM_NAME_REQUIRED);

        Validator.assertEmailValid(userSystemRequest.getEmail());

        Validator.assertNotBlank(userSystemRequest.getPassword(), UserSystemMessages.USER_SYSTEM_REQUEST_PASSWORD_REQUIRED);

        if (userSystemRequest.getPassword() != null) {
            Validator.assertFalse(userSystemRequest.getPassword().length() < UserSystem.MIM_LENGTH_PASSWORD, UserSystemMessages.USER_SYSTEM_PASSWORD_MIN_SIZE);
        }

        if (userSystemRequest.getRepeatPassword() != null) {
            Validator.assertEquals(userSystemRequest.getPassword(), userSystemRequest.getRepeatPassword(), UserSystemMessages.USER_SYSTEM_REQUEST_PASSWORD_NOTMATCH);
        }

        Validator.assertNotBlank(userSystemRequest.getPersonName(), PersonMessages.NAME_REQUIRED);

        Validator.assertNotBlank(userSystemRequest.getPersonSurname(), PersonMessages.SURNAME_REQUIRED);

        Validator.assertNotBlank(userSystemRequest.getPersonRegister(), PersonMessages.REGISTER_REQUIRED);

        Validator.assertNotNull(userSystemRequest.getPersonGender(), PersonMessages.GENDER_REQUIRED);


    }
}
