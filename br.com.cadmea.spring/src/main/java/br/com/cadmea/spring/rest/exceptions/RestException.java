package br.com.cadmea.spring.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.cadmea.comuns.exceptions.BusinessException;

@ResponseStatus(HttpStatus.CONFLICT)
public class RestException extends BusinessException {
	/**
	 *
	 */
	private static final long serialVersionUID = 7818412192158412195L;

	public RestException(RuntimeException e) {
		super("An error is throw during your request, please contact the administrator of system", e);
	}

	public RestException(String messagekey) {
		super(messagekey);
	}

	public RestException(Exception e) {
		super(e);
	}
}
