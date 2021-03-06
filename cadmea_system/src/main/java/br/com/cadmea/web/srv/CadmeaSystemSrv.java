/**
 *
 */
package br.com.cadmea.web.srv;

import br.com.cadmea.comuns.orm.enums.Result;
import br.com.cadmea.comuns.validator.Validator;
import br.com.cadmea.infra.negocio.BaseServiceSrvImpl;
import br.com.cadmea.model.orm.CadmeaSystem;
import br.com.cadmea.web.bo.CadmeaSystemBo;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.Serializable;

/**
 * @author Gilberto Santos
 */
@Service
public class CadmeaSystemSrv extends BaseServiceSrvImpl<CadmeaSystem> {

    @Inject
    private CadmeaSystemBo bo;

    @Override
    protected CadmeaSystemBo getBo() {
        return bo;
    }

    /**
     * @param identity
     * @return {@link CadmeaSystem}
     */
    public CadmeaSystem findByIdentity(final @NotEmpty String identity) {
        final CadmeaSystem cadmeaSystem = getBo().find("identity", identity, Result.UNIQUE);
        Validator.throwIfFail(cadmeaSystem == null, "a cadmea system cant be null");
        return cadmeaSystem;
    }

    /**
     * try to get a {@link CadmeaSystem} by params
     *
     * @param id
     * @return {@link CadmeaSystem} if found
     */
    public CadmeaSystem findById(final @NotEmpty Serializable id) {
        Validator.throwIfFail(id == null, "Identity of system can't be null or empty");
        return getBo().find("id", id, Result.UNIQUE);
    }

}
