/**
 *
 */
package br.com.cadmea.comuns.dto;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import br.com.cadmea.comuns.orm.EntityPersistent;

/**
 * @author Gilberto Santos
 *
 */
public interface DomainTransferObject<E extends EntityPersistent> {

  E getEntity();

  void setEntity(E entity);

  Collection<E> getEntities();

  public void setEntities(Collection<E> entities);

  Serializable getId();

  /**
   * Retorna os parametros de pesquisa
   *
   * @return Map<String, Object>
   */
  Map<String, Object> getParams();

}
