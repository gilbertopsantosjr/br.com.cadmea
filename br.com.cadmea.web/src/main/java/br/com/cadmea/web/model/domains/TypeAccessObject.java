package br.com.cadmea.web.model.domains;

import br.com.cadmea.comuns.orm.DomainElement;

/**
 * @author gilbertopsantosjr
 */
public enum TypeAccessObject implements DomainElement {

  RELATORIO("Relatório"), TELA("Tela"), PAGINA("Página"),
  COMPONENTE("Componente");

  private String descricao;

  private TypeAccessObject(String d) {
    this.descricao = d;
  }

  @Override
  public String getDescription() {
    return this.descricao;
  }

}
