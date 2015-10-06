package br.com.cadmea.spring.factorys;

import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

/**
 * Classe responsavel pela criacao dos beans.
 * 
 * @author Gilberto Santos
 */
public final class SpringBeanFactory {

	private static Logger logger = Logger.getLogger(SpringBeanFactory.class.getCanonicalName());

	private static final SpringBeanFactory instance = new SpringBeanFactory();

	private static ApplicationContext applicationContext;

	private SpringBeanFactory() {
		initialize();
	}

	private void initialize() {
		logger.info("Inicializando BeanFactory");
		applicationContext = ContextLoader.getCurrentWebApplicationContext();
	}

	public static SpringBeanFactory getInstance() {
		return instance;
	}

	@SuppressWarnings("unchecked")
	public static <T extends Object> T getBean(String idObject) {
		return (T) applicationContext.getBean(idObject);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Object> T getBean(Object clazz) {
		String nomeDaClasse = "";
		try {
			nomeDaClasse = Class.forName(clazz.toString().replace("class ", "")).getSimpleName();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (T) applicationContext.getBean(StringUtils.uncapitalize(nomeDaClasse));
	}

}
