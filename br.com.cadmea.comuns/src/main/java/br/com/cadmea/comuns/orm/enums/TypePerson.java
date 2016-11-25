package br.com.cadmea.comuns.orm.enums;

import br.com.cadmea.comuns.orm.DomainElement;

public enum TypePerson implements DomainElement {

  FISICA("Pessoa Fisica"), JURIDICA("Pessoa Juridica");

  private TypePerson(String descricao) {
    this.descricao = descricao;
  }

  private String descricao;

  @Override
  public String getDescription() {
    return descricao;
  }
}
