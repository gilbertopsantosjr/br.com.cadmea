package br.com.cadmea.web.util;

import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

import org.apache.log4j.Logger;

public class QualityRequestListener implements ServletRequestListener {
	
	private final static Logger logger = Logger.getLogger(QualityRequestListener.class);

	public void requestDestroyed(ServletRequestEvent servletRequestEvent) {
		ServletRequest servletRequest = servletRequestEvent.getServletRequest();
		logger.info("ServletRequest destroyed");
	}

	public void requestInitialized(ServletRequestEvent servletRequestEvent) {
		ServletRequest servletRequest = servletRequestEvent.getServletRequest();
		logger.info("ServletRequest initialized");
	}
}
