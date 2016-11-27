/**
 *
 */
package br.com.cadmea.web.business;

import java.util.Date;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import br.com.cadmea.comuns.orm.enums.Result;
import br.com.cadmea.comuns.orm.enums.Situation;
import br.com.cadmea.infra.negocio.BaseNegocial;
import br.com.cadmea.model.orm.UserSystem;

/**
 * @author Gilberto Santos
 *
 */
@Component
class UserBo extends BaseNegocial<UserSystem> {

  @Inject
  private UserDao userDao;

  @Override
  public UserDao getDao() {
    return userDao;
  }

  @Override
  public UserSystem insert(UserSystem entidade) {
    entidade.setDateRegister(new Date());
    entidade.setSituation(Situation.DISABLE);
    entidade.setLastVisit(new Date());
    return super.insert(entidade);
  }

  @Override
  public void save(UserSystem entidade) {
    entidade.setLastVisit(new Date());
    super.save(entidade);
  }

  @Override
  public boolean isThere(UserSystem entidade) {
    return getDao().find("email", entidade.getEmail(), Result.UNIQUE) != null;
  }

}
