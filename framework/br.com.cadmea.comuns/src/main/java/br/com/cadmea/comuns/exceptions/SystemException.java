package br.com.cadmea.comuns.exceptions;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Exceção de sistema.
 *
 * @author Gilberto Santos
 * @version 1.0
 */
@Data
public class SystemException extends RuntimeException {

    private Locale locale;
    private final List<String> messages = new ArrayList<>();

    /**
     * Cria o objeto.
     */
    public SystemException() {

    }

    public SystemException(final List<SystemException> exceptions) {
        for (final SystemException e : exceptions) {
            messages.add(e.getMessage());
        }
    }

    /**
     * Cria o objeto e atribui a mensagem da exceção.
     *
     * @param _chave Mensagem da exceção
     */
    public SystemException(final String _chave) {
        super(_chave);
    }

    /**
     * @param _chave
     * @param locale
     */
    public SystemException(final String _chave, final Locale locale) {
        //TODO load messages properties with locale
        super(_chave);
    }

    /**
     * Cria o objeto e atribui a mensagem e a causa da exceção.
     *
     * @param mensagem Mensagem da exceção
     * @param causa    Causa da exceção
     */
    public SystemException(final String mensagem, final Throwable causa) {
        super(mensagem, causa);
    }

    /**
     * Cria o objeto e atribui a causa da exceção.
     *
     * @param causa Causa da exceção
     */
    public SystemException(final Throwable causa) {
        super(causa);
    }


}
