/**
 *
 */
package br.com.cadmea.web.srv;

import br.com.cadmea.baseservico.BaseMaintenanceSrvImpl;
import br.com.cadmea.comuns.orm.enums.Result;
import br.com.cadmea.comuns.srv.BaseServico;
import br.com.cadmea.model.orm.SocialNetwork;
import br.com.cadmea.web.bo.SocialNetworkBo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * @author Gilberto Santos
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
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void save(final SocialNetwork socialNetwork) {
        bo.save(socialNetwork);
    }

    /**
     * @param id
     * @return {@link SocialNetwork}
     */
    @Transactional(readOnly = true)
    public SocialNetwork findRegistered(final String id) {
        return bo.find("idNetwork", id, Result.UNIQUE);
    }

}
