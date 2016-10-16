/**
 *
 */
package br.com.cadmea.comuns.exceptions.enums;

/**
 * @author gilbertopsantosjr
 *
 */
public enum DefaultMessages {

  NOT_FOUND("not.found.message"), FOUND("found.message");

  private String messageKey;

  DefaultMessages(String messageKey) {
    this.messageKey = messageKey;
  }

  public String getMessageKey() {
    return messageKey;
  }

}
