package br.com.cadmea.comuns.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;
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

  static char[] code;

  static final String caracteresCodigo = "ABCDEFGHIJKLMNOPQRSTUVXWYZ";
  static final String caracteresCodigoNumerico = "1234567890";
  static final String caracteresCodigoAlfaNumerico = "1234567890ABCDEFGHIJKLMNOPQRSTUVXWYZ";

  public static String obterMensagemDoArquivoDePropriedades(
      ObjetoTemplateMensagemVo otm) {
    StringBuilder mensagemCompleta = new StringBuilder();
    Locale locale = new Locale(otm.getIdiomasDoSistema().getDescription());
    ResourceBundle bundle = ResourceBundle
        .getBundle(ConstantesComum.APP_MENSAGENS_FILE, locale);

    if (bundle.containsKey(otm.obterMensagemNoPattern()))
      mensagemCompleta.append(bundle.getString(otm.obterMensagemNoPattern()));

    if (bundle.containsKey(otm.getChaveTemplate()))
      mensagemCompleta.append(bundle.getString(otm.getChaveTemplate()));

    return mensagemCompleta.toString();
  }

  public static String getCurrentDate(String pattern) {
    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    return sdf.format(new Date());
  }

  public static ObjetoTemplateMensagemVo obterObjetoTemplateMensagemVo(
      ConstraintViolation<?> violacao) {
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
      indiceAleatorio = geradorAleatorio
          .nextInt(caracteresCodigoNumerico.length());
      bufferCodigo.append(caracteresCodigoNumerico.charAt(indiceAleatorio));
    }
    return bufferCodigo.toString();
  }

  public static String getResourceProperty(Object clazz,
      String nomeArquivoResource, String propriedade) {

    Properties props = new Properties();
    String resource = nomeArquivoResource;

    InputStream stream = clazz.getClass().getClassLoader()
        .getResourceAsStream(resource);

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

  public static String getResourceProperty(String nomeArquivoResource,
      String propriedade) {

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

  public static void registrarLog(String mensagem, String nomeArquivo,
      Exception exception, Class<?> classe) {

    logger = classe != null ? Logger.getLogger(classe) : Logger.getRootLogger();

    BasicConfigurator.configure();
    Appender fileAppender;
    try {
      fileAppender = new FileAppender(
          new PatternLayout(PatternLayout.TTCC_CONVERSION_PATTERN),
          nomeArquivo + "_" + DateUtil.formataData(new Date()) + ".log");
      logger.addAppender(fileAppender);
      if (exception == null)
        logger.info(DateUtil.formataData(new Date()) + " "
            + DateUtil.formatarHora(new Time(new Date().getTime())) + " > "
            + mensagem);
      else
        logger.error(DateUtil.formataData(new Date()) + " "
            + DateUtil.formatarHora(new Time(new Date().getTime())) + " > "
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

  private static void checarString(String parametro, String mensagem)
      throws ParseException {
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

  public static String envolverString(String texto, String existente,
      String texto1, String texto2) {
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
        retorno = parametro.substring(0, 2) + "." + parametro.substring(2, 5)
            + "." + parametro.substring(5, 8) + "/" + parametro.substring(8, 12)
            + "-" + parametro.substring(12, 14);

      } else {
        retorno = parametro;

        for (int i = parametro.length(); i < 14; i++)
          retorno = "0" + retorno;

        retorno = retorno.substring(0, 2) + "." + retorno.substring(2, 5) + "."
            + retorno.substring(5, 8) + "/" + retorno.substring(8, 12) + "-"
            + retorno.substring(12, 14);

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

    } else if (parametro.substring(parametro.indexOf("@") + 1)
        .indexOf("@") > 0) {
      retorno = false;

    } else if ((parametro.substring(parametro.indexOf("@") + 1))
        .indexOf(".") <= 0) {
      retorno = false;

    } else if (parametro.substring(parametro.indexOf(".") + 1).length() <= 0) {
      retorno = false;

    } else if (parametro.substring(parametro.indexOf(".") + 1).length() > 0) {
      String auxiliar = parametro.substring(parametro.indexOf(".") + 1);

      if (auxiliar.length() > 1
          && auxiliar.substring(auxiliar.indexOf(".") + 1).length() <= 0) {
        retorno = false;

      } else {
        while (auxiliar.length() > 1
            && auxiliar.substring(auxiliar.indexOf(".") + 1).length() > 0) {

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

}
