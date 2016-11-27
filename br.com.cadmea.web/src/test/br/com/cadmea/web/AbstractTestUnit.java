package br.com.cadmea.web;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.inject.Inject;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.google.gson.Gson;

import br.com.cadmea.comuns.UnitaryTest;
import br.com.cadmea.comuns.util.Util;
import br.com.cadmea.model.orm.UserSystem;
import br.com.cadmea.spring.security.orm.UserAccess;
import br.com.cadmea.web.business.UserSrv;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = StartServerAndLoginScreen.class)
public abstract class AbstractTestUnit implements UnitaryTest {

  @Inject
  private UserSrv userSrv;

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

  public UserSystem simulateLogin(final String email) {

    final UserSystem user = userSrv.getUserBy(email);

    UserAccess userAccess = new UserAccess(user.getPerson().getName());
    user.getPermissions().forEach(a -> {
      userAccess.getRoles().add(a.getRole());
    });

    Authentication auth = new UsernamePasswordAuthenticationToken(userAccess,
        null, userAccess.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(auth);

    return user;
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
