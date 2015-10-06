package br.com.cadmea.spring.util;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.WebAttributes;

import br.com.cadmea.jsf.PageCodeBase;

public class LoginErrorPhaseListener implements PhaseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	PageCodeBase base = new PageCodeBase();

	@Override
	public void afterPhase(PhaseEvent arg0) {
	}

	@Override
	public void beforePhase(PhaseEvent arg0) {
		Exception dadosIncorretosException = (Exception) base.getSessionMap().get(WebAttributes.AUTHENTICATION_EXCEPTION);
		
		if (dadosIncorretosException instanceof BadCredentialsException) {
			base.getSessionMap().put(WebAttributes.AUTHENTICATION_EXCEPTION, null);
			base.addMessage("Dados incorretos!");
		}
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RENDER_RESPONSE;
	}

}
