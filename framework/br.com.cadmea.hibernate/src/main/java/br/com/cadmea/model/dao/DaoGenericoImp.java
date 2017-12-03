package br.com.cadmea.model.dao;

import br.com.cadmea.comuns.exceptions.DaoException;
import br.com.cadmea.comuns.orm.enums.ComparacaoData;
import br.com.cadmea.comuns.orm.enums.OrderToSort;
import br.com.cadmea.comuns.orm.enums.Result;
import br.com.cadmea.comuns.util.CadmeaConstants;
import br.com.cadmea.comuns.util.CriteriaData;
import br.com.cadmea.comuns.util.CriteriaDataConstrutor;
import br.com.cadmea.comuns.util.ValidatorUtil;
import br.com.cadmea.model.BaseEntityPersistent;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.ResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.Map.Entry;

/**
 * Interface para acesso a base de dados ja com metodos de manutencao (CRUD)
 * pre-definidos.
 *
 * @param <T> {@link BaseEntityPersistent}
 * @author Gilberto Santos
 * @version 1.0
 */
public abstract class DaoGenericoImp<T extends BaseEntityPersistent> implements DaoGenerico<T> {

    private final Class<T> clazz;
    private final Logger log = LoggerFactory.getLogger(getClass());

    @PersistenceContext
    private EntityManager em;

    public EntityManager getEntityManager() {
        return em;
    }

