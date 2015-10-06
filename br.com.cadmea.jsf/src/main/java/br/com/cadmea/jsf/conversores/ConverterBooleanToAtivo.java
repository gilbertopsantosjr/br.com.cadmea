/**
 * 
 */
package br.com.cadmea.jsf.conversores;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import br.com.cadmea.comuns.orm.enums.Ativo;

/**
 * @author gilberto
 * 
 */
public class ConverterBooleanToAtivo implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
	
		Boolean condicao = new Boolean(value);
		return Ativo.valueOf(condicao ? Ativo.ATIVO.name() : Ativo.INATIVO.name());
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		return value.toString();
	}

}
