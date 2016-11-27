package br.com.cadmea.web;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.cadmea.comuns.orm.enums.Gender;
import br.com.cadmea.comuns.orm.enums.Relationship;
import br.com.cadmea.model.orm.Permission;
import br.com.cadmea.model.orm.Person;
import br.com.cadmea.model.orm.Phone;
import br.com.cadmea.model.orm.UserSystem;
import br.com.cadmea.web.dto.UserFormDto;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestUserRestSrv extends AbstractTestUnit {

  @Autowired
  private WebApplicationContext webApplicationContext;

  private MockMvc mockMvc;

  private MediaType contentType = new MediaType(
      MediaType.APPLICATION_JSON.getType(),
      MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

  @Before
  public void setup() {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

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
    person.setName("Katiuscia Pereira dos Santos");
    person.setGender(Gender.FEMININO);
    person.setPhones(phones);

    Permission permission = new Permission();
    permission.setRole("ROLE_ADMIN");

    Set<Permission> permissions = new HashSet<>();
    permissions.add(permission);

    UserSystem entity = new UserSystem();
    entity.setDateRegister(getDate());
    entity.setEmail("katiuscia.pereira@gmail.com");
    entity.setPassword("password");
    entity.setNickname("kruty");
    entity.setPerson(person);
    entity.setPermissions(permissions);

    UserFormDto userDto = new UserFormDto();
    userDto.setEntity(entity);

    String jsonString = fromGson().toJson(userDto);

    this.mockMvc
        .perform(post("http://localhost:8080/api/public/user/create/")
            .contentType(contentType).content(jsonString))
        .andExpect(status().isCreated());
  }

  @Test
  public void b_userNotFound() throws Exception {
    mockMvc
        .perform(
            get("http://localhost:8080/api/public/user/load/").param("id", "2"))
        .andExpect(status().isNotFound());
  }

  @Test
  public void c_readSingleUserProfile() throws Exception {
    UserSystem user = simulateLogin("katiuscia.pereira@gmail.com");
    mockMvc
        .perform(
            get("http://localhost:8080/api/private/user/read/" + user.getId()))
        .andExpect(status().isOk())
        .andExpect(content().contentType(contentType))
        .andExpect(jsonPath("$.entity.nickname", is("kruty")));
  }

  @Test
  public void unauthenticaredUserCantAccessPrivateUrls() {

  }

  @Test
  public void recoveryPassword() {

  }

  @Test
  public void mandatoryFields() {

  }

  @Test
  public void userFoundWithTheSameEmail() {

  }

  @Override
  public void beforeAllTest() {
    // TODO Auto-generated method stub
  }

}
