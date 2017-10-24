/**
 *
 */
package br.com.cadmea.web.srv;

import br.com.cadmea.baseservico.BaseMaintenanceSrvImpl;
import br.com.cadmea.comuns.orm.enums.Result;
import br.com.cadmea.model.orm.CadmeaSystem;
import br.com.cadmea.model.orm.PasswordResetToken;
import br.com.cadmea.model.orm.UserSystem;
import br.com.cadmea.spring.beans.SmtpEmailSender;
import br.com.cadmea.spring.rest.exceptions.NotFoundException;
import br.com.cadmea.spring.security.orm.UserAccess;
import br.com.cadmea.web.bo.PasswordResetTokenBo;
import br.com.cadmea.web.bo.UserBo;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Gilberto Santos
 * a camada de servico prove acesso as regras de negocio,
 * sendo uma layer para validar, nao permitindo chegar objetos invalidos ate o negocio
 */
@Service
public class UserSrv extends BaseMaintenanceSrvImpl<UserSystem, UserBo> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Inject
    private UserBo userBo;

    @Inject
    private PasswordResetTokenBo passwordResetTokenBo;

    @Inject
    private SmtpEmailSender smtpEmailSender;

    @Lazy
    @Autowired
    private HttpServletRequest request;

    @Inject
    private PasswordEncoder passwordEncoder;

    @Inject
    private CadmeaSystemSrv cadmeaSystemSrv;

    @Override
    protected UserBo getBo() {
        return userBo;
    }

    @Override
    public UserSystem insert(final @NotNull UserSystem entidade) {
        logger.info(" save userSystem entity ");
        final String hashPassword = passwordEncoder.encode(entidade.getPassword());
        entidade.setPassword(hashPassword);
        return getBo().insert(entidade);
    }

    /**
     * search for user in Cadmea System
     *
     * @param systemName
     * @param email
     * @return {@link UserAccess}
     */
    public UserAccess authentication(final @NotEmpty String systemName, final @NotEmpty String email) {
        logger.info("starting authentication of user ");
        final CadmeaSystem cadmeaSystem = cadmeaSystemSrv.findBy(systemName);

        if (cadmeaSystem == null) {
            throw new NotFoundException("system.not.found");
        }

        final Long sysId = cadmeaSystem.getId();
        UserSystem userSystem = getUserBy(email);

        if (userSystem == null) {
            throw new NotFoundException("user.not.found");
        }

        userSystem = getUserBy(email, sysId);

        if (userSystem == null) {
            throw new NotFoundException("user.not.allow.in.system");
        }

        final UserAccess userAccess = new UserAccess(userSystem.getPerson().getName());

        userSystem.getPermissions().forEach(a -> {
            userAccess.getRoles().add(a.getRole());
        });

        final Authentication auth = new UsernamePasswordAuthenticationToken(userAccess, null,
                userAccess.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(auth);

        return userAccess;
    }

    /**
     * @param email
     */
    public void resetPassword(final @NotEmpty String email) {
        logger.info("starting recoveryPassword service");

        final UserSystem user = getUserBy(email);
        if (user == null) {
            throw new NotFoundException("user");
        }

        final String token = UUID.randomUUID().toString();
        createPasswordResetTokenForUser(user, token);

        //TODO criar um builder para gerar essas mensagens
        String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        appUrl = appUrl + "/api/private/user/changePassword?id=" + user.getId() + "&token=" + token;

        final String to = user.getEmail();
        final String subject = "Recovery password";
        final Map<String, Object> msg = new HashMap<String, Object>();

        msg.put("url", appUrl);
        msg.put("nickname", user.getNickname());
        // descobrir o sistema a quem pertence
        msg.put("fromNickname", "Cadmea Framework");
        msg.put("message", "Please , don't reply this email");

        smtpEmailSender.send(to, subject, msg, request.getLocale());
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
        return getBo().findByNamedQuery("loginByUsernameAndSystem", params, Result.UNIQUE);
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
        return getBo().findByNamedQuery("loginByUsername", params, Result.UNIQUE);
    }

    /**
     * create a PasswordResetToken
     *
     * @param user
     * @param token
     */
    private void createPasswordResetTokenForUser(final @NotNull UserSystem user, final @NotEmpty String token) {
        logger.info("create a password reset token for user: " + user.getNickname());
        final PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken(token);
        passwordResetToken.setUser(user);
        passwordResetToken.setExpiryDate(new Date());
        passwordResetTokenBo.save(passwordResetToken);
    }

    /**
     * @param token
     * @return {@link PasswordResetToken}
     */
    public PasswordResetToken getPasswordResetToken(final @NotEmpty String token) {
        logger.info(" get Password Reset Token ");
        return passwordResetTokenBo.getByToken(token);
    }

    /**
     * change the password of user
     */
    public void changeUserPassword(final @NotEmpty String password) {
        final UserSystem user = (UserSystem) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        logger.info("starting change password for the user:" + user.getNickname());
        final String hashPassword = passwordEncoder.encode(password);
        user.setPassword(hashPassword);
        getBo().save(user);
    }

}
