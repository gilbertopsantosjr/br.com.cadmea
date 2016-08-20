package br.com.cadmea.spring.rest;

import java.lang.reflect.ParameterizedType;
import java.rmi.ServerException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.cadmea.comuns.orm.Entidade;

/**
 * @author gilbertopsantosjr
 *
 */
public class GenericRestService<E extends Entidade> implements ServiceMap {

	private final Logger LOGGER = Logger.getLogger(this.getClass());

	private final Class<E> clazz;
	
	@SuppressWarnings("unchecked")
	public GenericRestService() {
		this.clazz = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public List<E> findAll() {
		if (this.LOGGER.isDebugEnabled()) {
			this.LOGGER.debug("Requesting all records.");
		}
		return null;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public E insert(@RequestBody E entityObject) {
		if (this.LOGGER.isDebugEnabled()) {
			this.LOGGER.debug(String.format("Saving the entity [%s].", entityObject));
		}

		return null;
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public void update(@RequestBody E entityObject) throws ServerException {
		this.LOGGER.debug(String.format("Request to update the record [%s].", entityObject));

		if (entityObject.getId() == null) {
			String errorMessage = String.format("ID of entity [%s] cannot be null.", entityObject.getClass());
			this.LOGGER.error(errorMessage);
			throw new ServerException(errorMessage);
		}
		
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	public void delete(@RequestBody E entityObject) {
		this.LOGGER.debug(String.format("Request to delete the record [%s].", entityObject));
		
	}
	

}
