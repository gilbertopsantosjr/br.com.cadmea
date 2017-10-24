/**
 *
 */
package br.com.cadmea.web.bo;

import br.com.cadmea.comuns.orm.enums.Result;
import br.com.cadmea.comuns.orm.enums.Situation;
import br.com.cadmea.infra.negocio.BaseNegocial;
import br.com.cadmea.model.orm.UserSystem;
import br.com.cadmea.web.dao.UserDao;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Date;

/**
 * @author Gilberto Santos
 */
@Component
public class UserBo extends BaseNegocial<UserSystem> {

    @Inject
    private UserDao userDao;

    @Override
    public UserDao getDao() {
        return userDao;
    }

    /**
     * validate the quantities of each contact form
     *
     * @param userSystem
     */
    public void validateEntity(final UserSystem userSystem) {

    }

    @Override
    public UserSystem insert(final UserSystem entidade) {
        validateEntity(entidade);
        entidade.setDateRegister(new Date());
        entidade.setSituation(Situation.DISABLE);
        entidade.setLastVisit(new Date());
        return super.insert(entidade);
    }

    @Override
    public void save(final UserSystem entidade) {
        validateEntity(entidade);
        entidade.setLastVisit(new Date());
        super.save(entidade);
    }

    @Override
    public boolean isThere(final UserSystem entidade) {
        return getDao().find("email", entidade.getEmail(), Result.UNIQUE) != null;
    }

}
