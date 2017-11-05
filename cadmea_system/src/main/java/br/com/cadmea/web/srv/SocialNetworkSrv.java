/**
 *
 */
package br.com.cadmea.web.srv;

import br.com.cadmea.comuns.orm.enums.Result;
import br.com.cadmea.dto.SocialNetworkStruct;
import br.com.cadmea.infra.negocio.BaseServiceSrvImpl;
import br.com.cadmea.model.orm.SocialNetwork;
import br.com.cadmea.web.bo.SocialNetworkBo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * @author Gilberto Santos
 */
@Service
public class SocialNetworkSrv extends BaseServiceSrvImpl<SocialNetworkStruct> {

    @Inject
    private SocialNetworkBo bo;

    @Override
    protected SocialNetworkBo getBo() {
        return bo;
    }

    /**
     * @param id
     * @return {@link SocialNetwork}
     */
    @Transactional(readOnly = true)
    public SocialNetwork findRegistered(final String id) {
        return getBo().find("idNetwork", id, Result.UNIQUE);
    }

}
