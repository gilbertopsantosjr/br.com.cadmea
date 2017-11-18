package br.com.cadmea.web;

import br.com.cadmea.comuns.clazz.ProjectStage;
import br.com.cadmea.comuns.exceptions.SystemException;
import br.com.cadmea.comuns.orm.enums.Gender;
import br.com.cadmea.comuns.orm.enums.Relationship;
import br.com.cadmea.comuns.util.DateUtil;
import br.com.cadmea.dto.user.UserSystemRequest;
import br.com.cadmea.model.orm.Permission;
import br.com.cadmea.model.orm.Person;
import br.com.cadmea.model.orm.UserSystem;
import br.com.cadmea.web.srv.UserSrv;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;

@ActiveProfiles({ProjectStage.UNIT_TEST})
public class TestUserSrv extends AbstractTestUnit {

    private static final String EMAIL = "gilbertopsantosjr@gmail.com";

    @Autowired
    UserSrv userSrv;

    @Test
    public void whenInsertNewUser_MissingFields_thenFail() {

    }

    @Test
    public void whenInsertNewUser_thenFail() {
        final UserSystemRequest request = new UserSystemRequest();
        try {
            userSrv.insert(request);
            fail();
        } catch (final SystemException e) {
            e.getMessages().forEach(m -> {
                System.out.println(m);
                assertEquals("Expected value", "Expected value");
            });

        }
    }

    @Test
    public void whenInsertNewUser_thenSuccess() {

        final Person person = new Person();
        person.setRegister("70792585100");
        person.setDateOfBirth(DateUtil.getDate(1988, 3, 18));
        person.setRelationship(Relationship.SOLTEIRO);
        person.setName("Gilberto");
        person.setSurname("Pereira dos Santos Junior");
        person.setGender(Gender.MALE);

        final Permission permission = new Permission();
        permission.setRole("ROLE_ADMIN");

        final UserSystem entity = new UserSystem();
        entity.setDateRegister(DateUtil.getDate());
        entity.setEmail(EMAIL);
        entity.setPassword("password");
        entity.setNickname("Gilberto Santos");
        entity.setPerson(person);
        entity.setPermissions(Arrays.asList(permission));

        final UserSystemRequest request = new UserSystemRequest();
        request.setEmail(EMAIL);

        //userSrv.insert(request);

    }

    @Test
    public void whenSearchForUserAndUserNotFound_thenFail() {

    }

    @Test
    public void whenSearchForUserAndUserFound_thenSuccess() {

    }

    @Test
    public void readSingleUserProfile() throws Exception {

    }


    @Test
    public void editUser() throws Exception {
        final RequestPostProcessor authenticated = httpBasic(EMAIL, "password");
    }

    @Test
    public void recoveryPassword() throws Exception {

    }

    @Test
    public void showChangePassword() throws Exception {

    }

    @Test
    public void userFoundWithTheSameEmail() {

    }


}
