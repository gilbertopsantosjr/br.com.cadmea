package br.com.cadmea.model.orm;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import br.com.cadmea.model.BaseEntityPersistent;

@Entity
@Table(name = "person_company")
@AttributeOverrides(@AttributeOverride(name = "id",
    column = @Column(name = "pej_id", nullable = false)))
public class PersonCompany extends BaseEntityPersistent {

  /**
   *
   */
  private static final long serialVersionUID = -1824241683777694152L;

  @NotEmpty
  @Column(nullable = false, length = 16, name = "pej_register")
  private String register;

  @NotNull
  @OneToOne(cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY)
  @JoinColumn(name = "pes_id", referencedColumnName = "pes_id",
      nullable = false, unique = true)
  private Person person;

  public String getRegister() {
    return register;
  }

  public void setRegister(String register) {
    this.register = register;
  }

  public Person getPerson() {
    return person;
  }

  public void setPerson(Person person) {
    this.person = person;
  }

}
