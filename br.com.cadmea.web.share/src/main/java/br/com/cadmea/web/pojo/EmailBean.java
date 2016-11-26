/**
 *
 */
package br.com.cadmea.web.pojo;

import br.com.cadmea.comuns.orm.BaseEntity;

/**
 * @author Gilberto Santos
 *
 */
@SuppressWarnings("serial")
public class EmailBean extends BaseEntity {

  private String address;

  private Boolean isDefault;

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
