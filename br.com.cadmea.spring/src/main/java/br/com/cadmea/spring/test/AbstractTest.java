package br.com.cadmea.spring.test;

import static io.restassured.RestAssured.basic;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.BeforeClass;

import com.google.gson.Gson;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class AbstractTest {

  @BeforeClass
  public static void init() {

    String port = System.getProperty("server.port");
    if (port == null) {
      RestAssured.port = Integer.valueOf(8080);
    } else {
      RestAssured.port = Integer.valueOf(port);
    }

    String basePath = System.getProperty("server.base");
    if (basePath == null) {
      basePath = "/api/";
    }
    RestAssured.basePath = basePath;

    String baseHost = System.getProperty("server.host");
    if (baseHost == null) {
      baseHost = "http://localhost";
    }
    RestAssured.baseURI = baseHost;

    RestAssured.given().contentType(ContentType.JSON);

  }

  @Before
  public void simulateLogin() {
    RestAssured.authentication = basic("gilbertopsantosjr@gmail.com", "123456");
  }

  public Gson fromGson() {
    return new Gson();
  }

  public Date getDate() {
    Calendar calendar = new GregorianCalendar();
    return calendar.getTime();
  }

  public Date getDate(int year, int month, int day) {
    Calendar calendar = new GregorianCalendar(year, month, day);
    return calendar.getTime();
  }

}
