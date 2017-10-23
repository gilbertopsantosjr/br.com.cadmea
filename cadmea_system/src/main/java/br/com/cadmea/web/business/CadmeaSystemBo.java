/**
 *
 */
package br.com.cadmea.web.business;

import br.com.cadmea.infra.negocio.BaseNegocial;
import br.com.cadmea.model.orm.CadmeaSystem;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * @author Gilberto Santos
 */
@Component
class CadmeaSystemBo extends BaseNegocial<CadmeaSystem> {

    @Inject
    private CadmeaSystemDao dao;


    @Override
    protected CadmeaSystemDao getDao() {
        return dao;
    }

}
