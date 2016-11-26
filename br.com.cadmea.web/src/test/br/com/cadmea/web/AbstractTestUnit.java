package br.com.cadmea.web;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.BeforeClass;

import com.google.gson.Gson;

import br.com.cadmea.comuns.UnitaryTest;
import br.com.cadmea.comuns.util.Util;

public abstract class AbstractTestUnit implements UnitaryTest {

	/**
	 * return a mock number for id
	 * 
	 * @return Long
	 */
	public Long getMockId() {
		return Long.parseLong(Util.generatorNumericCode(1));
	}

	private Gson gson = new Gson();

	@BeforeClass
	public static void init() {

	}
	
	public void simulateLogin() {

	}

	public Gson fromGson() {
		return gson;
	}

	
	public Date getDate() {
		Calendar calendar = new GregorianCalendar();
		return calendar.getTime();
	}

	public Date getDate(int year, int month, int day) throws ParseException {
		month = month > 0 ? month - 1 : month;
		Calendar calendar = new GregorianCalendar(year, month, day);
		return calendar.getTime();
	}

}
