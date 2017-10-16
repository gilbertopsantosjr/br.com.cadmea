/**
 * 
 */
package br.com.cadmea.comuns.util;

/**
 * @author gilberto.junior
 * Determina em que fase o projeto de software está vivendo.  
 */
public enum FaseDoSoftware {

	/**
	 * Servidor localhost de desenvolvimento
	 */
	FASE_DESENVOLVIMENTO("Desenv"),
	/**
	 * Servidor de validação dos requisitos 
	 */
	FASE_HOMOLOGACAO("Homolog"),
	/**
	 * Servidor no cliente
	 */
	FASE_PRODUCAO("Prod"),
	/**
	 * Servidor de testes 
	 */
	FASE_TESTE("Teste");
	
	private String nomeDaFase;
	
	FaseDoSoftware(String fase){
		this.nomeDaFase =  fase;
	} 
	
	public String getNomeDaFase(){
		return this.nomeDaFase;
	}
	
}
