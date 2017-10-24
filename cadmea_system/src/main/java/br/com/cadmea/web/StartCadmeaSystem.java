package br.com.cadmea.web;

import br.com.cadmea.spring.annotations.CadmeaSpring;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@CadmeaSpring
@ComponentScan(value = "br.com.cadmea.web")
public class StartCadmeaSystem implements CommandLineRunner {

    public static void main(final String[] args) {
        SpringApplication.run(StartCadmeaSystem.class, args);
    }

    @Override
    public void run(final String... args) throws Exception {

    }

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return (container -> {
            container.setContextPath("/cadmea");
            container.setPort(8080);
        });
    }
}