package br.com.cadmea.model.orm;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import br.com.cadmea.model.BaseEntityPersistent;

/**
 * @author Gilberto Santos
 *
 */
@Entity
@Table(name = "email")
@AttributeOverrides(@AttributeOverride(name = "id",
    column = @Column(name = "ema_id", nullable = false)))
public class Email extends BaseEntityPersistent {

  /**
   *
   */
  private static final long serialVersionUID = -2679869157137625329L;

  @NotNull
  @NotEmpty
  @org.hibernate.validator.constraints.Email
  @Column(name = "ema_address", nullable = false)
  private String address;

  @Column(name = "is_default", nullable = false)
  private Boolean isDefault;

  public Email() {
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
