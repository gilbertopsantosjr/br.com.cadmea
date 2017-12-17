package br.com.cadmea.web;

import br.com.cadmea.comuns.exceptions.SystemException;
import br.com.cadmea.comuns.i18n.Message;
import br.com.cadmea.comuns.i18n.MessageCommon;
import br.com.cadmea.comuns.orm.enums.Gender;
import br.com.cadmea.comuns.orm.enums.Situation;
import br.com.cadmea.dto.person.PersonMessages;
import br.com.cadmea.dto.usersystem.*;
import br.com.cadmea.model.orm.UserSystem;
import br.com.cadmea.spring.test.AbstractTestUnit;
import br.com.cadmea.web.srv.UserSrv;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.util.Locale;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;


@SpringBootTest(classes = StartCadmeaSystem.class)
public class UserSystemSrvTest extends AbstractTestUnit {

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
                    new Message(Locale.UK, UserSystemMessages.USER_SYSTEM_REQUEST_NICKNAME_REQUIRED),
                    new Message(Locale.UK, MessageCommon.EMAIL_INVALID),
                    new Message(Locale.UK, PersonMessages.NAME_REQUIRED),
                    new Message(Locale.UK, PersonMessages.SURNAME_REQUIRED),
                    new Message(Locale.UK, PersonMessages.REGISTER_REQUIRED),
                    new Message(Locale.UK, PersonMessages.GENDER_REQUIRED),
                    new Message(Locale.UK, PersonMessages.DATE_OF_BIRTH_REQUIRED)
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
            request.setPersonDateOfBirth("22/08/1980");
            userSrv.insert(request);
            fail();
        } catch (final SystemException e) {
            assertThat(e.getMessages(), hasItems(
                    new Message(Locale.UK, UserSystemMessages.USER_SYSTEM_REQUEST_NICKNAME_DUPLICATED)
            ));
        }
    }

    @Test
    public void whenInsertOrUpdateUserSystemWrongDateFormat_thenFail() {
        try {
            final UserSystemRequest request = new UserSystemRequest();
            request.setState(new UserSystemRequestBeforeInsert());
            request.setEmail("emaildateofbirthwrong@gmail.com");
            request.setSystemName(CADMEA_SYSTEM_NAME); // this user is not on this system
            request.setPassword("123456");
            request.setReadTerms(Boolean.TRUE);
            request.setNickname("dateofbirthwrong");
            request.setPersonGender(Gender.MALE);
            request.setPersonName("Gilberto");
            request.setPersonSurname("Pereira dos Santos Júnior");
            request.setPersonRegister("3734563");
            request.setPersonDateOfBirth("22-1980-08");
        } catch (final SystemException e) {
            assertThat(e.getMessages(), hasItems(
                    new Message(Locale.UK, MessageCommon.DATE_FORMAT_INVALID)
            ));
        }
    }

    @Test
    public void whenInsertNewUserAlreadyInCadmeaSystem_thenFail() {
        try {
            final UserSystemRequest request = new UserSystemRequest();
            request.setState(new UserSystemRequestBeforeInsert());
            request.setEmail(EMAIL);
            request.setSystemName(CADMEA_SYSTEM_NAME); // this user is not on this system
            request.setPassword("123456");
            request.setReadTerms(Boolean.TRUE);
            request.setNickname("gilbertopsantosjr");
            request.setPersonGender(Gender.MALE);
            request.setPersonName("Gilberto");
            request.setPersonSurname("Pereira dos Santos Júnior");
            request.setPersonRegister("3734563");
            request.setPersonDateOfBirth("22/08/1980");
            userSrv.insert(request);
            fail(" allow to insert a new user even if the user already exist in cadmea system");
        } catch (final SystemException e) {
            assertThat(e.getMessages(), hasItems(
                    new Message(Locale.UK, UserSystemMessages.USER_SYSTEM_FOUND)
            ));
        }
    }


    @Test
    public void whenInsertSameUser_thenFail() {
        try {
            final UserSystemRequest request = new UserSystemRequest();
            request.setState(new UserSystemRequestBeforeInsert());
            request.setEmail(EMAIL);
            request.setSystemName("MAGEC"); // this user is not on this system
            request.setPassword("123456");
            request.setReadTerms(Boolean.TRUE);
            request.setNickname("another_nickname");
            request.setPersonGender(Gender.MALE);
            request.setPersonName("Gilberto");
            request.setPersonSurname("Pereira dos Santos Júnior");
            request.setPersonRegister("3734563");
            request.setPersonDateOfBirth("22/08/1980");
            userSrv.insert(request);
            fail("the same email twice are not allow");
        } catch (final SystemException e) {
            assertThat(e.getMessages(), hasItems(
                    new Message(Locale.UK, UserSystemMessages.EMAIL_DUPLICATED)
            ));
        }
    }

    @Test
    public void whenInsertNewUser_thenSuccess() {
        try {
            final UserSystemRequest request = new UserSystemRequest();
            request.setState(new UserSystemRequestBeforeInsert());
            request.setEmail("contato@gilbertosantos.com");
            request.setSystemName(CADMEA_SYSTEM_NAME); // this user is not on this system
            request.setPassword("123456");
            request.setReadTerms(Boolean.TRUE);
            request.setNickname("gilbertosantos");
            request.setPersonGender(Gender.MALE);
            request.setPersonName("Gilberto");
            request.setPersonSurname("Pereira dos Santos Júnior");
            request.setPersonRegister("3734563");
            request.setPersonDateOfBirth("22/08/1980");

            final UserSystemResponse response = (UserSystemResponse) userSrv.insert(request);
            final UserSystem toInsert = response.getEntity();

            assertThat(toInsert.getPassword(), is(not(nullValue())));

            assertThat(toInsert.getSystems(), is(not(nullValue())));

            assertThat(toInsert.getSituation(), is(not(nullValue())));
            assertThat(toInsert.getSituation(), is(Situation.DISABLE));

            assertThat(toInsert.getLastVisit(), is(not(nullValue())));
            assertThat(toInsert.getDateRegister(), is(not(nullValue())));

        } catch (final SystemException e) {
            assertThat(e.getMessages(), is(empty()));
            fail("should works properly");
        }
    }


    @Test
    public void whenUserTryToAuthAndNotFound_thenFail() {
        try {
            final UserSystemRequest request = new UserSystemRequest();
            request.setState(new UserSystemRequestBeforeAuth());
            request.setEmail("anotheremail@gmail.com");
            request.setPassword("123456");
            request.setSystemName(CADMEA_SYSTEM_NAME);
            userSrv.authentication(request);
            fail();
        } catch (final SystemException e) {
            assertThat(e.getMessages(), hasItems(
                    new Message(Locale.UK, UserSystemMessages.USER_SRV_NOT_ALLOW_IN_SYSTEM)
            ));
        }
    }

    @Test
    public void whenUserTryToAuthAndMissingFields_thenFail() {
        try {
            final UserSystemRequest request = new UserSystemRequest();
            request.setState(new UserSystemRequestBeforeAuth());
            userSrv.authentication(request);
            fail();
        } catch (final SystemException e) {
            assertThat(e.getMessages(), hasItems(
                    new Message(Locale.UK, UserSystemMessages.USER_SYSTEM_REQUEST_SYSTEM_NAME_REQUIRED),
                    new Message(Locale.UK, UserSystemMessages.USER_SYSTEM_REQUEST_PASSWORD_REQUIRED),
                    new Message(Locale.UK, MessageCommon.EMAIL_INVALID)
            ));
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
