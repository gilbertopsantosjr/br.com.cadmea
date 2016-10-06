package br.com.cadmea.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import br.com.cadmea.spring.util.ApplicationContextUtil;


@SpringBootApplication
//@EnableAutoConfiguration
//@PropertySource("classpath:application.properties")
//@ComponentScan(basePackages = { "br.com.cadmea.**.*" })
public class SpringBootAppMainTest {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationContextUtil.class, args);
	}

}