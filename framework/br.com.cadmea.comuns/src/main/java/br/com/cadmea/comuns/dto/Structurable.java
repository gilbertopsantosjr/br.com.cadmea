/**
 *
 */
package br.com.cadmea.comuns.dto;

import br.com.cadmea.comuns.orm.EntityPersistent;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/**
 * @author Gilberto Santos
 * defines the way to structure any request comes from a client
 */
public interface Structurable<E extends EntityPersistent> extends Serializable {

    /**
     * @return
     */
    @JsonIgnore
    E getEntity();

    /**
     * validates user' inputs
     *
     * @return
     */
    @JsonIgnore
    void validate();
}
