package br.com.cadmea.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;

import br.com.cadmea.spring.annotations.CadmeaSpring;

@CadmeaSpring
@ComponentScan(basePackages = { "br.com.cadmea.web.*" })
@EntityScan(basePackages = { "br.com.cadmea.web.model" })
public class StartServerAndLoginScreen {

  public static void main(String[] args) {
    SpringApplication.run(StartServerAndLoginScreen.class, args);
  }

}