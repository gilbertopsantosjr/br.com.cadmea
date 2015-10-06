package br.com.cadmea.spring.util;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.JdbcTemplate;

public class SchemaCreator implements InitializingBean {

	private List<String> schemas;
	private DataSource dataSource;

	public List<String> getSchemas() {
		return schemas;
	}

	public void setSchemas(List<String> schemas) {
		this.schemas = schemas;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * Create schema.
	 * 
	 * @throws Exception
	 *             any exception
	 */
	public void afterPropertiesSet() throws Exception {
		if (getSchemas() != null) {
			for (String schema : getSchemas()) {
				JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
				jdbcTemplate.execute("DROP SCHEMA IF EXISTS "+schema+" CASCADE ");
				jdbcTemplate.execute("CREATE SCHEMA "+schema+" AUTHORIZATION cefas");
			}
		}
	}

}
