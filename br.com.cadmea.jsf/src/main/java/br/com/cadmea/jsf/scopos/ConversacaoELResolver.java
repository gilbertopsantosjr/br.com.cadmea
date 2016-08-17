package br.com.cadmea.jsf.scopos;

import java.beans.FeatureDescriptor;
import java.util.Iterator;

import javax.el.ELContext;
import javax.el.ELResolver;
import javax.el.PropertyNotFoundException;


public class ConversacaoELResolver extends ELResolver {

	@Override
	public Object getValue(ELContext elContext, Object base, Object property) {
 
		if (property == null) {
			throw new PropertyNotFoundException("Propriedade não encontrada e ou é nula.");
		}
		if (base == null) {
 
			if(Conversacao.NOME_ESCOPO.equals(property.toString()))
			{
				Conversacao conversacao = Conversacao.instancia();
				elContext.setPropertyResolved(true);
				return conversacao;
			}
 
			Conversacao conversacao = Conversacao.instancia();
			return getValue(conversacao, property.toString(), elContext);
 
		} else if (base instanceof Conversacao) {
			return getValue((Conversacao) base, property.toString(), elContext);
		}
		return null;
	}
 
	private Object getValue(Conversacao conversacao, String property, ELContext elContext) {
		Object objeto = conversacao.get(property);
		elContext.setPropertyResolved(objeto != null);
		return objeto;
	}

	@Override
	public Class<?> getCommonPropertyType(ELContext arg0, Object arg1) {
		return null;
	}

	@Override
	public Iterator<FeatureDescriptor> getFeatureDescriptors(ELContext arg0,
			Object arg1) {
		return null;
	}

	@Override
	public Class<?> getType(ELContext arg0, Object arg1, Object arg2) {
		return null;
	}

	@Override
	public boolean isReadOnly(ELContext arg0, Object arg1, Object arg2) {
		return false;
	}

	@Override
	public void setValue(ELContext arg0, Object arg1, Object arg2, Object arg3) {
	}
	
}
