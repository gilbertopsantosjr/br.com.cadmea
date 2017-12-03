package br.com.cadmea.web;

import br.com.cadmea.comuns.clazz.ProjectStage;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.servlet.Filter;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = StartCadmeaSystem.class)
@ActiveProfiles({ProjectStage.UNIT_TEST})
public abstract class AbstractTestUnit {

    @Autowired
    private Filter springSecurityFilterChain;


    @Before
    public void setup() {
    }


    @BeforeClass
    public static void init() {

    }

}
