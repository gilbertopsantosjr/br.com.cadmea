/**
 * 
 */
package br.com.cadmea.web.business;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import br.com.cadmea.baseservico.BaseMaintenanceSrvImpl;
import br.com.cadmea.comuns.orm.enums.Result;
import br.com.cadmea.model.orm.CadmeaSystem;
import br.com.cadmea.model.orm.UserSystem;

/**
 * @author Gilberto Santos
 *
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
	 * @return {@link User} if found
	 */
	public CadmeaSystem findBy(final String identity) {
		if (identity == null || identity.isEmpty())
			throw new RuntimeException("Identity of system can't be null or empty");

		Map<String, Object> params = new HashMap<>();
		params.put("identity", identity);

		return getBo().find(params, Result.UNIQUE);
	}

}
