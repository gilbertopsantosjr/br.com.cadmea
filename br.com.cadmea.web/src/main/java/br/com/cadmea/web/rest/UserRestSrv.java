package br.com.cadmea.web.rest;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.cadmea.model.orm.UserSystem;
import br.com.cadmea.spring.rest.GenericRestService;
import br.com.cadmea.spring.rest.ServicePath;
import br.com.cadmea.spring.rest.exceptions.PreconditionRequiredException;
import br.com.cadmea.spring.rest.exceptions.RestException;
import br.com.cadmea.spring.security.orm.UserAccess;
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

  private UserFormDto userDTo;

  @Override
  protected void beforeLoadClass() {
    userDTo = new UserFormDto();
  }

  @Override
  protected void beforeSave() {
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    final String hashPassword = passwordEncoder
        .encode(getViewForm().getEntity().getPassword());
    getViewForm().getEntity().setPassword(hashPassword);
  }

  @RequestMapping(path = "/api/private/user/read/{id}",
      method = RequestMethod.GET)
  public ResponseEntity<UserFormDto> readUserProfile(
      @PathVariable String identificador) {
    logger.info("starting readUserProfile service");
    UserFormDto found = new UserFormDto();
    ResponseEntity<UserSystem> user = super.load(identificador);
    found.setEntity(user.getBody());
    return new ResponseEntity<UserFormDto>(found, HttpStatus.OK);
  }

  @RequestMapping(value = "/authentication/", method = RequestMethod.POST)
  public ResponseEntity<UserFormDto> logIn(@RequestBody UserFormDto formDto) {
    logger.info("starting logIn service");

    isValidRequest(formDto);

    UserFormDto found = new UserFormDto();
    try {
      final String username = formDto.getEntity().getEmail();
      final UserSystem user = getService().getUserBy(username);
      found.setEntity(user);

      UserAccess userAccess = new UserAccess(user.getPerson().getName());
      user.getPermissions().forEach(a -> {
        userAccess.getRoles().add(a.getRole());
      });

      Authentication auth = new UsernamePasswordAuthenticationToken(userAccess,
          null, userAccess.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(auth);

    } catch (Exception e) {
      throw new RestException(e);
    }

    return new ResponseEntity<UserFormDto>(found, HttpStatus.OK);
  }

  private void isValidRequest(UserFormDto formDto) {
    if (!isValid(formDto) && !isValid(formDto.getEntity()))
      throw new PreconditionRequiredException("Invalid Object !");

    if (!isValid(formDto.getEntity().getEmail())
        && !isValid(formDto.getEntity().getEmail()))
      throw new PreconditionRequiredException("Username is required !");

    if (!isValid(formDto.getEntity().getPassword())
        && !isValid(formDto.getEntity().getPassword()))
      throw new PreconditionRequiredException("Password is required !");
  }

  static boolean isValid(final String str) {
    return str != null && !str.isEmpty();
  }

  static boolean isValid(final Object obj) {
    return obj != null;
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