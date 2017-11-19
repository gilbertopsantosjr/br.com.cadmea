package br.com.cadmea.comuns.dto;

import br.com.cadmea.comuns.orm.EntityPersistent;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

/**
 * @author Gilberto Santos
 * defines the way to structure any response comes from a client
 */
public abstract class Response<E extends EntityPersistent> implements Structurable<E> {

    /**
     * @return
     */
    public abstract List<E> getEntities();

    /**
     * @param entity
     */
    @JsonIgnore
    public abstract void setEntity(E entity);

    /**
     * clear any data into Response
     */
    public abstract void clear();
}
