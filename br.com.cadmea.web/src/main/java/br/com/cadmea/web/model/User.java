package br.com.cadmea.web.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import br.com.cadmea.model.orm.UserSystem;

/**
 * @author Gilberto Santos
 *
 */
@Entity
@AttributeOverrides(@AttributeOverride(name = "id",
    column = @Column(name = "usu_id", nullable = false)))
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Inheritance(strategy = InheritanceType.JOINED)
public class User extends UserSystem {

  /**
   *
   */
  private static final long serialVersionUID = -4947212525802209540L;

  @OneToOne(cascade = { CascadeType.PERSIST, CascadeType.REMOVE },
      fetch = FetchType.LAZY)
  @JoinColumn(name = "pes_id", referencedColumnName = "pes_id",
      nullable = false)
  private Person person;

  public User() {
    super();
  }

  public Person getPerson() {
    return person;
  }

  public void setPerson(Person person) {
    this.person = person;
  }

}
