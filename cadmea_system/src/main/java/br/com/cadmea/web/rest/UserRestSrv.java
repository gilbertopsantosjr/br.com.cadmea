package br.com.cadmea.web.rest;

import br.com.cadmea.comuns.validator.Validator;
import br.com.cadmea.dto.usersystem.UserSystemRequest;
import br.com.cadmea.dto.usersystem.UserSystemResponse;
import br.com.cadmea.model.orm.PasswordResetToken;
import br.com.cadmea.model.orm.SocialNetwork;
import br.com.cadmea.model.orm.UserSystem;
import br.com.cadmea.spring.pojos.UserAccess;
import br.com.cadmea.spring.rest.GenericRestService;
import br.com.cadmea.spring.rest.ServicePath;
import br.com.cadmea.web.srv.PasswordResetTokenSrv;
import br.com.cadmea.web.srv.UserSrv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

/**
 * @author Gilberto Santos
 */
@RestController
@RequestMapping(path = ServicePath.PUBLIC_ROOT_PATH + "/user")
public class UserRestSrv extends GenericRestService<UserSystemRequest> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Inject
    private UserSrv userSrv;

    private PasswordResetTokenSrv passwordResetTokenSrv;

    private UserSystemRequest userDTo;

    @Autowired
    private HttpServletRequest servletRequest;

    @Override
    protected void beforeLoadClass() {
        userDTo = new UserSystemRequest();
    }

    @Override
    protected void beforeSave() {
        if (servletRequest.getAttribute("socialNetwork") != null) {
            final SocialNetwork socialNetwork = (SocialNetwork) servletRequest.getAttribute("socialNetwork");
            if (socialNetwork != null) {
                userDTo.getEntity().setSocialNetworks(Arrays.asList(socialNetwork));
            }
        }
    }

    /**
     * @param struct
     * @return
     */
    @PostMapping(path = "/authentication/")
    public ResponseEntity<UserSystemResponse> logIn(@RequestBody final UserSystemRequest struct) {
        logger.info("starting logIn service");
        final UserAccess found = userSrv.authentication(struct);
        final UserSystemResponse response = new UserSystemResponse();
        response.setEntity(found);
        return new ResponseEntity<UserSystemResponse>(response, HttpStatus.OK);
    }


    /**
     * {Json Response Model}
     *
     * @return
     */
    @PostMapping(path = "/resetPassword")
    public ResponseEntity<UserSystemResponse> recoveryPassword(final @NotNull UserSystemRequest struct) {
        userSrv.resetPassword(struct);
        final UserSystemResponse response = new UserSystemResponse();
        response.setMessage("found");
        return new ResponseEntity<UserSystemResponse>(response, HttpStatus.OK);
    }

    /**
     * change to the new password
     *
     * @param struct
     * @return
     */
    @PostMapping(path = "/savePassword")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserSystemResponse> savePassword(final @NotNull UserSystemRequest struct) {
        userSrv.changeUserPassword(struct);
        final UserSystemResponse response = new UserSystemResponse();
        response.setMessage("message.resetPasswordSuc");
        return new ResponseEntity<UserSystemResponse>(response, HttpStatus.OK);
    }

    /**
     * Show the form to allow the user change his password
     *
     * @param request
     * @param id
     * @param token
     * @return arguments to allow client build the form
     */
    @GetMapping(value = "/showChangePassword")
    public ResponseEntity<UserSystemResponse> showChangePasswordPage(final HttpServletRequest request, @RequestParam("id") final long id,
                                                                     @RequestParam("token") final String token) {
        final Locale locale = request.getLocale();
        final PasswordResetToken passToken = passwordResetTokenSrv.getPasswordResetToken(token);
        final UserSystem user = passToken.getUser();
        final Calendar cal = Calendar.getInstance();

        final UserSystemResponse response = new UserSystemResponse();
        response.setMessage("message.resetPasswordSuc");
        response.setLocale(locale.getLanguage());

        Validator.throwIfFail(passToken == null || user.getId() != id, "auth.message.invalidToken");
        Validator.throwIfFail((passToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0, "auth.message.expired");

        final UserAccess userAccess = new UserAccess(user.getPerson().getName());
        userAccess.setRoles(user.getRoles());

        final Authentication auth = new UsernamePasswordAuthenticationToken(userAccess, null, userAccess.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        return new ResponseEntity<UserSystemResponse>(response, HttpStatus.OK);
    }

    @Override
    public UserSrv getService() {
        return userSrv;
    }


}