package br.com.cadmea.spring.tx;

import br.com.cadmea.model.util.LocalDateConverter;
import br.com.cadmea.model.util.LocalDateTimeConverter;
import br.com.cadmea.spring.util.JsonDateSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateExceptionTranslator;
import org.springframework.orm.hibernate5.support.OpenSessionInViewFilter;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import javax.servlet.Filter;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Optional;
import java.util.Properties;

@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@EntityScan(basePackages = {"br.com.cadmea.model"},
        basePackageClasses = {Jsr310JpaConverters.class, LocalDateConverter.class, LocalDateTimeConverter.class})
@EnableSpringDataWebSupport
public class PersistenceConfig {

    private static final String PROPERTY_NAME_HIBERNATE_MAX_FETCH_DEPTH = "cadmea.hibernate.max_fetch_depth";
    private static final String PROPERTY_NAME_HIBERNATE_JDBC_FETCH_SIZE = "cadmea.hibernate.jdbc.fetch_size";
    private static final String PROPERTY_NAME_HIBERNATE_JDBC_BATCH_SIZE = "cadmea.hibernate.jdbc.batch_size";
    private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "cadmea.hibernate.show_sql";
    private static final String PROPERTY_NAME_HIBERNATE_GENERATE_SQL = "cadmea.hibernate.generate_sql";
    private static final String PROPERTY_NAME_HIBERNATE_UPDATE_SQL = "cadmea.hibernate.hbm2ddl.auto";
    private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "cadmea.hibernate.dialect";
    private static final String ENTITYMANAGER_PACKAGES_TO_SCAN = "cadmea.package.entities";

    Properties env = null;
    InputStream input = null;

    @PostConstruct
    public void init() {
        loadEnvVariables();
    }

    @Bean
    public Module customModule() {
        final SimpleModule module = new SimpleModule();
        module.addSerializer(Date.class, new JsonDateSerializer.Serialize());
        module.addDeserializer(Date.class, new JsonDateSerializer.Deserialize());
        return module;
    }

    private void loadEnvVariables() {
        try {
            env = new Properties();
            final String filename = "cadmea.properties";
            input = PersistenceConfig.class.getClassLoader()
                    .getResourceAsStream(filename);
            env.load(input);
        } catch (final Exception e) {
            // TODO: handle exception
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Bean
    @Primary
    public DataSource dataSource() {
        final Optional<String> driverClasseOpt = Optional
                .ofNullable(env.getProperty("cadmea.jdbc.driverClassName"));
        final String driverClasse = driverClasseOpt.orElse("org.h2.Driver");

        final Optional<String> jdbcUrlOpt = Optional
                .ofNullable(env.getProperty("cadmea.jdbc.url"));

        final String jdbcUrl = jdbcUrlOpt
                .orElse("jdbc:h2:mem:cadmea;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false");

        final Optional<String> jdbcUserNameOpt = Optional
                .ofNullable(env.getProperty("cadmea.jdbc.username"));
        final String jdbcUserName = jdbcUserNameOpt.orElse("sa");

        final Optional<String> jdbcPasswordOpt = Optional
                .ofNullable(env.getProperty("cadmea.jdbc.password"));
        final String jdbcPassword = jdbcPasswordOpt.orElse("");

        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClasse);
        dataSource.setUrl(jdbcUrl);
        dataSource.setUsername(jdbcUserName);
        dataSource.setPassword(jdbcPassword);
        return dataSource;
    }

    @Bean
    public JpaTransactionManager jpaTransactionManager() {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager
                .setEntityManagerFactory(entityManagerFactoryBean().getObject());
        return transactionManager;
    }

    private HibernateJpaVendorAdapter vendorAdaptor() {
        final Optional<String> showSqlOpt = Optional
                .ofNullable(env.getProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));
        final Optional<String> generateSqlOpt = Optional
                .ofNullable(env.getProperty(PROPERTY_NAME_HIBERNATE_GENERATE_SQL));
        final Optional<String> dialectOpt = Optional
                .ofNullable(env.getProperty(PROPERTY_NAME_HIBERNATE_DIALECT));

        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        final Optional<String> driverClasseOpt = Optional
                .ofNullable(env.getProperty("cadmea.jdbc.driverClassName"));

        vendorAdapter.setDatabase(Database.H2);

        driverClasseOpt.ifPresent(x -> {
            if (x.contains("mysql")) {
                vendorAdapter.setDatabase(Database.MYSQL);
            }
            if (x.contains("postgresql")) {
                vendorAdapter.setDatabase(Database.POSTGRESQL);
            }
        });

        vendorAdapter.setDatabasePlatform(
                dialectOpt.orElse("org.hibernate.dialect.H2Dialect"));
        vendorAdapter.setShowSql(Boolean.valueOf(showSqlOpt.orElse("true")));
        vendorAdapter
                .setGenerateDdl(Boolean.valueOf(generateSqlOpt.orElse("true")));

        return vendorAdapter;
    }

