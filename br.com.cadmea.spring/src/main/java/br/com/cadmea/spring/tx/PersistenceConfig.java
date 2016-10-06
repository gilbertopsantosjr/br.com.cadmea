package br.com.cadmea.spring.tx;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
// @PropertySource(value = { "classpath:database/jdbc.properties" })
@EnableTransactionManagement
@EntityScan(basePackageClasses = { Jsr310JpaConverters.class })
public class PersistenceConfig {

  private static final String PROPERTY_NAME_HIBERNATE_MAX_FETCH_DEPTH = "hibernate.max_fetch_depth";
  private static final String PROPERTY_NAME_HIBERNATE_JDBC_FETCH_SIZE = "hibernate.jdbc.fetch_size";
  private static final String PROPERTY_NAME_HIBERNATE_JDBC_BATCH_SIZE = "hibernate.jdbc.batch_size";
  private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
  private static final String PROPERTY_NAME_HIBERNATE_GENERATE_SQL = "hibernate.generate_sql";
  private static final String PROPERTY_NAME_HIBERNATE_UPDATE_SQL = "hibernate.hbm2ddl.auto";
  private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";

  static final String ENTITYMANAGER_PACKAGES_TO_SCAN = "package.entities";

  @Autowired
  private Environment env;

  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
    dataSource.setUrl(env.getProperty("jdbc.url"));
    dataSource.setUsername(env.getProperty("jdbc.username"));
    dataSource.setPassword(env.getProperty("jdbc.password"));
    return dataSource;
  }

  @Bean
  public JpaTransactionManager jpaTransactionManager() {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager
        .setEntityManagerFactory(entityManagerFactoryBean().getObject());
    return transactionManager;
  }

  private HibernateJpaVendorAdapter vendorAdaptor() {
    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

    Boolean isShowSql = false;
    String showSql = env.getProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL);
    if (showSql != null && showSql.equals("true"))
      isShowSql = true;

    Boolean isGenerateSql = false;
    String generateSql = env.getProperty(PROPERTY_NAME_HIBERNATE_GENERATE_SQL);
    if (generateSql != null && generateSql.equals("true"))
      isGenerateSql = true;

    vendorAdapter.setShowSql(isShowSql);
    vendorAdapter.setGenerateDdl(isGenerateSql);
    vendorAdapter
        .setDatabasePlatform(env.getProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
    return vendorAdapter;
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
    String mypackages[] = new String[] {
        env.getProperty(ENTITYMANAGER_PACKAGES_TO_SCAN) };

    LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
    entityManagerFactoryBean.setJpaVendorAdapter(vendorAdaptor());
    entityManagerFactoryBean.setDataSource(dataSource());
    entityManagerFactoryBean.setJpaDialect(new HibernateJpaDialect());
    entityManagerFactoryBean
        .setPersistenceProviderClass(HibernatePersistenceProvider.class);
    entityManagerFactoryBean.setPackagesToScan(mypackages);
    entityManagerFactoryBean.setJpaProperties(jpaHibernateProperties());
    entityManagerFactoryBean.afterPropertiesSet();
    return entityManagerFactoryBean;
  }

  private Properties jpaHibernateProperties() {
    Properties properties = new Properties();
    properties.put(PROPERTY_NAME_HIBERNATE_UPDATE_SQL,
        env.getProperty(PROPERTY_NAME_HIBERNATE_UPDATE_SQL));
    properties.put(PROPERTY_NAME_HIBERNATE_MAX_FETCH_DEPTH,
        env.getProperty(PROPERTY_NAME_HIBERNATE_MAX_FETCH_DEPTH));
    properties.put(PROPERTY_NAME_HIBERNATE_JDBC_FETCH_SIZE,
        env.getProperty(PROPERTY_NAME_HIBERNATE_JDBC_FETCH_SIZE));
    properties.put(PROPERTY_NAME_HIBERNATE_JDBC_BATCH_SIZE,
        env.getProperty(PROPERTY_NAME_HIBERNATE_JDBC_BATCH_SIZE));
    properties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL,
        env.getProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));
    return properties;
  }

  @Bean
  public HibernateExceptionTranslator hibernateExceptionTranslator() {
    return new HibernateExceptionTranslator();
  }

}
