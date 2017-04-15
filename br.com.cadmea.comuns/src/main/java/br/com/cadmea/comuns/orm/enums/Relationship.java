package br.com.cadmea.comuns.orm.enums;

import br.com.cadmea.comuns.orm.DomainElement;

public enum Relationship implements DomainElement {

  NAO_HA_RESPOSTA(0), SOLTEIRO(1), CASADO(2),
  NAMORANDO(3), CELIBATO(4), DIVORCIADO(5);

  private int value;

  private Relationship(int v) {
    this.value = v;
  }
  
  public int getValue(){
	  return value;
  }

  @Override
  public String getDescription() {
    return this.name();
  }
}
