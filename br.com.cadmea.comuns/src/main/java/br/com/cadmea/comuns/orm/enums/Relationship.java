package br.com.cadmea.comuns.orm.enums;

import br.com.cadmea.comuns.orm.DomainElement;

public enum Relationship implements DomainElement {

  NAO_HA_RESPOSTA("Não ha respostas."), SOLTEIRO("Solteiro"), CASADO("Casado"),
  NAMORANDO("Namorando"), CELIBATO("Celibato"), DIVORCIADO("Divorciado");

  private String descricao;

  Relationship(String d) {
    this.descricao = d;
  }

  @Override
  public String getDescription() {
    return this.descricao;
  }
}
