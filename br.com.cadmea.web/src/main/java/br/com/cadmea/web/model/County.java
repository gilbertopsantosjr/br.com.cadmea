/**
 *
 */
package br.com.cadmea.web.model;

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
@Table(name = "county")
@AttributeOverrides(@AttributeOverride(name = "id",
    column = @Column(name = "est_id", nullable = false)))
public class County extends BaseEntityPersistent {

  /**
   *
   */
  private static final long serialVersionUID = -6557232697491036553L;

  @Column(name = "cou_name", nullable = false, length = 155, unique = true)
  private String name;

  @Column(name = "region", nullable = false, length = 5, unique = true)
  private String region;

  @OneToOne(cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY)
  @JoinColumn(name = "pai_id", referencedColumnName = "pai_id",
      nullable = false)
  private Country country;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getRegion() {
    return region;
  }

  public void setRegion(String region) {
    this.region = region;
  }

  public Country getCountry() {
    return country;
  }

  public void setCountry(Country country) {
    this.country = country;
  }

}
