package br.com.cadmea.spring.config;

import br.com.cadmea.model.util.LocalDateConverter;
import br.com.cadmea.model.util.LocalDateTimeConverter;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Optional;
import java.util.Properties;

@Configuration
@Import(SecurityConfig.class)
@EnableTransactionManagement(proxyTargetClass = true)
@EntityScan(basePackages = {"br.com.cadmea.model.orm"},
        basePackageClasses = {Jsr310JpaConverters.class, LocalDateConverter.class, LocalDateTimeConverter.class})
@EnableSpringDataWebSupport
public class PersistenceDatabaseConfig {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${cadmea.hibernate.max_fetch_depth:3}")
    private String PROPERTY_NAME_HIBERNATE_MAX_FETCH_DEPTH;

    @Value("${cadmea.hibernate.jdbc.fetch_size:50}")
    private String PROPERTY_NAME_HIBERNATE_JDBC_FETCH_SIZE;

    @Value("${cadmea.hibernate.jdbc.batch_size:10}")
    private String PROPERTY_NAME_HIBERNATE_JDBC_BATCH_SIZE;

    @Value("${cadmea.hibernate.show_sql:true}")
    private String PROPERTY_NAME_HIBERNATE_SHOW_SQL;

    @Value("${cadmea.hibernate.generate_sql:true}")
    private String PROPERTY_NAME_HIBERNATE_GENERATE_SQL;

    @Value("${cadmea.hibernate.hbm2ddl.auto:validate}")
    private String PROPERTY_NAME_HIBERNATE_UPDATE_SQL;

    @Value("${cadmea.hibernate.dialect:org.hibernate.dialect.H2Dialect}")
    private String PROPERTY_NAME_HIBERNATE_DIALECT;

    @Value("${cadmea.package.entities:hatuna.matata}")
    private String ENTITYMANAGER_PACKAGES_TO_SCAN;

    @Value("${cadmea.jdbc.driverClassName:org.h2.Driver}")
    private String DRIVER_CLASS_NAME;

    @Value("${cadmea.jdbc.url:jdbc:h2:mem:cadmea;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false}")
    private String JDBC_URL;

    @Value("${cadmea.jdbc.username:sa}")
    private String JDBC_USERNAME;

    @Value("${cadmea.jdbc.password}")
    private String JDBC_PASSWORD;

    @Value("${spring.profiles.active}")
    private String profile;

    @PostConstruct
    public void init() {
        logger.info(" persistence database config profile:" + profile);
    }

    /**
     * DataSource definition for database connection. Settings are read from
     * the application.properties file (using the env object).
     */
    @Bean(name = "dataSource")
    @ConfigurationProperties(prefix = "cadmea.jdbc")
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(DRIVER_CLASS_NAME);
        dataSource.setUrl(JDBC_URL);
        dataSource.setUsername(JDBC_USERNAME);
        dataSource.setPassword(JDBC_PASSWORD);
        return dataSource;
    }

    /**
     * define the JPA transaction manager
     *
     * @return
     */
    @Bean
    public JpaTransactionManager jpaTransactionManager() {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactoryBean().getObject());
        return transactionManager;
    }


    /**
     * Define Hibernate Configuration
     *
     * @return
     */
    @Bean
    @ConditionalOnBean(name = "dataSource")
    public HibernateJpaVendorAdapter vendorAdaptor() {
        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        final Optional<String> driverClasseOpt = Optional.ofNullable(DRIVER_CLASS_NAME);

        vendorAdapter.setDatabase(Database.H2);

        driverClasseOpt.ifPresent(driver -> {
            if (driver.contains("mysql")) {
                vendorAdapter.setDatabase(Database.MYSQL);
            }
            if (driver.contains("postgresql")) {
                vendorAdapter.setDatabase(Database.POSTGRESQL);
            }
        });

        vendorAdapter.setDatabasePlatform(PROPERTY_NAME_HIBERNATE_DIALECT);
        vendorAdapter.setShowSql(Boolean.valueOf(PROPERTY_NAME_HIBERNATE_GENERATE_SQL));
        vendorAdapter
                .setGenerateDdl(Boolean.valueOf(PROPERTY_NAME_HIBERNATE_GENERATE_SQL));

        return vendorAdapter;
    }

    /**
     * Declare the JPA entity manager factory.
     */
    @Bean(name = "transactionManager")
    @ConditionalOnBean(name = "dataSource")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
        final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        final String[] packages = new String[]{ENTITYMANAGER_PACKAGES_TO_SCAN};

        entityManagerFactoryBean.setJpaVendorAdapter(vendorAdaptor());
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setJpaDialect(jpaDialect());
        entityManagerFactoryBean
                .setPersistenceProviderClass(HibernatePersistenceProvider.class);
        entityManagerFactoryBean.setPackagesToScan(packages);
        entityManagerFactoryBean.setJpaProperties(jpaHibernateProperties());
        return entityManagerFactoryBean;
    }


    /**
     * The jpa dialect declaration.
     * <p>
     *
     * @return jpaDialect
     */
    @Bean
    public JpaDialect jpaDialect() {
        return new HibernateJpaDialect();
    }

    private Properties jpaHibernateProperties() {
        final Properties properties = new Properties();
        properties.put("hibernate.hbm2ddl.auto", PROPERTY_NAME_HIBERNATE_UPDATE_SQL);
        properties.put("hibernate.max_fetch_depth", PROPERTY_NAME_HIBERNATE_MAX_FETCH_DEPTH);
        properties.put("hibernate.jdbc.fetch_size", PROPERTY_NAME_HIBERNATE_JDBC_FETCH_SIZE);
        properties.put("hibernate.jdbc.batch_size", PROPERTY_NAME_HIBERNATE_JDBC_BATCH_SIZE);
        properties.put("hibernate.show_sql", PROPERTY_NAME_HIBERNATE_SHOW_SQL);
        properties.put("hibernate.generate_sql", PROPERTY_NAME_HIBERNATE_GENERATE_SQL);

        //properties.put("hibernate.default_schema", "public");
        properties.put("hibernate.current_session_context_class", "thread"); // Contexto de sessão a ser usado
        properties.put("hibernate.transaction.factory_class", "org.hibernate.transaction.JDBCTransactionFactory "); // Delegada as transações (JDBC) a bases de dados (Padrão)
        properties.put("hibernate.jdbc.factory_class", "org.hibernate.jdbc.NonBatchingBatcherFactory"); //Selecione um org.hibernate.jdbc.Batcher personalizado
        properties.put("hibernate.jdbc.batch_size", 100);// Lote de SQL
        properties.put("hibernate.cache.use_second_level_cache", true); //Cache
        properties.put("hibernate.transaction.auto_close_session", true); // Fecha sessão automaticamente
        properties.put("hibernate.generate_statistics", false); // Estatisticas de processos SQL
        properties.put("hibernate.use_sql_comments", false);
        properties.put("hibernate.connection.pool_size", 50); //Poll de conexão
        properties.put("hibernate.format_sql", false);

        return properties;
    }

    /**
     * @return
     */
    @Bean
    public HibernateExceptionTranslator hibernateExceptionTranslator() {
        return new HibernateExceptionTranslator();
    }

    /**
     * PersistenceExceptionTranslationPostProcessor is a bean post processor
     * which adds an advisor to any bean annotated with Repository so that any
     * platform-specific exceptions are caught and then rethrown as one
     * Spring's unchecked data access exceptions (i.e. a subclass of
     * DataAccessException).
     */
    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }


    /**
     * @param prop
     * @return
     */
    private String removeCadmea(final String prop) {
        return prop.replace("cadmea.", "");
    }


}
