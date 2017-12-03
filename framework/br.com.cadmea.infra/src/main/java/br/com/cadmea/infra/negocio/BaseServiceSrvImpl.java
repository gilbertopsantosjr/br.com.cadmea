package br.com.cadmea.infra.negocio;

import br.com.cadmea.comuns.dto.Request;
import br.com.cadmea.comuns.dto.Response;
import br.com.cadmea.comuns.dto.Structurable;
import br.com.cadmea.comuns.exceptions.DaoException;
import br.com.cadmea.comuns.orm.EntityPersistent;
import br.com.cadmea.comuns.orm.enums.Result;
import br.com.cadmea.comuns.srv.BaseService;

import java.io.Serializable;
import java.util.*;

/**
 * A layer Service receive an structure {@link Structurable} from a request
 * then it will create and populate an {@link EntityPersistent}
 * then it will forward it to {@link BaseNegocial}
 * <p>
 * Validations may occours on this layer
 *
 * @version 1.0
 */
public abstract class BaseServiceSrvImpl<E extends EntityPersistent> implements BaseService<E> {

    /**
     * Retorna a instância de BO.
     *
     * @return O BO que será utilizado nas operações de manutenção
     */
    protected abstract <B extends BaseNegocial<E>> B getBo();

    /**
     * Generic Reponse
     */
    private final Response<E> response = new Response<E>() {
        private E entity;
        private final List<E> entities = Collections.emptyList();

        @Override
        public void setEntity(final E entity) {
            this.entity = entity;
        }

        @Override
        public E getEntity() {
            return entity;
        }

        @Override
        public List<E> getEntities() {
            return entities;
        }

        @Override
        public void clear() {
            entity = null;
            entities.clear();
        }
    };

    /**
     * {@inheritDoc}
     */
    @Override
    public <R extends Request<E>> Response<E> insert(final R struct) {
        struct.validate();
        final E entity = getBo().insert(struct.getEntity());
        response.clear();
        response.setEntity(entity);
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <R extends Request<E>> Response<E> save(final R struct) {
        struct.validate();
        getBo().save(struct.getEntity());
        response.clear();
        response.setEntity(struct.getEntity());
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <R extends Request<E>> Boolean remove(final R struct) {
        struct.validate();
        getBo().remove(struct.getEntity());
        return getBo().find(struct.getEntity().getId()) != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response<E> find(final Serializable identificador) {
        final E entity = getBo().find(identificador);
        response.clear();
        response.setEntity(entity);
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response<E> find(final Map<String, Object> params) {
        final Collection<E> entities = getBo().find(params);
        response.clear();
        response.getEntities().addAll(entities);
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response<E> find(final Map<String, Object> params, final Result res) {
        final E entity = getBo().find(params, res);
        response.clear();
        response.setEntity(entity);
        return response;
    }

    /**
     * {@inheritDoc}
     */
    public Response<E> find(final String propNome, final Object valor) {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put(propNome, valor);
        final E entity = getBo().find(params, Result.UNIQUE);
        response.clear();
        response.setEntity(entity);
        return response;
    }

    /**
     * {@inheritDoc}
     */
    public Response find(final String namedQuery, final Map<String, Object> parameters) throws DaoException {
        final Collection<E> entities = getBo().findByNamedQuery(namedQuery, parameters);
        response.clear();
        response.getEntities().addAll(entities);
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response listAll() {
        final Collection<E> entities = getBo().findAll();
        response.clear();
        response.getEntities().addAll(entities);
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response listAll(final Map<String, Object> params, final int de, final int ate) {
        final Collection<E> entities = getBo().findAll(de, ate);
        response.clear();
        response.getEntities().addAll(entities);
        return response;
    }


}
