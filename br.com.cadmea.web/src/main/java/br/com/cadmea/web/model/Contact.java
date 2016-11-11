package br.com.cadmea.web.model;

import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.cadmea.model.BaseEntityPersistent;

/**
 * @author Gilberto Santos
 *
 */
@Entity
@Table(name = "contact")
@AttributeOverrides(@AttributeOverride(name = "id",
    column = @Column(name = "con_id", nullable = false)))
public class Contact extends BaseEntityPersistent {

  /**
   *
   */
  private static final long serialVersionUID = -5495084763580433705L;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  @JoinColumn(name = "con_id", nullable = true)
  private Set<Email> emails;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  @JoinColumn(name = "con_id", nullable = false)
  private Set<Phone> telefones;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL,
      mappedBy = "contact")
  @JoinColumn(name = "pes_id", nullable = false)
  private Person person;

  public Person getPerson() {
    return person;
  }

  public void setPerrsons(Person person) {
    this.person = person;
  }

  public Set<Phone> getTelefones() {
    return telefones;
  }

  public void setTelefones(Set<Phone> telefones) {
    this.telefones = telefones;
  }

  public Set<Email> getEmails() {
    return emails;
  }

  public void setEmails(Set<Email> emails) {
    this.emails = emails;
  }

}
