package br.com.cadmea.web.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


public class HabilitaAcessoExternoParaWebServicesFilter implements Filter  {

	private static final Logger logger = Logger.getLogger(HabilitaAcessoExternoParaWebServicesFilter.class.getCanonicalName());
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		
		logger.info( "HabilitaAcessoRemotoFilter add CORS Headers" );
		 
        HttpServletResponse res = (HttpServletResponse) arg1;
        res.addHeader("Access-Control-Allow-Origin", getEnderecosAutorizados() );
        res.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        res.addHeader("Access-Control-Allow-Headers", "Content-Type");
        arg2.doFilter(arg0, arg1);
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
	}
	
	public String getEnderecosAutorizados(){
	 // TODO colocar uma lista de IPE validos
	    return "*";
	}

}
