package br.com.cadmea.spring.config;

import br.com.cadmea.spring.beans.SmtpEmailSender;
import br.com.cadmea.spring.beans.StringToEnumConverterFactory;
import br.com.cadmea.spring.util.JsonDateSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.orm.hibernate5.support.OpenSessionInViewFilter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.annotation.PostConstruct;
import javax.servlet.Filter;
import java.util.Date;
import java.util.Locale;


@Configuration
@PropertySource(value = "classpath:config/cadmea-${spring.profiles.active}.properties")
@Import(PersistenceDatabaseConfig.class)
public class CoreConfig {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${spring.profiles.active}")
    private String profile;

    @PostConstruct
    public void init() {
        logger.info("core config profile:" + profile);
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public Module jsonDateSerializer() {
        final SimpleModule module = new SimpleModule();
        module.addSerializer(Date.class, new JsonDateSerializer.Serialize());
        module.addDeserializer(Date.class, new JsonDateSerializer.Deserialize());
        return module;
    }

    @Bean
    public LocaleResolver localeResolver() {
        final SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.UK);
        return slr;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        final LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    @Bean
    public PasswordEncoder passwordencoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SmtpEmailSender smtpEmailSender() {
        return new SmtpEmailSender();
    }

    @Bean
    public StringToEnumConverterFactory stringToEnumConverterFactory() {
        return new StringToEnumConverterFactory();
    }


    /**
     * @return FilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean registerOpenEntityManagerInViewFilterBean() {
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(openSessionInView());
        registrationBean.addUrlPatterns("/api/*");
        return registrationBean;
    }

    /**
     * openSessionInView
     *
     * @return
     */
    @Bean
    public Filter openSessionInView() {
        return new OpenSessionInViewFilter();
    }


}
