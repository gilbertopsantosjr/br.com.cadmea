/**
 *
 */
package br.com.cadmea.dto;

import br.com.cadmea.comuns.dto.FormDto;
import br.com.cadmea.model.orm.UserSystem;

/**
 * @author Gilberto Santos
 *
 */
public class UserFormDto extends FormDto<UserSystem> {

	private String systemName;
	private String url;
	private String repeatPassword;
	
	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRepeatPassword() {
		return repeatPassword;
	}

	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}
	
	
	
}
