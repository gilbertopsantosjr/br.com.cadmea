package br.com.cadmea.spring.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.cadmea.comuns.exceptions.BusinessException;

@ResponseStatus(HttpStatus.PRECONDITION_REQUIRED)
public class PreConditionRequiredException extends BusinessException {
	/**
	 *
	 */
	private static final long serialVersionUID = -2599832067129917109L;

	public PreConditionRequiredException(String message) {
		super(message);
	}
	
	public PreConditionRequiredException(Exception e) {
		super(e);
	}

}
