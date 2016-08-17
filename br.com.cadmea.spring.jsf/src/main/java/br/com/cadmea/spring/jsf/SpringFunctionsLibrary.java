package br.com.cadmea.spring.jsf;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import br.com.cadmea.spring.util.SpringFunctions;

import com.sun.faces.facelets.tag.AbstractTagLibrary;

/**
 * Gilberto Santos!
 */
public class SpringFunctionsLibrary extends AbstractTagLibrary {

	public final static String NAMESPACE = "http://br.com.cadmea.jsf/spring/fnc";
	
	public static final SpringFunctionsLibrary INSTANCE = new SpringFunctionsLibrary();

	public SpringFunctionsLibrary() {
		super(NAMESPACE);
		init(SpringFunctions.class);
	}

	private void init(Class<?>... functionClasses) {
		for (Class<?> fc : functionClasses) {
			for (Method method : fc.getMethods()) {
				if (Modifier.isStatic(method.getModifiers())) {
					this.addFunction(method.getName(), method);
				}
			}
		}
	}

	
	
}
