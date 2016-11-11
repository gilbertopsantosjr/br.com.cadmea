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
import br.com.cadmea.web.model.User;

/**
 * @author Gilberto Santos
 *
 */
@Component
class UserBo extends BaseNegocial<User> {

  @Inject
  private UserDao userDao;

  @Override
  public UserDao getDao() {
    return userDao;
  }

  @Override
  public User insert(User entidade) {
    entidade.setDateRegister(new Date());
    entidade.setSituation(Situation.DISABLE);
    entidade.setLastVisit(new Date());
    return super.insert(entidade);
  }

  @Override
  public void save(User entidade) {
    entidade.setLastVisit(new Date());
    super.save(entidade);
  }

  @Override
  public boolean isThere(User entidade) {
    return getDao().find("email", entidade.getEmail(), Result.UNIQUE) != null;
  }

}
