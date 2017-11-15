package br.com.cadmea.dto;

import br.com.cadmea.comuns.dto.Request;
import br.com.cadmea.model.orm.PasswordResetToken;

public class PasswordResetTokenRequest implements Request<PasswordResetToken> {

    @Override
    public void validate() {

    }

    @Override
    public String getLocale() {
        return null;
    }

    @Override
    public PasswordResetToken getEntity() {
        return null;
    }
}
