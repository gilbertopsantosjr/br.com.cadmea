package br.com.cadmea.comuns.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Time;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintViolation;

import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.Appender;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import br.com.cadmea.comuns.clazz.ObjetoTemplateMensagemVo;

public class Util {

	static Logger logger;
	public static String[] REPLACES = { "a", "e", "i", "o", "u", "c" };
	public static Pattern[] PATTERNS = null;

	static char f[] = { 32, 137, 239, 188, 102, 125, 221, 72, 212, 68, 81, 37, 86, 237, 147, 149, 70, 229, 17, 124, 115, 207, 33, 20, 122,
			143, 25, 215, 51, 183, 138, 142, 146, 211, 110, 173, 1, 228, 189, 14, 103, 78, 162, 36, 253, 167, 116, 255, 158, 45, 185, 50,
			98, 168, 250, 235, 54, 141, 195, 247, 240, 63, 148, 2, 224, 169, 214, 180, 62, 22, 117, 108, 19, 172, 161, 159, 160, 47, 43,
			171, 194, 175, 178, 56, 196, 112, 23, 220, 89, 21, 164, 130, 157, 8, 85, 251, 216, 44, 94, 179, 226, 38, 90, 119, 40, 202, 34,
			206, 35, 69, 231, 246, 29, 109, 74, 71, 176, 6, 60, 145, 65, 13, 77, 151, 12, 127, 95, 199, 57, 101, 5, 232, 150, 210, 129, 24,
			181, 10, 121, 187, 48, 193, 139, 252, 219, 64, 88, 233, 96, 128, 80, 53, 191, 144, 218, 11, 106, 132, 155, 104, 91, 136, 31,
			42, 243, 66, 126, 135, 30, 26, 87, 186, 182, 154, 242, 123, 82, 166, 208, 39, 152, 190, 113, 205, 114, 105, 225, 84, 73, 163,
			99, 111, 204, 61, 200, 217, 170, 15, 198, 28, 192, 254, 134, 234, 222, 7, 236, 248, 201, 41, 177, 156, 92, 131, 67, 249, 245,
			184, 203, 9, 241, 0, 27, 46, 133, 174, 75, 18, 93, 209, 100, 120, 76, 213, 16, 83, 4, 107, 140, 52, 58, 55, 3, 244, 97, 197,
			238, 227, 118, 49, 79, 230, 223, 165, 153, 59 };

	static char[] code;

	static final String caracteresCodigo = "ABCDEFGHIJKLMNOPQRSTUVXWYZ";
	static final String caracteresCodigoNumerico = "1234567890";
	static final String caracteresCodigoAlfaNumerico = "1234567890ABCDEFGHIJKLMNOPQRSTUVXWYZ";

	public static String obterMensagemDoArquivoDePropriedades(ObjetoTemplateMensagemVo otm) {
		StringBuilder mensagemCompleta = new StringBuilder();
		Locale locale = new Locale(otm.getIdiomasDoSistema().getDescricao());
		ResourceBundle bundle = ResourceBundle.getBundle(ConstantesComum.APP_MENSAGENS_FILE, locale);
		
		if (bundle.containsKey(otm.obterMensagemNoPattern()))
			mensagemCompleta.append(bundle.getString(otm.obterMensagemNoPattern()));
		
		if (bundle.containsKey(otm.getChaveTemplate()))
			mensagemCompleta.append(bundle.getString(otm.getChaveTemplate()));
		
		return mensagemCompleta.toString();
	}
	
	public static String getCurrentDate(String pattern){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(new Date());
	}
	
	public static ObjetoTemplateMensagemVo obterObjetoTemplateMensagemVo(ConstraintViolation<?> violacao){
		ObjetoTemplateMensagemVo otm = new ObjetoTemplateMensagemVo();
		if (violacao != null) {
			otm.setModelo(violacao.getRootBeanClass().getCanonicalName());
			otm.setAtributo(violacao.getPropertyPath().toString());
			otm.setChaveTemplate(violacao.getMessageTemplate());
		}
		return otm;
	}

	public static String geraCodigo(int tamanhoCodigo) {
		Random geradorAleatorio = new Random();
		int indiceAleatorio;
		StringBuffer bufferCodigo = new StringBuffer();
		for (int i = 0; i < tamanhoCodigo; i++) {
			indiceAleatorio = geradorAleatorio.nextInt(caracteresCodigo.length());
			bufferCodigo.append(caracteresCodigo.charAt(indiceAleatorio));
		}
		return bufferCodigo.toString();
	}

	public static String generatorNumericCode(int tamanhoCodigo) {
		Random geradorAleatorio = new Random();
		int indiceAleatorio;
		StringBuffer bufferCodigo = new StringBuffer();
		for (int i = 0; i < tamanhoCodigo; i++) {
			indiceAleatorio = geradorAleatorio.nextInt(caracteresCodigoNumerico.length());
			bufferCodigo.append(caracteresCodigoNumerico.charAt(indiceAleatorio));
		}
		return bufferCodigo.toString();
	}

