/**
 *
 */
package br.com.cadmea.web.business;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import br.com.cadmea.baseservico.BaseMaintenanceSrvImpl;
import br.com.cadmea.web.model.User;

/**
 * @author Gilberto Santos
 */
@Service
public class UserSrv extends BaseMaintenanceSrvImpl<User, UserBo> {

  @Inject
  private UserBo userBo;

  @Override
  protected UserBo getBo() {
    return userBo;
  }

}
