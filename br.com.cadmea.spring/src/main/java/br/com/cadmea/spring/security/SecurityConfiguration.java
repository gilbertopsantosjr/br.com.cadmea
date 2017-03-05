package br.com.cadmea.spring.security;

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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    http
    	.csrf().disable().authorizeRequests()
    	
    	.antMatchers("/resources/**").permitAll()
    	.antMatchers("/register").permitAll()
    	.antMatchers("/login").permitAll()
    	.antMatchers("/logout").permitAll()
    	
    	.antMatchers(ServicePath.PUBLIC_ROOT_PATH + ServicePath.ALL).permitAll()
    	
    	.antMatchers(HttpMethod.GET, ServicePath.PRIVATE_ROOT_PATH)
        .access(allRoles)
        .antMatchers(HttpMethod.POST, ServicePath.PRIVATE_ROOT_PATH)
        .access(allRoles)
        .antMatchers(HttpMethod.PUT, ServicePath.PRIVATE_ROOT_PATH)
        .access(allRoles)
        .antMatchers(HttpMethod.DELETE, ServicePath.PRIVATE_ROOT_PATH)
        .access(allRoles)
    	
        .anyRequest().fullyAuthenticated()
        
        .and()
    .formLogin()                      
        .loginPage(ServicePath.LOGIN_PATH)
        .and()
    .httpBasic()
    	.and()
    	.logout()
    	.permitAll();
  
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
