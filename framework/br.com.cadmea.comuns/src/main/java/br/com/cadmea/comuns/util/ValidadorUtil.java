/**
 * 
 */
package br.com.cadmea.comuns.util;

import java.text.ParseException;

/**
 * @author gilbertosantos
 * 
 */
public class ValidadorUtil {

	static public boolean isValidEmail(String email) {
		EmailValidator validator = new EmailValidator();
		return validator.validate(email);
	}

	/**
	 * Verifica se o digito verificador e valido
	 * 
	 * @param strCpf
	 * @return
	 */
	static public boolean validaCPF(String strCpf) {
		int d1, d2;
		int digito1, digito2, resto;
		int digitoCPF;
		String nDigResult;
		d1 = d2 = 0;
		digito1 = digito2 = resto = 0;

		if (strCpf.length() > 11 || strCpf.length() < 11)
			return false;

		for (int nCount = 1; nCount < strCpf.length() - 1; nCount++) {
			digitoCPF = Integer.valueOf(strCpf.substring(nCount - 1, nCount)).intValue();
			d1 = d1 + (11 - nCount) * digitoCPF;
			d2 = d2 + (12 - nCount) * digitoCPF;
		}
		;
		resto = (d1 % 11);
		if (resto < 2)
			digito1 = 0;
		else
			digito1 = 11 - resto;

		d2 += 2 * digito1;
		resto = (d2 % 11);
		if (resto < 2)
			digito2 = 0;
		else
			digito2 = 11 - resto;
		String nDigVerific = strCpf.substring(strCpf.length() - 2, strCpf.length());
		nDigResult = String.valueOf(digito1) + String.valueOf(digito2);
		return nDigVerific.equals(nDigResult);
	}

	public static String formatarCPF(String parametro) {

		String retorno = null;

		if (!Util.isVazio(parametro)) {

			if (parametro.length() == 11) {
				retorno = parametro.substring(0, 3) + "." + parametro.substring(3, 6) + "." + parametro.substring(6, 9) + "-"
						+ parametro.substring(9, 11);

			} else {
				retorno = parametro;

				for (int i = parametro.length(); i < 11; i++)
					retorno = "0" + retorno;

				retorno = retorno.substring(0, 3) + "." + retorno.substring(3, 6) + "." + retorno.substring(6, 9) + "-"
						+ retorno.substring(9, 11);

			}

		}

		return retorno;

	}

	/**
	 * 
	 * @param parametro
	 * @return
	 * @throws ParseException
	 */
	public static String desformatarCEP(String parametro) throws ParseException {
		if (Util.isVazio(parametro))
			return parametro;

		if (parametro.length() != 9)
			return parametro;

		String retorno = parametro.substring(0, 5) + parametro.substring(6, 9);

		checarDesformatacao(retorno);

		return retorno;
	}

	public static boolean contemMascaraCPF(String parametro) {
		if (parametro.contains(".") && parametro.contains("-")) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param parametro
	 * @return
	 * @throws ParseException
	 */
	public static String desformatarCPF(String parametro) throws ParseException {

		if (Util.isVazio(parametro)) {
			throw new ParseException("CPF NÃƒO INFORMADO.", 0);
		}

		if (!contemMascaraCPF(parametro) && parametro.length() == 11) {
			return parametro;
		}

		if (parametro.length() != 14) {
			throw new ParseException("TAMANHO DO CPF INFORMADO INVÃLIDO", 0);
		}

		String retorno = parametro.substring(0, 3) + parametro.substring(4, 7) + parametro.substring(8, 11) + parametro.substring(12, 14);

		checarDesformatacao(retorno);

		return retorno;

	}

	private static void checarDesformatacao(String parametro) throws ParseException {
		for (int posicao = 0; posicao < parametro.length(); posicao++) {
			if (!Character.isDigit(parametro.charAt(posicao)))
				throw new ParseException("O VALOR PASSADO POSSUI CARACTER(ES) INVALIDO(S).", 0);
		}
	}

	public static boolean isUFValida(String parametro) {
		String matriz = "RS#SC#PR#SP#RJ#MG#ES#MS#MT#GO#TO#DF#BA#AL#SE#RN#PE#CE#MA#PI#PB#FN#AM#AC#PA#RO#RR#AP#rs#sc#pr#sp#rj#mg#es#ms#mt#go#to#df#ba#al#se#rn#pe#ce#ma#pi#pb#fn#am#ac#pa#ro#rr#ap";
		if (matriz.indexOf(parametro.toUpperCase()) == -1)
			return false;
		else
			return true;
	}

	/**
	 * 
	 * @param parametro
	 * @return
	 * @throws ParseException
	 */
	public static String desformatarCNPJ(String parametro) throws ParseException {
		if (Util.isVazio(parametro))
			throw new ParseException("CNPJ NAO INFORMADO.", 0);

		if (parametro.length() != 18)
			throw new ParseException("TAMANHO DO CNPJ INVALIDO", 0);

		String retorno = parametro.substring(0, 2) + parametro.substring(3, 6) + parametro.substring(7, 10) + parametro.substring(11, 15)
				+ parametro.substring(16, 18);

		checarDesformatacao(retorno);

		return retorno;

	}
	
	
	/**
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isValid(final String str) {
		return str != null && !str.isEmpty();
	}

	/**
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isValid(final Object obj) {
		return obj != null;
	}

}
