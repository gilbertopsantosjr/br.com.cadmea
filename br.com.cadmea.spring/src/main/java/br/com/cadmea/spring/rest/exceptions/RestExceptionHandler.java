/**
 * 
 */
package br.com.cadmea.spring.rest.exceptions;

import java.util.Date;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author Gilberto Santos
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		RestExceptionDetails reDetails = RestExceptionDetails.Builder.newBuilder()
				.withTimestamp(new Date().getTime())
				.withStatus(status.value())
				.withTitle("Cadmea Exception")
				.withDetails(ex.getMessage())
				.withDeveloperMessage(ex.getClass().getName())
				.build();
		
		return new ResponseEntity<> (reDetails, status);
		
	}

	/**
	 * 
	 * @param nfExcetion
	 * @return ResponseEntity 
	 */
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<?> handleNotFoundException(NotFoundException nfExcetion){
		
		RestExceptionDetails reDetails = RestExceptionDetails.Builder.newBuilder()
				.withTimestamp(new Date().getTime())
				.withStatus(HttpStatus.NOT_FOUND.value())
				.withTitle("Not Found Exception")
				.withDetails(nfExcetion.getMessage())
				.withDeveloperMessage(nfExcetion.getClass().getName())
				.build();
		
		return new ResponseEntity<> (reDetails, HttpStatus.NOT_FOUND);
		
	}

	/**
	 * 
	 * @param cVExcetion
	 * @return
	 */
	@ExceptionHandler(javax.validation.ConstraintViolationException.class)
	public ResponseEntity<?> handlePreConditionRequiredException(ConstraintViolationException cVExcetion){
		
		RestExceptionDetails.Builder builder = RestExceptionDetails.Builder.newBuilder()
			.withTimestamp(new Date().getTime())
			.withStatus(HttpStatus.BAD_REQUEST.value())
			.withTitle("Validation Field Exception")
			.withDetails(cVExcetion.getMessage())
			.withDeveloperMessage(cVExcetion.getClass().getName());
		
		Set<ConstraintViolation<?>> constraintViolations = cVExcetion.getConstraintViolations();
		
		constraintViolations.stream().forEach( c -> {
			builder.withValidationField( String.format( "%s", c.getPropertyPath() ), c.getMessage() );
		} );
		
		RestExceptionDetails reDetails = builder.build();
		
		return new ResponseEntity<> (reDetails, HttpStatus.BAD_REQUEST);
	}
}
