/**
 *
 */
package br.com.cadmea.web.rest;

import br.com.cadmea.dto.user.UserSystemRequest;
import br.com.cadmea.dto.user.UserSystemResponse;
import br.com.cadmea.spring.rest.GenericRestService;
import br.com.cadmea.spring.rest.ServicePath;
import br.com.cadmea.web.srv.UserSrv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

/**
 * @author Gilberto Santos
 */
@RestController
@RequestMapping(path = ServicePath.PRIVATE_ROOT_PATH + "/user")
public class ReadUserProfileSrv extends GenericRestService<UserSystemRequest> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Inject
    private UserSrv userSrv;

    /**
     * @param id
     * @return
     */
    @RequestMapping(value = "/read/{id}", method = RequestMethod.GET)
    public ResponseEntity<UserSystemResponse> readUserProfile(@PathVariable("id") final String id) {
        logger.info("starting readUserProfile service");
        final UserSystemResponse found = (UserSystemResponse) getService().find(Long.valueOf(id));
        return new ResponseEntity<UserSystemResponse>(found, HttpStatus.OK);
    }


    @Override
    public UserSrv getService() {
        return userSrv;
    }


}
