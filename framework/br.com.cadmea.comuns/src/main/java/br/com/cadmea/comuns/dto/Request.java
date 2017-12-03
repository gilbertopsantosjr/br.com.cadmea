/**
 *
 */
package br.com.cadmea.comuns.dto;

import br.com.cadmea.comuns.orm.EntityPersistent;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Locale;

/**
 * @author Gilberto Santos
 * defines the way to structure any request comes from a client
 */
public abstract class Request<E extends EntityPersistent> implements Structurable<E> {

    /**
     * The State of this Request
     */
    private State state;

    /**
     * validates user' inputs
     *
     * @return
     */
    @JsonIgnore
    public abstract void validate();


    /**
     * {@inheritDoc}
     *
     * @return a locale this Request as String
     */
    @JsonIgnore
    public String getLocale() {
        return Locale.UK.getLanguage();
    }

    /**
     *
     */
    public State getState() {
        return state;
    }

    /**
     *
     */
    public void setState(final State state) {
        this.state = state;
    }
}
