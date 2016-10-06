/**
 *
 */
package br.com.cadmea.comuns.orm.enums;

import br.com.cadmea.comuns.orm.DomainElement;

/**
 * @author gilberto
 *
 */
public enum Situation implements DomainElement {

  ACTIVE("1"), DISABLE("0");

  private String description;

  Situation(String _description) {
    this.description = _description;
  }

  @Override
  public String getDescription() {
    return description;
  }

}
