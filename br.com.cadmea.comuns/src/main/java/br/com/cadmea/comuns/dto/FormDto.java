package br.com.cadmea.comuns.dto;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import br.com.cadmea.comuns.orm.EntityPersistent;

public abstract class FormDto<E extends EntityPersistent>
    implements DomainTransferObject<E> {

  private E entity;
  private Collection<E> entities;

  @Override
  public E getEntity() {
    return entity;
  }

  @Override
  public void setEntity(E entity) {
    this.entity = entity;
  }

  @Override
  public Collection<E> getEntities() {
    return entities;
  }

  @Override
  public void setEntities(Collection<E> entities) {
    this.entities = entities;
  }

  @Override
  public Long getId() {
    if (getEntity() != null) {
      if (getEntity().getId() != null)
        return getEntity().getId();
    }
    return null;
  }

  @SuppressWarnings("unchecked")
  public void newInstance() {
    try {
      Class<E> entidade = (Class<E>) ((ParameterizedType) getClass()
          .getGenericSuperclass()).getActualTypeArguments()[0];
      setEntity(entidade.newInstance());
    } catch (IllegalAccessException ex) {
      ex.printStackTrace();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  public boolean validate() {
    return true;
  }

  @Override
  public Map<String, Object> getParams() {
    return new HashMap<String, Object>();
  }

}
