package br.com.cadmea.comuns.util;

import br.com.cadmea.comuns.i18n.MessageCommon;
import br.com.cadmea.comuns.validator.Validator;

import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {

    static String FMT_DATA = "dd/MM/yyyy";
    static String FMT_HORA = "HH:mm:ss";
    static String FMT_HORA_CURTA = "HH:mm";
    static String FMT_DATA_HORA = FMT_DATA + " " + FMT_HORA;
    static String FMT_DATA_HORA_CURTA = FMT_DATA + " " + FMT_HORA_CURTA;
    static final SimpleDateFormat formatDate = new SimpleDateFormat(FMT_DATA);
    static final SimpleDateFormat formatDateTime = new SimpleDateFormat(FMT_DATA_HORA);

    /**
     * @param data
     * @return
     */
    public static Date addDay(final Date data) {
        final Calendar c = Calendar.getInstance();
        c.setTime(data);
        c.add(Calendar.DATE, 1);
        return c.getTime();
    }

    /**
     * @return
     */
    public static Boolean isWeekend() {
        final Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        final int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        return (dayOfWeek == Calendar.SATURDAY) || (dayOfWeek == Calendar.SUNDAY);
    }

    /**
     * @param dataInicial
     * @param dataFinal
     * @return
     */
    public static double diferencaEmHoras(final Date dataInicial, final Date dataFinal) {
        final long diferenca = dataFinal.getTime() - dataInicial.getTime();
        final double diferencaEmDias = (diferenca / 1000) / 60 / 60 / 24;
        final long horasRestantes = (diferenca / 1000) / 60 / 60 % 24;
        return (diferencaEmDias * 24) + horasRestantes;
    }

    public static double diferencaEmMinutos(final Date dataInicial, final Date dataFinal) {
        final long diferenca = dataFinal.getTime() - dataInicial.getTime();
        final double diferencaEmMinutos = (diferenca / 1000) / 60;
        return diferencaEmMinutos;
    }

    /**
     * @param data
     * @param dias
     * @return
     */
    public static Date addDays(Date data, final int dias) {
        for (int i = 0; i < dias; i++) {
            data = addDay(data);
        }
        return data;
    }

    /**
     * @param parametro
     * @return
     */
    public static boolean comparaAnoAnterior(final String parametro) {
        final GregorianCalendar data = new GregorianCalendar();
        final String auxiliar = String.valueOf(data.get(Calendar.YEAR) - 1);
        if (auxiliar.equals(parametro)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param parametro
     * @return
     */
    public static boolean comparaAnoAtual(final String parametro) {
        final GregorianCalendar data = new GregorianCalendar();
        final String auxiliar = String.valueOf(data.get(Calendar.YEAR));
        if (auxiliar.equals(parametro)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param parametro
     * @return
     */
    public static boolean comparaMesAtual(final String parametro) {
        final GregorianCalendar data = new GregorianCalendar();
        final String auxiliar = String.valueOf(data.get(Calendar.MONTH - 1));
        if (auxiliar.equals(parametro)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * return the currently DAte
     *
     * @return
     */
    public static LocalDateTime getDate() {
        final Calendar calendar = new GregorianCalendar();
        final Date input = calendar.getTime();
        return input.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * @param year
     * @param month
     * @param day
     * @return
     * @throws ParseException
     */
    public static LocalDate getDate(final int year, int month, final int day) {
        month = month > 0 ? month - 1 : month;
        final Calendar calendar = new GregorianCalendar(year, month, day);
        final Date input = calendar.getTime();
        return input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * @param date
     * @return
     */
    public static LocalDate getDate(final Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        final Date input = calendar.getTime();
        return input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * @param date
     * @return
     */
    public static LocalDate getDate(final String date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(parseDate(date));
        final Date input = calendar.getTime();
        return input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }


    /**
     * @param parametro
     * @return
     */
    public static boolean comparaHoraAtual(final String parametro) {
        final GregorianCalendar data = new GregorianCalendar();
        final String auxiliar = String.valueOf(data.get(Calendar.HOUR_OF_DAY));
        if (auxiliar.equals(parametro)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param parametro
     * @return
     */
    public static boolean comparaAnoPosterior(final String parametro) {
        final GregorianCalendar data = new GregorianCalendar();
        final String auxiliar = String.valueOf(data.get(Calendar.YEAR) + 1);
        if (auxiliar.equals(parametro)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * parse the valid date as String to a valid Date object
     * format dd/mm/yyyy
     *
     * @param strDate {@link String}
     * @return Date {@link Date}
     */
    public static Date parseDate(final String strDate) {
        Date dtReturn = null;
        try {
            dtReturn = formatDate.parse(strDate);
        } catch (final ParseException e) {
            Validator.throwIfFail(true, MessageCommon.DATE_FORMAT_INVALID);
        }
        return dtReturn;
    }

    /**
     * parse the valid date as String to a valid Date object
     * format dd/mm/yyyy hh:mm:ss
     *
     * @param strDate {@link String}
     * @return Date {@link Date}
     */
    public static Date parseDataHora(final String strDate) {
        Date dtReturn = null;
        try {
            dtReturn = formatDateTime.parse(strDate);
        } catch (final ParseException e) {
            Validator.throwIfFail(true, MessageCommon.DATE_FORMAT_INVALID);
        }
        return dtReturn;
    }

    /**
     * @param hora
     * @return
     */
    public static Time parseHora(final String hora) {
        final SimpleDateFormat format = new SimpleDateFormat(FMT_HORA);
        try {
            return new Time(format.parse(hora).getTime());
        } catch (final ParseException e) {
            return null;
        }
    }

    /**
     * @param hora
     * @return
     */
    public static Time parseHoraCurta(final String hora) {
        final SimpleDateFormat format = new SimpleDateFormat(FMT_HORA_CURTA);

        try {
            return new Time(format.parse(hora).getTime());
        } catch (final ParseException e) {
            return null;
        }
    }

    /**
     * @param parametro
     * @return
     */
    public static String formatarHora(final String parametro) {
        String retorno = null;
        if (!ValidatorUtil.isValid(parametro)) {
            retorno = parametro.substring(11, 19);
        }
        return retorno;
    }

    /**
     * @param data
     * @return
     */
    public static String formataData(final Date data) {
        if (ValidatorUtil.isValid(data)) {
            return null;
        }
        final java.text.SimpleDateFormat formatData = new java.text.SimpleDateFormat(
                FMT_DATA);
        return formatData.format(data);
    }

    /**
     * @param data
     * @return
     */
    public static String formataDataHoraCurta(final Date data) {
        if (ValidatorUtil.isValid(data)) {
            return null;
        }
        final java.text.SimpleDateFormat formatData = new java.text.SimpleDateFormat(
                FMT_DATA_HORA_CURTA);
        return formatData.format(data);
    }

    /**
     * @param data
     * @return
     */
    public static String formataDataHora(final Date data) {
        if (ValidatorUtil.isValid(data)) {
            return null;
        }
        final java.text.SimpleDateFormat formatData = new java.text.SimpleDateFormat(
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
    public static boolean validarDataInicioFim(final Date dtInicio, final Date dtFim)
            throws NullPointerException {
        if (!ValidatorUtil.isValid(dtInicio) && !ValidatorUtil.isValid(dtFim)) {
            if (dtFim.before(dtInicio)) {
                return false;
            }
        } else {
            throw new NullPointerException("INFORME AS DUAS DATAS.");
        }
        return true;
    }

    /**
     * @param dtInicio
     * @param dtFim
     * @return
     * @throws NullPointerException
     */
    public static boolean validarDataInicioMenorIgualFim(final Date dtInicio,
                                                         final Date dtFim) throws NullPointerException {
        if (!ValidatorUtil.isValid(dtInicio) && !ValidatorUtil.isValid(dtFim)) {
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
    public static double diferencaEmDias(final Date dataInicial, final Date dataFinal) {
        double result = 0;
        final long diferenca = dataFinal.getTime() - dataInicial.getTime();
        final double diferencaEmDias = (diferenca / 1000) / 60 / 60 / 24;
        final long horasRestantes = (diferenca / 1000) / 60 / 60 % 24;
        result = diferencaEmDias + (horasRestantes / 24d);
        return result;
    }

    /**
     * @param vl1
     * @param vl2
     * @param texto
     * @return
     * @throws ParseException
     */
    public static Date dataEntrePosicoes(final int vl1, final int vl2, final String texto)
            throws ParseException {
        final SimpleDateFormat format = new SimpleDateFormat("ddMMyy");
        return format.parse(texto.substring(vl1 - 1, vl2));
    }

    /**
     * Retorna a Data Atual no Formato dd/MM/yyyy
     */
    public static Date getDataAtualSimples() {
        final SimpleDateFormat simplaDateFormat = new SimpleDateFormat();
        Date result = null;
        final String now = simplaDateFormat.format(System.currentTimeMillis());
        try {
            result = simplaDateFormat.parse(now);
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @param target
     * @param start
     * @param end
     * @return
     */
    public static boolean isDateInInterval(final Date target, final Date start, final Date end) {
        return ((target.compareTo(start) >= 0) && (target.compareTo(end) <= 0));
    }

    /**
     * @param strDate
     * @return
     */
    public static Date parseDataHoraCurta(final String strDate) {
        final SimpleDateFormat format = new SimpleDateFormat(FMT_DATA_HORA_CURTA);

        try {
            return format.parse(strDate);
        } catch (final ParseException e) {
            return null;
        }
    }

    /**
     * @param date1
     * @param date2
     * @return
     */
    public static Date retornaMaior(final Date date1, final Date date2) {
        if (date1.after(date2)) {
            return date1;
        } else {
            return date2;
        }
    }

    /**
     * @param date1
     * @param date2
     * @return
     */
    public static Date retornaMenor(final Date date1, final Date date2) {
        if (date1.before(date2)) {
            return date1;
        } else {
            return date2;
        }
    }

    /**
     * @param data
     * @return
     */
    public static boolean ehMenorDeIdade(final Date data) {
        return DateUtil.retornaAnoEntreDatas(data,
                DateUtil.parseDate(DateUtil.retornaDataAtual("dd/MM/yyyy"))) < 18;
    }

    /**
     * @param date
     * @return
     */
    public static Date zeraHoraMinSegMili(final Date date) {
        final GregorianCalendar calendar = new GregorianCalendar();

        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    /**
     * @param dataInicial
     * @param dataFinal
     * @return
     */
    public static Integer retornaAnoEntreDatas(final Date dataInicial, final Date dataFinal) {
        final Calendar cal1 = Calendar.getInstance();
        final Calendar cal2 = Calendar.getInstance();
        cal1.setTime(dataInicial);
        cal2.setTime(dataFinal);
        final int year1 = cal1.get(Calendar.YEAR);
        final int year2 = cal2.get(Calendar.YEAR);
        final int month1 = cal1.get(Calendar.MONTH);
        final int month2 = cal2.get(Calendar.MONTH);
        final int day1 = cal1.get(Calendar.DAY_OF_MONTH);
        final int day2 = cal2.get(Calendar.DAY_OF_MONTH);
        int idade = year2 - year1;
        if ((month2 < month1) || ((month2 == month1) && (day2 < day1))) {
            idade -= 1;
        }
        return new Integer(idade);
    }

    /**
     * @param data
     * @param hora
     * @return
     */
    public static Date addHora(final Date data, final Time hora) {
        final String dataTemp = formataData(data);
        final String horaTemp = formatarHora(hora);
        return parseDataHora(dataTemp + " " + horaTemp);
    }

    /**
     * @param formato
     * @param data
     * @return
     */
    public static String formataData(final String formato, final Date data) {
        if (ValidatorUtil.isValid(data)) {
            return null;
        }
        final java.text.SimpleDateFormat formatData = new java.text.SimpleDateFormat(
                formato);
        return formatData.format(data);
    }

    /**
     * @param mascara
     * @return
     */
    public static String retornaDataAtual(final String mascara) {
        final Date date = new Date();
        final java.text.SimpleDateFormat formatData = new java.text.SimpleDateFormat(
                mascara);
        return formatData.format(date);
    }

    /**
     * @param time
     * @return
     */
    public static String formatarHora(final Time time) {
        final SimpleDateFormat format = new SimpleDateFormat(FMT_HORA);
        return format.format(time);
    }

    /**
     * @param time
     * @return
     */
    public static String formatarHoraCurta(final Date time) {
        final SimpleDateFormat format = new SimpleDateFormat(FMT_HORA_CURTA);
        return format.format(time);
    }

    /**
     * @param parametro
     * @return
     * @throws IOException
     */
    public static String formataDataPorExtenso(final String parametro)
            throws IOException {

        String dia = null;
        String mes = null;
        String ano = null;
        String retorno = null;

        if (!ValidatorUtil.isValid(parametro)) {

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

            final int valor = Integer.parseInt(mes);

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
