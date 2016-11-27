package br.com.cadmea.web.business;

import org.springframework.stereotype.Repository;

import br.com.cadmea.model.dao.DaoGenericoImp;
import br.com.cadmea.model.orm.UserSystem;

/**
 * @author Gilberto Santos
 */
@Repository
class UserDao extends DaoGenericoImp<UserSystem, Long> {

}
