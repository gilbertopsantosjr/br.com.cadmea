package br.com.cadmea.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// @EnableAutoConfiguration
// @PropertySource("classpath:application.properties")
// @ComponentScan(basePackages = { "br.com.cadmea.**.*" })
public class SpringBootAppMainTest {

  public static void main(String[] args) {
    SpringApplication.run(SpringBootAppMainTest.class, args);
  }

}