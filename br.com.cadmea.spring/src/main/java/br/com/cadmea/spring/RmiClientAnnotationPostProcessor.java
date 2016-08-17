package br.com.cadmea.spring;

import java.lang.reflect.Field;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import org.springframework.util.StringUtils;

/**
 * @author gilberto.junior
 */
public class RmiClientAnnotationPostProcessor implements BeanFactoryPostProcessor, BeanClassLoaderAware {

	
	private static final Logger logger = LoggerFactory.getLogger(RmiClientAnnotationPostProcessor.class);
			
	private List<Class<?>> remoteServiceInterfaces;
	private String host;
	private int port;
	private String basePackage;
	private ClassLoader classLoader;
	private boolean refreshStubOnConnectFailure;
	private boolean cacheStub;
	private boolean lookupStubOnStartup;

	public void postProcessBeanFactory(	ConfigurableListableBeanFactory beanFactory ) throws BeansException {
		logger.info(" RmiClientAnnotationPostProcessor >> postProcessBeanFactory ");

		for (String beanName : beanFactory.getBeanDefinitionNames()) {
			BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
			logger.info(" BeanDefinitionNames : " + beanName);
			// Ignore abstract bean!
			if (beanDefinition.isAbstract()
					|| beanDefinition.getBeanClassName() == null
					|| beanDefinition.getBeanClassName().indexOf(basePackage) == -1) {
				continue;
			}

			try {
				Class<?> clazz = Class.forName(beanDefinition.getBeanClassName());
				Field[] fields = clazz.getDeclaredFields();

				for (Field field : fields) {
					BeanClient client = field.getAnnotation(BeanClient.class);
					if (client == null) {
						continue;
					}
					Class<?> remoteServiceInterface = field.getType();
					RmiProxyFactoryBean clientBean = getRmiProxyFactoryBean(remoteServiceInterface);
					String clientBeanName = getClientName(field.getName());

					if (!beanFactory.containsSingleton(clientBeanName)) {
						beanFactory.registerSingleton(clientBeanName, clientBean);
						logger.info("Register rmi client: " + clientBean	+ " " + "with name '" + clientBeanName	+ "' success.");
					}
				}
			} catch (ClassNotFoundException e) {
				throw new FatalBeanException("No class for name " + beanName, e);
			}
		}
	}

	private String getClientName(String remoteServiceInterfaceName) {
		return StringUtils.uncapitalize(remoteServiceInterfaceName) + "Client";
	}

	private RmiProxyFactoryBean getRmiProxyFactoryBean(	Class<?> remoteServiceInterface) {
		RmiProxyFactoryBean client = new RmiProxyFactoryBean();
		logger.info(" RmiClientAnnotationPostProcessor >> getRmiProxyFactoryBean");
		logger.info(" >> RemoteServiceInterface name : "	+ remoteServiceInterface.getSimpleName());
		logger.info(" >> RemoteServiceInterface host : " + host);
		logger.info(" >> RemoteServiceInterface port : " + port);

		client.setServiceInterface(remoteServiceInterface);
		client.setServiceUrl("rmi://" + host + ":" + port + "/"	+ remoteServiceInterface.getSimpleName());
		client.setRefreshStubOnConnectFailure(refreshStubOnConnectFailure);
		client.setCacheStub(cacheStub);
		client.setLookupStubOnStartup(lookupStubOnStartup);
		client.setBeanClassLoader(classLoader);
		client.afterPropertiesSet();

		return client;
	}

	public void setRemoteServiceInterfaces(	List<Class<?>> remoteServiceInterfaces) {
		this.remoteServiceInterfaces = remoteServiceInterfaces;
	}

	public List<Class<?>> getRemoteServiceInterfaces() {
		return remoteServiceInterfaces;
	}

	public String getBasePackage() {
		return basePackage;
	}

	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setBeanClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	public boolean isRefreshStubOnConnectFailure() {
		return refreshStubOnConnectFailure;
	}

	public void setRefreshStubOnConnectFailure(
			boolean refreshStubOnConnectFailure) {
		this.refreshStubOnConnectFailure = refreshStubOnConnectFailure;
	}

	public boolean isCacheStub() {
		return cacheStub;
	}

	public void setCacheStub(boolean cacheStub) {
		this.cacheStub = cacheStub;
	}

	public boolean isLookupStubOnStartup() {
		return lookupStubOnStartup;
	}

	public void setLookupStubOnStartup(boolean lookupStubOnStartup) {
		this.lookupStubOnStartup = lookupStubOnStartup;
	}

}
