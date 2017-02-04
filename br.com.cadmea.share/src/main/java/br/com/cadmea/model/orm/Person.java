package br.com.cadmea.model.orm;

import java.util.Date;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import br.com.cadmea.comuns.orm.enums.Gender;
import br.com.cadmea.comuns.orm.enums.Relationship;
import br.com.cadmea.model.BaseEntityPersistent;

/**
 * @author Gilberto Santos
 *
 */
@Entity
@Table(name = "cadmea_person_member")
@AttributeOverrides(@AttributeOverride(name = "id",
    column = @Column(name = "pes_id", nullable = false)))
public class Person extends BaseEntityPersistent {

  /**
   *
   */
  private static final long serialVersionUID = 6754653476445927880L;

  @Column(nullable = false, length = 255, name = "pes_name")
  private String name;

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

  @ManyToMany(fetch = FetchType.LAZY, targetEntity = Email.class,
      cascade = { CascadeType.ALL })
  @JoinTable(name = "cadmea_emails_per_person",
      joinColumns = { @JoinColumn(name = "user_id") },
      inverseJoinColumns = { @JoinColumn(name = "ema_id") })
  private Set<Email> emails;

  @ManyToMany(fetch = FetchType.LAZY, targetEntity = Phone.class,
      cascade = { CascadeType.ALL })
  @JoinTable(name = "cadmea_phones_per_person",
      joinColumns = { @JoinColumn(name = "user_id") },
      inverseJoinColumns = { @JoinColumn(name = "pho_id") })
  private Set<Phone> phones;

  public Set<Email> getEmails() {
    return emails;
  }

  public void setEmails(Set<Email> emails) {
    this.emails = emails;
  }

  public Set<Phone> getPhones() {
    return phones;
  }

  public void setPhones(Set<Phone> phones) {
    this.phones = phones;
  }

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

}
