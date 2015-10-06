package br.com.cadmea.jsf.uteis;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import com.sun.faces.facelets.tag.AbstractTagLibrary;

public class FunctionsLibrary extends AbstractTagLibrary {

	public final static String NAMESPACE = "http://br.com.cadmea.jsf/fnc";

	public static final FunctionsLibrary INSTANCE = new FunctionsLibrary();

	public FunctionsLibrary() {
		super(NAMESPACE);
		init(BaseFunctions.class);
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
