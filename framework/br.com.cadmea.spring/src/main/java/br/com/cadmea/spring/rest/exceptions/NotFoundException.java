package br.com.cadmea.spring.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.cadmea.comuns.exceptions.BusinessException;

/**
 * in case the resource is not found
 * 
 * @author Gilberto Santos
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends BusinessException {

	/**
	 *
	 */
	private static final long serialVersionUID = -1525179392679285327L;

	public NotFoundException(String messageKey) {
		super(messageKey);
	}

	public NotFoundException(BusinessException e) {
		super(e);
	}
}
