package br.com.cadmea.spring.util;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = { "br.com.cadmea.**.*" })
public class ApplicationContextUtil  {

	@Bean(name = "applicationProperty")
	public ApplicationProperty getApplicationProperty() {
		return new ApplicationProperty();
	}

	
}
