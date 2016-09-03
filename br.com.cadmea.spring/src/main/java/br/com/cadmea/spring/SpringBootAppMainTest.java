package br.com.cadmea.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.cadmea.spring.util.ApplicationContextUtil;


@SpringBootApplication
public class SpringBootAppMainTest {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationContextUtil.class, args);
	}

}