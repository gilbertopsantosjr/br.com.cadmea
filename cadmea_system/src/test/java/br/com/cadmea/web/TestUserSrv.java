package br.com.cadmea.web;

import br.com.cadmea.comuns.exceptions.SystemException;
import br.com.cadmea.comuns.i18n.Message;
import br.com.cadmea.comuns.i18n.MessageCommon;
import br.com.cadmea.comuns.orm.enums.Gender;
import br.com.cadmea.comuns.orm.enums.Relationship;
import br.com.cadmea.comuns.util.DateUtil;
import br.com.cadmea.dto.person.PersonMessages;
import br.com.cadmea.dto.usersystem.UserSystemMessages;
import br.com.cadmea.dto.usersystem.UserSystemRequest;
import br.com.cadmea.dto.usersystem.UserSystemRequestBeforeInsert;
import br.com.cadmea.model.orm.Person;
import br.com.cadmea.model.orm.Role;
import br.com.cadmea.model.orm.UserSystem;
import br.com.cadmea.web.srv.UserSrv;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.util.Arrays;
import java.util.Locale;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;


public class TestUserSrv extends AbstractTestUnit {

    private static final String EMAIL = "gilbertopsantosjr@gmail.com";
    private static final String CADMEA_SYSTEM_NAME = "CADMEA_TEST";


    @Autowired
    UserSrv userSrv;


    @Test
    public void whenInsertNewUserMissingUserSystemRequestMandatoryFields_thenFail() {
        try {
            final UserSystemRequest request = new UserSystemRequest();
            request.setState(new UserSystemRequestBeforeInsert());
            userSrv.insert(request);
            fail();
        } catch (final SystemException e) {
            assertThat(e.getMessages(), hasItems(
                    new Message(Locale.UK, UserSystemMessages.USER_SYSTEM_REQUEST_SYSTEM_NAME_REQUIRED),
                    new Message(Locale.UK, UserSystemMessages.USER_SYSTEM_REQUEST_PASSWORD_REQUIRED),
                    new Message(Locale.UK, UserSystemMessages.USER_SYSTEM_REQUEST_READ_TERMS),
                    new Message(Locale.UK, UserSystemMessages.USER_SYSTEM_REQUEST_NICKNAME),
                    new Message(Locale.UK, MessageCommon.EMAIL_INVALID)
            ));
        }
    }


    @Test
    public void whenPasswordIsLessThan6Chars_thenFail() {
        try {
            final UserSystemRequest request = new UserSystemRequest();
            request.setState(new UserSystemRequestBeforeInsert());
            request.setEmail(EMAIL);
            request.setSystemName(CADMEA_SYSTEM_NAME);
            request.setPassword("123");
            request.setReadTerms(Boolean.TRUE);
            request.setNickname("gilbertopsantosjr");
            userSrv.insert(request);
            fail();
        } catch (final SystemException e) {
            assertThat(e.getMessages(), hasItems(
                    new Message(Locale.UK, UserSystemMessages.USER_SYSTEM_PASSWORD_MIN_SIZE)
            ));
        }
    }

    @Test
    public void whenInsertNewUserNickNameAlreadyInUser_thenFail() {
        try {
            final UserSystemRequest request = new UserSystemRequest();
            request.setState(new UserSystemRequestBeforeInsert());
            request.setEmail(EMAIL);
            request.setSystemName("MAGEC"); // this user is not on this system
            request.setPassword("123456");
            request.setReadTerms(Boolean.TRUE);
            request.setNickname("gilbertopsantosjr");
            request.setPersonGender(Gender.MALE);
            request.setPersonSurname("Pereira dos Santos Júnior");
            request.setPersonName("Gilberto");
            request.setPersonRegister("3734563");
            userSrv.insert(request);
            fail();
        } catch (final SystemException e) {
            assertThat(e.getMessages(), hasItems(
                    new Message(Locale.UK, UserSystemMessages.USER_SYSTEM_REQUEST_NICKNAME_DUPLICATED)
            ));
        }
    }

    @Test
    public void whenInsertNewUserMissingUserSystemEntityMandatoryFields_thenSuccess() {
        try {
            final UserSystemRequest request = new UserSystemRequest();
            request.setState(new UserSystemRequestBeforeInsert());
            request.setEmail(EMAIL);
            request.setSystemName(CADMEA_SYSTEM_NAME);
            request.setPassword("123456");
            request.setReadTerms(Boolean.TRUE);
            request.setNickname("gilbertopsantosjr");
            userSrv.insert(request);
            fail();
        } catch (final SystemException e) {
            assertThat(e.getMessages(), hasItems(
                    new Message(Locale.UK, PersonMessages.NAME_REQUIRED),
                    new Message(Locale.UK, PersonMessages.SURNAME_REQUIRED),
                    new Message(Locale.UK, PersonMessages.REGISTER_REQUIRED),
                    new Message(Locale.UK, PersonMessages.GENDER_REQUIRED)
            ));
        }
    }

    //@Test
    public void whenInsertSameUser_thenFail() {
        final UserSystemRequest request = new UserSystemRequest();
        request.setState(new UserSystemRequestBeforeInsert());
        request.setEmail(EMAIL);
        userSrv.insert(request);
        fail("the same email twice are not allow");
    }

    //@Test
    public void whenInsertNewUser_thenSuccess() {
        try {
            final Person person = new Person();
            person.setRegister("70792585100");
            person.setDateOfBirth(DateUtil.getDate(1988, 3, 18));
            person.setRelationship(Relationship.SOLTEIRO);
            person.setName("Gilberto");
            person.setSurname("Pereira dos Santos Junior");
            person.setGender(Gender.MALE);

            final Role role = new Role();
            role.setName("ROLE_ADMIN");

            final UserSystem entity = new UserSystem();
            entity.setDateRegister(DateUtil.getDate());
            entity.setEmail(EMAIL);
            entity.setPassword("password");
            entity.setNickname("Gilberto Santos");
            entity.setPerson(person);
            entity.setRoles(Arrays.asList(role));

            final UserSystemRequest request = new UserSystemRequest();
            request.setEmail(EMAIL);

            userSrv.insert(request);

        } catch (final SystemException e) {
            assertThat(e.getMessages(), is(empty()));
        }
    }

    //@Test
    public void whenSearchForUserAndUserNotFound_thenFail() {

    }

    //@Test
    public void whenSearchForUserAndUserFound_thenSuccess() {

    }

    //@Test
    public void readSingleUserProfile() throws Exception {

    }


    //@Test
    public void editUser() throws Exception {
        final RequestPostProcessor authenticated = httpBasic(EMAIL, "password");
    }

    //@Test
    public void recoveryPassword() throws Exception {

    }

    //@Test
    public void showChangePassword() throws Exception {

    }

    //@Test
    public void userFoundWithTheSameEmail() {

    }


}
