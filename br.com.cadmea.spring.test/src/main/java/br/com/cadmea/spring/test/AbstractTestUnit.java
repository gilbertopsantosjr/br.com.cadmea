package br.com.cadmea.spring.test;

import br.com.cadmea.comuns.UnitaryTest;
import br.com.cadmea.comuns.util.Util;

public abstract class AbstractTestUnit implements UnitaryTest {

	/**
	 * return a mock number for id
	 * @return Long 
	 */
	public Long getMockId(){
		return Long.parseLong(Util.generatorNumericCode(1));
	}
	
	public void beforeAllTest(){} 
	
}
