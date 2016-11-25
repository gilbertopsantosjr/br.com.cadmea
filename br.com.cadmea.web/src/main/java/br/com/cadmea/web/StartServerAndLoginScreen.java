package br.com.cadmea.web;

import javax.security.auth.message.config.AuthConfigFactory;

import org.apache.catalina.authenticator.jaspic.AuthConfigFactoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import br.com.cadmea.spring.annotations.CadmeaSpring;

@CadmeaSpring
@ComponentScan(basePackages = { "br.com.cadmea"})
@EntityScan(basePackages = { "br.com.cadmea" })
public class StartServerAndLoginScreen {

	public static void main(String[] args) {
		if (AuthConfigFactory.getFactory() == null) {
			AuthConfigFactory.setFactory(new AuthConfigFactoryImpl());
		}
		SpringApplication.run(StartServerAndLoginScreen.class, args);
	}

}