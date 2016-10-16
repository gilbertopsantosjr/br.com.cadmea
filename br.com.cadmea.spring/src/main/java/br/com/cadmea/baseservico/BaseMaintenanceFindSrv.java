package br.com.cadmea.baseservico;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import br.com.cadmea.comuns.exceptions.DaoException;
import br.com.cadmea.comuns.orm.EntityPersistent;
import br.com.cadmea.comuns.orm.enums.Result;
import br.com.cadmea.comuns.srv.BaseServico;

public interface BaseMaintenanceFindSrv<E extends EntityPersistent>
    extends BaseServico<E> {

  /**
   * Retorna a entidade solicitada.
   *
   * @param identificador
   * @return entidade
   */
  @Override
  E find(Serializable identificador);

  /**
   * Retorna uma coleção de entidade ordenado pelo identificador
   *
   * @param params
   * @param orderBy
   * @return Collection<E>
   */
  @Override
  Collection<E> find(Map<String, Object> params);

  /**
   * Busca o objeto da entidade por várias propriedade igual a valor
   *
   * @param params
   * @return E
   */
  @Override
  E find(Map<String, Object> params, Result res);

  /**
   * Busca o objeto da entidade por uma propriedade igual a valor
   *
   * @param propNome
   * @param valor
   * @return E
   */
  E find(String propNome, Object valor);

  /**
   * Retorna todos os objetos da entidade
   *
   * @return Collection
   */
  @Override
  Collection<E> listAll();

  /**
   *
   * @param de
   * @param ate
   * @return Collection
   */
  @Override
  Collection<E> listAll(Map<String, Object> params, int de, int ate);

  /**
   *
   * @param namedQuery
   * @param parameters
   * @return
   * @throws DaoException
   */
  Collection<E> find(String namedQuery, Map<String, Object> parameters)
      throws DaoException;

}