package br.com.cadmea.jsf.conversores;

import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter("dateConverter")
public class DateConverter implements Converter {

	public Object getAsObject(FacesContext arg0, UIComponent arg1, String valorTela) throws ConverterException {

		System.out.println(valorTela);
		return new Date();
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object obj) throws ConverterException {
		return obj.toString(); 
	}
}
