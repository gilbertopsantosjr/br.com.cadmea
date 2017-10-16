package br.com.cadmea.spring.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

import br.com.cadmea.spring.rest.exceptions.ExceptionHandlingController;
import br.com.cadmea.spring.rest.exceptions.RestExceptionHandler;
import br.com.cadmea.spring.util.JsonDateSerializer;

@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@SpringBootApplication
@EnableAutoConfiguration(exclude = {  DataSourceAutoConfiguration.class, JpaRepositoriesAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
@PropertySource("classpath:cadmea.properties")
@Import(value = {ExceptionHandlingController.class, JsonDateSerializer.class, RestExceptionHandler.class})
public @interface CadmeaSpring {
  String basePackage() default "";
}
