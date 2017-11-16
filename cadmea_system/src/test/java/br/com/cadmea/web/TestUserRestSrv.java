package br.com.cadmea.web;

import br.com.cadmea.comuns.clazz.ProjectStage;
import br.com.cadmea.comuns.orm.enums.Gender;
import br.com.cadmea.comuns.orm.enums.Relationship;
import br.com.cadmea.comuns.orm.enums.Situation;
import br.com.cadmea.comuns.util.DateUtil;
import br.com.cadmea.dto.user.UserSystemRequest;
import br.com.cadmea.model.orm.Permission;
import br.com.cadmea.model.orm.Person;
import br.com.cadmea.model.orm.UserSystem;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.util.Arrays;
import java.util.Locale;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ActiveProfiles({ProjectStage.UNIT_TEST, ProjectStage.SYSTEM_TEST})
public class TestUserRestSrv extends AbstractTestUnit {

    private static final String EMAIL = "gilbertopsantosjr@gmail.com";

    @Test
    public void a_testRegisterNewUser() throws Exception {

        final Person person = new Person();
        person.setRegister("70792585100");
        person.setDateOfBirth(DateUtil.getDate(1988, 3, 18));
        person.setRelationship(Relationship.NAMORANDO);
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

        final UserSystemRequest userDto = new UserSystemRequest();


        final String jsonString = fromGson().toJson(userDto);

        mockMvc
                .perform(post(getCreateUrlForEntity("user", false))
                        .contentType(contentType).content(jsonString))
                .andExpect(status().isCreated());
    }

    @Test
    public void b_userNotFound() throws Exception {
        mockMvc.perform(get(getLoadUrlForEntity("user", false), 2))
                .andExpect(status().isNotFound());
    }

    @Test
    public void c_readSingleUserProfile() throws Exception {
        mockMvc
                .perform(get(getReadUrlForEntity("user", true), 1)
                        .with(httpBasic(EMAIL, "password")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.entity.nickname", is("Gilberto Santos")));
    }

    @Test
    public void unauthenticatedUserCantAccessPrivateUrls() throws Exception {
        mockMvc.perform(get(getReadUrlForEntity("user", true), 3))
                .andExpect(unauthenticated());
    }

    @Test
    public void d_editUser() throws Exception {
        final RequestPostProcessor authenticated = httpBasic(EMAIL, "password");

        final String urlToReadUser = getReadUrlForEntity("user", true);

        final String json = mockMvc.perform(get(urlToReadUser, 1).with(authenticated))
                .andReturn().getResponse().getContentAsString();

        assertTrue(!json.isEmpty());
        UserSystemRequest userDto = null;

        userDto = fromGson().fromJson(json, UserSystemRequest.class);

        assertTrue(userDto != null);

        final UserSystem entity = userDto.getEntity();
        entity.setLastVisit(DateUtil.getDate());
        entity.setSituation(Situation.ACTIVE);

        //userDto.setEntity(entity);

        final String jsonString = fromGson().toJson(userDto);

        // call the server to updated
        mockMvc
                .perform(put(getUpdateUrlForEntity("user", true)).with(authenticated)
                        .contentType(contentType).content(jsonString))
                .andExpect(status().isOk());
    }

    @Test
    public void recoveryPassword() throws Exception {
        mockMvc
                .perform(post(SERVER + "/api/public/user/resetPassword")
                        .param("email", EMAIL).contentType(contentType))
                .andExpect(status().isOk());
    }

    @Test
    public void showChangePassword() throws Exception {

        final String json = mockMvc
                .perform(get(SERVER + "/api/public/user/showChangePassword")
                        .locale(Locale.UK).param("id", "1")
                        .param("token", "d705f07b-f29b-4742-8c9f-5175f10a0587")
                        .contentType(contentType))
                .andReturn().getResponse().getContentAsString();

        assertTrue(!json.isEmpty());
/*
        final GenericResponse genericResponde = fromGson().fromJson(json,
                GenericResponse.class);

        assertTrue(genericResponde.getMessage().equals("Ok")); */

    }

    @Test
    public void userFoundWithTheSameEmail() {

    }


}
