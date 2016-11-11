package br.com.cadmea.web.ctrl;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cadmea.spring.rest.GenericRestService;
import br.com.cadmea.spring.rest.ServicePath;
import br.com.cadmea.web.business.UserSrv;
import br.com.cadmea.web.dto.UserDTo;
import br.com.cadmea.web.model.User;

/**
 * @author Gilberto Santos
 *
 */
@RestController
@RequestMapping(path = ServicePath.PUBLIC_ROOT_PATH + "/user")
public class UserRestSrv extends GenericRestService<User, UserDTo> {

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Inject
  private UserSrv userSrv;

  private UserDTo userDTo;

  @Override
  protected void beforeLoadClass() {
    userDTo = new UserDTo();
  }

  @Override
  protected void beforeSave() {
    final String hashPassword = passwordEncoder
        .encode(getViewForm().getEntity().getPassword());
    getViewForm().getEntity().setPassword(hashPassword);
  }

  @Override
  public UserSrv getService() {
    return userSrv;
  }

  @Override
  public UserDTo getViewForm() {
    return userDTo;
  }

}