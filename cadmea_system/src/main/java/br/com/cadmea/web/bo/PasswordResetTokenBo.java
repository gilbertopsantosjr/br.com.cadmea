/**
 *
 */
package br.com.cadmea.web.bo;

import br.com.cadmea.comuns.exceptions.BusinessException;
import br.com.cadmea.comuns.orm.enums.Result;
import br.com.cadmea.infra.negocio.BaseNegocial;
import br.com.cadmea.model.orm.PasswordResetToken;
import br.com.cadmea.web.dao.PasswordResetTokenDao;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Gilberto Santos
 */
@Component
public class PasswordResetTokenBo extends BaseNegocial<PasswordResetToken> {

    @Inject
    private PasswordResetTokenDao dao;

    @Override
    protected PasswordResetTokenDao getDao() {
        return dao;
    }

    public PasswordResetToken getByToken(final String token) {
        if (token == null || token.isEmpty()) {
            throw new BusinessException("Token can't be null or empty");
        }

        final Map<String, Object> params = new HashMap<>();
        params.put("token", token);

        return getDao().find(params, Result.UNIQUE);
    }

}
