package br.com.cadmea.comuns.excecao;

/**
 * Excecao para registros ja existentes.
 * 
 * @author Gilberto Santos
 * @version 1.0
 */
@SuppressWarnings("serial")
public class JaExisteException extends BusinessException {

	/**
	 * Cria o objeto e atribui a mensagem padrao da exceção.
	 */
	public JaExisteException() {
		super("framework.exception.registrojaexiste");
	}
	
	/**
	 * Cria o objeto e atribui a mensagem da exceção.
	 * @param mensagem Mensagem da excecao
	 */
	public JaExisteException(String mensagem) {
		super(mensagem);
	}
	
	/**
	 * Cria o objeto e atribui a mensagem e a causa da exceção.
	 * @param mensagem Mensagem da excecao
	 * @param causa Causa da excecao
	 */
	public JaExisteException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
	
	/**
	 * Cria o objeto e atribui a referencia ao arquivo de propriedades.
	 * 
	 * @param mensagem Chave no arquivo de propriedades
	 * @param _args Valores relacionados a chave
	 */
	public JaExisteException(String mensagem, String... _args) {
		super(mensagem, _args);
	}
	
	/**
	 * Cria o objeto e atribui a causa da exceção.
	 * @param causa Causa da excecao
	 */
	public JaExisteException(Throwable causa) {
		super(causa);
	}
	
}
