package br.com.cadmea.infra.negocio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.cadmea.comuns.exceptions.BusinessException;
import br.com.cadmea.comuns.exceptions.DaoException;
import br.com.cadmea.comuns.exceptions.enums.DefaultMessages;
import br.com.cadmea.comuns.orm.enums.Result;
import br.com.cadmea.model.BaseEntityPersistent;
import br.com.cadmea.model.dao.DaoGenerico;

/**
 * Classe responsavel pelas regras de negocio da entidade manipulada pela classe
 * concreta e com algumas operacaoes de persistencia ja definidas.
 *
 * @version 1.0
 * @param <E>
 *          Entidade que sera manipulada
 */
public abstract class BaseNegocial<E extends BaseEntityPersistent>
    implements Negocial<E> {

  /**
   * Construtor protegido para rentringir a criacao desta classe somente a
   * subclasses.
   */
  protected BaseNegocial() {
    // Construtor.
  }

  /**
   * private final Hibernate3DtoCopier copier = new Hibernate3DtoCopier();
   *
   * public Hibernate3DtoCopier getCopier() { return copier; }
   */

  /**
   * Retorna instancia de DAO.
   *
   * @return O valor do atributo dao
   */
  protected abstract DaoGenerico<E> getDao();

  /*
   * (non-Javadoc)
   *
   * @see br.com.cadmea.infra.negocio.Negocial#find(java.io.Serializable)
   */
  @Override
  @Transactional(readOnly = true)
  public E find(Serializable identificador) {
    E entidade = getDao().find(identificador);
    if (entidade == null) {
      throw new BusinessException(DefaultMessages.NOT_FOUND);
    }
    return entidade;
  }

  /*
   * (non-Javadoc)
   *
   * @see br.com.cadmea.infra.negocio.Negocial#insert(E)
   */
  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public E insert(E entidade) {
    if (isThere(entidade))
      throw new BusinessException(DefaultMessages.FOUND);
    return getDao().save(entidade);
  }

  /*
   * (non-Javadoc)
   *
   * @see br.com.cadmea.infra.negocio.Negocial#save(E)
   */
  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void save(E entity) {
    getDao().save(entity);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void save(Collection<E> entities) {
    getDao().save(entities);
  }

  /*
   * (non-Javadoc)
   *
   * @see br.com.cadmea.infra.negocio.Negocial#remove(E)
   */
  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void remove(E entidade) {
    if (!isThere(entidade))
      throw new BusinessException(DefaultMessages.NOT_FOUND);
    getDao().remove(entidade);
  }

  /*
   * (non-Javadoc)
   *
   * @see br.com.cadmea.infra.negocio.Negocial#remove(java.util.Collection)
   */
  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void remove(Collection<E> entidades) {
    Collection<E> removidas = new ArrayList<E>();
    Iterator<E> iterator = entidades.iterator();
    while (iterator.hasNext()) {
      E entidade = iterator.next();
      try {
        remove(entidade);
        removidas.add(entidade);
      } catch (Exception e) {
        continue;
      }
    }
    entidades.removeAll(removidas);
  }

  /*
   * (non-Javadoc)
   *
   * @see br.com.cadmea.infra.negocio.Negocial#find(java.util.Map,
   * br.com.cadmea.comuns.orm.enums.Result)
   */
  @Override
  @Transactional(readOnly = true)
  public E find(Map<String, Object> params, Result resl) {
    E entidade = getDao().find(params, resl);
    if (entidade == null) {
      throw new BusinessException(DefaultMessages.NOT_FOUND);
    }
    // E copy = copier.hibernate2dto(entidade);
    return entidade;
  }

  @Override
  @Transactional(readOnly = true)
  public Collection<E> find(String namedQuery, Map<String, Object> parameters)
      throws DaoException {
    return getDao().find(namedQuery, parameters);
  }

  @Override
  @Transactional(readOnly = true)
  public E find(String propriedade, Object valor, Result res)
      throws DaoException {
    return getDao().find(propriedade, valor, res);
  }

  /*
   * (non-Javadoc)
   *
   * @see br.com.cadmea.infra.negocio.Negocial#find(java.util.Map)
   */
  @Override
  @Transactional(readOnly = true)
  public Collection<E> find(Map<String, Object> params) {
    Collection<E> entidades = getDao().find(params);
    if (entidades == null) {
      throw new BusinessException(DefaultMessages.NOT_FOUND);
    }
    return entidades;
  }

  /*
   * (non-Javadoc)
   *
   * @see br.com.cadmea.infra.negocio.Negocial#listAll()
   */
  @Override
  @Transactional(readOnly = true)
  public Collection<E> findAll() {
    return getDao().listAll();
  }

  /*
   * (non-Javadoc)
   *
   * @see br.com.cadmea.infra.negocio.Negocial#listAll(int, int)
   */
  @Override
  @Transactional(readOnly = true)
  public Collection<E> findAll(int de, int ate) {
    return getDao().listAll(new HashMap<String, Object>(), de, ate);
  }

  /*
   * (non-Javadoc)
   *
   * @see br.com.cadmea.infra.negocio.Negocial#listAll(java.util.Map, int, int)
   */
  @Override
  @Transactional(readOnly = true)
  public Collection<E> findAll(Map<String, Object> params, int de, int ate) {
    return getDao().listAll(params, de, ate);
  }

  /**
   * Obtem a descricao da entidade informada.
   *
   * @param entidade
   *          Entidade a ser obtida a descricao
   * @return A descricao da entidade informada
   */
  protected String getDescricao(E entidade) {
    return entidade.getId().toString();
  }

  /**
   * Verifica se o registro informado ja existe.<br>
   * Obs. Este metodo deve ser sobrescrito quando houver a necessidade de
   * validar se os dados informados ja existem e nao podem ser incluidos. Este
   * metodo e chamado na inclusao e na alteracao.
   *
   * @param entidade
   *          Objeto a ser verificado.
   * @return <code>true</code> caso o objeto ja exista e <code>false</code> caso
   *         contrario.
   */
  protected boolean isThere(E entidade) {
    return true;
  }

}
