package br.com.cadmea.dto;

import br.com.cadmea.comuns.dto.Request;
import br.com.cadmea.model.orm.SocialNetwork;

public class SocialNetworkRequest extends Request<SocialNetwork> {

    @Override
    public void validate() {

    }

    @Override
    public String getLocale() {
        return null;
    }

    @Override
    public SocialNetwork getEntity() {
        return null;
    }
}
