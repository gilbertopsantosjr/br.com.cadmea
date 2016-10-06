package br.com.cadmea.spring.rest;

import java.lang.reflect.ParameterizedType;
import java.rmi.ServerException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.cadmea.baseservico.BaseMaintenanceSrv;
import br.com.cadmea.model.orm.BaseEntityPersistent;

/**
 * @author Gilberto Santos
 *
 */
public abstract class GenericRestService<E extends BaseEntityPersistent>
    implements ServiceMap {

  private final Logger LOGGER = Logger.getLogger(this.getClass());

  private final Class<E> clazz;

  @SuppressWarnings("unchecked")
  public GenericRestService() {
    this.clazz = (Class<E>) ((ParameterizedType) getClass()
        .getGenericSuperclass()).getActualTypeArguments()[0];
  }

  public abstract BaseMaintenanceSrv<E> getBaseMaintenanceSrv();

  @RequestMapping(path = "/find/all", method = RequestMethod.GET)
  public List<E> findAll() {
    if (this.LOGGER.isDebugEnabled()) {
      this.LOGGER.debug("Requesting all records.");
    }
    return new ArrayList<>(getBaseMaintenanceSrv().listAll());
  }

  @RequestMapping(path = "/find/query", method = RequestMethod.GET)
  public List<E> findByNamedQuery() {
    if (this.LOGGER.isDebugEnabled()) {
      this.LOGGER.debug("Requesting all records.");
    }
    return new ArrayList<>(getBaseMaintenanceSrv().find("findByKey",
        new HashMap<String, Object>()));
  }

  @RequestMapping(path = "/entity", method = RequestMethod.GET)
  public String getNameOfEntity() {
    return ToStringBuilder.reflectionToString(clazz,
        ToStringStyle.MULTI_LINE_STYLE);
  }

  @RequestMapping(path = "/create", method = RequestMethod.POST)
  public E insert(@RequestBody E entityObject) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug(String.format("Saving the entity [%s].", entityObject));
    }
    return getBaseMaintenanceSrv().insert(entityObject);
  }

  @RequestMapping(path = "/update", method = RequestMethod.PUT)
  public void update(@RequestBody E entityObject) throws ServerException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug(
          String.format("Request to update the record [%s].", entityObject));
    }
    if (entityObject.getId() == null) {
      String errorMessage = String.format("ID of entity [%s] cannot be null.",
          entityObject.getClass());
      this.LOGGER.error(errorMessage);
      throw new ServerException(errorMessage);
    } else {
      getBaseMaintenanceSrv().save(entityObject);
    }
  }

  @RequestMapping(path = "/delete", method = RequestMethod.DELETE)
  public void delete(@RequestBody E entityObject) throws ServerException {
    if (LOGGER.isDebugEnabled()) {
      this.LOGGER.debug(
          String.format("Request to delete the record [%s].", entityObject));
    }

    if (entityObject.getId() == null) {
      String errorMessage = String.format("ID of entity [%s] cannot be null.",
          entityObject.getClass());
      this.LOGGER.error(errorMessage);
      throw new ServerException(errorMessage);
    } else {
      getBaseMaintenanceSrv().remove(entityObject);
    }
  }

}
