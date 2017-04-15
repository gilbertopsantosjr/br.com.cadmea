package br.com.cadmea.comuns.util;

import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {

  static String FMT_DATA = "dd/MM/yyyy";
  static String FMT_HORA = "HH:mm:ss";
  static String FMT_HORA_CURTA = "HH:mm";
  static String FMT_DATA_HORA = FMT_DATA + " " + FMT_HORA;
  static String FMT_DATA_HORA_CURTA = FMT_DATA + " " + FMT_HORA_CURTA;

  /**
   *
   * @param data
   * @return
   */
  public static Date addDay(Date data) {
    Calendar c = Calendar.getInstance();
    c.setTime(data);
    c.add(Calendar.DATE, 1);
    return c.getTime();
  }

  public static Boolean isWeekend() {
    Calendar c = Calendar.getInstance();
    c.setTime(new Date());
    int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
    return (dayOfWeek == Calendar.SATURDAY) || (dayOfWeek == Calendar.SUNDAY);
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

  /**
   *
   * @param data
   * @param numeroDias
   * @return
   */
  public static Date addDays(Date data, int dias) {
    for (int i = 0; i < dias; i++) {
      data = addDay(data);
    }
    return data;
  }

  /**
   *
   * @param parametro
   * @return
   */
  public static boolean comparaAnoAnterior(String parametro) {
    GregorianCalendar data = new GregorianCalendar();
    String auxiliar = String.valueOf(data.get(Calendar.YEAR) - 1);
    if (auxiliar.equals(parametro))
      return true;
    else
      return false;
  }

  /**
   *
   * @param parametro
   * @return
   */
  public static boolean comparaAnoAtual(String parametro) {
    GregorianCalendar data = new GregorianCalendar();
    String auxiliar = String.valueOf(data.get(Calendar.YEAR));
    if (auxiliar.equals(parametro))
      return true;
    else
      return false;
  }

  /**
   *
   * @param parametro
   * @return
   */
  public static boolean comparaMesAtual(String parametro) {
    GregorianCalendar data = new GregorianCalendar();
    String auxiliar = String.valueOf(data.get(Calendar.MONTH - 1));
    if (auxiliar.equals(parametro))
      return true;
    else
      return false;
  }

  /**
   *
   * @param parametro
   * @return
   */
  public static boolean comparaHoraAtual(String parametro) {
    GregorianCalendar data = new GregorianCalendar();
    String auxiliar = String.valueOf(data.get(Calendar.HOUR_OF_DAY));
    if (auxiliar.equals(parametro))
      return true;
    else
      return false;
  }

  /**
   *
   * @param parametro
   * @return
   */
  public static boolean comparaAnoPosterior(String parametro) {
    GregorianCalendar data = new GregorianCalendar();
    String auxiliar = String.valueOf(data.get(Calendar.YEAR) + 1);
    if (auxiliar.equals(parametro))
      return true;
    else
      return false;
  }

  /**
   * @param strDate
   * @return
   */
  public static Date parseDate(String strDate) {
    SimpleDateFormat format = new SimpleDateFormat(FMT_DATA);
    try {
      return format.parse(strDate);
    } catch (ParseException e) {
      return null;
    }
  }

  /**
   *
   * @param strDate
   * @return
   */
  public static Date parseDataHora(String strDate) {
    SimpleDateFormat format = new SimpleDateFormat(FMT_DATA_HORA);
    try {
      return format.parse(strDate);
    } catch (ParseException e) {
      return null;
    }
  }

  /**
   * @param hora
   * @return
   */
  public static Time parseHora(String hora) {
    SimpleDateFormat format = new SimpleDateFormat(FMT_HORA);
    try {
      return new Time(format.parse(hora).getTime());
    } catch (ParseException e) {
      return null;
    }
  }

  /**
   * @param hora
   * @return
   */
  public static Time parseHoraCurta(String hora) {
    SimpleDateFormat format = new SimpleDateFormat(FMT_HORA_CURTA);

    try {
      return new Time(format.parse(hora).getTime());
    } catch (ParseException e) {
      return null;
    }
  }

  /**
   *
   * @param parametro
   * @return
   */
  public static String formatarHora(String parametro) {
    String retorno = null;
    if (!Util.isVazio(parametro))
      retorno = parametro.substring(11, 19);
    return retorno;
  }

  /**
   *
   * @param data
   * @return
   */
  public static String formataData(Date data) {
    if (Util.isVazio(data))
      return null;
    java.text.SimpleDateFormat formatData = new java.text.SimpleDateFormat(
        FMT_DATA);
    return formatData.format(data);
  }

  /**
   *
   * @param data
   * @return
   */
  public static String formataDataHoraCurta(Date data) {
    if (Util.isVazio(data))
      return null;
    java.text.SimpleDateFormat formatData = new java.text.SimpleDateFormat(
        FMT_DATA_HORA_CURTA);
    return formatData.format(data);
  }

  /**
   *
   * @param data
   * @return
   */
  public static String formataDataHora(Date data) {
    if (Util.isVazio(data))
      return null;
    java.text.SimpleDateFormat formatData = new java.text.SimpleDateFormat(
        FMT_DATA_HORA);
    return formatData.format(data);
  }

  /**
   * Metodo que verifica se a data final informada e menor que a data inicial
   * informada.
   *
   * @param dtInicio
   * @param dtFim
   * @return boolean
   * @throws NullPointerException
   */
  public static boolean validarDataInicioFim(Date dtInicio, Date dtFim)
      throws NullPointerException {
    if (!Util.isVazio(dtInicio) && !Util.isVazio(dtFim)) {
      if (dtFim.before(dtInicio))
        return false;
    } else {
      throw new NullPointerException("INFORME AS DUAS DATAS.");
    }
    return true;
  }

  /**
   *
   * @param dtInicio
   * @param dtFim
   * @return
   * @throws NullPointerException
   */
  public static boolean validarDataInicioMenorIgualFim(Date dtInicio,
      Date dtFim) throws NullPointerException {
    if (!Util.isVazio(dtInicio) && !Util.isVazio(dtFim)) {
      return dtInicio.compareTo(dtFim) <= 0;
    } else {
      throw new NullPointerException("INFORME AS DUAS DATAS.");
    }
  }

  /**
   * diferencaEmDias = resultado Ã© diferenÃ§a entre as datas em dias
   * horasRestantes = calcula as horas restantes result = transforma as horas
   * restantes em fraÃ§Ã£o de dias
   *
   * @param dataInicial
   * @param dataFinal
   * @return
   */
  public static double diferencaEmDias(Date dataInicial, Date dataFinal) {
    double result = 0;
    long diferenca = dataFinal.getTime() - dataInicial.getTime();
    double diferencaEmDias = (diferenca / 1000) / 60 / 60 / 24;
    long horasRestantes = (diferenca / 1000) / 60 / 60 % 24;
    result = diferencaEmDias + (horasRestantes / 24d);
    return result;
  }

  /**
   *
   * @param vl1
   * @param vl2
   * @param texto
   * @return
   * @throws ParseException
   */
  public static Date dataEntrePosicoes(int vl1, int vl2, String texto)
      throws ParseException {
    SimpleDateFormat format = new SimpleDateFormat("ddMMyy");
    return format.parse(texto.substring(vl1 - 1, vl2));
  }

  /**
   * Retorna a Data Atual no Formato dd/MM/yyyy
   */
  public static Date getDataAtualSimples() {
    SimpleDateFormat simplaDateFormat = new SimpleDateFormat();
    Date result = null;
    String now = simplaDateFormat.format(System.currentTimeMillis());
    try {
      result = simplaDateFormat.parse(now);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return result;
  }

  /**
   *
   * @param target
   * @param start
   * @param end
   * @return
   */
  public static boolean isDateInInterval(Date target, Date start, Date end) {
    return ((target.compareTo(start) >= 0) && (target.compareTo(end) <= 0));
  }

  /**
   *
   * @param strDate
   * @return
   */
  public static Date parseDataHoraCurta(String strDate) {
    SimpleDateFormat format = new SimpleDateFormat(FMT_DATA_HORA_CURTA);

    try {
      return format.parse(strDate);
    } catch (ParseException e) {
      return null;
    }
  }

  /**
   *
   * @param date1
   * @param date2
   * @return
   */
  public static Date retornaMaior(Date date1, Date date2) {
    if (date1.after(date2)) {
      return date1;
    } else {
      return date2;
    }
  }

  /**
   *
   * @param date1
   * @param date2
   * @return
   */
  public static Date retornaMenor(Date date1, Date date2) {
    if (date1.before(date2)) {
      return date1;
    } else {
      return date2;
    }
  }

  /**
   *
   * @param data
   * @return
   */
  public static boolean ehMenorDeIdade(Date data) {
    return DateUtil.retornaAnoEntreDatas(data,
        DateUtil.parseDate(DateUtil.retornaDataAtual("dd/MM/yyyy"))) < 18;
  }

  /**
   *
   * @param date
   * @return
   */
  public static Date zeraHoraMinSegMili(Date date) {
    GregorianCalendar calendar = new GregorianCalendar();

    calendar.setTime(date);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);

    return calendar.getTime();
  }

  /**
   *
   * @param dataInicial
   * @param dataFinal
   * @return
   */
  public static Integer retornaAnoEntreDatas(Date dataInicial, Date dataFinal) {
    Calendar cal1 = Calendar.getInstance();
    Calendar cal2 = Calendar.getInstance();
    cal1.setTime(dataInicial);
    cal2.setTime(dataFinal);
    int year1 = cal1.get(Calendar.YEAR);
    int year2 = cal2.get(Calendar.YEAR);
    int month1 = cal1.get(Calendar.MONTH);
    int month2 = cal2.get(Calendar.MONTH);
    int day1 = cal1.get(Calendar.DAY_OF_MONTH);
    int day2 = cal2.get(Calendar.DAY_OF_MONTH);
    int idade = year2 - year1;
    if ((month2 < month1) || ((month2 == month1) && (day2 < day1))) {
      idade -= 1;
    }
    return new Integer(idade);
  }

  /**
   *
   * @param data
   * @param hora
   * @return
   */
  public static Date addHora(Date data, Time hora) {
    String dataTemp = formataData(data);
    String horaTemp = formatarHora(hora);
    return parseDataHora(dataTemp + " " + horaTemp);
  }

  /**
   *
   * @param formato
   * @param data
   * @return
   */
  public static String formataData(String formato, Date data) {
    if (Util.isVazio(data))
      return null;
    java.text.SimpleDateFormat formatData = new java.text.SimpleDateFormat(
        formato);
    return formatData.format(data);
  }

  /**
   *
   * @param mascara
   * @return
   */
  public static String retornaDataAtual(String mascara) {
    Date date = new Date();
    java.text.SimpleDateFormat formatData = new java.text.SimpleDateFormat(
        mascara);
    return formatData.format(date);
  }

  /**
   * @param time
   * @return
   */
  public static String formatarHora(Time time) {
    SimpleDateFormat format = new SimpleDateFormat(FMT_HORA);
    return format.format(time);
  }

  /**
   * @param time
   * @return
   */
  public static String formatarHoraCurta(Date time) {
    SimpleDateFormat format = new SimpleDateFormat(FMT_HORA_CURTA);
    return format.format(time);
  }

  /**
   *
   * @param parametro
   * @return
   * @throws IOException
   */
  public static String formataDataPorExtenso(String parametro)
      throws IOException {

    String dia = null;
    String mes = null;
    String ano = null;
    String retorno = null;

    if (!Util.isVazio(parametro)) {

      if (parametro.length() == 8) {
        dia = parametro.substring(0, 2);
        mes = parametro.substring(2, 4);
        ano = parametro.substring(4, 8);

      } else if (parametro.length() == 10) {
        dia = parametro.substring(0, 2);
        mes = parametro.substring(3, 5);
        ano = parametro.substring(6, 10);

      } else {
        throw new IOException(
            "FORMATO DA DATA NAO LOCALIZADO EM FORMATADATAPOREXTENSO.");

      }

      int valor = Integer.parseInt(mes);

      switch (valor) {
      case 1:
        mes = "Janeiro";
        break;
      case 2:
        mes = "Fevereiro";
        break;
      case 3:
        mes = "MarÃ§o";
        break;
      case 4:
        mes = "Abril";
        break;
      case 5:
        mes = "Maio";
        break;
      case 6:
        mes = "Junho";
        break;
      case 7:
        mes = "Julho";
        break;
      case 8:
        mes = "Agosto";
        break;
      case 9:
        mes = "Setembro";
        break;
      case 10:
        mes = "Outubro";
        break;
      case 11:
        mes = "Novembro";
        break;
      case 12:
        mes = "Dezembro";
        break;

      }

      retorno = dia + " de " + mes + " de " + ano;

    }

    return retorno;

  }

}
