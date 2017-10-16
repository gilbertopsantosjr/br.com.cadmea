package br.com.cadmea.web;

import java.util.Date;

import br.com.cadmea.comuns.orm.BaseEntity;
import br.com.cadmea.comuns.orm.enums.Gender;
import br.com.cadmea.comuns.orm.enums.Relationship;

@SuppressWarnings("serial")
public class PersonBean extends BaseEntity {

  private String name;
  private String register;
  private Gender gender;
  private Relationship relationship;
  private Date dateOfBirth;
  private ContactBean contact;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getRegister() {
    return register;
  }

  public void setRegister(String register) {
    this.register = register;
  }

  public Gender getGender() {
    return gender;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  public Relationship getRelationship() {
    return relationship;
  }

  public void setRelationship(Relationship relationship) {
    this.relationship = relationship;
  }

  public Date getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(Date dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public ContactBean getContact() {
    return contact;
  }

  public void setContact(ContactBean contact) {
    this.contact = contact;
  }

}