    public DaoGenericoImp() {
        clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     *
     * @param namedQuery
     * @param parameters
     * @return
     */
    @Override
    public Collection<T> findByNamedQuery(final String namedQuery, final Map<String, Object> parameters) {
        final TypedQuery<T> q = obterPorNamedQuery(namedQuery, parameters);
        return q.getResultList();
    }

    /**
     *
     * @param namedQuery
     * @param parameters
     * @param resultado
     * @return
     */
    @Override
    public T findByNamedQuery(final String namedQuery, final Map<String, Object> parameters, final Result resultado) {
        T retorno = null;
        try {
            final TypedQuery<T> query = obterPorNamedQuery(namedQuery, parameters);
            if (resultado.equals(Result.UNIQUE)) {
                query.setMaxResults(1);
                retorno = query.getSingleResult();
            }
        } catch(javax.persistence.NoResultException e) {

        }
        return retorno;
    }


    /**
     * is mandatory following this patterns <b> NameEntity.namedQuery </b> in
     * order to created named queries
     *
     * @param namedQuery
     * @param parameters
     * @return
     */
    private TypedQuery<T> obterPorNamedQuery(final String namedQuery, final Map<String, Object> parameters) {
        final String preNamedQuery = getClazz().getSimpleName() + "." + namedQuery;
        final TypedQuery<T> q = (TypedQuery<T>) getEntityManager().createNamedQuery(preNamedQuery);
        if (parameters != null) {
            for (final Entry<String, Object> e : parameters.entrySet()) {
                q.setParameter(e.getKey(), e.getValue());
            }
        }
        return q;
    }

    /**
     * Insere o objeto informado na entidade.
     *
     * @param entidade Objeto da a ser inserido.
     * @return Chave da entidade inserida na base de dados, podendo ser um
     * objeto com mais de um atributo representando a chave
     */
    @Override
    public void insert(final T entidade) {
        save(entidade);
    }

    /**
     *
     * @param entidades
     */
    @Override
    public void save(final Collection<T> entidades) {
        for (final T t : entidades) {
            save(t);
        }
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public T find(final Serializable id) {
        return getEntityManager().find(getClazz(), id);
    }

    /**
     * obtem pelo identificador natural id
     *
     * @param id
     * @return T
     */
    public T get(final Long id) {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        return findByNamedQuery(CadmeaConstants.GET_BY_ID, params, Result.UNIQUE);
    }

    /**
     * Recupera a sessao do hibernate.
     *
     * @return Session sessao do hibernate
     */
    public Session getSessionOfHibernate() {
        final Session session = getEntityManager().unwrap(Session.class);
        return session;
    }

    private Collection<T> obter(final Map<String, Object> params, final Criteria criteria) throws RuntimeException {
        return obter(consulta(params, criteria));
    }

    @Override
    public Collection<T> find(final String propriedade, final Object valor) {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put(propriedade, valor);
        return find(params);
    }

    @Override
    public T find(final String propriedade, final Object valor, final Result res) throws DaoException {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put(propriedade, valor);
        return find(params, Result.UNIQUE);
    }

    @Override
    public Collection<T> find(final Map<String, Object> params) throws RuntimeException {
        Criteria criteria = novoCriteria();
        criteria = consulta(params, criteria);
        return obter(criteria, Criteria.DISTINCT_ROOT_ENTITY);
    }

    private Criteria consulta(final Map<String, Object> params, final Criteria criteria) {
        final Set<String> chaves = params.keySet();
        for (final String chave : chaves) {
            final String propNome = chave;
            final Object valor = params.get(chave);
            if (valor instanceof Enum) {
                if (valor.getClass().isEnum() && !ValidatorUtil.isValid(valor)) {
                    if (!(valor instanceof OrderToSort)) {
                        criteria.add(Restrictions.eq(propNome, valor));
                    }
                }
            }
            if (valor instanceof Number) {
                criteria.add(Restrictions.eq(propNome, valor));
            }
            if (valor instanceof Date) {
                criteria.add(Restrictions.eq(propNome, valor));
            }
            if (valor instanceof CriteriaDataConstrutor) {
                final CriteriaDataConstrutor criteriaDataConstrutor = (CriteriaDataConstrutor) valor;
                for (final CriteriaData criteriaData : criteriaDataConstrutor.getCriterioData()) {
                    final ComparacaoData comparacaoData = criteriaData.getComparacao();
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
                            criteria.add(
                                    Restrictions.between(propNome, criteriaData.getDataUm(), criteriaData.getDataDois()));
                            break;
                        default:
                            break;
                    }
                }
                criteriaDataConstrutor.destruir();
            }

            if (valor instanceof String) {
                final String stringIdentificavel = (String) valor;
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
                        criteria.add(Restrictions.like(propNome, stringIdentificavel, MatchMode.START).ignoreCase());
                    }
                    if (!modeStart && modeEnd) {
                        criteria.add(Restrictions.like(propNome, stringIdentificavel, MatchMode.END).ignoreCase());
                    }
                    if (modeStart && modeEnd) {
                        criteria.add(Restrictions.like(propNome, stringIdentificavel, MatchMode.ANYWHERE).ignoreCase());
                    }
                } else {
                    if (propNome.equalsIgnoreCase("password")) {
                        criteria.add(Restrictions.eq(propNome, valor));
                    } else {
                        criteria.add(Restrictions.eq(propNome, valor).ignoreCase());
                    }
                }
            }

            if (valor instanceof OrderToSort) {
                final OrderToSort ordenacao = (OrderToSort) valor;
                criteria.addOrder(ordenacao.equals(OrderToSort.ASC) ? Order.asc(propNome) : Order.desc(propNome));
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

    public Criteria novoCriteria(final String alias) {
        return getSessionOfHibernate().createCriteria(getClazz(), alias);
    }

    protected Collection<T> obter(final Criteria criteria, final ResultTransformer resultTransformer) {
        criteria.setResultTransformer(resultTransformer);
        return criteria.list();
    }

    protected Collection<T> obter(final Criteria criteria) {
        return criteria.list();
    }

    public Class<T> getClazz() {
        return clazz;
    }

    @Override
    public synchronized T save(final T entidade) throws DaoException {
        try {
            if (entidade.getId() != null || getEntityManager().contains(entidade)) {
                getEntityManager().merge(entidade);
            } else {
                getEntityManager().persist(entidade);
            }
            getEntityManager().flush();
        } catch (final OptimisticLockException ole) {
            throw new DaoException(ole);

        } catch (final PersistenceException ro) {
            throw new RollbackException(ro);

        }
        return entidade;
    }

    @Override
    public synchronized void remove(final T entidade) {
        try {
            if (entidade.getId() == null) {
                throw new RuntimeException("the instance doesnt exist");
            }
            getEntityManager().remove(entidade);
            getEntityManager().flush();
        } catch (final Exception e) {
            log.error("error when remove on DaoGenerico", e);
        }
    }

    @Override
    public void remove(final Collection<T> entidades) {
        for (final T t : entidades) {
            remove(t);
        }
    }

    @Override
    public Collection<T> listAll() {
        return listAll(new HashMap<String, Object>(), 0, 0);
    }

    @Override
    public T find(final Map<String, Object> params, final Result resultado) throws DaoException {
        final List<T> lista = (List<T>) find(params);
        T retorno = null;
        if (!lista.isEmpty()) {
            retorno = lista.get(0);
        }
        return retorno;
    }

    @Override
    public Collection<T> listAll(final Map<String, Object> params, final int de, final int ate) {
        Collection<T> results = null;
        try {
            final Criteria criteria = novoCriteria();
            criteria.setFirstResult((de - 1) * ate);
            criteria.setMaxResults(ate);
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            criteria.addOrder(Order.desc("id"));
            results = obter(params, criteria);
        } catch (final Exception e) {
            log.error("erro ao listar", e);
        }
        return results;
    }
}
