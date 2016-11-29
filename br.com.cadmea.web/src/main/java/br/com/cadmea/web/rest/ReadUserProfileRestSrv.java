/**
 *
 */
package br.com.cadmea.web.rest;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.cadmea.model.orm.UserSystem;
import br.com.cadmea.spring.rest.GenericRestService;
import br.com.cadmea.spring.rest.ServicePath;
import br.com.cadmea.web.business.UserSrv;
import br.com.cadmea.web.dto.UserFormDto;

/**
 * @author Gilberto Santos
 *
 */
@RestController
@RequestMapping(path = ServicePath.PRIVATE_ROOT_PATH + "/user")
public class ReadUserProfileRestSrv
    extends GenericRestService<UserSystem, UserFormDto> {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Inject
  private UserSrv userSrv;

  private UserFormDto userDTo;

  @Override
  @PostConstruct
  public void init() {
    userDTo = new UserFormDto();
  }

  @RequestMapping(value = "/read/{id}", method = RequestMethod.GET)
  public ResponseEntity<UserFormDto> readUserProfile(
      @PathVariable("id") String id) {
    logger.info("starting readUserProfile service");
    UserFormDto found = new UserFormDto();
    UserSystem user = getService().find(Long.valueOf(id));
    found.setEntity(user);
    return new ResponseEntity<UserFormDto>(found, HttpStatus.OK);
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
