/**
 * 
 */
package br.com.cadmea.comuns.orm;


/**
 * @author gilberto
 * determina se um instancia do banco esta ativa ou inativa
 */
public interface Ativavel  {

	/**
	 * retorna a condicao do EstadoAtivo da entidade
	 * @return Boolean
	 */
	Boolean getAtivo();
	
	
	/**
	 * Campo para alteração do atributo para ativo
	 * 
	 * @param ativo 
	 */
	void setAtivo(Boolean ativo);
	
}