    @Bean(name = "transactionManager")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
        final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

        if (env == null) {
            loadEnvVariables();
        }

        final String[] packages = new String[2];

        if (null != (env.getProperty(ENTITYMANAGER_PACKAGES_TO_SCAN))
                && !env.getProperty(ENTITYMANAGER_PACKAGES_TO_SCAN).isEmpty()) {
            packages[0] = env.getProperty(ENTITYMANAGER_PACKAGES_TO_SCAN);
        }

        if (null != env.getProperty("cadmea.tables.create") && env.getProperty("cadmea.tables.create").equals("true")) {
            packages[1] = "br.com.cadmea.model.orm";
        } else {
            packages[1] = "hatuna.matata";
        }

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
     *
     * @return jpaDialect
     */
    @Bean
    public JpaDialect jpaDialect() {
        return new HibernateJpaDialect();
    }

    private Properties jpaHibernateProperties() {
        final Properties properties = new Properties();

        final Optional<String> dialectOpt = Optional
                .ofNullable(env.getProperty(PROPERTY_NAME_HIBERNATE_UPDATE_SQL));
        properties.put(removeCadmea(PROPERTY_NAME_HIBERNATE_UPDATE_SQL),
                dialectOpt.orElse("create-drop"));

        final Optional<String> fechDepthOpt = Optional
                .ofNullable(env.getProperty(PROPERTY_NAME_HIBERNATE_MAX_FETCH_DEPTH));
        properties.put(removeCadmea(PROPERTY_NAME_HIBERNATE_MAX_FETCH_DEPTH),
                fechDepthOpt.orElse("3"));

        final Optional<String> fechSizeOpt = Optional
                .ofNullable(env.getProperty(PROPERTY_NAME_HIBERNATE_JDBC_FETCH_SIZE));
        properties.put(removeCadmea(PROPERTY_NAME_HIBERNATE_JDBC_FETCH_SIZE),
                fechSizeOpt.orElse("50"));

        final Optional<String> bathSizeOpt = Optional
                .ofNullable(env.getProperty(PROPERTY_NAME_HIBERNATE_JDBC_FETCH_SIZE));

        properties.put(removeCadmea(PROPERTY_NAME_HIBERNATE_JDBC_BATCH_SIZE),
                bathSizeOpt.orElse("10"));

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

        return properties;
    }

    @Bean
    public FilterRegistrationBean registerOpenEntityManagerInViewFilterBean() {
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(openSessionInView());
        registrationBean.addUrlPatterns("/api/*");
        return registrationBean;
    }

    @Bean
    public HibernateJpaSessionFactoryBean sessionFactory() {
        return new HibernateJpaSessionFactoryBean();
    }

    @Bean
    public Filter openSessionInView() {
        return new OpenSessionInViewFilter();
    }

    @Bean
    public PersistenceAnnotationBeanPostProcessor persistenceAnnotationBeanPostProcessor() {
        return new PersistenceAnnotationBeanPostProcessor();
    }

    @Bean
    public HibernateExceptionTranslator hibernateExceptionTranslator() {
        return new HibernateExceptionTranslator();
    }

    private String removeCadmea(final String prop) {
        return prop.replace("cadmea.", "");
    }

}
