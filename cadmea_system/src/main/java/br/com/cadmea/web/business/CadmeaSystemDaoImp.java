/**
 *
 */
package br.com.cadmea.web.business;

import br.com.cadmea.model.dao.DaoGenericoImp;
import br.com.cadmea.model.orm.CadmeaSystem;
import org.springframework.stereotype.Repository;

/**
 * @author Gilberto Santos
 */
@Repository
class CadmeaSystemDaoImp extends DaoGenericoImp<CadmeaSystem, Long> implements CadmeaSystemDao {

}
