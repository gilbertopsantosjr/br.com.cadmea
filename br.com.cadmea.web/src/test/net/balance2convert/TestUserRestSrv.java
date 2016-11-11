package net.balance2convert;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import br.com.cadmea.spring.test.AbstractTest;
import br.com.cadmea.web.dto.UserDTo;
import br.com.cadmea.web.model.Contact;
import br.com.cadmea.web.model.Person;
import br.com.cadmea.web.model.Phone;
import br.com.cadmea.web.model.User;
import br.com.cadmea.web.model.domains.Gender;
import br.com.cadmea.web.model.domains.Relationship;

public class TestUserRestSrv extends AbstractTest {

  @Test
  public void testRegisterNewUser() {

    Person person = new Person();
    person.setRegister("70792585100");
    person.setDateOfBirth(getDate(1988, 03, 13));
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

    System.out.println(jsonString);
  }

}
