/**
 *
 */
package br.com.cadmea.web.rest;

import br.com.cadmea.dto.UserCreateStc;
import br.com.cadmea.model.orm.UserSystem;
import br.com.cadmea.spring.rest.GenericRestService;
import br.com.cadmea.spring.rest.ServicePath;
import br.com.cadmea.web.PermissionBean;
import br.com.cadmea.web.UserBean;
import br.com.cadmea.web.srv.UserSrv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.HashSet;

/**
 * @author Gilberto Santos
 */
@RestController
@RequestMapping(path = ServicePath.PRIVATE_ROOT_PATH + "/user")
public class ReadUserProfileSrv extends GenericRestService<UserCreateStc> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Inject
    private UserSrv userSrv;

    private UserCreateStc userDTo;

    @Override
    @PostConstruct
    public void init() {
        userDTo = new UserCreateStc();
    }

    /**
     * @param id
     * @return
     */
    @RequestMapping(value = "/read/{id}", method = RequestMethod.GET)
    public ResponseEntity<UserCreateStc> readUserProfile(@PathVariable("id") final String id) {
        logger.info("starting readUserProfile service");
        final UserSystem entity = getService().find(Long.valueOf(id));
        if (entity != null) {
            final UserCreateStc found = new UserCreateStc();
            //found.setEntity(entity);
            return new ResponseEntity<UserCreateStc>(found, HttpStatus.OK);
        }
        return new ResponseEntity<UserCreateStc>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Get UserSystem data from database and turns into UserBean
     *
     * @param user
     * @return {@link UserBean}
     */
    private UserBean getUserBeanFactory(final UserSystem user) {
        final UserBean entity = new UserBean();
        entity.setNickname(user.getNickname());
        entity.setId(user.getId());
        entity.setEmail(user.getEmail());
        entity.setSituation(user.getSituation());
        //entity.setDateExpire(user.getDateExpire());

        entity.setPermissions(new HashSet<>());

        user.getPermissions().forEach(a -> {
            final PermissionBean pb = new PermissionBean();
            pb.setId(a.getId());
            pb.setRole(a.getRole());
            entity.getPermissions().add(pb);
        });
        return entity;
    }


    @Override
    public UserSrv getService() {
        return userSrv;
    }


}
