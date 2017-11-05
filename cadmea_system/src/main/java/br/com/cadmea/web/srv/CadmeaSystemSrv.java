/**
 *
 */
package br.com.cadmea.web.srv;

import br.com.cadmea.comuns.orm.enums.Result;
import br.com.cadmea.comuns.validator.Validator;
import br.com.cadmea.dto.CadmeaSystemStruct;
import br.com.cadmea.infra.negocio.BaseServiceSrvImpl;
import br.com.cadmea.model.orm.CadmeaSystem;
import br.com.cadmea.web.bo.CadmeaSystemBo;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * @author Gilberto Santos
 */
@Service
public class CadmeaSystemSrv extends BaseServiceSrvImpl<CadmeaSystemStruct> {

    @Inject
    private CadmeaSystemBo bo;

    @Override
    protected CadmeaSystemBo getBo() {
        return bo;
    }

    /**
     * @param systemName
     * @return
     */
    public CadmeaSystem findByName(final @NotEmpty String systemName) {
        final CadmeaSystem cadmeaSystem = getBo().find("name", systemName, Result.UNIQUE);
        Validator.assertNotNull(cadmeaSystem, "a cadmea system cant be null");
        Validator.failIfAnyExceptionsFound();
        return cadmeaSystem;
    }

    /**
     * try to get a {@link CadmeaSystem} by params
     *
     * @param id
     * @return {@link CadmeaSystem} if found
     */
    public CadmeaSystem findBy(final @NotEmpty String id) {
        Validator.assertNotBlank(id, "Identity of system can't be null or empty");
        Validator.failIfAnyExceptionsFound();
        return getBo().find("id", id, Result.UNIQUE);
    }

}
