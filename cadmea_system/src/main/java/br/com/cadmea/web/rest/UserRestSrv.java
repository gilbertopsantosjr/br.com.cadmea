package br.com.cadmea.web.rest;

import br.com.cadmea.dto.UserAuthenticationStc;
import br.com.cadmea.dto.UserCreateStc;
import br.com.cadmea.model.orm.PasswordResetToken;
import br.com.cadmea.model.orm.SocialNetwork;
import br.com.cadmea.model.orm.UserSystem;
import br.com.cadmea.spring.pojos.UserAccess;
import br.com.cadmea.spring.rest.GenericRestService;
import br.com.cadmea.spring.rest.ServicePath;
import br.com.cadmea.spring.util.GenericResponse;
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
public class UserRestSrv extends GenericRestService<UserCreateStc> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Inject
    private UserSrv userSrv;

    private PasswordResetTokenSrv passwordResetTokenSrv;

    private UserCreateStc userDTo;

    @Autowired
    private HttpServletRequest servletRequest;

    @Override
    protected void beforeLoadClass() {
        userDTo = new UserCreateStc();
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
    public ResponseEntity<UserAccess> logIn(@RequestBody final UserAuthenticationStc struct) {
        logger.info("starting logIn service");
        final UserAccess found = userSrv.authentication(struct);
        return new ResponseEntity<UserAccess>(found, HttpStatus.OK);
    }


    /**
     * {Json Response Model}
     *
     * @return
     */
    @PostMapping(path = "/resetPassword")
    public GenericResponse recoveryPassword(final @NotNull UserCreateStc struct) {
        userSrv.resetPassword(struct);
        return new GenericResponse("recoveryPassword", "");
    }

    /**
     * change to the new password
     *
     * @param struct
     * @return
     */
    @PostMapping(path = "/savePassword")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public GenericResponse savePassword(final @NotNull UserCreateStc struct) {
        userSrv.changeUserPassword(struct);
        return new GenericResponse("message.resetPasswordSuc", "");
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
    public GenericResponse showChangePasswordPage(final HttpServletRequest request, @RequestParam("id") final long id,
                                                  @RequestParam("token") final String token) {
        final Locale locale = request.getLocale();
        final PasswordResetToken passToken = passwordResetTokenSrv.getPasswordResetToken(token);
        final UserSystem user = passToken.getUser();

        GenericResponse response = new GenericResponse("Ok");
        response.setLocale(locale);

        if (passToken == null || user.getId() != id) {
            response = new GenericResponse("message.resetPasswordSuc", "auth.message.invalidToken");
        }

        final Calendar cal = Calendar.getInstance();
        if ((passToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            response = new GenericResponse("message.resetPasswordSuc", "auth.message.expired");
        }

        final UserAccess userAccess = new UserAccess(user.getPerson().getName());
        user.getPermissions().forEach(a -> {
            userAccess.getRoles().add(a.getRole());
        });

        final Authentication auth = new UsernamePasswordAuthenticationToken(userAccess, null, userAccess.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        return response;
    }

    @Override
    public UserSrv getService() {
        return userSrv;
    }


}