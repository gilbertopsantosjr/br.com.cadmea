package br.com.cadmea.spring.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;

@Configuration
@Import(SecurityConfig.class)
public class PersistenceDatabaseConfig {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${spring.profiles.active}")
    private String profile;

    @PostConstruct
    public void init() {
        logger.info(" persistence database config profile:" + profile);
    }

}
