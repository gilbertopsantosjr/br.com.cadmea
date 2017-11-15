package br.com.cadmea.dto;

import br.com.cadmea.comuns.dto.Request;
import br.com.cadmea.model.orm.CadmeaSystem;


public class CadmeaSystemRequest implements Request<CadmeaSystem> {

    @Override
    public void validate() {

    }

    @Override
    public String getLocale() {
        return null;
    }

    @Override
    public CadmeaSystem getEntity() {
        return null;
    }
}
