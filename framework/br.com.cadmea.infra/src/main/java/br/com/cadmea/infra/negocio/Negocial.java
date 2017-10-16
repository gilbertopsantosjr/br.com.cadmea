package br.com.cadmea.infra.negocio;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import br.com.cadmea.comuns.exceptions.DaoException;
import br.com.cadmea.comuns.orm.EntityPersistent;
import br.com.cadmea.comuns.orm.enums.Result;


public interface Negocial<E extends EntityPersistent> {

  /**
   *
   * @param identificador
   * @return
   */
  public abstract E find(Serializable identificador);

  /**
   *
   * @param entidade
   * @return
   */
  public abstract E insert(E entidade);

  /**
   *
   * @param entidade
   */
  public abstract void save(E entidade);

  /**
   *
   * @param entidade
   */
  public abstract void save(Collection<E> entidade);

  /**
   *
   * @param entidade
   */
  public abstract void remove(E entidade);

  /**
   *
   * @param entidades
   */
  public abstract void remove(Collection<E> entidades);

  /**
   *
   * @param params
   * @param resl
   * @return
   */
  public abstract E find(Map<String, Object> params, Result resl);

  /**
   *
   * @param namedQuery
   * @param parameters
   * @return
   * @throws DaoException
   */
  Collection<E> findByNamedQuery(String namedQuery, Map<String, Object> parameters)
      throws DaoException;

  
  /**
   * 
   * @param namedQuery
   * @param parameters
   * @param resl
   * @return
   * @throws DaoException
   */
  E findByNamedQuery(String namedQuery, Map<String, Object> parameters, Result resl)
	      throws DaoException;

  /**
   *
   * @param propriedade
   * @param valor
   * @param res
   * @return
   * @throws DaoException
   */
  public E find(String propriedade, Object valor, Result res)
      throws DaoException;

  /**
   *
   * @param params
   * @return
   */
  public abstract Collection<E> find(Map<String, Object> params);

  /**
   *
   * @return
   */
  public Collection<E> findAll();

  /**
   *
   * @param de
   * @param ate
   * @return
   */
  public abstract Collection<E> findAll(int de, int ate);

  /**
   *
   * @param params
   * @param de
   * @param ate
   * @return
   */
  public abstract Collection<E> findAll(Map<String, Object> params, int de,
      int ate);

}