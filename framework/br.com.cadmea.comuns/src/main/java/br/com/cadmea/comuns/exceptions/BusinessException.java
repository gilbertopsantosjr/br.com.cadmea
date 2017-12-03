package br.com.cadmea.comuns.exceptions;

import br.com.cadmea.comuns.i18n.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Exceção de negocio.
 *
 * @version 1.0
 */
public class BusinessException extends SystemException {

    private String[] args;
    private List<Message> messages = new ArrayList<>();

    @Override
    public List<Message> getMessages() {
        return messages;
    }

    /**
     * Cria o objeto.
     */
    public BusinessException() {
        // Construtor.
    }

    public BusinessException(final List<Message> messages) {
        this.messages = messages;
    }

    /**
     * Cria o objeto e atribui a mensagem da exceção.
     *
     * @param mensagem Mensagem da exceção
     */
    public BusinessException(final Message mensagem) {
        super(mensagem.getText());
    }

    /**
     * Cria o objeto e atribui a mensagem da exceção.
     *
     * @param mensagem Mensagem da exceção
     */
    public BusinessException(final String mensagem) {
        super(mensagem);
    }

    /**
     * Cria o objeto e atribui a mensagem e a causa da exceção.
     *
     * @param mensagem Mensagem da exceção
     * @param causa    Causa da exceção
     */
    public BusinessException(final String mensagem, final Throwable causa) {
        super(mensagem, causa);
    }

    /**
     * Cria o objeto e atribui a causa da exceção.
     *
     * @param causa Causa da exceção
     */
    public BusinessException(final Throwable causa) {
        super(causa);
    }

    /**
     * Cria o objeto e atribui a referencia ao arquivo de propriedades.
     *
     * @param mensagem Chave no arquivo de propriedades
     * @param _args    Valores relacionados a chave
     */
    public BusinessException(final String mensagem, final String... _args) {
        super(mensagem);
        args = _args;
    }

    /**
     * Retorna o valor do atributo valores.
     *
     * @return O valor do atributo valores
     */
    public String[] getArgs() {
        return args;
    }

}
