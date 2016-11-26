package br.com.cadmea.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.cadmea.comuns.orm.enums.Gender;
import br.com.cadmea.comuns.orm.enums.Relationship;
import br.com.cadmea.web.dto.UserDTo;
import br.com.cadmea.web.model.Contact;
import br.com.cadmea.web.model.Person;
import br.com.cadmea.web.model.Phone;
import br.com.cadmea.web.model.User;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = StartServerAndLoginScreen.class)
public class TestUserRestSrv extends AbstractTestUnit {

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void testRegisterNewUser() throws Exception {

		Person person = new Person();
		person.setRegister("70792585100");
		person.setDateOfBirth(getDate(1980, 8, 22));
		person.setRelationship(Relationship.NAMORANDO);
		person.setName("Katiuscia Pereira dos Santos");
		person.setGender(Gender.FEMININO);

		Phone phone = new Phone();
		phone.setNumber("556292392765");
		phone.setIsDefault(Boolean.TRUE);

		Set<Phone> telefones = new HashSet<>();
		telefones.add(phone);

		Contact contato = new Contact();
		contato.setTelefones(telefones);

		person.setContact(contato);

		User entity = new User();
		entity.setDateRegister(getDate());
		entity.setEmail("katiuscia.pereira@gmail.com");
		entity.setPassword("2kaEjuS2");
		entity.setUsername("kruty");
		entity.setPerson(person);

		UserDTo userDto = new UserDTo();
		userDto.setEntity(entity);

		String jsonString = fromGson().toJson(userDto);

		this.mockMvc.perform(
				post("http://localhost:8080/api/public/user/create/")
				.contentType(contentType)
				.content(jsonString))
				.andExpect(status().isCreated());

	}

	@Override
	public void beforeAllTest() {
		// TODO Auto-generated method stub
	}

}
