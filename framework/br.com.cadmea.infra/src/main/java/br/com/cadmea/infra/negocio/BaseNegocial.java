package br.com.cadmea.infra.negocio;

import br.com.cadmea.comuns.exceptions.BusinessException;
import br.com.cadmea.comuns.exceptions.DaoException;
import br.com.cadmea.comuns.i18n.Message;
import br.com.cadmea.comuns.i18n.MessageCommon;
import br.com.cadmea.comuns.orm.EntityPersistent;
import br.com.cadmea.comuns.orm.enums.Result;
import br.com.cadmea.model.dao.DaoGenerico;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;

/**
 * This Class open an {@link Transactional} with the persistence layer
 * This Class will receive a {@link EntityPersistent} ready to persist
 * This Class provides the standards behaviors to persist
 * <p>
 * This Class wrappes kind Rules that , when is necessary to consult another entity to allow persist
 * like, The room can' have more vacancies rather than your capacity
 * when the action is from entity to entity, then should be applied in the business layer
 * <p>
 * A camada Bo prepara a entidade para a camada DAO
 *
 * @param <E> entity que sera manipulada
 * @version 1.0
 */
@Transactional
public abstract class BaseNegocial<E extends EntityPersistent> {

    /**
     * Construtor protegido para rentringir a criacao desta classe somente a
     * subclasses.
     */
    public BaseNegocial() {
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
    public abstract DaoGenerico<E> getDao();

    /**
     * @param id
     * @return
     */
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public E find(final Serializable id) {
        final E entity = getDao().find(id);
        if (entity == null) {
            throw new BusinessException(MessageCommon.NOT_FOUND);
        }
        return entity;
    }

    /**
     * the main diff between save and save is that save method
     * avoid duplicate records
     *
     * @param entity
     * @return
     */
    public E insert(final E entity) {
        if (isThere(entity)) {
            throw new BusinessException(new Message(MessageCommon.FOUND));
        }
        getDao().insert(entity);
        return getDao().find(entity.getId());
    }

    /**
     * @param entity
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void save(final E entity) {
        getDao().save(entity);
    }

    /**
     * @param entities
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void save(final Collection<E> entities) {
        getDao().save(entities);
    }

    /**
     * @param entity
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void remove(final E entity) {
        if (!isThere(entity)) {
            throw new BusinessException(new Message(MessageCommon.NOT_FOUND));
        }
        getDao().remove(entity);
    }

    /**
     * @param entidades
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void remove(final Collection<E> entidades) {
        final Collection<E> removidas = new ArrayList<>();
        final Iterator<E> iterator = entidades.iterator();
        while (iterator.hasNext()) {
            final E entity = iterator.next();
            try {
                remove(entity);
                removidas.add(entity);
            } catch (final Exception e) {
                continue;
            }
        }
        entidades.removeAll(removidas);
    }

    /**
     * @param params
     * @param resl
     * @return
     */
    @Transactional(readOnly = true)
    public E find(final Map<String, Object> params, final Result resl) {
        return getDao().find(params, resl);
    }

    /**
     * @param namedQuery
     * @param parameters
     * @return
     * @throws DaoException
     */
    @Transactional(readOnly = true)
    public Collection<E> findByNamedQuery(final String namedQuery, final Map<String, Object> parameters)
            throws DaoException {
        return getDao().findByNamedQuery(namedQuery, parameters);
    }

    /**
     * @param namedQuery
     * @param parameters
     * @param resl
     * @return
     * @throws DaoException
     */
    @Transactional(readOnly = true)
    public E findByNamedQuery(final String namedQuery, final Map<String, Object> parameters, final Result resl)
            throws DaoException {
        return getDao().findByNamedQuery(namedQuery, parameters, resl);
    }

    /**
     * @param propriedade
     * @param valor
     * @param res
     * @return
     * @throws DaoException
     */
    @Transactional(readOnly = true)
    public E find(final String propriedade, final Object valor, final Result res)
            throws DaoException {
        return getDao().find(propriedade, valor, res);
    }

    /**
     * @param params
     * @return
     */
    @Transactional(readOnly = true)
    public Collection<E> find(final Map<String, Object> params) {
        final Collection<E> entidades = getDao().find(params);
        if (entidades == null) {
            throw new BusinessException(new Message(MessageCommon.NOT_FOUND));
        }
        return entidades;
    }

    /**
     * @return
     */
    @Transactional(readOnly = true)
    public Collection<E> findAll() {
        return getDao().listAll();
    }

    /**
     * @param de
     * @param ate
     * @return
     */
    @Transactional(readOnly = true)
    public Collection<E> findAll(final int de, final int ate) {
        return getDao().listAll(new HashMap<>(), de, ate);
    }

    /**
     * @param params
     * @param de
     * @param ate
     * @return
     */
    @Transactional(readOnly = true)
    public Collection<E> findAll(final Map<String, Object> params, final int de, final int ate) {
        return getDao().listAll(params, de, ate);
    }

    /**
     * Obtem a descricao da entity informada.
     *
     * @param entity entity a ser obtida a descricao
     * @return A descricao da entity informada
     */
    public String getDescricao(final E entity) {
        return entity.getId().toString();
    }

    /**
     * Verifica se o registro informado ja existe.<br>
     * Obs. Este metodo deve ser sobrescrito quando houver a necessidade de
     * validar se os dados informados ja existem e nao podem ser incluidos. Este
     * metodo e chamado na inclusao e na alteracao.
     *
     * @param {@link E} entity
     */
    public boolean isThere(final E entity) {
        throw new UnsupportedOperationException();
    }

}
