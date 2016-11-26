/**
 *
 */
package br.com.cadmea.web.dto;

import br.com.cadmea.comuns.dto.FormDto;
import br.com.cadmea.web.pojo.UserBean;

public class UserDto extends FormDto<UserBean> {

	public UserDto() {
		super();
		this.newInstance();
	}

	public String getFirstName() {
		return "Gilberto";
	}

	public String getLastName() {
		return "Santos";
	}

	public String getUsername() {
		return getEntity().getUsername();
	}

	public String getPassword() {
		return getEntity().getPassword();
	}

}
