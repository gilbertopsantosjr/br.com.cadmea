package br.com.cadmea.web.dao;

import br.com.cadmea.model.dao.DaoGenerico;
import br.com.cadmea.model.orm.CadmeaSystem;
import org.springframework.transaction.annotation.Transactional;

/**
 * Contract DAO
 *
 * @author Gilberto Santos
 */
@Transactional
public interface CadmeaSystemDao extends DaoGenerico<CadmeaSystem> {
}