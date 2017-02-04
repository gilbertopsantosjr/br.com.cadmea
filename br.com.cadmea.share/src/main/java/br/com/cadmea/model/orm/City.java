/**
 *
 */
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

import br.com.cadmea.model.BaseEntityPersistent;

/**
 * @author Gilberto Santos
 *
 */
@Entity
@Table(name = "city")
@AttributeOverrides(@AttributeOverride(name = "id",
    column = @Column(name = "cid_id", nullable = false)))
public class City extends BaseEntityPersistent {

  /**
   *
   */
  private static final long serialVersionUID = 590918291252994539L;

  @Column(name = "cit_name", nullable = false, length = 255)
  private String nome;

  @OneToOne(cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY)
  @JoinColumn(name = "est_id", referencedColumnName = "est_id",
      nullable = false)
  private County county;

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public County getCounty() {
    return county;
  }

  public void setCounty(County county) {
    this.county = county;
  }

}
