package br.com.cadmea.web;

import java.nio.charset.Charset;
import java.text.ParseException;
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

import br.com.cadmea.comuns.UnitaryTest;
import br.com.cadmea.comuns.util.Util;
import br.com.cadmea.spring.util.SpringFunctions;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = StartServerAndLoginScreen.class)
public abstract class AbstractTestUnit implements UnitaryTest {

  @Autowired
  private Filter springSecurityFilterChain;

  @Autowired
  private WebApplicationContext webApplicationContext;

  protected MockMvc mockMvc;

  protected MediaType contentType = new MediaType(
      MediaType.APPLICATION_JSON.getType(),
      MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

  @Before
  public void setup() {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
        .addFilters(springSecurityFilterChain).build();
  }

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
