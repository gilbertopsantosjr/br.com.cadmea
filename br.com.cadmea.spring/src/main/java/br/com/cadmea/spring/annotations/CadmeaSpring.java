package br.com.cadmea.spring.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

import br.com.cadmea.spring.security.CustomUserDetailsService;
import br.com.cadmea.spring.security.HeaderHandler;
import br.com.cadmea.spring.security.SecurityConfiguration;
import br.com.cadmea.spring.tx.PersistenceConfig;

@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@SpringBootApplication
@EnableAutoConfiguration
@Import({ PersistenceConfig.class, SecurityConfiguration.class,
    CustomUserDetailsService.class, HeaderHandler.class })
@PropertySource("classpath:cadmea.properties")
public @interface CadmeaSpring {
  String basePackage() default "";
}
