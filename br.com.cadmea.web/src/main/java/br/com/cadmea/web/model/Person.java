package br.com.cadmea.web.model;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.com.cadmea.comuns.orm.enums.Gender;
import br.com.cadmea.comuns.orm.enums.Relationship;
import br.com.cadmea.model.BaseEntityPersistent;
import br.com.cadmea.spring.util.JsonDateSerializer;

/**
 * @author Gilberto Santos
 *
 */
@Entity
@Table(name = "cadmea_person_member")
@AttributeOverrides(@AttributeOverride(name = "id",
    column = @Column(name = "pes_id", nullable = false)))
@JsonAutoDetect
public class Person extends BaseEntityPersistent {

  /**
   *
   */
  private static final long serialVersionUID = 6754653476445927880L;

  @NotEmpty
  @Column(nullable = false, length = 255, name = "pes_name")
  private String name;

  @NotEmpty
  @Column(nullable = false, length = 11, name = "pes_register")
  private String register;

  @NotNull
  @Column(nullable = false, length = 1, name = "pes_gender")
  @Enumerated(EnumType.ORDINAL)
  private Gender gender;

  @NotNull
  @Column(nullable = true, length = 1, name = "pes_relationship")
  @Enumerated(EnumType.ORDINAL)
  private Relationship relationship;

  @NotNull
  @Column(nullable = false, name = "pes_date_of_birth")
  @Temporal(TemporalType.TIMESTAMP)
  private Date dateOfBirth;

  @OneToOne(cascade = { CascadeType.PERSIST, CascadeType.REMOVE },
      fetch = FetchType.LAZY)
  @JoinColumn(name = "con_id", nullable = true)
  private Contact contact;

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

  public Contact getContact() {
    return contact;
  }

  public void setContact(Contact contact) {
    this.contact = contact;
  }

}
