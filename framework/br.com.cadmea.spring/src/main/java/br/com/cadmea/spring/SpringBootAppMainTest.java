package br.com.cadmea.spring;

import org.springframework.boot.SpringApplication;

import br.com.cadmea.spring.annotations.CadmeaSpring;

@CadmeaSpring
public class SpringBootAppMainTest {

  public static void main(String[] args) {
    SpringApplication.run(SpringBootAppMainTest.class, args);
  }

}