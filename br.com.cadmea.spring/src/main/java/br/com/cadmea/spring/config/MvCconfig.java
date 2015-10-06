package br.com.cadmea.spring.config;

import br.com.cadmea.spring.util.JsonViewResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gilbertopsantosjr on 13/05/15.
 */
@EnableWebMvc
@Configuration
public class MvCconfig extends WebMvcConfigurerAdapter {

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        Map<String, MediaType> mediaTypes = new HashMap<String, MediaType>();
        mediaTypes.put("json", MediaType.APPLICATION_JSON);

        configurer.mediaTypes(mediaTypes);
        configurer.ignoreAcceptHeader(true);
    }
/*
    @Bean
    @ConditionalOnMissingBean(RequestContextListener.class)
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    } */

    @Bean
    public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager) {
        ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();

        List<ViewResolver> resolvers = new ArrayList<ViewResolver>();

        InternalResourceViewResolver r1 = new InternalResourceViewResolver();
        r1.setPrefix("/WEB-INF/pages/");
        r1.setSuffix(".jsp");
        r1.setViewClass(JstlView.class);
        resolvers.add(r1);

        JsonViewResolver r2 = new JsonViewResolver();
        resolvers.add(r2);

        resolver.setContentNegotiationManager(manager);
        return resolver;
    }

}
