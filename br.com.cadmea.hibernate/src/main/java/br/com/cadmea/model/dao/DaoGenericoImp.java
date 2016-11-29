package br.com.cadmea.model.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;
import javax.validation.ValidationException;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.ResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.cadmea.comuns.exceptions.DaoException;
import br.com.cadmea.comuns.exceptions.SystemException;
import br.com.cadmea.comuns.orm.enums.ComparacaoData;
import br.com.cadmea.comuns.orm.enums.OrderToSort;
import br.com.cadmea.comuns.orm.enums.Result;
import br.com.cadmea.comuns.util.ConstantesComum;
import br.com.cadmea.comuns.util.CriteriaData;
import br.com.cadmea.comuns.util.CriteriaDataConstrutor;
import br.com.cadmea.comuns.util.Util;
import br.com.cadmea.model.BaseEntityPersistent;

/**
 * Interface para acesso a base de dados ja com metodos de manutencao (CRUD)
 * pre-definidos.
 *
 * @param <E>
 *          Entidade a ser manipulado
 * @author Gilberto Santos
 * @version 1.0
 */
public abstract class DaoGenericoImp<T extends BaseEntityPersistent, ID extends Serializable>
    implements DaoGenerico<T> {

  private final Class<T> clazz;
  private final Logger log = LoggerFactory.getLogger(this.getClass());

  @PersistenceContext
  private EntityManager em;

  public EntityManager getEntityManager() {
    return em;
  }

  @SuppressWarnings("unchecked")
  public DaoGenericoImp() {
    this.clazz = (Class<T>) ((ParameterizedType) getClass()
        .getGenericSuperclass()).getActualTypeArguments()[0];
  }

  @Override
  public Collection<T> find(String namedQuery, Map<String, Object> parameters) {
    TypedQuery<T> q = obterPorNamedQuery(namedQuery, parameters);
    return q.getResultList();
  }

  @Override
  public T find(String namedQuery, Map<String, Object> parameters,
      Result resultado) {
    T retorno = null;
    TypedQuery<T> query = obterPorNamedQuery(namedQuery, parameters);
    if (resultado.equals(Result.UNIQUE))
      retorno = obterApenasUmaEntidade(query);
    return retorno;
  }

  /**
   * opcao para tratar entidade nao encontrada com getSingleResult
   */
  private T obterApenasUmaEntidade(TypedQuery<T> query) {
    query.setMaxResults(1);
    List<T> list = query.getResultList();
    if (list == null || list.size() == 0) {
      return null;
    }
    return list.get(0);
  }

  @SuppressWarnings("unchecked")
  private TypedQuery<T> obterPorNamedQuery(String namedQuery,
      Map<String, Object> parameters) {
    String preNamedQuery = getClazz().getSimpleName() + "." + namedQuery;
    TypedQuery<T> q = (TypedQuery<T>) getEntityManager()
        .createNamedQuery(preNamedQuery);
    if (parameters != null) {
      for (Entry<String, Object> e : parameters.entrySet()) {
        q.setParameter(e.getKey(), e.getValue());
      }
    }
    return q;
  }

  /**
   * Insere o objeto informado na entidade.
   *
   * @param entidade
   *          Objeto da a ser inserido.
   * @return Chave da entidade inserida na base de dados, podendo ser um objeto
   *         com mais de um atributo representando a chave
   */
  @Override
  public void insert(T entidade) {
    save(entidade);
  }

  @Override
  public void save(Collection<T> entidades) {
    // getEntityManager().getTransaction().begin();
    for (T t : entidades) {
      save(t);
    }
    // getEntityManager().getTransaction().commit();
  }

  @Override
  public T find(Serializable identificador) {
    T obj = null;
    obj = getEntityManager().find(getClazz(), identificador);
    if (obj == null)
      throw new SystemException(
          "Not possible to find entity: " + getClazz().getSimpleName());
    return obj;
  }

  /**
   * obtem pelo identificador natural id
   *
   * @param id
   * @return T
   */
  public T get(Long id) {
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("id", id);
    return find(ConstantesComum.OBTER_POR_ID, params, Result.UNIQUE);
  }

  /**
   * Recupera a sessao do hibernate.
   *
   * @return Session sessao do hibernate
   */
  public Session getSessionOfHibernate() {
    if (getEntityManager().getDelegate() != null)
      return (Session) getEntityManager().getDelegate();
    else
      throw new RuntimeException("Session not found !");
  }

  private Collection<T> obter(Map<String, Object> params, Criteria criteria)
      throws RuntimeException {
    return obter(consulta(params, criteria));
  }

  @Override
  public Collection<T> find(String propriedade, Object valor) {
    Map<String, Object> params = new HashMap<String, Object>();
    params.put(propriedade, valor);
    return find(params);
  }

  @Override
  public T find(String propriedade, Object valor, Result res)
      throws DaoException {
    Map<String, Object> params = new HashMap<String, Object>();
    params.put(propriedade, valor);
    return find(params, Result.UNIQUE);
  }

  @Override
  public Collection<T> find(Map<String, Object> params)
      throws RuntimeException {
    Criteria criteria = novoCriteria();
    criteria = consulta(params, criteria);
    return obter(criteria, Criteria.DISTINCT_ROOT_ENTITY);
  }

  private Criteria consulta(Map<String, Object> params, Criteria criteria) {
    Set<String> chaves = params.keySet();
    for (String chave : chaves) {
      String propNome = chave;
      Object valor = params.get(chave);
      if (valor instanceof Enum) {
        if (valor.getClass().isEnum() && !Util.isVazio(valor))
          if (!(valor instanceof OrderToSort)) {
            criteria.add(Restrictions.eq(propNome, valor));
          }
      }
      if (valor instanceof Number) {
        criteria.add(Restrictions.eq(propNome, valor));
      }
      if (valor instanceof Date) {
        criteria.add(Restrictions.eq(propNome, valor));
      }
      if (valor instanceof CriteriaDataConstrutor) {
        CriteriaDataConstrutor criteriaDataConstrutor = (CriteriaDataConstrutor) valor;
        for (CriteriaData criteriaData : criteriaDataConstrutor
            .getCriterioData()) {
          ComparacaoData comparacaoData = criteriaData.getComparacao();
          switch (comparacaoData) {
          case IGUAL:
            criteria.add(Restrictions.eq(propNome, criteriaData.getDataUm()));
            break;
          case IGUAL_OU_MAIOR:
            criteria.add(Restrictions.ge(propNome, criteriaData.getDataUm()));
            break;
          case IGUAL_OU_MENOR:
            criteria.add(Restrictions.le(propNome, criteriaData.getDataUm()));
            break;
          case ENTRE:
            criteria.add(Restrictions.between(propNome,
                criteriaData.getDataUm(), criteriaData.getDataDois()));
            break;
          default:
            break;
          }
        }
        criteriaDataConstrutor.destruir();
      }

      if (valor instanceof String) {
        String stringIdentificavel = (String) valor;
        final String PERCENT = "%";
        if (stringIdentificavel.contains(PERCENT)) {
          boolean modeStart = false;
          boolean modeEnd = false;
          if (stringIdentificavel.startsWith(PERCENT)) {
            stringIdentificavel.replace(PERCENT, "");
            modeStart = true;
          }
          if (stringIdentificavel.endsWith(PERCENT)) {
            stringIdentificavel.replace(PERCENT, "");
            modeEnd = true;
          }
          if (modeStart && !modeEnd) {
            criteria.add(Restrictions
                .like(propNome, stringIdentificavel, MatchMode.START)
                .ignoreCase());
          }
          if (!modeStart && modeEnd) {
            criteria.add(
                Restrictions.like(propNome, stringIdentificavel, MatchMode.END)
                    .ignoreCase());
          }
          if (modeStart && modeEnd) {
            criteria.add(Restrictions
                .like(propNome, stringIdentificavel, MatchMode.ANYWHERE)
                .ignoreCase());
          }
        } else {
          if (propNome.equalsIgnoreCase("password"))
            criteria.add(Restrictions.eq(propNome, valor));
          else
            criteria.add(Restrictions.eq(propNome, valor).ignoreCase());
        }
      }

      if (valor instanceof OrderToSort) {
        OrderToSort ordenacao = (OrderToSort) valor;
        criteria.addOrder(ordenacao.equals(OrderToSort.ASC)
            ? Order.asc(propNome) : Order.desc(propNome));
      }

      if (valor instanceof Boolean) {
        criteria.add(Restrictions.eq(propNome, valor));
      }
    }
    return criteria;
  }

  public Criteria novoCriteria() {
    return getSessionOfHibernate().createCriteria(getClazz());
  }

  public Criteria novoCriteria(String alias) {
    return getSessionOfHibernate().createCriteria(getClazz(), alias);
  }

  @SuppressWarnings("unchecked")
  protected Collection<T> obter(Criteria criteria,
      ResultTransformer resultTransformer) {
    criteria.setResultTransformer(resultTransformer);
    return criteria.list();
  }

  @SuppressWarnings("unchecked")
  protected Collection<T> obter(Criteria criteria) {
    return criteria.list();
  }

  public Class<T> getClazz() {
    return this.clazz;
  }

  @Override
  public synchronized T save(T entidade) throws DaoException {
    try {
      if (entidade.getId() != null || getEntityManager().contains(entidade)) {
        getEntityManager().merge(entidade);
      } else {
        getEntityManager().persist(entidade);
      }
      getEntityManager().flush();
    } catch (OptimisticLockException ole) {
      throw new DaoException(ole);

    } catch (PersistenceException ro) {
      throw new RollbackException(ro);

    } catch (ValidationException ve) {
      throw new DaoException(ve);
    }
    return entidade;
  }

  @Override
  public synchronized void remove(T entidade) {
    try {
      if (entidade.getId() == null)
        throw new RuntimeException("the instance doesnt exist");
      getEntityManager().remove(entidade);
      getEntityManager().flush();
    } catch (Exception e) {
      log.error("error when remove on DaoGenerico", e);
    }
  }

  @Override
  public void remove(Collection<T> entidades) {
    for (T t : entidades) {
      remove(t);
    }
  }

  @Override
  public Collection<T> listAll() {
    return listAll(new HashMap<String, Object>(), 0, 0);
  }

  @Override
  public T find(Map<String, Object> params, Result resultado)
      throws DaoException {
    final List<T> lista = (List<T>) find(params);
    T retorno = null;
    if (!lista.isEmpty()) {
      retorno = lista.get(0);
    }
    return retorno;
  }

  @Override
  public Collection<T> listAll(Map<String, Object> params, int de, int ate) {
    Collection<T> results = null;
    try {
      Criteria criteria = novoCriteria();
      if (de > 0)
        criteria.setFirstResult(de);
      if (ate > 0)
        criteria.setMaxResults(ate);
      criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
      results = obter(params, criteria);
    } catch (Exception e) {
      log.error("erro ao listar", e);
    }
    return results;
  }
}
