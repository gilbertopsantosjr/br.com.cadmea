/**
 *
 */
package br.com.cadmea.web.bo;

import br.com.cadmea.comuns.orm.enums.Result;
import br.com.cadmea.comuns.util.DateUtil;
import br.com.cadmea.dto.usersystem.UserSystemMessages;
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

import static br.com.cadmea.comuns.validator.Validator.throwIfFail;

/**
 * @author Gilberto Santos
 */
@Component
public class UserBo extends BaseNegocial<UserSystem> {

    private final Logger log = LoggerFactory.getLogger(getClass());


    @Inject
    private UserDao userDao;

    @Override
    public UserDao getDao() {
        return userDao;
    }


    @Override
    public void save(final UserSystem entidade) {
        entidade.setLastVisit(DateUtil.getDate());
        super.save(entidade);
    }

    /**
     * try to get a {@link UserSystem} of system by email
     *
     * @param {@link String} email
     * @return {@link UserSystem} if found
     */
    public UserSystem findBy(final @NotEmpty String email) {
        log.info(" find user by email " + email);
        final Map<String, Object> params = new HashMap<>();
        params.put("email", email);
        return findByNamedQuery("findByUsername", params, Result.UNIQUE);
    }


    /**
     * try to get a {@link UserSystem} of system by nickname
     *
     * @param {@link String} nickname
     * @return {@link UserSystem} if found
     */
    public UserSystem findByNickName(final @NotEmpty String nickname) {
        log.info(" find user by nickname " + nickname);
        final Map<String, Object> params = new HashMap<>();
        params.put("nickname", nickname);
        return find(params, Result.UNIQUE);
    }

    /**
     * look if the {@link UserSystem} is already in the {@link br.com.cadmea.model.orm.CadmeaSystem}
     *
     * @param {@link String} email
     * @param {@link Long} sysId
     * @return {@link UserSystem} if found
     */
    public UserSystem findBy(final @NotEmpty String email, final @NotNull Long sysId) {
        log.info(" find user by email " + email);
        final Map<String, Object> params = new HashMap<>();
        params.put("email", email);
        params.put("sysId", sysId);
        return findByNamedQuery("findByUsernameAndSystem", params, Result.UNIQUE);
    }


    @Override
    public boolean isThere(UserSystem entity) {
        /**
         * In case the nickname is already in use, then fail
         */
        throwIfFail(findByNickName(entity.getNickname()) != null, UserSystemMessages.USER_SYSTEM_REQUEST_NICKNAME_DUPLICATED);

        /**
         * In case the user try to insert and existent email  doesn't matter the system
         */
        throwIfFail( findBy(entity.getEmail()) != null, UserSystemMessages.EMAIL_DUPLICATED );

        return false;
    }
}
