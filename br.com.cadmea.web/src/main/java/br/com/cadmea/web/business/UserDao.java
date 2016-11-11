package br.com.cadmea.web.business;

import org.springframework.stereotype.Repository;

import br.com.cadmea.model.dao.DaoGenericoImp;
import br.com.cadmea.web.model.User;

/**
 * @author Gilberto Santos
 */
@Repository
class UserDao extends DaoGenericoImp<User, Long> {

}
