package br.com.cadmea.jsf.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import br.com.cadmea.jsf.PageCodeBase;

@FacesValidator("mustBeEmail")
public class EmailValidator implements Validator {
	
	@Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String orgName = (String) value;  
    
        br.com.cadmea.comuns.util.EmailValidator validator = new br.com.cadmea.comuns.util.EmailValidator();
        
        if(!validator.validate(orgName) ){  
        	PageCodeBase.addMessage("009", FacesMessage.SEVERITY_ERROR, component);
            throw new ValidatorException(new FacesMessage());  
        } 
    }
	
	
	
	
}
