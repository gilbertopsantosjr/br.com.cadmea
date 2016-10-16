package br.com.cadmea.baseservico;

import java.util.Collection;

import br.com.cadmea.comuns.orm.EntityPersistent;

/**
 *
 * @author Gilberto Santos
 *
 * @param <E>
 */
public interface BaseMaintenanceSrv<E extends EntityPersistent>
    extends BaseMaintenanceFindSrv<E> {

  /**
   * Insere a entidade passada por parâmetro.
   *
   * @param entidade
   * @return chave da mensagem de sucesso da operação.
   */
  E insert(E entidade);

  /**
   * Salva a entidade passada por parâmetro.
   *
   * @param entidade
   */
  void save(E entidade);

  /**
   * Salva a entidade passada por parâmetro.
   *
   * @param entidades
   */
  void save(Collection<E> entidades);

  /**
   * Remove a entidade passada por parâmetro.
   *
   * @param entidade
   */
  void remove(E entidade);

  /**
   * Remove todas as entidades da coleção.
   *
   * @param entities
   */
  void remove(Collection<E> entities);

}