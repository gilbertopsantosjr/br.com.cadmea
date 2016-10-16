package br.com.cadmea.spring.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import br.com.cadmea.spring.rest.ServicePath;

@EnableWebSecurity
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@PropertySource(value = "classpath:cadmea.properties",
    ignoreResourceNotFound = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Value("${cadmea.users.roles:ROLE_ADMIN}")
  public String ROLES;

  @Lazy
  @Autowired
  private UserDetailsService userService;

  @Lazy
  @Autowired
  private HeaderHandler headerHandler;

  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(this.userService)
        .passwordEncoder(passwordencoder());
  }

  @Bean(name = "passwordEncoder")
  public PasswordEncoder passwordencoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    final String allRoles = prepareAllRoles();

    http.httpBasic().and().authorizeRequests()
        // Global Authority to OPTIONS (permit all).
        .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
        .antMatchers(ServicePath.PUBLIC_ROOT_PATH + ServicePath.ALL).permitAll()

        .antMatchers(HttpMethod.GET, ServicePath.PRIVATE_ROOT_PATH)
        .access(allRoles)
        .antMatchers(HttpMethod.POST, ServicePath.PRIVATE_ROOT_PATH)
        .access(allRoles)
        .antMatchers(HttpMethod.PUT, ServicePath.PRIVATE_ROOT_PATH)
        .access(allRoles)
        .antMatchers(HttpMethod.DELETE, ServicePath.PRIVATE_ROOT_PATH)
        .access(allRoles)

        .anyRequest().fullyAuthenticated().and()
        // Logout configuration.
        .logout()
        .logoutRequestMatcher(
            new AntPathRequestMatcher(ServicePath.LOGOUT_PATH))
        .logoutSuccessHandler(headerHandler).and()
        // We don't need sessions to be created.
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
        // CSRF configuration.
        .csrf().csrfTokenRepository(csrfTokenRepository()).and()
        .addFilterAfter(csrfHeaderFilter(), CsrfFilter.class)
        .addFilterAfter(headerHandler, ChannelProcessingFilter.class);
  }

  private CsrfTokenRepository csrfTokenRepository() {
    HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
    repository.setHeaderName("X-XSRF-TOKEN");
    return repository;
  }

  private Filter csrfHeaderFilter() {
    return new OncePerRequestFilter() {

      @Override
      protected void doFilterInternal(HttpServletRequest request,
          HttpServletResponse response, FilterChain filterChain)
          throws ServletException, IOException {
        CsrfToken csrf = (CsrfToken) request
            .getAttribute(CsrfToken.class.getName());

        if (csrf != null) {
          Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
          String token = csrf.getToken();

          if (cookie == null
              || token != null && !token.equals(cookie.getValue())) {
            cookie = new Cookie("XSRF-TOKEN", token);
            cookie.setPath("/");
            response.addCookie(cookie);
          }
        }

        filterChain.doFilter(request, response);
      }

    };
  }

  private String prepareAllRoles() {
    String allRoles = "";
    if (ROLES.contains(",")) {
      String[] roles = ROLES.split(",");
      for (int i = 0; i < roles.length; i++) {
        if (roles.length > i) {
          allRoles += "'" + roles[i].trim() + "',";
        }
      }
      allRoles = allRoles.substring(0, allRoles.length() - 1);
    } else {
      allRoles = "'" + ROLES + "'";
    }

    return "hasRole(" + allRoles + ")";
  }

}
