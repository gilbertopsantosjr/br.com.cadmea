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
import br.com.cadmea.web.model.User;

/**
 * @author Gilberto Santos
 */
@Service
public class UserSrv extends BaseMaintenanceSrvImpl<User, UserBo> {

	@Inject
	private UserBo userBo;

	@Override
	protected UserBo getBo() {
		return userBo;
	}

	/**
	 * try to get a {@link User} of system by params
	 * @param username
	 * @param password
	 * @return {@link User} if found
	 */
	public User getUserBy(final String username, final String password) {
		if(username == null || username.isEmpty())
			throw new RuntimeException("Username can't be null or empty");
		
		if(password == null || password.isEmpty())
			throw new RuntimeException("Password can't be null or empty");
		
		Map<String, Object> params = new HashMap<>();
		params.put("password", password);
		params.put("username", username);
		
		return getBo().find(params, Result.UNIQUE);
	}

}
