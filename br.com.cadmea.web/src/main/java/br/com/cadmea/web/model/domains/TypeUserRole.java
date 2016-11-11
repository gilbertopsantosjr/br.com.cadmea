package br.com.cadmea.web.model.domains;

import br.com.cadmea.comuns.orm.DomainElement;

/**
 * @author Gilberto Santos
 */
public enum TypeUserRole implements DomainElement {

  USUARIO("Usuario"), ADMIN("Fundador");

  private TypeUserRole(String d) {
    this.descricao = d;
  }

  private String descricao;

  @Override
  public String getDescription() {
    return descricao;
  }

}
