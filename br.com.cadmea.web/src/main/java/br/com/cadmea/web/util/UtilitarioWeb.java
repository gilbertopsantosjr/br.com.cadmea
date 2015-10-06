package br.com.cadmea.web.util;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.bind.Element;

import org.apache.commons.lang.WordUtils;

import br.com.cadmea.comuns.util.Util;

public class UtilitarioWeb {

	public static String getEnderecoBaseDinamico(HttpServletRequest requisicao) {

		String endereco_ = null;
		String contexto_ = null;

		if (requisicao.getServerPort() == 443)
			endereco_ = "https://" + requisicao.getServerName();
		else
			endereco_ = "http://" + requisicao.getServerName();

		if (requisicao.getServerPort() != 80 && requisicao.getServerPort() != 443)
			endereco_ += ":" + requisicao.getServerPort();

		if (!Util.isVazio(requisicao.getContextPath())
				&& !Util.addTrim(requisicao.getContextPath()).equals("/"))
			contexto_ = Util.addTrim(requisicao.getContextPath());

		return endereco_ + (contexto_ != null ? contexto_ : "");

	}
	
	public static String getManagedBeanByPage(String page){
		String [] path = page.split("/");
		String managedBean = ""; 
		if(path.length > 0)
			managedBean = path[(path.length-1)].replace(".jsf", "Ctrl");
		return WordUtils.capitalize( managedBean );
	}
	
	public static String getCurrentPage(HttpServletRequest request){
		return request.getServletPath();
	}

	public static boolean isVazio(HttpServletRequest requisicao, String campo) {

		if (requisicao.getParameter(campo) instanceof String) {
			return (Util.isVazio(requisicao.getParameter(campo)));

		} else if (requisicao.getAttribute(campo) instanceof String) {
			return (Util.isVazio((String) requisicao.getAttribute(campo)));

		} else if (requisicao.getAttribute(campo) instanceof Collection) {
			Collection<?> auxiliar = (Collection<?>) requisicao.getAttribute(campo);

			if (auxiliar.isEmpty())
				return true;
			else
				return false;

		} else if (requisicao.getAttribute(campo) instanceof Element) {
			Element elemento = (Element) requisicao.getAttribute(campo);

			return (Util.isVazio(elemento.toString()));

		} else {
			return true;

		}
	}

	public static boolean isVazio(HttpSession sessao, String campo) {

		if (sessao.getAttribute(campo) instanceof String) {
			return (Util.isVazio((String) sessao.getAttribute(campo)));

		} else if (sessao.getAttribute(campo) instanceof Collection) {
			Collection<?> auxiliar = (Collection<?>) sessao.getAttribute(campo);

			return auxiliar.isEmpty();

		} else if (sessao.getAttribute(campo) instanceof Element) {
			Element elemento = (Element) sessao.getAttribute(campo);

			return (Util.isVazio(elemento.toString()));

		} else {
			return true;

		}

	}

}
