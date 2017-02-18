package br.com.cadmea.comuns.exceptions;

import java.util.List;
import java.util.Locale;

/**
 * Exceção de sistema.
 * 
 * @author Gilberto Santos
 * @version 1.0
 */
@SuppressWarnings("serial")
public class SystemException extends RuntimeException {

	private Locale locale;
	private List<String> messages;
	
	/**
	 * Cria o objeto.
	 */
	public SystemException() {
		
	}

	/**
	 * Cria o objeto e atribui a mensagem da exceção.
	 * 
	 * @param mensagem Mensagem da exceção
	 */
	public SystemException(String _chave)  {
		super(_chave);
	}

	/**
	 * Cria o objeto e atribui a mensagem e a causa da exceção.
	 * 
	 * @param mensagem Mensagem da exceção
	 * @param causa Causa da exceção
	 */
	public SystemException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}

	/**
	 * Cria o objeto e atribui a causa da exceção.
	 * 
	 * @param causa Causa da exceção
	 */
	public SystemException(Throwable causa) {
		super(causa);
	}
	
	
	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

}
