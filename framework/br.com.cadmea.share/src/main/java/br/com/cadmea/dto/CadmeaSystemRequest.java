package br.com.cadmea.dto;

import br.com.cadmea.comuns.dto.Request;
import br.com.cadmea.model.orm.CadmeaSystem;


public class CadmeaSystemRequest extends Request<CadmeaSystem> {

    @Override
    public void validate() {

    }


    @Override
    public CadmeaSystem getEntity() {
        return null;
    }
}
