package br.com.cadmea.web.model.domains;

import br.com.cadmea.comuns.orm.DomainElement;

public enum TipoDePessoa implements DomainElement {

  FISICA("Pessoa Fisica"), JURIDICA("Pessoa Juridica");

  private TipoDePessoa(String descricao) {
    this.descricao = descricao;
  }

  private String descricao;

  @Override
  public String getDescription() {
    return descricao;
  }
}
