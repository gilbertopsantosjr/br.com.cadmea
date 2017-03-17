package br.com.cadmea.model.orm;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.cadmea.model.BaseEntityPersistent;

/**
 * @author Gilberto Santos
 *
 */
@Entity
@Table(name = "social_network")
@AttributeOverrides(@AttributeOverride(name = "id",
    column = @Column(name = "soc_id", nullable = false)))
public class SocialNetwork extends BaseEntityPersistent {

  /**
   *
   */
  private static final long serialVersionUID = -2679869157137625329L;

  @NotNull
  @Column(name = "soc_address", nullable = false)
  private String address;

  @Column(name = "is_default", nullable = false)
  private Boolean isDefault;

  public SocialNetwork() {
    super();
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public Boolean getIsDefault() {
    return isDefault;
  }

  public void setIsDefault(Boolean isDefault) {
    this.isDefault = isDefault;
  }

}