package br.com.cadmea.spring.config;

import br.com.cadmea.spring.rest.ServicePath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;

@Configuration
@EnableWebSecurity
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${spring.profiles.active}")
    private String profile;

    @Value("${cadmea.users.roles:ROLE_NONE}")
    private String ROLES;

    @Lazy
    @Autowired
    private UserDetailsService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        logger.info(" security config profile:" + profile);
    }

    @Override
    public void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }


    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        final String allRoles = prepareAllRoles();
        http
                .csrf().disable().authorizeRequests()
                .antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources", "/configuration/security", "/swagger-ui.html", "/webjars/**").permitAll()
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/register").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/logout").permitAll()
                .antMatchers("/forgot").permitAll()
                .antMatchers("/showRecoveryPassword").permitAll()
                .antMatchers("/connect/**").permitAll()
                .antMatchers("/social/**", "/signin/**", "/signup/**").permitAll()

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
                .logout()
                .logoutUrl(ServicePath.LOGOUT_PATH)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl(ServicePath.LOGIN_PATH)
                .permitAll();
    }

    /**
     * get All roles defined in cadmea.properties
     *
     * @return
     */
    private String prepareAllRoles() {
        String allRoles = "";
        if (ROLES.contains(",")) {
            final String[] roles = ROLES.split(",");
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
