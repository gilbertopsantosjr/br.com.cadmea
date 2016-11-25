package br.com.cadmea.spring.tx;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@EntityScan(basePackages = { "br.com.cadmea.model.orm" }, basePackageClasses= { Jsr310JpaConverters.class })
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
	
	private void loadEnvVariables(){
		try {
			env = new Properties();
			final String filename = "cadmea.properties";
			input = PersistenceConfig.class.getClassLoader().getResourceAsStream(filename);
			env.load(input);
		} catch (Exception e) {
			// TODO: handle exception
		} finally{
        	if(input!=null){
        		try {
        			input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        }
	}

	@Bean
	@Primary
	public DataSource dataSource() {
		Optional<String> driverClasseOpt = Optional.ofNullable(env.getProperty("cadmea.jdbc.driverClassName"));
		String driverClasse = driverClasseOpt.orElse("org.h2.Driver");

		Optional<String> jdbcUrlOpt = Optional.ofNullable(env.getProperty("cadmea.jdbc.url"));
		String jdbcUrl = jdbcUrlOpt.orElse("jdbc:h2:mem:cadmea;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false");

		Optional<String> jdbcUserNameOpt = Optional.ofNullable(env.getProperty("cadmea.jdbc.username"));
		String jdbcUserName = jdbcUserNameOpt.orElse("sa");

		Optional<String> jdbcPasswordOpt = Optional.ofNullable(env.getProperty("cadmea.jdbc.password"));
		String jdbcPassword = jdbcPasswordOpt.orElse("");

		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(driverClasse);
		dataSource.setUrl(jdbcUrl);
		dataSource.setUsername(jdbcUserName);
		dataSource.setPassword(jdbcPassword);
		return dataSource;
	}

	@Bean
	public JpaTransactionManager jpaTransactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactoryBean().getObject());
		return transactionManager;
	}

	private HibernateJpaVendorAdapter vendorAdaptor() {
		Optional<String> showSqlOpt = Optional.ofNullable(env.getProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));
		Optional<String> generateSqlOpt = Optional.ofNullable(env.getProperty(PROPERTY_NAME_HIBERNATE_GENERATE_SQL));
		Optional<String> dialectOpt = Optional.ofNullable(env.getProperty(PROPERTY_NAME_HIBERNATE_DIALECT));

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setDatabase(Database.H2);
		vendorAdapter.setDatabasePlatform(dialectOpt.orElse("org.hibernate.dialect.H2Dialect"));
		vendorAdapter.setShowSql(Boolean.valueOf(showSqlOpt.orElse("true")));
		vendorAdapter.setGenerateDdl(Boolean.valueOf(generateSqlOpt.orElse("true")));
		return vendorAdapter;
	}

	@Bean(name = "transactionManager")
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
		if(env == null)
			loadEnvVariables();
		
		String entityPackagesToScan = "";
		
		if ( null != (env.getProperty(ENTITYMANAGER_PACKAGES_TO_SCAN)) && !env.getProperty(ENTITYMANAGER_PACKAGES_TO_SCAN).isEmpty() )
			entityPackagesToScan = env.getProperty(ENTITYMANAGER_PACKAGES_TO_SCAN);
		
		String mypackages[] = new String[] { "br.com.cadmea.model.orm", entityPackagesToScan };

		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setJpaVendorAdapter(vendorAdaptor());
		entityManagerFactoryBean.setDataSource(dataSource());
		entityManagerFactoryBean.setJpaDialect(new HibernateJpaDialect());
		entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
		entityManagerFactoryBean.setPackagesToScan(mypackages);
		entityManagerFactoryBean.setJpaProperties(jpaHibernateProperties());
		return entityManagerFactoryBean;
	}

	private Properties jpaHibernateProperties() {
		Properties properties = new Properties();

		Optional<String> dialectOpt = Optional.ofNullable(env.getProperty(PROPERTY_NAME_HIBERNATE_UPDATE_SQL));
		properties.put(removeCadmea(PROPERTY_NAME_HIBERNATE_UPDATE_SQL), dialectOpt.orElse("create"));

		Optional<String> fechDepthOpt = Optional.ofNullable(env.getProperty(PROPERTY_NAME_HIBERNATE_MAX_FETCH_DEPTH));
		properties.put(removeCadmea(PROPERTY_NAME_HIBERNATE_MAX_FETCH_DEPTH), fechDepthOpt.orElse("3"));

		Optional<String> fechSizeOpt = Optional.ofNullable(env.getProperty(PROPERTY_NAME_HIBERNATE_JDBC_FETCH_SIZE));
		properties.put(removeCadmea(PROPERTY_NAME_HIBERNATE_JDBC_FETCH_SIZE), fechSizeOpt.orElse("50"));

		Optional<String> bathSizeOpt = Optional.ofNullable(env.getProperty(PROPERTY_NAME_HIBERNATE_JDBC_FETCH_SIZE));

		properties.put(removeCadmea(PROPERTY_NAME_HIBERNATE_JDBC_BATCH_SIZE), bathSizeOpt.orElse("10"));

		return properties;
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
