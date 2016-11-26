/**
 *
 */
package br.com.cadmea.web.pojo;

import br.com.cadmea.comuns.orm.BaseEntity;

/**
 * @author gilbertopsantosjr
 *
 */
@SuppressWarnings("serial")
public class PhoneBean extends BaseEntity {

  private String number;
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
