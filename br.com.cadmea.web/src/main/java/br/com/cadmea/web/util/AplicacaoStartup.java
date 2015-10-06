/**
 * 
 */
package br.com.cadmea.web.util;


import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import br.com.cadmea.comuns.util.ConstantesComum;
import br.com.cadmea.comuns.util.FaseDoSoftware;

/**
 * @author gilberto.junior
 * 
 */
public class AplicacaoStartup implements ServletContextListener {

	private static Logger logger = Logger.getLogger(AplicacaoStartup.class);

	private static ServletContext servletContext = null;
	public static FaseDoSoftware faseDoSoftware;

	@Override
	public void contextInitialized(ServletContextEvent event) {
		servletContext = event.getServletContext();
		faseDoSoftware = FaseDoSoftware.valueOf((String) servletContext.getInitParameter(ConstantesComum.FASE_DO_SOFTWARE));
		logger.info("O software esta na fase: \t" + faseDoSoftware.name() );
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
	}

	public static ServletContext getServletContext() {
		return servletContext;
	}
	

}