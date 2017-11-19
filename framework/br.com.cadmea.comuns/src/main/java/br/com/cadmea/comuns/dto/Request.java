/**
 *
 */
package br.com.cadmea.comuns.dto;

import br.com.cadmea.comuns.orm.EntityPersistent;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Gilberto Santos
 * defines the way to structure any request comes from a client
 */
public abstract class Request<E extends EntityPersistent> implements Structurable<E> {

    /**
     * validates user' inputs
     *
     * @return
     */
    @JsonIgnore
    public abstract void validate();


    @JsonIgnore
    public abstract String getLocale();


}
