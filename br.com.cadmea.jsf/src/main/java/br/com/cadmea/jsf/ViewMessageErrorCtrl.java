package br.com.cadmea.jsf;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class ViewMessageErrorCtrl implements Serializable {
	
	@ManagedProperty(value = "#{param.code}")
	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public void preRender(){
		if(code != null && !code.equals(""))
			PageCodeBase.addMessage(code, FacesMessage.SEVERITY_ERROR);
	}
	
}
