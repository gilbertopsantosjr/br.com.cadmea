package br.com.cadmea.comuns.dto;

import br.com.cadmea.comuns.orm.EntityPersistent;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public interface Structurable<E extends EntityPersistent> extends Serializable {

    /**
     * @return
     */
    @JsonIgnore
    E getEntity();


}
