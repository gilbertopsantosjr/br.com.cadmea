package br.com.cadmea.baseservico;

import java.util.Collection;

import br.com.cadmea.comuns.orm.Entidade;

/**
 * 
 * @author Gilberto Santos
 *
 * @param <E>
 */
public interface BaseMaintenanceSrv<E extends Entidade> extends BaseMaintenanceFindSrv<E> {

  /**
   * Insere a entidade passada por parâmetro.
   * @param entidade
   * @return chave da mensagem de sucesso da operação.
   */
  E insert(E entidade);

  /**
   * Salva a entidade passada por parâmetro.
   * @param entidade
   * @return chave da mensagem de sucesso da operação.
   */
  void save(E entidade);

  /**
   * 
   * @param entidades
   */
  void save(Collection<E> entidades);

  /**
   * Remove a entidade passada por parâmetro.
   * @param entidade
   * @return chave da mensagem de sucesso da operação.
   */
  void remove(E entidade);

  /**
   * Remove todas as entidades da coleção.
   * @param entities
   * @return chave da mensagem de sucesso da operação.
   */
  void remove(Collection<E> entities);

}