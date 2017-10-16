package br.com.cadmea.comuns.orm.enums;

import br.com.cadmea.comuns.orm.DomainElement;

public enum Gender implements DomainElement {

	MALE(0), FEMALE(1);

	private Gender(int d) {
		this.value = d;
	}

	private int value;

	public int getValue() {
		return value;
	}

	@Override
	public String getDescription() {
		return name();
	}

}
