package br.com.cadmea.web;

import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.Filter;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import br.com.cadmea.comuns.UnitaryTest;
import br.com.cadmea.comuns.util.Util;
import br.com.cadmea.web.util.SpringFunctions;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = StartServerAndLoginScreen.class)
public abstract class AbstractTestUnit implements UnitaryTest {

  @Autowired
  private Filter springSecurityFilterChain;

  @Autowired
  private WebApplicationContext webApplicationContext;

  protected static final String SERVER = "http://localhost:8080/";

  protected MockMvc mockMvc;

  private Gson gson;

  protected MediaType contentType = new MediaType(
      MediaType.APPLICATION_JSON.getType(),
      MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

  JsonDeserializer<Date> deser = new JsonDeserializer<Date>() {
    @Override
    public Date deserialize(JsonElement json, Type typeOfT,
        JsonDeserializationContext context) throws JsonParseException {
      DateFormat writeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      Date date = null;
      try {
        date = writeFormat.parse(json.getAsString());
      } catch (ParseException e) {
        e.printStackTrace();
      }
      return json == null ? null : date;
    }
  };

  @Before
  public void setup() {
    gson = new GsonBuilder().registerTypeAdapter(Date.class, deser).create();

    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
        .addFilters(springSecurityFilterChain).build();
  }

  /**
   *
   * @param entityName
   * @param isProtectedUrl
   * @return the url path as String to create a resource in a rest service
   */
  protected String getCreateUrlForEntity(final String entityName,
      boolean isProtectedUrl) {
    if (!isProtectedUrl)
      return SERVER + "api/public/" + entityName + "/create/";
    else
      return SERVER + "api/private/" + entityName + "/create/";
  }

  /**
   *
   * @param entityName
   * @param isProtectedUrl
   * @return
   */
  protected String getReadUrlForEntity(final String entityName,
      boolean isProtectedUrl) {
    if (!isProtectedUrl)
      return SERVER + "api/public/" + entityName + "/read/{id}";
    else
      return SERVER + "api/private/" + entityName + "/read/{id}";
  }

  /**
   *
   * @param entityName
   * @param isProtectedUrl
   * @return
   */
  protected String getLoadUrlForEntity(final String entityName,
      boolean isProtectedUrl) {
    if (!isProtectedUrl)
      return SERVER + "api/public/" + entityName + "/load/{id}";
    else
      return SERVER + "api/private/" + entityName + "/load/{id}";
  }

  /**
   *
   * @param entityName
   * @param isProtectedUrl
   * @return
   */
  protected String getUpdateUrlForEntity(final String entityName,
      boolean isProtectedUrl) {
    if (!isProtectedUrl)
      return SERVER + "api/public/" + entityName + "/update/";
    else
      return SERVER + "api/private/" + entityName + "/update/";
  }

  /**
   * return a mock number for id
   *
   * @return Long
   */
  public Long getMockId() {
    return Long.parseLong(Util.generatorNumericCode(1));
  }

  @BeforeClass
  public static void init() {

  }

  public void simulateLogin(final String email) {
    SpringFunctions.createHeaders(email, "password");
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
