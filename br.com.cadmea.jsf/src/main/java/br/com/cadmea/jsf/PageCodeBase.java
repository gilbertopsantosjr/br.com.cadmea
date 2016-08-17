package br.com.cadmea.jsf;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.FacesEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.cadmea.comuns.orm.Acessivel;
import br.com.cadmea.web.util.UtilitarioWeb;


public class PageCodeBase {

	protected Acessivel getUsuarioLogado() {
		return null;
	}
	
	public String getCurrentPage(){
		return UtilitarioWeb.getCurrentPage(getServletRequest());
	}

	protected void gotoPage(String pageName) {
		if (pageName != null) {
			FacesContext context = getFacesContext();
			UIViewRoot newView = context.getApplication().getViewHandler().createView(context, pageName);
			context.setViewRoot(newView);
			context.renderResponse();
			//context.getExternalContext().redirect(pageName);
		}
	}
	
	public static void goToPageError(int error){
		try {
			getServletResponse().sendError(error);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateComponent(String idComponent) {
		UIComponent comp = findComponentInRoot(idComponent);
		if (comp != null)
			comp.setRendered(true);
		else {
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(idComponent);
		}
	}

	public static void addMessage(String msg) {
		FacesMessage message = new FacesMessage(msg);
		getFacesContext().addMessage(null, message);
	}

	/**
	 * Metodo utilizado para mostrar excecoes de forma generica.
	 * 
	 * @author Gilberto Santos
	 */
	public void showException(Exception e) {
		// Modificar esse GATO
		e.printStackTrace();

	}
	
	public static Locale getLocale(){
		return FacesContext.getCurrentInstance().getViewRoot().getLocale();
	}

	public static void addMessage(String key, FacesMessage.Severity severity) {
		addMessage( getMessageByCodeInExternalResource(key) );
	}
	
	public static String getMessageByCodeInExternalResource(String key){
		String message = null;
		try {
			if(key == null)
				throw new RuntimeException("key message not found !!");
			
			ResourceBundle rb = ResourceBundle.getBundle("message", getLocale());
			Enumeration <String> keys = rb.getKeys();
			while (keys.hasMoreElements()) {
				 message = rb.getString(key);
				 if(message != null)
					 break;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return message;
	}

	public static void addMessage(String key, FacesMessage.Severity severity, UIComponent uIComponent) {
		FacesMessage message = new FacesMessage(getMessageByCodeInExternalResource(key));
		getFacesContext().addMessage(uIComponent.getClientId(getFacesContext()), message);
	}

	/**
	 * Returns a full system path for a file path given relative to the web
	 * project
	 */
	protected static String getRealPath(String relPath) {
		String path = relPath;
		try {
			URL url = FacesContext.getCurrentInstance().getExternalContext().getResource(relPath);
			if (url != null) {
				path = url.getPath();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return path;
	}

	/**
	 * 
	 * @param event
	 * @param name
	 * @return Serializable
	 */
	public Serializable getEventAttribute(FacesEvent event, String name) {
		return (Serializable) event.getComponent().getAttributes().get(name);
	}

	/**
	 * 
	 * @return
	 */
	public Map<String, String> getRequestParam() {
		return getFacesContext().getExternalContext().getRequestParameterMap();
	}

	/**
	 * 
	 * @return
	 */
	public static HttpServletRequest getServletRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}

	/**
	 * 
	 * @return
	 */
	public static HttpServletResponse getServletResponse() {
		return (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
	}

	/**
	 * 
	 * @return
	 */
	public Map<String, Object> getRequest() {
		return getFacesContext().getExternalContext().getRequestMap();
	}

	/**
	 * 
	 * @return
	 */
	public static Map<String, Object> getSession() {
		return getFacesContext().getExternalContext().getSessionMap();
	}

	/**
	 * 
	 * @return
	 */
	public Map<String, Object> getSessionMap() {
		Map<String, Object> sessionScope = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		return sessionScope;
	}

	public PageCodeBase getManagedBean() {
		return null;
	}

	public void setManagedBeanAttribute() {

	}

	public static FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

	public static UIComponent findComponentInRoot(String id) {
		UIComponent ret = null;

		FacesContext context = FacesContext.getCurrentInstance();
		if (context != null) {
			UIComponent root = context.getViewRoot();
			ret = findComponent(root, id);
		}

		return ret;
	}

	public static UIComponent findComponent(UIComponent base, String id) {

		// Is the "base" component itself the match we are looking for?
		if (id.equals(base.getId())) {
			return base;
		}

		// Search through our facets and children
		UIComponent kid = null;
		UIComponent result = null;
		Iterator<?> kids = base.getFacetsAndChildren();
		while (kids.hasNext() && (result == null)) {
			kid = (UIComponent) kids.next();
			if (id.equals(kid.getId())) {
				result = kid;
				break;
			}
			result = findComponent(kid, id);
			if (result != null) {
				break;
			}
		}
		return result;
	}

}
