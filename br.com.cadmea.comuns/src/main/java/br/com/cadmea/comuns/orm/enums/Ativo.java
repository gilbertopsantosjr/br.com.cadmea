/**
 * 
 */
package br.com.cadmea.comuns.orm.enums;

import br.com.cadmea.comuns.orm.ElementoDeDominio;

/**
 * @author gilberto
 *
 */
public enum Ativo implements ElementoDeDominio {

	ATIVO("Ativo"),
	INATIVO("Inativo");
	
	private String descricao;
	
	Ativo(String _descricao){
		this.descricao = _descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}

}
