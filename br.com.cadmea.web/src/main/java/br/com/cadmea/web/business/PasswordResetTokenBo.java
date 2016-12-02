/**
 *
 */
package br.com.cadmea.web.business;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import br.com.cadmea.comuns.exceptions.BusinessException;
import br.com.cadmea.comuns.orm.enums.Result;
import br.com.cadmea.infra.negocio.BaseNegocial;
import br.com.cadmea.model.orm.PasswordResetToken;

/**
 * @author Gilberto Santos
 *
 */
@Component
class PasswordResetTokenBo extends BaseNegocial<PasswordResetToken> {

  @Inject
  private PasswordResetTokenDao dao;

  @Override
  protected PasswordResetTokenDao getDao() {
    return dao;
  }

  public PasswordResetToken getByToken(String token) {
    if (token == null || token.isEmpty())
      throw new BusinessException("Token can't be null or empty");

    Map<String, Object> params = new HashMap<>();
    params.put("token", token);

    return getDao().find(params, Result.UNIQUE);
  }

}
