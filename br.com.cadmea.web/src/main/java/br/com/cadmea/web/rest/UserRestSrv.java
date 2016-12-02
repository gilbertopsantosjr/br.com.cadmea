package br.com.cadmea.web.rest;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.cadmea.model.orm.PasswordResetToken;
import br.com.cadmea.model.orm.UserSystem;
import br.com.cadmea.spring.rest.GenericRestService;
import br.com.cadmea.spring.rest.ServicePath;
import br.com.cadmea.spring.rest.exceptions.NotFoundException;
import br.com.cadmea.spring.security.orm.UserAccess;
import br.com.cadmea.spring.util.GenericResponse;
import br.com.cadmea.spring.util.SmtpEmailSender;
import br.com.cadmea.web.business.UserSrv;
import br.com.cadmea.web.dto.UserFormDto;

/**
 * @author Gilberto Santos
 *
 */
@RestController
@RequestMapping(path = ServicePath.PUBLIC_ROOT_PATH + "/user")
public class UserRestSrv extends GenericRestService<UserSystem, UserFormDto> {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Inject
  private UserSrv userSrv;

  @Inject
  private SmtpEmailSender smtpEmailSender;

  private UserFormDto userDTo;

  private BCryptPasswordEncoder passwordEncoder;

  @Override
  protected void beforeLoadClass() {
    userDTo = new UserFormDto();
    passwordEncoder = new BCryptPasswordEncoder();
  }

  @Override
  protected void beforeSave() {
    final String hashPassword = passwordEncoder
        .encode(getViewForm().getEntity().getPassword());
    getViewForm().getEntity().setPassword(hashPassword);
  }

  @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
  public GenericResponse recoveryPassword(HttpServletRequest request,
      @RequestParam("email") String userEmail) {
    logger.info("starting recoveryPassword service");

    UserSystem user = userSrv.getUserBy(userEmail);
    if (user == null) {
      throw new NotFoundException("user");
    }

    String token = UUID.randomUUID().toString();
    userSrv.createPasswordResetTokenForUser(user, token);

    String appUrl = "http://" + request.getServerName() + ":"
        + request.getServerPort() + request.getContextPath();

    final String to = user.getEmail();
    final String subject = "Recovery password";
    final Map<String, Object> msg = new HashMap<String, Object>();

    appUrl = appUrl + "/api/private/user/changePassword?id=" + user.getId()
        + "&token=" + token;

    msg.put("url", appUrl);
    msg.put("nickname", user.getNickname());
    msg.put("fromNickname", "Cadmea Framework");
    msg.put("message", "Please , don't reply this email");

    smtpEmailSender.send(to, subject, msg, request.getLocale());

    return new GenericResponse("recoveryPassword", "");
  }

  /**
   * change to the new password
   *
   * @param password
   * @return
   */
  @RequestMapping(value = "/savePassword", method = RequestMethod.POST)
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public GenericResponse savePassword(
      @RequestParam("password") String password) {
    UserSystem user = (UserSystem) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();
    final String hashPassword = passwordEncoder.encode(password);
    userSrv.changeUserPassword(user, hashPassword);
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
  @RequestMapping(value = "/showChangePassword", method = RequestMethod.GET)
  public GenericResponse showChangePasswordPage(HttpServletRequest request,
      @RequestParam("id") long id, @RequestParam("token") String token) {
    Locale locale = request.getLocale();
    PasswordResetToken passToken = userSrv.getPasswordResetToken(token);
    UserSystem user = passToken.getUser();

    GenericResponse response = new GenericResponse("Ok");
    response.setLocale(locale);

    if (passToken == null || user.getId() != id) {
      response = new GenericResponse("message.resetPasswordSuc",
          "auth.message.invalidToken");
    }

    Calendar cal = Calendar.getInstance();
    if ((passToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
      response = new GenericResponse("message.resetPasswordSuc",
          "auth.message.expired");
    }

    UserAccess userAccess = new UserAccess(user.getPerson().getName());
    user.getPermissions().forEach(a -> {
      userAccess.getRoles().add(a.getRole());
    });

    Authentication auth = new UsernamePasswordAuthenticationToken(userAccess,
        null, userAccess.getAuthorities());
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