package br.com.cadmea.comuns.exceptions;

import br.com.cadmea.comuns.exceptions.enums.DefaultMessages;

/**
 * Exceção de negocio.
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class BusinessException extends SystemException {

  private String[] args;

  /**
   * Cria o objeto.
   */
  public BusinessException() {
    // Construtor.
  }

  /**
   * Cria o objeto e atribui a mensagem da exceção.
   *
   * @param mensagem
   *          Mensagem da exceção
   */
  public BusinessException(DefaultMessages mensagem) {
    super(mensagem.getMessageKey());
  }

  /**
   * Cria o objeto e atribui a mensagem da exceção.
   * 
   * @param mensagem
   *          Mensagem da exceção
   */
  public BusinessException(String mensagem) {
    super(mensagem);
  }

  /**
   * Cria o objeto e atribui a mensagem e a causa da exceção.
   * 
   * @param mensagem
   *          Mensagem da exceção
   * @param causa
   *          Causa da exceção
   */
  public BusinessException(String mensagem, Throwable causa) {
    super(mensagem, causa);
  }

  /**
   * Cria o objeto e atribui a causa da exceção.
   * 
   * @param causa
   *          Causa da exceção
   */
  public BusinessException(Throwable causa) {
    super(causa);
  }

  /**
   * Cria o objeto e atribui a referencia ao arquivo de propriedades.
   * 
   * @param mensagem
   *          Chave no arquivo de propriedades
   * @param _args
   *          Valores relacionados a chave
   */
  public BusinessException(String mensagem, String... _args) {
    super(mensagem);
    this.args = _args;
  }

  /**
   * 
   * Retorna o valor do atributo valores.
   * 
   * @return O valor do atributo valores
   */
  public String[] getArgs() {
    return args;
  }

}