	public static String getResourceProperty(Object clazz, String nomeArquivoResource, String propriedade) {

		Properties props = new Properties();
		String resource = nomeArquivoResource;

		InputStream stream = clazz.getClass().getClassLoader().getResourceAsStream(resource);

		try {
			props.load(stream);
			return props.getProperty(propriedade);

		} catch (IOException ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		} finally {
			try {
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static double diferencaEmHoras(Date dataInicial, Date dataFinal) {
		long diferenca = dataFinal.getTime() - dataInicial.getTime();
		double diferencaEmDias = (diferenca / 1000) / 60 / 60 / 24;
		long horasRestantes = (diferenca / 1000) / 60 / 60 % 24;
		return (diferencaEmDias * 24) + horasRestantes;
	}

	public static double diferencaEmMinutos(Date dataInicial, Date dataFinal) {
		long diferenca = dataFinal.getTime() - dataInicial.getTime();
		double diferencaEmMinutos = (diferenca / 1000) / 60;
		return diferencaEmMinutos;
	}

	public static String getResourceProperty(String nomeArquivoResource, String propriedade) {

		Properties props = new Properties();

		try {
			props.load(new FileInputStream(new File(nomeArquivoResource)));
			return props.getProperty(propriedade);

		} catch (IOException ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		return null;
	}

	public static void registrarLog(String mensagem, String nomeArquivo, Exception exception, Class<?> classe) {

		logger = classe != null ? Logger.getLogger(classe) : Logger.getRootLogger();

		BasicConfigurator.configure();
		Appender fileAppender;
		try {
			fileAppender = new FileAppender(new PatternLayout(PatternLayout.TTCC_CONVERSION_PATTERN), nomeArquivo + "_"
					+ removerCaracterDeString(DateUtil.formataData(new Date()), '/') + ".log");
			logger.addAppender(fileAppender);
			if (exception == null)
				logger.info(DateUtil.formataData(new Date()) + " " + DateUtil.formatarHora(new Time(new Date().getTime())) + " > "
						+ mensagem);
			else
				logger.error(DateUtil.formataData(new Date()) + " " + DateUtil.formatarHora(new Time(new Date().getTime())) + " > "
						+ exception.getMessage(), exception);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static String criptografarMD5(String texto) {

		try {
			MessageDigest mdTexto = MessageDigest.getInstance("MD5");
			BigInteger hash = new BigInteger(mdTexto.digest(texto.getBytes()));

			char[] encodeHex = Hex.encodeHex(hash.toByteArray());

			return new String(encodeHex);

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean validaEnderecoIp(String endereco) {

		// Validando mÃ¡scaras padrÃ£o: 0.0.0.0, 1.1.1.1, 255.255.255.0 (netmask)
		// ou 255.255.255.255 (broadcast)

		if (endereco.equals("0.0.0.0")) {
			return false;
		} else if (endereco.equals("1.1.1.1")) {
			return false;
		} else if (endereco.equals("255.255.255.0")) {
			return false;
		} else if (endereco.equals("255.255.255.255")) {
			return false;
		}

		// SenÃ£o, vamos procurar saber se Ã© realmente vÃ¡lido!

		Pattern p = Pattern
				.compile("((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9][0-9]|[0-9])\\.){3}(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9][0-9]|[0-9])");
		Matcher m = p.matcher(endereco);
		return m.matches();
	}

	public static String addTrim(String parametro) {
		if (!isVazio(parametro))
			return parametro.trim();
		else
			return "";
	}

	public static boolean verificarDesformatacao(String parametro) {
		for (int posicao = 0; posicao < parametro.length(); posicao++) {
			if (!Character.isDigit(parametro.charAt(posicao)))
				return false;
		}
		return true;
	}

	public static void checarNull(Object valor) throws ParseException {
		if (valor == null)
			throw new ParseException("VALOR NULO", 0);

	}

	public static void checarString(String parametro) throws ParseException {
		checarString(parametro, null);
	}

	private static void checarString(String parametro, String mensagem) throws ParseException {
		for (int posicao = 0; posicao < mensagem.length(); posicao++) {
			if (!Character.isDigit(parametro.charAt(posicao))) {
				if (mensagem != null)
					throw new ParseException(mensagem, 0);
			}
		}
	}

	public static String checkNullResult(String parametro) {
		if (parametro == null)
			return "NULO";
		else
			return parametro.trim();
	}

	public static String desformatarCep(String parametro) {
		String retorno = parametro.substring(0, 5) + parametro.substring(6, 9);
		return retorno;
	}

	public static String desformatarValor(String parametro) {

		String retorno = "";

		int x, y, tamanho;

		tamanho = parametro.length() + 1;

		if (parametro.indexOf(",") != -1) {
			for (x = 0, y = 1; y < tamanho; x++, y++) {
				if (parametro.substring(x, y).compareTo(".") == 0)
					continue;
				retorno = retorno + parametro.substring(x, y);
			}

			retorno = retorno.replace(',', '.');
		} else {
			retorno = parametro;
		}
		return (retorno);

	}

	public static String envolverString(String texto, String existente, String texto1, String texto2) {
		int len = existente.length();
		int pos = texto.indexOf(existente);
		int oldpos = 0;
		StringBuffer ret = new StringBuffer();
		for (; pos >= 0; pos = texto.indexOf(existente, oldpos)) {
			ret.append(texto.substring(oldpos, pos));
			ret.append(texto1);
			ret.append(texto.substring(pos, pos + len));
			ret.append(texto2);
			oldpos = pos + len;
		}

		ret.append(texto.substring(oldpos));
		return ret.toString();
	}

	
	

	/**
	 * Retira acentos de vogais e troca ï¿½ por c. Alï¿½m disso esse mï¿½todo retira
	 * espaï¿½os duplicados. (Versï¿½o para tipos String) Retorna uma nova instï¿½ncia
	 * de String contendo o texto da String de entrada com os caracteres
	 * acentuados e o 'ï¿½' trocados pelos seus correspondentes normais. Data de
	 * criaï¿½ï¿½o: (21/08/2001 10:51:07)
	 * 
	 * Exemplo:
	 * 
	 * Seja a chamada:
	 * 
	 * TratadorTexto.filtrarAcentos("Nï¿½s nï¿½o comemos maï¿½ï¿½.")
	 * 
	 * O retorno serï¿½:
	 * 
	 * "Nos nao comemos maca."
	 * 
	 * @return String
	 * @param texto
	 *            String
	 */

	public static String filtrarEspacosRepetidos(String texto) {
		String result = texto.toString().trim();
		while (result.indexOf("  ") != -1) {
			result = result.toString().replaceAll("  ", " ");
		}
		return result;
	}

	public static Date parseData(String strFormato, String strDate) {
		SimpleDateFormat format = new SimpleDateFormat(strFormato);

		try {
			return format.parse(strDate);
		} catch (Exception e) {
			return null;
		}
	}

	public static String formatarCEP(String parametro) {

		String retorno = null;

		if (!isVazio(parametro)) {

			if (parametro.length() == 8) {
				retorno = parametro.substring(0, 5) + "-" + parametro.substring(5, 8);

			} else {
				retorno = parametro;

				for (int i = parametro.length(); i < 8; i++)
					retorno = "0" + retorno;

				retorno = retorno.substring(0, 5) + "-" + retorno.substring(5, 8);

			}

		}

		return retorno;

	}

	public static String formatarCNPJ(String parametro) {

		String retorno = null;

		if (!isVazio(parametro)) {

			if (parametro.length() == 14) {
				retorno = parametro.substring(0, 2) + "." + parametro.substring(2, 5) + "." + parametro.substring(5, 8) + "/"
						+ parametro.substring(8, 12) + "-" + parametro.substring(12, 14);

			} else {
				retorno = parametro;

				for (int i = parametro.length(); i < 14; i++)
					retorno = "0" + retorno;

				retorno = retorno.substring(0, 2) + "." + retorno.substring(2, 5) + "." + retorno.substring(5, 8) + "/"
						+ retorno.substring(8, 12) + "-" + retorno.substring(12, 14);

			}

		}

		return retorno;

	}

	public static boolean isVazio(String valor) {
		return valor == null || (valor.trim()).length() == 0;
	}

	public static boolean isVazio(Object[] obj) {
		return obj == null || obj.length == 0;
	}

	public static boolean isVazio(Object obj) {
		return obj == null;
	}

	public static boolean isVazio(char valor) {
		Character character = new Character(valor);
		return isVazio(character.toString());
	}

	public static boolean isVazio(Collection<?> lista) {
		return lista == null || lista.isEmpty();
	}

	public static boolean isVazio(Map<?, ?> mapa) {
		return mapa == null || mapa.isEmpty();
	}

	public static boolean isVazio(Date date) {
		return date == null || (date.toString().trim()).length() == 0;
	}

	public static boolean isVazio(Double valor) {
		return valor == null || (valor.toString().trim()).length() == 0;
	}

	public static boolean isVazio(Integer valor) {
		return valor == null || (valor.toString().trim()).length() == 0;
	}

	public static boolean isVazio(Long valor) {
		return valor == null || (valor.toString().trim()).length() == 0;
	}

	/**
	 * Retira de 'texto' os brancos do inicio e do fim e os brancos excedentes
	 * entre as palavras. Este metodo substitui os caracteres com codigo menor
	 * ou igual /u0020 (espaco em branco e caracteres de controle) de entre as
	 * palavras do texto por um unico espaco em branco e exclui as ocorrencias
	 * destes caracteres do inicio e do fim do texto.
	 * 
	 * @return String
	 * @param texto
	 *            String;
	 */
	public static String limparBrancosExcedentes(String texto) {
		int i;
		char c;
		StringBuffer ret = new StringBuffer();
		String txt = texto.trim();

		if (txt == null)
			return "";
		if (txt == texto)
			txt = new String(texto);

		for (i = 0; i < txt.length(); i++) {
			c = txt.charAt(i);
			if (c > '\u0020') {
				ret.append(c);
			} else if (ret.length() > 0 && ret.charAt(ret.length() - 1) > '\u0020') {
				ret.append('\u0020');
			}
		}

		return ret.toString();
	}

	private static boolean matchFilenameMask(String filename, String mascara) {
		if (mascara == null || mascara.trim().equals("*")) {
			return true;
		}

		String nomeArq = null;
		String extensao = null;
		int index = filename.indexOf(".");

		if (index == -1) {
			nomeArq = filename;
		} else {
			nomeArq = filename.substring(0, index);
			extensao = filename.substring(index + 1);
		}

		String nomeArqMask = null;
		String extensaoMask = null;

		index = mascara.indexOf(".");
		if (index == -1) {
			extensaoMask = mascara;
		} else {
			nomeArqMask = mascara.substring(0, index);
			extensaoMask = mascara.substring(index + 1);
		}

		return (matchMask(nomeArq, nomeArqMask) && matchMask(extensao, extensaoMask));
	}

	// Checa se o alvo passado bate com a mï¿½scara.

	private static boolean matchMask(String alvo, String mascara) {
		if ((mascara == null && alvo == null) || (mascara.trim().equals("*") && alvo != null)) {
			return true;
		}

		if ((mascara != null && alvo == null) || (mascara == null && alvo != null)) {
			return false;
		}

		// Agora, serï¿½o avaliadas as 3 possibilidades para a mï¿½scara: asterisco
		// no INICIO, MEIO ou FIM.
		// Para isso, a mï¿½scara serï¿½ dividida de acordo com as apariï¿½ï¿½es do
		// asterisco, sendo que o mï¿½ximo que se
		// pode ter ï¿½: [TEXTO][*]TEXTO[*][TEXTO].
		// Sendo assim, os trï¿½s pontos possï¿½veis para a apariï¿½ï¿½o de 'TEXTO' serï¿½
		// lanï¿½ada no array de 3 posiï¿½ï¿½es abaixo.
		// A primeira posiï¿½ï¿½o diz respeito ao inicio da mï¿½scara, ou seja, o alvo
		// deve comeï¿½ar com esta string.
		// A segunda posiï¿½ï¿½o dis respeito ao meio da mï¿½scara, ou seja, o alvo
		// tem que ter esse texto.
		// E a terceira indica que o alvo deve terminar com a string em questï¿½o.
		String possibilidade[] = new String[3];
		int posicao = 0;

		for (StringTokenizer stringtokenizer = new StringTokenizer(mascara, "*", true); stringtokenizer.hasMoreElements();) {
			String token = stringtokenizer.nextToken();
			if (token.equals("*")) {
				posicao++;
			} else {
				possibilidade[posicao] = token;
			}
		}

		if (posicao >= 3) {
			throw new RuntimeException("A m\341scara utilizada est\341 fora do padr\343o");
		}

		int inicio = 0;
		int fim = alvo.length();

		String inicioMask = possibilidade[0];
		String meioMask = possibilidade[1];
		String fimMask = possibilidade[2];

		// checa se o inï¿½cio do 'alvo' bate com o inï¿½cio da mï¿½scara
		if (inicioMask != null) {
			if (!alvo.startsWith(inicioMask)) {
				return false;
			}
			inicio = inicioMask.length();
		}

		// checa se o fim do 'alvo' bate com o fim da mï¿½scara
		if (fimMask != null) {
			if (!alvo.endsWith(fimMask)) {
				return false;
			}
			fim -= fimMask.length();
		}

		// checa possibilidade MEIO
		return ((meioMask == null) || (alvo.substring(inicio, fim).indexOf(meioMask) != -1));

	}

	/**
	 * Centraliza um objeto em um determinado espaï¿½o de caracteres. Data de
	 * criaï¿½ï¿½o: (11/3/2003 12:11:32)
	 * 
	 * @return java.lang.String
	 * @param texto
	 *            java.lang.String
	 */
	public static String padC(String valor, int comprimento) {

		String retorno = " ";
		int mediaDiferenca = 0;
		if (comprimento > valor.length())
			mediaDiferenca = (int) (comprimento - valor.length()) / 2;

		if (!isVazio(valor)) {
			retorno = valor;

			for (int i = 0; i < mediaDiferenca; i++)
				retorno = " " + retorno;

			for (int i = 0; i < (comprimento - valor.length() - mediaDiferenca); i++)
				retorno = retorno + " ";

		}

		return retorno;
	}

	public static String padL(double valor, int comprimento) {

		String retorno = valor + "";

		if (comprimento > 0) {

			try {
				String dado = String.valueOf(valor);

				retorno = dado;

				for (int i = 0; i < comprimento - dado.length(); i++)
					retorno = retorno + " ";

			} catch (Exception e) {

			}

		}

		return retorno;

	}

	public static String padL(int valor, int comprimento) {

		String retorno = valor + "";

		if (comprimento > 0) {

			try {
				String dado = String.valueOf(valor);

				retorno = dado;

				for (int i = 0; i < comprimento - dado.length(); i++)
					retorno = retorno + " ";

			} catch (Exception e) {

			}

		}

		return retorno;

	}

	public static String padL(String valor, int comprimento) {

		String retorno = "";

		if (!isVazio(valor)) {
			retorno = valor;

			for (int i = 0; i < comprimento - valor.length(); i++)
				retorno = retorno + " ";

		}

		return retorno;

	}

	public static String padR(double valor, int comprimento) {

		String retorno = valor + "";

		if (comprimento > 0) {

			try {
				String dado = String.valueOf(valor);

				retorno = dado;

				for (int i = 0; i < comprimento - dado.length(); i++)
					retorno = " " + retorno;

			} catch (Exception e) {

			}

		}

		return retorno;

	}

	public static String padR(int valor, int comprimento) {

		String retorno = valor + "";

		if (comprimento > 0) {

			try {
				String dado = String.valueOf(valor);

				retorno = dado;

				for (int i = 0; i < comprimento - dado.length(); i++)
					retorno = " " + retorno;

			} catch (Exception e) {

			}

		}

		return retorno;

	}

	public static String padR(String valor, int comprimento) {

		String retorno = " ";

		if (!isVazio(valor)) {
			retorno = valor;

			for (int i = 0; i < comprimento - valor.length(); i++)
				retorno = " " + retorno;

		}

		return retorno;

	}

	public static String removeCaracterFromString(String valor, char dado) {

		String retorno = valor;

		for (int i = retorno.indexOf(dado); i != -1; i = retorno.indexOf(dado, i))
			retorno = retorno.substring(0, i) + retorno.substring(i + 1);

		return retorno;

	}

	public static String removeCaracterFromString(String valor, String dado) {

		String retorno = valor;

		for (int i = 0; i < dado.length(); i++)
			retorno = removeCaracterFromString(retorno, dado.charAt(i));

		return retorno;
	}

	public static String removerCaracterDeString(String valor, char dado) {

		String auxiliar = valor;

		for (int i = auxiliar.indexOf(dado); i != -1; i = auxiliar.indexOf(dado, i))
			auxiliar = auxiliar.substring(0, i) + auxiliar.substring(i + 1);

		return auxiliar;

	}

	public static String removerCaracterDeString(String valor, String dado) {

		String auxiliar = valor;

		for (int i = 0; i < dado.length(); i++)
			auxiliar = removeCaracterFromString(auxiliar, dado.charAt(i));

		return auxiliar;

	}

	/**
	 * Retira de 'texto' todas as ocorrï¿½ncias dos caracteres presentes em
	 * 'caracteres'. Data de criaï¿½ï¿½o: (21/08/2001 18:50)
	 * 
	 * Veja as chamadas e os seus respectivos valores de retorno:
	 * 
	 * TratadorTexto.retirarCaracteres("Olï¿½ mundo java!", "jï¿½") > "Ol mundo ava"
	 * TratadorTexto.retirarCaracteres("Olï¿½ mundo java!", " ") > "Olï¿½mundojava!"
	 * TratadorTexto.retirarCaracteres("Olï¿½ mundo java!", "Ooa") > "lï¿½ mund jv!"
	 * TratadorTexto.retirarCaracteres("Olï¿½ *mundo))) ja~va!", "~!>*") >
	 * "Olï¿½ mundo java"
	 * 
	 * @return String
	 * @param texto
	 *            String;
	 * @param caracteres
	 *            String;
	 */
	public static String retirarCaracteres(String texto, String caracteres) {
		int i, j;
		StringBuffer ret = new StringBuffer();
		boolean inserir;

		for (i = 0; i < texto.length(); i++) {
			inserir = true;
			for (j = 0; j < caracteres.length() && inserir; j++)
				if (texto.charAt(i) == caracteres.charAt(j))
					inserir = false;
			if (inserir)
				ret.append(texto.charAt(i));
		}
		return ret.toString();
	}

	public static String strZero(String parametro, int comprimento) {

		String retorno = "";

		if (!isVazio(parametro) && comprimento >= 0) {

			retorno = addTrim(parametro);

			if (parametro.length() >= 0 && parametro.length() < comprimento) {

				for (int i = 0; i < comprimento - parametro.length(); i++)
					retorno = "0" + retorno;

			}

		}

		return retorno;

	}

	/**
	 * Este mï¿½todo troca todas as referï¿½ncias a 'oldText' por 'newText'. Este
	 * mï¿½todo foi criado para que se pudesse alterar todos os arquivos abaixo de
	 * um determinado diretï¿½rio de sï¿½ uma vez.
	 * 
	 * @param diretorio
	 *            File apontando para o diretï¿½rio raiz para a
	 *            pesquisa/substituiï¿½ï¿½o
	 * @param oldText
	 *            texto a ser substituï¿½dos
	 * @param newText
	 *            novo texto para o lugar de oldText
	 * @param pesquisaSubDir
	 *            variï¿½vel booleana para o caso de se desejar somente alterar no
	 *            diretï¿½rio raiz
	 * @param qtdFilesChanged
	 *            array de uma posiï¿½ï¿½o (inicializada com zero) que retornarï¿½ a
	 *            quantidade de arquivos onde a alteraï¿½ï¿½o foi realizada.
	 * 
	 */
	public static void substituirString(File dir, String oldText, String newText, boolean pesquisaSubDir, int qtdFilesChanged[])
			throws IOException, FileNotFoundException {
		substituirString(dir, oldText, newText, pesquisaSubDir, null, qtdFilesChanged);
	}

	/**
	 * Este mï¿½todo troca todas as referï¿½ncias a 'oldText' por 'newText'. Este
	 * mï¿½todo foi criado para que se pudesse alterar vï¿½rios os arquivos abaixo
	 * de um determinado diretï¿½rio de sï¿½ uma vez. Para isso, ele recebe, dentre
	 * os vï¿½rios parï¿½metros uma mï¿½scara. Esta mï¿½scara ï¿½ utilizada para se fazer
	 * um filtro nos nomes dos arquivos a serem alterados e deve conter, no
	 * mï¿½ximo, dois asteriscos, sendo que as possibilidades sï¿½o:
	 * [texto][*][texto][*][texto]. As seguintes mï¿½scaras, portanto, seriam
	 * vï¿½lidas: *.jsp, *.html, index*.txt, *index*.jsp, *in*dex.jsp, in*de*x.jsp
	 * 
	 * 
	 * @param diretorio
	 *            File apontando para o diretï¿½rio raiz para a
	 *            pesquisa/substituiï¿½ï¿½o
	 * @param oldText
	 *            texto a ser substituï¿½dos
	 * @param newText
	 *            novo texto para o lugar de oldText
	 * @param pesquisaSubDir
	 *            variï¿½vel booleana para o caso de se desejar somente alterar no
	 *            diretï¿½rio raiz
	 * @param mascara
	 *            mascara utilizada na pesquisa dos arquivos onde serï¿½o
	 *            realizadas as substituiï¿½ï¿½es
	 * @param qtdFilesChanged
	 *            array de uma posiï¿½ï¿½o (inicializada com zero) que retornarï¿½ a
	 *            quantidade de arquivos onde a alteraï¿½ï¿½o foi realizada.
	 * 
	 */
	public static void substituirString(File diretorio, String oldText, String newText, boolean pesquisaSubDir, String mascara,
			int qtdFilesChanged[]) throws IOException, FileNotFoundException {

		if (diretorio.isFile() && matchFilenameMask(diretorio.getName(), mascara)) {

			FileInputStream fis = new FileInputStream(diretorio);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte buffer[] = new byte[256];
			int qtdBytesLidos;

			while ((qtdBytesLidos = fis.read(buffer)) != -1) {
				baos.write(buffer, 0, qtdBytesLidos);
			}

			fis.close();
			baos.close();

			String conteudo = new String(baos.toByteArray());
			conteudo = substituirString(conteudo, oldText, newText);
			FileOutputStream fos = new FileOutputStream(diretorio);
			fos.write(conteudo.getBytes());
			fos.close();
			qtdFilesChanged[0]++;

			System.out.print("file seached => " + diretorio);
			if ((new String(baos.toByteArray())).indexOf(oldText) != -1) {
				System.out.println(" (file changed)");
				qtdFilesChanged[0] += 1;
			} else {
				System.out.println("\n");
			}

		} else if (pesquisaSubDir && diretorio.isDirectory()) {
			String files[] = diretorio.list();
			for (int i = 0; i < files.length; i++) {
				substituirString(new File(diretorio, files[i]), oldText, newText, pesquisaSubDir, mascara, qtdFilesChanged);
			}
		}

	}

	/**
	 * Substitui em 'texto' todas as ocorrencias de 'existente' por 'nova'. Data
	 * de criaï¿½ï¿½o: (22/08/2001 14:30)
	 * 
	 * Veja as chamadas e os seus respectinvo retornos:
	 * 
	 * TratadorTexto.substituirString("Olï¿½ mundo java!", "java", "cruel") >
	 * "Olï¿½ mundo cruel!" TratadorTexto.substituirString("Olï¿½ mundo java!", " ",
	 * "%") > "Olï¿½%mundo%java!"
	 * TratadorTexto.substituirString("Olï¿½ mundo java!", "a", "ah") >
	 * "Olï¿½ mundo jahvah!"
	 * 
	 * 
	 * @return String
	 * @param texto
	 *            String;
	 * @param existente
	 *            String;
	 * @param nova
	 *            String;
	 */
	public static String substituirString(String texto, String existente, String nova) {
		int len = existente.length();
		int pos = texto.indexOf(existente);
		int oldpos = 0;
		StringBuffer ret = new StringBuffer();

		while (pos >= 0) {
			ret.append(texto.substring(oldpos, pos));
			ret.append(nova);
			oldpos = pos + len;
			pos = texto.indexOf(existente, oldpos);
		}
		ret.append(texto.substring(oldpos));

		return ret.toString();
	}

	public static String substituirStringSemCase(String texto, String existente, String nova) {
		int len = existente.length();
		int pos = texto.toLowerCase().indexOf(existente.toLowerCase());
		int oldpos = 0;
		StringBuffer ret = new StringBuffer();
		for (; pos >= 0; pos = texto.toLowerCase().indexOf(existente.toLowerCase(), oldpos)) {
			ret.append(texto.substring(oldpos, pos));
			ret.append(nova);
			oldpos = pos + len;
		}

		ret.append(texto.substring(oldpos));
		return ret.toString();
	}

	/**
	 * Preenche espaï¿½os vazios como se fosse uma tabulaï¿½ï¿½o pontilhada.
	 * Observaï¿½ï¿½o: O "comprimento" passado como parï¿½metro deve ser, no mï¿½nimo, a
	 * quantidade de caracteres do parï¿½metro "valor" + 2. Data de criaï¿½ï¿½o:
	 * (26/3/2003 12:11:32)
	 * 
	 * @return java.lang.String
	 * @param texto
	 *            java.lang.String
	 * @param comprimento
	 */
	public static String tabPonto(String valor, int comprimento) {

		String retorno = " ";
		int preenchimento = 0;

		if (valor != null && valor.length() > 0) {
			retorno = valor;
			preenchimento = comprimento - valor.length();
			for (int i = 0; i < preenchimento; i++) {
				if (i == (preenchimento - 2))
					retorno = retorno + ": ";
				else if (i < (preenchimento - 2))
					retorno = retorno + ".";
				else if (preenchimento == 1)
					retorno = retorno + ":";
			}
		}

		return retorno;

	}

	public static String tratarMensagemErro(String parametro) {

		if (!isVazio(parametro))
			return "\n<inicio-mensagem-erro>\n" + addTrim(parametro) + "\n<fim-mensagem-erro>";
		else
			return "\n<inicio-mensagem-erro>\nMENSAGEM DE ERRO Nï¿½O INFORMADA\n<fim-mensagem-erro>";

	}

	public static String tratarMensagemErro(String mensagemErro, String pilhaErro) {

		String retorno = null;

		if (!isVazio(mensagemErro))
			retorno = "\n<inicio-mensagem-erro>\n" + addTrim(mensagemErro) + "\n<fim-mensagem-erro>";
		else
			retorno = "\n<inicio-mensagem-erro>\nMENSAGEM DE ERRO Nï¿½O INFORMADA\n<fim-mensagem-erro>";

		if (!isVazio(pilhaErro))
			retorno += "\n<inicio-pilha-erro>\n" + addTrim(pilhaErro) + "\n<fim-pilha-erro>";
		else
			retorno += "\n<inicio-pilha-erro>\nPILHA DE ERRO Nï¿½O INFORMADA\n<fim-pilha-erro>";

		return retorno;

	}

	/**
	 * Insert the method's description here. Creation date: (23/03/2001
	 * 09:24:22)
	 * 
	 * @return java.lang.String
	 * @param parametro
	 *            java.lang.String
	 */
	public static String tratarStringDB2(String parametro) {

		String retorno = null;
		String auxiliar = parametro.trim();

		boolean finaliza = false;

		if (auxiliar.length() <= 255) {
			retorno = "'" + auxiliar + "'";

		} else {
			while (auxiliar.length() > 255) {

				if (auxiliar.length() == 255) {
					retorno = retorno + "'" + auxiliar.substring(0, 254) + "'";
					finaliza = true;

				} else {
					retorno = retorno + "'" + auxiliar.substring(0, 254) + "'||";

				}

				auxiliar = auxiliar.substring(254);

			}

			if (!finaliza)
				retorno = retorno + "'" + auxiliar + "'";

		}

		return retorno;

	}

	public static String trocaPontoPorVirgula(String parametro) {
		return parametro.replace('.', ',');

	}

	public static String trocaVirgulaPorPonto(String parametro) {
		return parametro.replace(',', '.');

	}

	public static boolean validarDDD(String parametro) {

		boolean retorno = true;

		try {
			double valor = Double.parseDouble(parametro);

			if (valor <= 0 || valor > 999)
				retorno = false;

		} catch (Exception e) {
			retorno = false;

		}

		return retorno;

	}

	public static boolean validarEmail(String parametro) {

		boolean retorno = true;

		if (isVazio(parametro)) {
			retorno = false;

		} else if (parametro.indexOf("@") <= 0) {
			retorno = false;

		} else if (parametro.substring(0, parametro.indexOf("@")).length() <= 0) {
			retorno = false;

		} else if (parametro.substring(parametro.indexOf("@") + 1).length() <= 0) {
			retorno = false;

		} else if (parametro.substring(parametro.indexOf("@") + 1).indexOf("@") > 0) {
			retorno = false;

		} else if ((parametro.substring(parametro.indexOf("@") + 1)).indexOf(".") <= 0) {
			retorno = false;

		} else if (parametro.substring(parametro.indexOf(".") + 1).length() <= 0) {
			retorno = false;

		} else if (parametro.substring(parametro.indexOf(".") + 1).length() > 0) {
			String auxiliar = parametro.substring(parametro.indexOf(".") + 1);

			if (auxiliar.length() > 1 && auxiliar.substring(auxiliar.indexOf(".") + 1).length() <= 0) {
				retorno = false;

			} else {
				while (auxiliar.length() > 1 && auxiliar.substring(auxiliar.indexOf(".") + 1).length() > 0) {

					if (auxiliar.indexOf(".") > 0)
						auxiliar = auxiliar.substring(auxiliar.indexOf(".") + 1);
					else
						break;

				}

				if (auxiliar.indexOf(".") > 0)
					retorno = false;
				else
					retorno = true;

			}

		}

		return retorno;

	}

	public static boolean validarTelefone(String parametro) {

		boolean retorno = true;

		try {
			double valor = Double.parseDouble(parametro);

			if (valor <= 0)
				retorno = false;

		} catch (Exception e) {
			retorno = false;

		}

		return retorno;

	}

	/**
	 * Recebe data no format dd/mm/aaaa e valida o dia, mï¿½s e ano.
	 * 
	 * @param data
	 * @return true or false
	 */
	public static boolean validaData(String data) {
		GregorianCalendar calendar = new GregorianCalendar();
		int dia = 0, mes = 0, ano = 0;
		String diaStr = data.substring(0, data.indexOf("/"));
		String mesStr = data.substring(data.indexOf("/") + 1, data.lastIndexOf("/"));
		String anoStr = data.substring(data.lastIndexOf("/") + 1, data.length());
		try {
			dia = Integer.parseInt(diaStr);
			mes = Integer.parseInt(mesStr);
			ano = Integer.parseInt(anoStr);
		} catch (Exception e) {
			return false;
		}
		// Verifica se o ano ï¿½ maior que 1900
		if (ano < 1900)
			return false;

		if (dia < 1 || mes < 1 || ano < 1)
			return false;
		else if (mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 8 || mes == 10 || mes == 12)
			return dia <= 31;
		else if (mes == 4 || mes == 6 || mes == 9 || mes == 11)
			return dia <= 30;
		else if (mes == 2)
			if (calendar.isLeapYear(ano))
				return dia <= 29;
			else
				return dia <= 28;
		else
			return !(mes > 12);
		/*
		 * GregorianCalendar calendar = new GregorianCalendar(); int dia = 0,
		 * mes = 0, ano = 0; if (!data.equals("")) { String diaStr =
		 * data.substring(0, 2); String mesStr = data.substring(3, 5); String
		 * anoStr = data.substring(6, 10); try { dia = Integer.parseInt(diaStr);
		 * mes = Integer.parseInt(mesStr); ano = Integer.parseInt(anoStr); }
		 * catch (Exception e) { return false; } if (dia < 1 || mes < 1 || ano <
		 * 1) return false; else if (mes == 1 || mes == 3 || mes == 5 || mes ==
		 * 7 || mes == 8 || mes == 10 || mes == 12) if (dia <= 31) return true;
		 * else return false; else if (mes == 4 || mes == 6 || mes == 9 || mes
		 * == 11) if (dia <= 30) return true; else return false; else if (mes ==
		 * 2) if (calendar.isLeapYear(ano)) if (dia <= 29) return true; else
		 * return false; else if (dia <= 28) return true; else return false;
		 * else if (dia <= 28) return true; else return false; // return true; }
		 * return true;
		 */
	}

	/**
	 * Recebe um objeto String e um ï¿½ndice da quebra da string, consequentemente
	 * serï¿½ adicionado reticï¿½ncias na String de retorno. Lembre-se que a
	 * contagem comeï¿½a do ZERO.
	 * 
	 * Exemplo:
	 * 
	 * String result = truncarString("ABCDE", 2);
	 * 
	 * result == "ABC..."
	 * 
	 * @param alvo
	 * @param indiceQuebra
	 * @return String
	 */
	public static String truncarString(String alvo, int indiceQuebra) {
		StringBuffer result = new StringBuffer(alvo);
		if (result.length() > indiceQuebra) {
			result = new StringBuffer(alvo.substring(0, indiceQuebra));
			result.append("...");
		}
		return result.toString();
	}

	/**
	 * Coloca em maiï¿½scula um caracter da String fornecida de acordo com um
	 * index.
	 * 
	 * @param alvo
	 * @param index
	 * @return
	 */
	public static String transMaiuscula(String alvo, int index) throws StringIndexOutOfBoundsException {
		StringBuffer result = new StringBuffer(alvo);
		return result.replace(index, (index + 1), alvo.substring(index, (index + 1)).toUpperCase()).toString();
	}

	/**
	 * Coloca em minï¿½scula um caracter da String fornecida de acordo com um
	 * index.
	 * 
	 * @param alvo
	 * @param index
	 * @return
	 */
	public static String transMinuscula(String alvo, int index) throws StringIndexOutOfBoundsException {
		StringBuffer result = new StringBuffer(alvo);
		return result.replace(index, (index + 1), alvo.substring(index, (index + 1)).toLowerCase()).toString();
	}

	/**
	 * Retorna a representaï¿½ï¿½o em <code>java.util.Date</code> do tempo de jogo.
	 * Espera-se um texto no formato mm'ss.
	 * 
	 * @param tempo
	 * @return
	 */
	public static Date parseTempoJogo(String tempo) {
		SimpleDateFormat format = new SimpleDateFormat("ss");

		try {
			return format.parse(tempo);
		} catch (ParseException ignored) {
			return null;
		}
	}

	/**
	 * Retorna um texto formato para mm'ss.
	 * 
	 * @param tempo
	 * @return
	 */
	public static String formatTempoJogo(Date tempo) {
		SimpleDateFormat format = new SimpleDateFormat("mm''ss");

		return format.format(tempo);
	}

	/**
	 * Retorna uma lista com nï¿½meros aleatï¿½rios, baseado nos parametros
	 * recebidos
	 * 
	 * @param qtd
	 *            Quantidade de numeros que serï¿½ retornado no List
	 * @param max
	 *            Nï¿½mero mï¿½ximo que pode ser escolhido aleatoriamente.
	 * @return
	 */
	public static List<Integer> pickIntRandomNumbers(int qtd, int max) {
		Random random = new Random(new Date().getTime());
		List<Integer> numberList = new ArrayList<Integer>();
		if (max >= qtd)
			for (int i = 0; i < qtd; i++) {
				int n = -1;
				do {
					n = random.nextInt(max);
				} while (numberList.contains(n));

				numberList.add(n);
			}
		return numberList;
	}

	public static String decodeHexString(String hexText) {

		String decodedText = null;
		String chunk = null;

		if (hexText != null && hexText.length() > 0) {
			int numBytes = hexText.length() / 2;

			byte[] rawToByte = new byte[numBytes];
			int offset = 0;

			for (int i = 0; i < numBytes; i++) {
				chunk = hexText.substring(offset, offset + 2);
				offset += 2;
				rawToByte[i] = (byte) (Integer.parseInt(chunk, 16) & 0x000000FF);
			}
			// decodedText = new String(rawToByte);
			decodedText = rawToByte.toString(); // TODO VER SE Ã‰ VAZIA
		}
		return decodedText;
	}

	public static String encodeHexString(String sourceText) {

		byte[] rawData = sourceText.getBytes();
		StringBuffer hexText = new StringBuffer();
		String initialHex = null;
		int initHexLength = 0;

		for (int i = 0; i < rawData.length; i++) {
			int positiveValue = rawData[i] & 0x000000FF;
			initialHex = Integer.toHexString(positiveValue);
			initHexLength = initialHex.length();
			while (initHexLength++ < 2) {
				hexText.append("0");
			}
			hexText.append(initialHex);
		}
		return hexText.toString();
	}

	public static String retornaTextoException(Exception exception) {

		String s = new String();

		if (exception != null) {
			StringWriter saida = new StringWriter();
			PrintWriter writer = new PrintWriter(saida);
			exception.printStackTrace(writer);

			s = saida.toString();
		}

		return s;
	}

	/**
	 * @param valor
	 *            = valor que sera arredondado
	 * @param casas
	 *            = numero de casas
	 * @param useCeil
	 *            = true arredonda para cima, false trunca o valor
	 */
	public static double arredondar(String valor, int casas, boolean useCeil) {
		BigDecimal bd = new BigDecimal(valor);
		return bd.setScale(casas, useCeil ? RoundingMode.CEILING : RoundingMode.FLOOR).doubleValue();
	}

	public static String alfabeticoEntrePosicoes(int vl1, int vl2, String texto) {
		return texto.substring(vl1 - 1, vl2).trim();
	}

	public static String alfanumericoEntrePosicoes(int vl1, int vl2, String texto) {
		return texto.substring(vl1 - 1, vl2).trim();
	}

	public static String valorEntrePosicoes(int vl1, int vl2, String texto) {
		return String.copyValueOf(texto.toCharArray(), vl1, vl2);
	}

	public static String retornoEntrePosicoes(int vl1, int vl2, String texto) {
		return texto.substring(vl1 - 1, vl2).trim();
	}

	public static String retiraZerosAEsquerda(String string) {
		String resposta = string;
		while (resposta != null && resposta.length() > 0 && resposta.charAt(0) == '0') {
			resposta = resposta.substring(1);
		}
		return resposta;
	}

	/**
	 * Formata uma string no formato monetario.
	 * 
	 * @param valor
	 * @param casasDecimais
	 * @return String O valor formatado no formato
	 *         [999.]999,99http://www.bb.com.
	 *         br/portalbb/home23,116,116,1,1,1,1.bb
	 */
	public static String formatarValorMonetario(String valor, int casasDecimais) {
		String resultado = "";
		if (isStringContemValor(valor)) {
			resultado = retiraZerosAEsquerda(valor);
			int tamanho = resultado.length();
			if (tamanho == 0) {
				resultado = "";
			} else if (tamanho <= casasDecimais) {
				resultado = "0," + completaComZerosAEsquerda(resultado, casasDecimais);
			} else {
				StringBuffer sb = new StringBuffer();
				sb.append(resultado.substring(tamanho - casasDecimais)).reverse().append(',');
				int cursor = tamanho - (casasDecimais + 1);

				while (cursor >= 0) {
					for (int i = 0; i < (casasDecimais + 1) && cursor >= 0; i++, cursor--) {
						sb.append(resultado.charAt(cursor));
					}
					if (cursor >= 0) {
						sb.append('.');
					}
				}
				resultado = sb.reverse().toString();
			}
		}
		return resultado;
	}

	public static String formatDoubleJuros(double valor) {
		DecimalFormat f = new DecimalFormat("#,##0.0000");
		f.setDecimalFormatSymbols(new DecimalFormatSymbols(new Locale("pt", "BR")));
		return f.format(valor / 10000);
	}

	public static String formatDoubleMoeda(double valor) {
		DecimalFormat f = new DecimalFormat("#,##0.00");
		f.setDecimalFormatSymbols(new DecimalFormatSymbols(new Locale("pt", "BR")));
		if (valor == 0) {
			return f.format(valor);
		}
		return f.format(valor / 100);
	}

	/**
	 * Verifica se a string contem um valor (texto)
	 * 
	 * @param string
	 *            A string que sera testada
	 * @return true, se existir algum texto diferente de vazio na string, false
	 *         caso contrario
	 */
	public static boolean isStringContemValor(String string) {
		return string != null && !string.trim().equals("") && !string.trim().equals("null");
	}

	public static String formataDoubleDuasCasasSemVirgula(Double valor) {
		DecimalFormat formatoDecimal = new DecimalFormat("###0.00");
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setDecimalSeparator('.');
		formatoDecimal.setDecimalFormatSymbols(symbols);
		return formatoDecimal.format(valor);
	}

	public static String formataDoubleQuatroCasasSemVirgula(Double valor) {
		DecimalFormat formatoDecimal = new DecimalFormat("###0.0000");
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setDecimalSeparator('.');
		formatoDecimal.setDecimalFormatSymbols(symbols);
		return formatoDecimal.format(valor);
	}

	/**
	 * Completa com zeros a esquerda, ate a string ter o tamanho tamanhoTotal
	 * 
	 * @param string
	 *            A string de entrada
	 * @param tamanhoTotal
	 *            O tamanho total da string resultate
	 * @return Uma nova string, com zeros a esquerda
	 */
	public static String completaComZerosAEsquerda(String string, int tamanhoTotal) {
		String resultado = "";
		if (isStringContemValor(string)) {
			for (int i = string.length(); i < tamanhoTotal; i++) {
				resultado = "0" + resultado;
			}
		}
		return resultado + string;
	}

	private static void compilePatterns() {
		PATTERNS = new Pattern[REPLACES.length];
		PATTERNS[0] = Pattern.compile("[Ã¢Ã£Ã¡Ã Ã¤]", Pattern.CASE_INSENSITIVE);
		PATTERNS[1] = Pattern.compile("[Ã©Ã¨ÃªÃ«]", Pattern.CASE_INSENSITIVE);
		PATTERNS[2] = Pattern.compile("[Ã­Ã¬Ã®Ã¯]", Pattern.CASE_INSENSITIVE);
		PATTERNS[3] = Pattern.compile("[Ã³Ã²Ã´ÃµÃ¶]", Pattern.CASE_INSENSITIVE);
		PATTERNS[4] = Pattern.compile("[ÃºÃ¹Ã»Ã¼]", Pattern.CASE_INSENSITIVE);
		PATTERNS[5] = Pattern.compile("[Ã§]", Pattern.CASE_INSENSITIVE);
	}

	public static String replaceSpecial(String text) {
		if (PATTERNS == null) {
			compilePatterns();
		}

		String result = text;
		for (int i = 0; i < PATTERNS.length; i++) {
			Matcher matcher = PATTERNS[i].matcher(result);
			result = matcher.replaceAll(REPLACES[i]);
		}
		return result;
	}

	
	
	/**
	 * Efetua o replace no StringBuffer informado. AtenÃ§Ã£o!! NÃ£o efetua o
	 * replaceAll. Somente efetua a substituiÃ§Ã£o da primeria ocorrÃªncia de
	 * String encontrada no StringBuffer.
	 * 
	 * @param sb
	 *            StringBuffer do conteÃºdo.
	 * @param oldString
	 *            String a ser encontrada.
	 * @param newString
	 *            String a ser substituÃ­da.
	 */
	public static void replace(StringBuffer sb, String oldString, String newString) {
		int index = sb.indexOf(oldString);
		if (index >= 0) {
			sb.replace(index, (index + oldString.length()), newString);
		}
	}

	/**
	 * Retira tags indesejadas, que influenciam na exibiÃ§Ã£o do conteÃºdo pelo
	 * Internet Explorer
	 * 
	 * @param texto
	 * @return
	 */
	public static String retirarTags(String texto) {
		List<String> tags = new ArrayList<String>();

		tags.add("<w:View>Normal</w:View>");
		tags.add("<w:Zoom>0</w:Zoom>");
		tags.add("<w:HyphenationZone>21</w:HyphenationZone>");
		tags.add("<w:SaveIfXMLInvalid>false</w:SaveIfXMLInvalid>");
		tags.add("<w:IgnoreMixedContent>false</w:IgnoreMixedContent>");
		tags.add("<w:AlwaysShowPlaceholderText>false</w:AlwaysShowPlaceholderText>");
		tags.add("<w:BrowserLevel>MicrosoftInternetExplorer4</w:BrowserLevel>");
		tags.add("<!--[if gte mso 9]>");
		tags.add("<![endif]-->");
		tags.add("<!--[if gte mso 10]>");
		tags.add("<xml>");
		tags.add("</xml>");
		tags.add("PT-BR");
		tags.add("X-NONE");

		for (String tag : tags) {
			texto = texto.replaceAll(tag, "");
		}

		String tag = "<![^>]+>";
		String tag2 = "<x[^>]+>";
		String tag3 = "<w[^>]+>";
		String tag4 = "</x[^>]+>";
		String tag5 = "</w[^>]+>";
		String tag6 = "<m[^>]+>";
		String tag7 = "</m[^>]+>";
		String tag8 = "<style>[^>]+</style>";

		texto = texto.replaceAll(tag, "").replaceAll(tag2, "").replaceAll(tag3, "").replaceAll(tag4, "").replaceAll(tag5, "")
				.replaceAll(tag6, "").replaceAll(tag7, "").replaceAll(tag8, "");

		return texto;
	}

	public static String gerarNuCodigoCertificado() {
		Long nuCodigoCertificado = 0L;
		do {
			nuCodigoCertificado = (long) (Math.random() * (Math.pow(10, 8)));
		} while (nuCodigoCertificado < 10000000);
		return nuCodigoCertificado.toString();
	}

	public static byte[] fileToByte(File arquivo) throws Exception {
		FileInputStream fis = new FileInputStream(arquivo);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int bytesRead = 0;
		while ((bytesRead = fis.read(buffer, 0, 8192)) != -1) {
			baos.write(buffer, 0, bytesRead);
		}
		fis.close();
		return baos.toByteArray();
	}

	public static String converterStringUtf8(String string) {
		try {
			byte[] bytes = string.getBytes("ISO-8859-1");
			return new String(bytes, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String converterStringPara_ISO_8859_1(String string) {
		try {
			byte[] bytes = string.getBytes("UTF-8");
			return new String(bytes, "ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean isConversaoUtf8Valida(String string) {
		return !string.contains("ï¿½");
	}

	public static boolean isNullOrEmpty(Number value) {
		return (value == null) || !(value.doubleValue() > 0);
	}

	public static boolean isValidXMLCharacters(String value) {
		boolean result = true;
		char current;

		if (!Util.isVazio(value)) {
			for (int i = 0; i < value.length(); i++) {
				current = value.charAt(i);
				if ((current == 0x9) || (current == 0xA) || (current == 0xD) || ((current >= 0x20) && (current <= 0xD7FF))
						|| ((current >= 0xE000) && (current <= 0xFFFD)) || ((current >= 0x10000) && (current <= 0x10FFFF))) {
				} else {
					result = false;
					break;
				}
			}
		}

		return result;
	}
}
