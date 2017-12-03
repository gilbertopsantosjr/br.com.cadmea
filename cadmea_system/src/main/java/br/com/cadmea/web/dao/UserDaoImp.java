package br.com.cadmea.web.dao;

import br.com.cadmea.model.dao.DaoGenericoImp;
import br.com.cadmea.model.orm.UserSystem;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

/**
 * @author Gilberto Santos
 */
@Repository
public class UserDaoImp extends DaoGenericoImp<UserSystem> implements UserDao {

    @Inject
    private RoleDao permissionDao;


}
