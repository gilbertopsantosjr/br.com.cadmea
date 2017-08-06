/**
 * 
 */
package br.com.cadmea.spring.rest.exceptions;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Gilberto Santos
 */
public class RestExceptionDetails {

	private String title;
	private int status;
	private String details;
	private long timestamp;
	private String developerMessage;
	private Map<String, String> validationFields;

	private RestExceptionDetails(final String title, final int status, final String details, final long timestamp, final String developerMessage, final Map<String, String> validationFields) {
		this.title = title;
		this.status = status;
		this.details = details;
		this.timestamp = timestamp;
		this.developerMessage = developerMessage;
		this.validationFields = validationFields;
	}

	public String getTitle() {
		return title;
	}

	public int getStatus() {
		return status;
	}

	public String getDetails() {
		return details;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public String getDeveloperMessage() {
		return developerMessage;
	}
	
	public Map<String, String> getValidationFields() {
		return validationFields;
	}

	public static final class Builder {
		private String title;
		private int status;
		private String details;
		private long timestamp;
		private String developerMessage;
		private Map<String, String> validationFields = new HashMap<String, String>();
		
		public static Builder newBuilder(){
			return new Builder();
		}
		
		public Builder withTitle(String title){
			this.title = title;
			return this;
		}
		
		public Builder withStatus(int status){
			this.status = status;
			return this;
		}
		
		public Builder withDetails(String details){
			this.details = details;
			return this;
		}
		
		public Builder withTimestamp(long timestamp){
			this.timestamp = timestamp;
			return this;
		}
		
		public Builder withDeveloperMessage(String developerMessage){
			this.developerMessage = developerMessage;
			return this;
		}
		
		public Builder withValidationField(String field, String message){
			validationFields.put(field, message);
			return this;
		}
		
		public RestExceptionDetails build(){
			return new RestExceptionDetails(title, status, details, timestamp, developerMessage, validationFields);
		}
		
	} 
	
}
