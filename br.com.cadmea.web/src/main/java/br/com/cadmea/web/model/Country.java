/**
 *
 */
package br.com.cadmea.web.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import br.com.cadmea.model.BaseEntityPersistent;

/**
 * @author Gilberto Santos
 *
 */
@Entity
@Table(name = "country")
@AttributeOverrides(@AttributeOverride(name = "id",
    column = @Column(name = "pai_id", nullable = false)))
public class Country extends BaseEntityPersistent {

  /**
   *
   */
  private static final long serialVersionUID = 1732697967209359284L;

  @NotEmpty
  @Length(min = 3, max = 250)
  @Column(nullable = true, name = "con_name", length = 255, unique = true)
  private String name;

  @NotEmpty
  @Length(min = 3, max = 25)
  @Column(nullable = false, name = "con_language", length = 25)
  private String language;

  @NotEmpty
  @Length(min = 3, max = 5)
  @Column(nullable = true, name = "co_code", length = 5)
  private String code;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

}
