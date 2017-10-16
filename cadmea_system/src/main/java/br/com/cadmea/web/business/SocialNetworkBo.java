/**
 * 
 */
package br.com.cadmea.web.business;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import br.com.cadmea.comuns.orm.enums.Result;
import br.com.cadmea.infra.negocio.BaseNegocial;
import br.com.cadmea.model.orm.SocialNetwork;

/**
 * @author Gilberto Santos
 *
 */
@Component
class SocialNetworkBo extends BaseNegocial<SocialNetwork> {

	@Inject
	private SocialNetworkDao dao;

	@Override
	protected SocialNetworkDao getDao() {
		return dao;
	}
	
	@Override
	public void save(SocialNetwork entity) {
		if(!isThere(entity))
			super.save(entity);
	}


	@Override
	public boolean isThere(SocialNetwork entidade) {
		return getDao().find("idNetwork", entidade.getIdNetwork(), Result.UNIQUE) != null;
	}

}
