package br.com.cadmea.web.rest;

import br.com.cadmea.comuns.util.ValidadorUtil;
import br.com.cadmea.dto.UserFormDto;
import br.com.cadmea.model.orm.PasswordResetToken;
import br.com.cadmea.model.orm.SocialNetwork;
import br.com.cadmea.model.orm.UserSystem;
import br.com.cadmea.spring.rest.GenericRestService;
import br.com.cadmea.spring.rest.ServicePath;
import br.com.cadmea.spring.rest.exceptions.PreConditionRequiredException;
import br.com.cadmea.spring.security.orm.UserAccess;
import br.com.cadmea.spring.util.GenericResponse;
import br.com.cadmea.web.business.UserSrv;
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
import java.util.Calendar;
import java.util.HashSet;
import java.util.Locale;

/**
 * @author Gilberto Santos
 */
@RestController
@RequestMapping(path = ServicePath.PUBLIC_ROOT_PATH + "/user")
public class UserRestSrv extends GenericRestService<UserSystem, UserFormDto> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Inject
    private UserSrv userSrv;

    private UserFormDto userDTo;

    @Autowired
    private HttpServletRequest servletRequest;

    @Override
    protected void beforeLoadClass() {
        userDTo = new UserFormDto();
    }

    @Override
    protected void beforeSave() {
        if (servletRequest.getAttribute("socialNetwork") != null) {
            final SocialNetwork socialNetwork = (SocialNetwork) servletRequest.getAttribute("socialNetwork");
            if (socialNetwork != null) {
                getViewForm().getEntity().setSocialNetworks(new HashSet<>());
                getViewForm().getEntity().getSocialNetworks().add(socialNetwork);
            }
        }
    }

    /**
     * @param formDto
     * @return
     */
    @PostMapping(path = "/authentication/")
    public ResponseEntity<UserAccess> logIn(@RequestBody final UserFormDto formDto) {
        logger.info("starting logIn service");

        isValidRequest(formDto);

        final String systemName = formDto.getSystemName();
        final String email = formDto.getEntity().getEmail();

        final UserAccess found = userSrv.authentication(systemName, email);

        return new ResponseEntity<UserAccess>(found, HttpStatus.OK);
    }

    /**
     * check if the {@link UserFormDto} is a valid on request
     *
     * @param formDto
     */
    private void isValidRequest(final UserFormDto formDto) {
        if (!ValidadorUtil.isValid(formDto) || !ValidadorUtil.isValid(formDto.getEntity())) {
            throw new PreConditionRequiredException("Invalid Object !");
        }

        if (!ValidadorUtil.isValid(formDto.getSystemName())) {
            throw new PreConditionRequiredException("System name is required !");
        }

        if (!ValidadorUtil.isValid(formDto.getEntity().getEmail())) {
            throw new PreConditionRequiredException("Username is required !");
        } else if (!ValidadorUtil.isValidEmail(formDto.getEntity().getEmail())) {
            throw new PreConditionRequiredException("Email must be valid !");
        }

        if (!ValidadorUtil.isValid(formDto.getEntity().getPassword())) {
            throw new PreConditionRequiredException("Password is required !");
        }
    }

    /**
     * @param request
     * @param userEmail
     * @return
     */
    @PostMapping(path = "/resetPassword")
    public GenericResponse recoveryPassword(final HttpServletRequest request, @RequestParam("email") final String userEmail) {
        userSrv.resetPassword(userEmail);
        return new GenericResponse("recoveryPassword", "");
    }

    /**
     * change to the new password
     *
     * @param password
     * @return
     */
    @PostMapping(path = "/savePassword")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public GenericResponse savePassword(@RequestParam("password") final String password) {
        userSrv.changeUserPassword(password);
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
        final PasswordResetToken passToken = userSrv.getPasswordResetToken(token);
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

    @Override
    public UserFormDto getViewForm() {
        return userDTo;
    }

}