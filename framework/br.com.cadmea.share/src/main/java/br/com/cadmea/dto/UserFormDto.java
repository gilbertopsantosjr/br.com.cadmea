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
	private String pictureProfile;
	
	public String getPictureProfile() {
		return pictureProfile;
	}

	public void setPictureProfile(String pictureProfile) {
		this.pictureProfile = pictureProfile;
	}

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

	@Override
	public boolean validate() {
		if(!getEntity().getPassword().equalsIgnoreCase(getRepeatPassword())){
			setMessage("Password's not match !");
			return false;
		}
		return super.validate();
	}
	
}
