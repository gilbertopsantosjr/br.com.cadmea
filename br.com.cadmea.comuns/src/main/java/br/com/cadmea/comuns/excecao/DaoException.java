package br.com.cadmea.comuns.excecao;

/**
 * Exceção da camada de integração.
 * 
 * @author Gilberto Santos
 * @version 1.0
 */
@SuppressWarnings("serial")
public class DaoException extends SystemException {

	/**
	 * Cria o objeto.
	 */
	public DaoException() {
		//Construtor.
	}
	
	/**
	 * Cria o objeto e atribui a mensagem da exceção.
	 * 
	 * @param mensagem Mensagem da exceção
	 */
	public DaoException(String mensagem) {
		super(mensagem);
	}
	
	/**
	 * Cria o objeto e atribui a mensagem e a causa da exceção.
	 * 
	 * @param mensagem Mensagem da exceção
	 * @param causa Causa da exceção
	 */
	public DaoException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
	
	/**
	 * Cria o objeto e atribui a causa da exceção.
	 * 
	 * @param causa Causa da exceção
	 */
	public DaoException(Throwable causa) {
		super(causa);
	}
	
}
