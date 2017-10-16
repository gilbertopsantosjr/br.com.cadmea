package br.com.cadmea.web;

import javax.security.auth.message.config.AuthConfigFactory;

import br.com.cadmea.spring.security.SecurityConfiguration;
import org.apache.catalina.authenticator.jaspic.AuthConfigFactoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;

import br.com.cadmea.spring.annotations.CadmeaSpring;
import br.com.cadmea.spring.util.WebConfig;

@CadmeaSpring
@ComponentScan(basePackages = {"br.com.cadmea"}, excludeFilters = {
		@ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE, value=SecurityConfiguration.class),
        @ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE, value=WebConfig.class) } )
public class StartCadmeaSystem extends SpringBootServletInitializer  {

	public static void main(String[] args) {
		if (AuthConfigFactory.getFactory() == null) {
			AuthConfigFactory.setFactory(new AuthConfigFactoryImpl());
		}
		SpringApplication.run(StartCadmeaSystem.class, args);
	}
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(StartCadmeaSystem.class);
    }

}