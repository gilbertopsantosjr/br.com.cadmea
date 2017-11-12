package br.com.cadmea.comuns.dto;

import br.com.cadmea.comuns.orm.EntityPersistent;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

/**
 * @author Gilberto Santos
 * defines the way to structure any response comes from a client
 */
public interface Response<E extends EntityPersistent> extends Structurable<E> {

    /**
     * @return
     */
    List<E> getEntities();

    /**
     * @param entity
     */
    @JsonIgnore
    void setEntity(E entity);
}
