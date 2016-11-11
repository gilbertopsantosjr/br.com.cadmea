package br.com.cadmea.web.model;

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
@Table(name = "phone")
@AttributeOverrides(@AttributeOverride(name = "id",
    column = @Column(name = "pho_id", nullable = false)))
public class Phone extends BaseEntityPersistent {

  /**
   *
   */
  private static final long serialVersionUID = -7461192408651374226L;

  @NotNull
  @NotEmpty
  @Column(name = "pho_number", nullable = false, length = 15)
  private String number;

  @NotNull
  @Column(name = "is_default", nullable = false)
  private Boolean isDefault;

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public Boolean getIsDefault() {
    return isDefault;
  }

  public void setIsDefault(Boolean isDefault) {
    this.isDefault = isDefault;
  }

}
