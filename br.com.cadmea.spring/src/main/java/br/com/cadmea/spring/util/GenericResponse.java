package br.com.cadmea.spring.util;

import java.util.Locale;

public class GenericResponse {
  private String message;
  private String error;
  private Locale locale;

  public GenericResponse(String message) {
    super();
    this.message = message;
  }

  public GenericResponse(String message, String error) {
    super();
    this.message = message;
    this.error = error;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

  public Locale getLocale() {
    return locale;
  }

  public void setLocale(Locale locale) {
    this.locale = locale;
  }

}
