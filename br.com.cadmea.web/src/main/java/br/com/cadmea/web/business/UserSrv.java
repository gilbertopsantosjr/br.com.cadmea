/**
 *
 */
package br.com.cadmea.web.business;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import br.com.cadmea.baseservico.BaseMaintenanceSrvImpl;
import br.com.cadmea.comuns.orm.enums.Result;
import br.com.cadmea.model.orm.UserSystem;

/**
 * @author Gilberto Santos
 */
@Service
public class UserSrv extends BaseMaintenanceSrvImpl<UserSystem, UserBo> {

  @Inject
  private UserBo userBo;

  @Override
  protected UserBo getBo() {
    return userBo;
  }

  /**
   * try to get a {@link User} of system by params
   *
   * @param email
   * @param password
   * @return {@link User} if found
   */
  public UserSystem getUserBy(final String email) {
    if (email == null || email.isEmpty())
      throw new RuntimeException("Email can't be null or empty");

    Map<String, Object> params = new HashMap<>();
    params.put("email", email);

    return getBo().find(params, Result.UNIQUE);
  }

}
