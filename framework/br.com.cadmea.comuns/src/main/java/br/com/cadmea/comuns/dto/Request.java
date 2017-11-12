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
public interface Request<E extends EntityPersistent> extends Structurable<E> {


    /**
     * validates user' inputs
     *
     * @return
     */
    @JsonIgnore
    void validate();


}
