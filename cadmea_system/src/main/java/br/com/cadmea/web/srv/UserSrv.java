/**
 *
 */
package br.com.cadmea.web.srv;

import br.com.cadmea.comuns.dto.Request;
import br.com.cadmea.comuns.dto.Response;
import br.com.cadmea.comuns.orm.enums.Situation;
import br.com.cadmea.comuns.util.DateUtil;
import br.com.cadmea.dto.usersystem.UserSystemMessages;
import br.com.cadmea.dto.usersystem.UserSystemRequest;
import br.com.cadmea.dto.usersystem.UserSystemResponse;
import br.com.cadmea.infra.negocio.BaseServiceSrvImpl;
import br.com.cadmea.model.orm.CadmeaSystem;
import br.com.cadmea.model.orm.UserSystem;
import br.com.cadmea.spring.beans.SmtpEmailSender;
import br.com.cadmea.spring.pojos.UserAccess;
import br.com.cadmea.web.bo.UserBo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static br.com.cadmea.comuns.validator.Validator.throwIfFail;

/**
 * @author Gilberto Santos
 * a camada de servico prove acesso as regras de negocio,
 * sendo uma layer para validar, nao permitindo chegar objetos invalidos ate o negocio
 */
@Service
public class UserSrv extends BaseServiceSrvImpl<UserSystem> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Inject
    private UserBo userBo;

    @Inject
    private PasswordResetTokenSrv passwordResetTokenSrv;

    @Inject
    private SmtpEmailSender smtpEmailSender;

    @Inject
    private PasswordEncoder passwordEncoder;

    @Inject
    private CadmeaSystemSrv cadmeaSystemSrv;

    @Value("${spring.profiles.active}")
    private String profile;

    //Url to the rest server like http://localhost:8080/cadmea/user/changePassword
    @Value("${cadmea.url.system.changePassword}")
    private String cadmeaUrlSystemChangePassword;

    @Override
    protected UserBo getBo() {
        return userBo;
    }

    /**
     * * the service layer defines the values defined by business logic
     *
     * @param struct
     * @param <R>
     * @return
     */
    @Override
    public <R extends Request<UserSystem>> Response<UserSystem> insert(final R struct) {
        final UserSystemRequest request = ((UserSystemRequest) struct);

        request.validate();

        final String hashPassword = passwordEncoder.encode(request.getPassword());
        final CadmeaSystem cadmeaSystem = cadmeaSystemSrv.findBySystemName(request.getSystemName());

        throwIfFail(cadmeaSystem == null, UserSystemMessages.USER_SRV_NOT_FOUND);

        /**
         * In case the userSystem is already in the Cadmea System, then fail
         */
        throwIfFail(getBo().findBy(request.getEmail(), cadmeaSystem.getId()) != null, UserSystemMessages.USER_SRV_FOUND);

        /**
         * In case the nickname is already in use, then fail
         */
        throwIfFail(getBo().findByNickName(request.getNickname()) != null, UserSystemMessages.USER_SYSTEM_REQUEST_NICKNAME_DUPLICATED);

        final UserSystem toInsert = struct.getEntity();

        toInsert.setPassword(hashPassword);
        toInsert.setSystems(Arrays.asList(cadmeaSystem));
        toInsert.setDateRegister(DateUtil.getDate());
        toInsert.setSituation(Situation.DISABLE);
        toInsert.setLastVisit(DateUtil.getDate());

        final UserSystem inserted = getBo().insert(toInsert);

        throwIfFail(inserted == null, UserSystemMessages.USER_SRV_NOT_ALLOW_IN_SYSTEM);

        final UserSystemResponse response = new UserSystemResponse();
        response.setEntity(toInsert);

        return response;
    }

    /**
     * search for user in Cadmea System
     *
     * @param struct
     * @return {@link UserAccess}
     */
    public UserAccess authentication(final @NotNull UserSystemRequest struct) {
        logger.info("starting authentication of user ");

        struct.validate();

        final CadmeaSystem cadmeaSystem = cadmeaSystemSrv.findBy(struct.getSystemName());
        throwIfFail(cadmeaSystem == null, UserSystemMessages.USER_SRV_NOT_FOUND);

        final UserSystem userSystem = getBo().findBy(struct.getEmail(), cadmeaSystem.getId());
        throwIfFail(userSystem == null, UserSystemMessages.USER_SRV_NOT_ALLOW_IN_SYSTEM);

        //TODO adding custom SQL to get a person' name without anything else
        final UserAccess userAccess = new UserAccess(userSystem.getPerson().getName());
        userAccess.setRoles(userSystem.getRoles());
    
        final Authentication auth = new UsernamePasswordAuthenticationToken(userAccess, null,
                userAccess.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(auth);

        userSystem.setLastVisit(DateUtil.getDate());
        getBo().save(userSystem);

        return userAccess;
    }


    public void resetPassword(final @NotNull UserSystemRequest struct) {
        logger.info("starting recoveryPassword service for:" + struct.getEmail());

        final UserSystem user = getBo().findBy(struct.getEmail());
        throwIfFail(user == null, UserSystemMessages.USER_SRV_FOUND);

        final String token = UUID.randomUUID().toString();
        passwordResetTokenSrv.createPasswordResetTokenForUser(user, token);

        //TODO criar um builder para gerar essas mensagens
        final String appUrl = cadmeaUrlSystemChangePassword + user.getId() + "&token=" + token;

        final String to = user.getEmail();
        final String subject = "Recovery password";
        final Map<String, Object> msg = new HashMap<String, Object>();

        msg.put("url", appUrl);
        msg.put("nickname", user.getNickname());
        // descobrir o sistema a quem pertence
        msg.put("fromNickname", "Cadmea Framework");
        msg.put("message", "Please , don't reply this email");

        smtpEmailSender.send(to, subject, msg);
    }


    /**
     * change the password of user
     */
    public void changeUserPassword(final @NotNull UserSystemRequest struct) {
        final UserSystem user = (UserSystem) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        logger.info("starting change password for the user:" + user.getNickname());
        final String hashPassword = passwordEncoder.encode(struct.getPassword());
        user.setPassword(hashPassword);
        getBo().save(user);
    }


}
