package br.com.cadmea.comuns.excecao;

/**
 * Excecao para registros ja existentes.
 * 
 * @author Gilberto Santos
 * @version 1.0
 */
@SuppressWarnings("serial")
public class RelacionadoException extends BusinessException {

	/**
	 * Cria o objeto e atribui a mensagem padrao da exceção.
	 */
	public RelacionadoException() {
		super("framework.exception.registrosrelacionados");
	}
	
	/**
	 * Cria o objeto e atribui a mensagem da exceção.
	 * @param mensagem Mensagem da excecao
	 */
	public RelacionadoException(String mensagem) {
		super(mensagem);
	}
	
	/**
	 * Cria o objeto e atribui a mensagem e a causa da exceção.
	 * @param mensagem Mensagem da excecao
	 * @param causa Causa da excecao
	 */
	public RelacionadoException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
	
	/**
	 * Cria o objeto e atribui a referencia ao arquivo de propriedades.
	 * 
	 * @param mensagem Chave no arquivo de propriedades
	 * @param _args Valores relacionados a chave
	 */
	public RelacionadoException(String mensagem, String... _args) {
		super(mensagem, _args);
	}
	
	/**
	 * Cria o objeto e atribui a causa da exceção.
	 * @param causa Causa da excecao
	 */
	public RelacionadoException(Throwable causa) {
		super(causa);
	}
	
}
