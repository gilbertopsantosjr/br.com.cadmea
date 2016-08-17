package br.com.cadmea.comuns.excecao;

/**
 * Exceção para registros não existentes.
 * 
 * @author Gilberto Santos
 * @version 1.0
 */
@SuppressWarnings("serial")
public class NoneExistException extends BusinessException {

	/**
	 * Cria o objeto e atribui a mensagem padrão da exceção.
	 */
	public NoneExistException() {
		super("framework.exception.registronaoexiste");
	}
	
	/**
	 * Cria o objeto e atribui a mensagem da exceção.
	 * 
	 * @param mensagem Mensagem da exceção
	 */
	public NoneExistException(String mensagem) {
		super(mensagem);
	}
	
	/**
	 * Cria o objeto e atribui a mensagem e a causa da exceção.
	 * 
	 * @param mensagem Mensagem da exceção
	 * @param causa Causa da exceção
	 */
	public NoneExistException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
	
	/**
	 * Cria o objeto e atribui a referencia ao arquivo de propriedades.
	 * 
	 * @param mensagem Chave no arquivo de propriedades
	 * @param _args Valores relacionados a chave
	 */
	public NoneExistException(String mensagem, String... _args) {
		super(mensagem, _args);
	}
	
	/**
	 * Cria o objeto e atribui a causa da exceção.
	 * 
	 * @param causa Causa da exceção
	 */
	public NoneExistException(Throwable causa) {
		super(causa);
	}
	
}
