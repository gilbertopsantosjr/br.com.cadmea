/**
 * 
 */
package br.com.cadmea.web.business;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import br.com.cadmea.infra.negocio.BaseNegocial;
import br.com.cadmea.model.dao.DaoGenerico;
import br.com.cadmea.model.orm.CadmeaSystem;

/**
 * @author Gilberto Santos
 */
@Component
class CadmeaSystemBo extends BaseNegocial<CadmeaSystem> {

	@Inject
	private CadmeaSystemDao dao;
	
	
	@Override
	protected DaoGenerico<CadmeaSystem> getDao() {
		return dao;
	}

}
