package br.com.cadmea.spring.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Service;

/**
 * @author gilberto.junior
 * anotação para conseguir exportar o objeto bean no Spring como 
 * serviço SOA enterprise. 
 */
//determina que essa anotação para o escopo de classe
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE }) 
//determina o momento em que pode ser acessada a anotação, notável pela JVM
@Retention(RetentionPolicy.RUNTIME) 
@Service
public @interface BeanService {
	String tipo() default "rmi";
	String serviceName() default "";
	String service() default "";
	String serviceInterface() default "";
}
