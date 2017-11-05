package br.com.cadmea.web.srv;

import br.com.cadmea.dto.PasswordResetTokenStruct;
import br.com.cadmea.infra.negocio.BaseServiceSrvImpl;
import br.com.cadmea.model.orm.PasswordResetToken;
import br.com.cadmea.model.orm.UserSystem;
import br.com.cadmea.web.bo.PasswordResetTokenBo;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Service
public class PasswordResetTokenSrv extends BaseServiceSrvImpl<PasswordResetTokenStruct> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Inject
    private PasswordResetTokenBo passwordResetTokenBo;

    /**
     * create a PasswordResetToken
     *
     * @param user
     * @param token
     */
    public void createPasswordResetTokenForUser(final @NotNull UserSystem user, final @NotEmpty String token) {
        logger.info("create a password reset token for user: " + user.getNickname());
        final PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken(token);
        passwordResetToken.setUser(user);
        passwordResetToken.setExpiryDate(new Date());
        getBo().save(passwordResetToken);
    }

    /**
     * @param token
     * @return {@link PasswordResetToken}
     */
    public PasswordResetToken getPasswordResetToken(final @NotEmpty String token) {
        logger.info(" get Password Reset Token ");
        return getBo().getByToken(token);
    }

    @Override
    protected PasswordResetTokenBo getBo() {
        return passwordResetTokenBo;
    }
}
