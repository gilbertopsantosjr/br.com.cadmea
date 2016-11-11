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
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import br.com.cadmea.model.BaseEntityPersistent;

/**
 * @author Gilberto Santos
 *
 */
@Entity
@Table(name = "address")
@AttributeOverrides(@AttributeOverride(name = "id",
    column = @Column(name = "end_id", nullable = false)))
public class Address extends BaseEntityPersistent {

  /**
   *
   */
  private static final long serialVersionUID = 6345651268472020921L;

  @NotNull
  @OneToOne(cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY)
  @JoinColumn(name = "cid_id", referencedColumnName = "cid_id",
      nullable = false)
  private City city;

  @NotNull
  @NotEmpty
  private String neight;

  @NotNull
  @NotEmpty
  private String address;

  @NotNull
  @NotEmpty
  private String zip;

  @NotNull
  @NotEmpty
  private String complement;

  public City getCity() {
    return city;
  }

  public void setCity(City city) {
    this.city = city;
  }

  public String getNeight() {
    return neight;
  }

  public void setNeight(String neight) {
    this.neight = neight;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getZip() {
    return zip;
  }

  public void setZip(String zip) {
    this.zip = zip;
  }

  public String getComplement() {
    return complement;
  }

  public void setComplement(String complement) {
    this.complement = complement;
  }

}
