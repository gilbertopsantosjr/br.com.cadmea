/**
 * 
 */
package br.com.cadmea.web.business;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.cadmea.baseservico.BaseMaintenanceSrvImpl;
import br.com.cadmea.comuns.orm.enums.Result;
import br.com.cadmea.comuns.srv.BaseServico;
import br.com.cadmea.model.orm.SocialNetwork;

/**
 * @author Gilberto Santos
 *
 */
@Service
public class SocialNetworkSrv extends BaseMaintenanceSrvImpl<SocialNetwork, SocialNetworkBo> implements BaseServico<SocialNetwork> {

	@Inject
	private SocialNetworkBo bo;
	
	@Override
	protected SocialNetworkBo getBo() {
		return bo;
	}

	/**
	 * verify is already exist a {@link SocialNetwork} for reference user
	 * if not creates one
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void save(SocialNetwork socialNetwork){
		bo.save(socialNetwork);
	}
	
	/**
	 * 
	 * @param id
	 * @return {@link SocialNetwork}
	 */
	@Transactional(readOnly = true)
	public SocialNetwork findRegistered(String id){
		return bo.find("idNetwork", id, Result.UNIQUE);
	}

}
