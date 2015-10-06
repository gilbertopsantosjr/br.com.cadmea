package br.com.cadmea.comuns.orm.enums;

import br.com.cadmea.comuns.orm.ElementoDeDominio;
import br.com.cadmea.comuns.util.Util;

public enum CenariosDeViolacao implements ElementoDeDominio {
	
	/*
	 * 		{javax.validation.constraints.AssertFalse.message}	=	deve ser falso 
	 * 		{javax.validation.constraints.AssertTrue.message}	=	deve ser verdadeiro
	 *		{javax.validation.constraints.Digits.message}	=	valor numérico fora dos limites 
	 *		{javax.validation.constraints.Future.message}	=	deve ser no futuro 
	 *		{javax.validation.constraints.Past.message}	=	deve ser no passado
	 *		{javax.validation.constraints.Max.message}	=	deve ser menor ou igual a {value}
	 *		{javax.validation.constraints.Min.message}	= 	deve ser maior ou igual a {value}
	 *		{javax.validation.constraints.NotNull.message}	=	não pode ser vazio 
	 *		{javax.validation.constraints.Null.message}	=	deve ser vazio 
	 *		{javax.validation.constraints.Pattern.message}	=	deve corresponder "{regexp}"
	 *		{javax.validation.constraints.Size.message}	=	o tamanho deve estar entre {min} e {max}
	 *		{org.hibernate.validator.constraints.Length.message}	=	o tamanho deve estar entre {min} e {max}
	 *		{org.hibernate.validator.constraints.NotEmpty.message}	=	não pode ser vazio 
	 *	 	org.hibernate.validator.constraints.Email.message= parece não ser um e-mail válido
	 *		org.hibernate.validator.constraints.NotBlank.message= não pode ser em branco
	 *		org.hibernate.validator.constraints.Range.message= deve estar entre {min} e {max}
	 *		org.hibernate.validator.constraints.URL.message= deve ser uma URL válida
	 *		org.hibernate.validator.constraints.CreditCardNumber.message= deve ser um número de cartão de crédito válido
	 */
	
	NAO_PODE_SER_VAZIO(""),
	NAO_PODE_EXCEDER_TAMANHO_MAXIMO(Util.geraCodigo(Integer.valueOf(Util.generatorNumericCode(3))).toLowerCase()),
	NAO_PODE_EXCEDER_TAMANHO_MINIMO(Util.geraCodigo(Integer.valueOf(Util.generatorNumericCode(1))).toLowerCase()),
	NAO_PODE_CONTER_CARACTERES_ESPECIAIS_COM_ACENTUACAO("!@#$%*().çÇáéíóúýÁÉÍÓÚÝàèìòùÀÈÌÒÙãõñäëïöüÿÄËÏÖÜÃÕÑâêîôûÂÊÎÔÛ"),
	NAO_PODE_CONTER_CARACTERES_ESPECIAIS_EXCETO_ACENTUACAO("!@#$%*()."),
	NAO_PODE_CONTER_CARACTERES_NUMERICOS("123456789"),
	NAO_PODE_SER_EM_BRANCO("                                                                                                                                                                                                                                                                                       ");
	
	String valor;
	
	CenariosDeViolacao(String o){
		
		valor = o;
	}

	@Override
	public String getDescricao() {
		return valor;
	}
	
	public String getChaveViolacao(){
		String chave = null;
		switch (this) {
			case NAO_PODE_SER_VAZIO:
				chave = "NotEmpty";
				break;
				
			case NAO_PODE_EXCEDER_TAMANHO_MAXIMO:
				chave = "Length";
				break;
				
			case NAO_PODE_EXCEDER_TAMANHO_MINIMO:
				chave = "Length";
				break;	
	
			case NAO_PODE_CONTER_CARACTERES_ESPECIAIS_COM_ACENTUACAO:
				chave = "Pattern";
				break;
				
			case NAO_PODE_CONTER_CARACTERES_ESPECIAIS_EXCETO_ACENTUACAO:
				chave = "Pattern";
				break;
	
			case NAO_PODE_CONTER_CARACTERES_NUMERICOS:
				chave = "Pattern";
				break;
				
			case NAO_PODE_SER_EM_BRANCO:
				chave = "NotBlank";
				break;
		}
		return chave;
	}

}
