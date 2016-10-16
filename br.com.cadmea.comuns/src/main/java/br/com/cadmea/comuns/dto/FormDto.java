package br.com.cadmea.comuns.dto;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import br.com.cadmea.comuns.orm.EntityPersistent;

public abstract class FormDto<E extends EntityPersistent>
    implements DomainTransferObject {

  private Serializable id;
  private E entity;
  private Collection<E> entities;

  public E getEntity() {
    return entity;
  }

  public void setEntity(E entity) {
    this.entity = entity;
  }

  public Collection<E> getEntities() {
    return entities;
  }

  public void setEntities(Collection<E> entities) {
    this.entities = entities;
  }

  public Serializable getId() {
    return id;
  }

  public void setId(Serializable id) {
    this.id = id;
  }

  @SuppressWarnings("unchecked")
  public void createNewInstance() {
    try {
      Class<E> entidade = (Class<E>) ((ParameterizedType) getClass()
          .getGenericSuperclass()).getActualTypeArguments()[0];
      setEntity(entidade.newInstance());
    } catch (IllegalAccessException ex) {
      ex.printStackTrace();
    } catch (InstantiationException e) {
      e.printStackTrace();
    }

  }

  public boolean validate() {
    return true;
  }

  /**
   * Retorna os parametros de pesquisa
   *
   * @return Map<String, Object>
   */
  public Map<String, Object> getParams() {
    return new HashMap<String, Object>();
  }

}
