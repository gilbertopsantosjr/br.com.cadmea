package br.com.cadmea.web;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import br.com.cadmea.comuns.orm.enums.Gender;
import br.com.cadmea.comuns.orm.enums.Relationship;
import br.com.cadmea.comuns.orm.enums.Situation;
import br.com.cadmea.dto.UserFormDto;
import br.com.cadmea.model.orm.Permission;
import br.com.cadmea.model.orm.Person;
import br.com.cadmea.model.orm.Phone;
import br.com.cadmea.model.orm.UserSystem;
import br.com.cadmea.spring.util.GenericResponse;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestUserRestSrv extends AbstractTestUnit {

  private static final String EMAIL = "gilbertopsantosjr@gmail.com";

  @Test
  public void a_testRegisterNewUser() throws Exception {

    Phone phone = new Phone();
    phone.setNumber("556292392765");
    phone.setIsDefault(Boolean.TRUE);

    Set<Phone> phones = new HashSet<>();
    phones.add(phone);

    Person person = new Person();
    person.setRegister("70792585100");
    person.setDateOfBirth(getDate(1988, 3, 18));
    person.setRelationship(Relationship.NAMORANDO);
    person.setName("Gilbert Pereira dos Santos Junior");
    person.setGender(Gender.MASCULINO);
    person.setPhones(phones);

    Permission permission = new Permission();
    permission.setRole("ROLE_ADMIN");

    Set<Permission> permissions = new HashSet<>();
    permissions.add(permission);

    UserSystem entity = new UserSystem();
    entity.setDateRegister(getDate());
    entity.setEmail(EMAIL);
    entity.setPassword("password");
    entity.setNickname("Gilberto Santos");
    entity.setPerson(person);
    entity.setPermissions(permissions);

    UserFormDto userDto = new UserFormDto();
    userDto.setEntity(entity);

    String jsonString = fromGson().toJson(userDto);

    this.mockMvc
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

    String json = mockMvc.perform(get(urlToReadUser, 1).with(authenticated))
        .andReturn().getResponse().getContentAsString();

    assertTrue(!json.isEmpty());
    UserFormDto userDto = null;

    userDto = fromGson().fromJson(json, UserFormDto.class);

    assertTrue(userDto != null);

    UserSystem entity = userDto.getEntity();
    entity.setLastVisit(new Date());
    entity.setSituation(Situation.ACTIVE);

    userDto.setEntity(entity);

    String jsonString = fromGson().toJson(userDto);

    // call the server to updated
    this.mockMvc
        .perform(put(getUpdateUrlForEntity("user", true)).with(authenticated)
            .contentType(contentType).content(jsonString))
        .andExpect(status().isOk());
  }

  @Test
  public void recoveryPassword() throws Exception {
    this.mockMvc
        .perform(post(SERVER + "/api/public/user/resetPassword")
            .param("email", EMAIL).contentType(contentType))
        .andExpect(status().isOk());
  }

  @Test
  public void showChangePassword() throws Exception {

    final String json = this.mockMvc
        .perform(get(SERVER + "/api/public/user/showChangePassword")
            .locale(Locale.UK).param("id", "1")
            .param("token", "d705f07b-f29b-4742-8c9f-5175f10a0587")
            .contentType(contentType))
        .andReturn().getResponse().getContentAsString();

    assertTrue(!json.isEmpty());

    GenericResponse genericResponde = fromGson().fromJson(json,
        GenericResponse.class);

    assertTrue(genericResponde.getMessage().equals("Ok"));

  }

  @Test
  public void userFoundWithTheSameEmail() {

  }

  @Override
  public void beforeAllTest() {
    // TODO Auto-generated method stub
  }

}
