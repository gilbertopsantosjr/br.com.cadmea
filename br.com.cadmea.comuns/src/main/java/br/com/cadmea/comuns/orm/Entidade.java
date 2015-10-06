package br.com.cadmea.comuns.orm;

import java.io.Serializable;

/**
 * supertipo de todas as entidades para utilizacao do identificador.
 * 
 * @author Gilberto Santos
 * @version 1.0
 */
public interface Entidade extends Serializable {

	/**
	 * Retorna o Id da entidade.
	 * 
	 * @return O identificador da entidade
	 */
	Long getId();

	/**
	 * 
	 * @param id
	 */
	void setId(Long id);

}
