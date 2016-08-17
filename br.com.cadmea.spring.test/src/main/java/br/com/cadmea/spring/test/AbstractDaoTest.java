package br.com.cadmea.spring.test;

import static org.testng.AssertJUnit.assertNotNull;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import br.com.cadmea.comuns.UnitaryTest;

/**
 * @author Gilberto Santos
 */
@ContextConfiguration(locations = { "classpath:applicationContext-test.xml" })
public abstract class AbstractDaoTest extends AbstractTestNGSpringContextTests implements UnitaryTest {

	public static final Logger logger = Logger.getLogger("AbstractDaoTest");

	@Autowired
	private ApplicationContext applicationContext;

	@BeforeClass
	public void setUp() throws Exception {
		assertNotNull(" Not possible start the Spring Context", getApplicationContext());
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

}