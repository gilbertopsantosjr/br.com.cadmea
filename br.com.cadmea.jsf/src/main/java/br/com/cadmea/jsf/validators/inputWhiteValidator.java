package br.com.cadmea.jsf.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.apache.commons.lang.StringUtils;

import br.com.cadmea.jsf.PageCodeBase;

@FacesValidator("noWhiteSpaces")
public class inputWhiteValidator implements Validator {
	
	@Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String orgName = (String) value;  
    
       
        
        if(StringUtils.isBlank(orgName) ){  
        	PageCodeBase.addMessage("006", FacesMessage.SEVERITY_ERROR, component);
            throw new ValidatorException(new FacesMessage());  
        } 
    }
	
	
	
	
}
