package br.com.cadmea.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;

/**
 * @author gilberto.junior
 */
public class SpringApplicationListener implements ApplicationListener<ContextStartedEvent> {

	private static final Logger logger = LoggerFactory.getLogger(SpringApplicationListener.class);

	private String pacote;

	public SpringApplicationListener() {
		super();
		logger.info("Application ServerSide context foi criado!");
	}

	public void onApplicationEvent(final ContextStartedEvent event) {
		logger.info("Buscando por objetos remotos em : '" + event.getApplicationContext().getDisplayName() + "' ");
		try {
			AnotacaoServiceExporter.exportServices(getPacote(), event.getApplicationContext());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getPacote() {
		return pacote;
	}

	public void setPacote(String pacote) {
		this.pacote = pacote;
	}

}
