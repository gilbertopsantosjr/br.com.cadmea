package br.com.cadmea.jsf.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import br.com.cadmea.jsf.PageCodeBase;

@FacesValidator("noSpecialCharacter")
public class InputInvalideCaracterValidator implements Validator {
	
	@Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String orgName = (String) value;  
        if(isSpecialCaracter(orgName)){
        	PageCodeBase.addMessage("007", FacesMessage.SEVERITY_ERROR, component);
            throw new ValidatorException(new FacesMessage());  
        }
    }

	private static boolean isSpecialCaracter(String check){
		Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(check);
		return m.find();
	}
	
}
