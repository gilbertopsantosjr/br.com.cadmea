/**
 *
 */
package br.com.cadmea.web.business;

import br.com.cadmea.baseservico.BaseMaintenanceSrvImpl;
import br.com.cadmea.comuns.orm.enums.Result;
import br.com.cadmea.model.orm.CadmeaSystem;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Gilberto Santos
 */
@Service
public class CadmeaSystemSrv extends BaseMaintenanceSrvImpl<CadmeaSystem, CadmeaSystemBo> {

    @Inject
    private CadmeaSystemBo bo;

    @Override
    protected CadmeaSystemBo getBo() {
        return bo;
    }

    /**
     * try to get a {@link CadmeaSystem} by params
     *
     * @param identity
     * @return {@link CadmeaSystem} if found
     */
    public CadmeaSystem findBy(final String identity) {
        if (identity == null || identity.isEmpty()) {
            throw new RuntimeException("Identity of system can't be null or empty");
        }

        final Map<String, Object> params = new HashMap<>();
        params.put("identity", identity);

        return getBo().find(params, Result.UNIQUE);
    }

}
