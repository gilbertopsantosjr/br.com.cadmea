package br.com.cadmea.comuns.dto;

import br.com.cadmea.comuns.orm.EntityPersistent;

import java.util.HashMap;
import java.util.Map;

public abstract class DataStructure<E extends EntityPersistent> implements Request<E> {


    private E entity;

    @Override
    public E getEntity() {
        //
        return entity;
    }

    @Override
    public void validate() {

    }


    public Map<String, Object> getParams() {
        return new HashMap<String, Object>();
    }

}
