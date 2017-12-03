/**
 *
 */
package br.com.cadmea.web.bo;

import br.com.cadmea.comuns.orm.enums.Result;
import br.com.cadmea.infra.negocio.BaseNegocial;
import br.com.cadmea.model.orm.SocialNetwork;
import br.com.cadmea.web.dao.SocialNetworkDao;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * @author Gilberto Santos
 */
@Component
public class SocialNetworkBo extends BaseNegocial<SocialNetwork> {

    @Inject
    private SocialNetworkDao dao;

    @Override
    public SocialNetworkDao getDao() {
        return dao;
    }


    @Override
    public boolean isThere(final SocialNetwork entidade) {
        return getDao().find("idNetwork", entidade.getIdNetwork(), Result.UNIQUE) != null;
    }

}
