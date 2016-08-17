package br.com.cadmea.spring;

import java.io.IOException;
import java.lang.annotation.Annotation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.remoting.rmi.RmiServiceExporter;

import br.com.cadmea.comuns.clazz.LoaderClazzs;
import br.com.cadmea.spring.annotations.BeanService;

/**
 * @author gilberto.junior
 */
public class AnotacaoServiceExporter {

	private static final Logger logger = LoggerFactory.getLogger(AnotacaoServiceExporter.class);

	/**
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static void exportServices(String packages, ApplicationContext ctx) throws ClassNotFoundException, IOException {
		BeanService remoteService;
		Class<?> clazz[] = LoaderClazzs.getClasses(packages); // destino dos
																// serviços
		for (int i = 0; i < clazz.length; i++)
			for (Annotation annotation : clazz[i].getAnnotations()) {
				if (annotation instanceof BeanService) {
					remoteService = (BeanService) annotation;
					// caso o serviço seja exportado via rmi
					if (remoteService.tipo().equals("rmi")) {
						try {
							RmiServiceExporter exporter = new RmiServiceExporter();
							exporter.setServicePort(1199);
							exporter.setServiceName(clazz[i].getSimpleName());

							// referencia ao bean @Service no Spring
							String primeiroChar = clazz[i].getSimpleName().substring(0, 1).toLowerCase();
							String nomeDoBean = primeiroChar + clazz[i].getSimpleName().substring(1, clazz[i].getSimpleName().length());
							exporter.setService(ctx.getBean(nomeDoBean));

							// objeto é identificado e gerenciado pelo contexto do Spring
							exporter.setBeanClassLoader(ctx.getClassLoader());
							// interface de acesso remoto no share
							exporter.setServiceInterface(clazz[i].getInterfaces()[0]);
							// prepara e exporta o objeto pra ter acesso remoto
							exporter.afterPropertiesSet();
							
							logger.info("objeto remoto para o service:" + nomeDoBean + " foi criado ! ");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
	}

}
