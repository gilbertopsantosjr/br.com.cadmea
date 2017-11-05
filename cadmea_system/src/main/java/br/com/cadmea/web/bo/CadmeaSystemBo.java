/**
 *
 */
package br.com.cadmea.web.bo;

import br.com.cadmea.infra.negocio.BaseNegocial;
import br.com.cadmea.model.orm.CadmeaSystem;
import br.com.cadmea.web.dao.CadmeaSystemDao;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * @author Gilberto Santos
 */
@Component
public class CadmeaSystemBo extends BaseNegocial<CadmeaSystem> {

    @Inject
    private CadmeaSystemDao dao;


    @Override
    public CadmeaSystemDao getDao() {
        return dao;
    }

}
