package br.com.cadmea.comuns.orm;



/**
 * Interface para utilização de exclusão lógica
 * 
 * @author gilberto santos
 * @version 1.0
 */
public interface ExclusaoLogica  {

	/**
	 * Campo para alteração do atributo para excluido
	 * 
	 * @param estado Estado da exclusão
	 */
	void setExcluido(Boolean estado);

	/**
	 * 
	 * @return verdadeiro caso o registro nao pode aparecer para o usuario
	 */
	Boolean getExcluido();
}
