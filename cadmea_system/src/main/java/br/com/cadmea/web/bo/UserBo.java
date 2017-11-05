/**
 *
 */
package br.com.cadmea.web.bo;

import br.com.cadmea.comuns.orm.enums.Result;
import br.com.cadmea.comuns.util.DateUtil;
import br.com.cadmea.infra.negocio.BaseNegocial;
import br.com.cadmea.model.orm.UserSystem;
import br.com.cadmea.web.dao.UserDao;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Gilberto Santos
 */
@Component
public class UserBo extends BaseNegocial<UserSystem> {

    private final Logger logger = LoggerFactory.getLogger(getClass());


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
    public void save(final UserSystem entidade) {
        validateEntity(entidade);
        entidade.setLastVisit(DateUtil.getDate());
        super.save(entidade);
    }

    /**
     * try to get a {@link UserSystem} of system by params
     *
     * @param email
     * @return {@link UserSystem} if found
     */
    public UserSystem getUserBy(final @NotEmpty String email) {
        logger.info(" get user by email " + email);
        final Map<String, Object> params = new HashMap<>();
        params.put("email", email);
        return getDao().findByNamedQuery("loginByUsername", params, Result.UNIQUE);
    }

    /**
     * try to get a {@link UserSystem} of system by params
     *
     * @param email
     * @param sysId
     * @return {@link UserSystem} if found
     */
    public UserSystem getUserBy(final @NotEmpty String email, final @NotNull Long sysId) {
        logger.info(" get user by email " + email);
        final Map<String, Object> params = new HashMap<>();
        params.put("email", email);
        params.put("sysId", sysId);
        return getDao().findByNamedQuery("loginByUsernameAndSystem", params, Result.UNIQUE);
    }


    @Override
    public boolean isThere(final UserSystem entidade) {
        return getDao().find("email", entidade.getEmail(), Result.UNIQUE) != null;
    }

}
