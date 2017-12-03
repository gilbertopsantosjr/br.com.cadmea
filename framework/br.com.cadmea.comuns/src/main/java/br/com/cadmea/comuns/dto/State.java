package br.com.cadmea.comuns.dto;

import br.com.cadmea.comuns.orm.EntityPersistent;

/**
 *
 */
public interface State<E extends EntityPersistent> {
    /**
     *
     */
    void doAction(Request<E> struct);
}
