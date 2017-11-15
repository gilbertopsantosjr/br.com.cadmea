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
public abstract class BaseServiceSrvImpl<R extends Request> implements BaseService {

    /**
     * Retorna a instância de BO.
     *
     * @return O BO que será utilizado nas operações de manutenção
     */
    protected abstract <B extends BaseNegocial<EntityPersistent>> B getBo();

    /**
     * Generic Reponse
     */
    private final Response response = new Response() {
        private EntityPersistent entity;
        private final List<EntityPersistent> entities = Collections.emptyList();

        @Override
        public void setEntity(final EntityPersistent entity) {
            this.entity = entity;
        }

        @Override
        public EntityPersistent getEntity() {
            return entity;
        }

        @Override
        public List<EntityPersistent> getEntities() {
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
    public Response insert(final Request struct) {
        struct.validate();
        final EntityPersistent entity = getBo().insert(struct.getEntity());
        response.clear();
        response.setEntity(entity);
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(final Request struct) {
        struct.validate();
        getBo().save(struct.getEntity());
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(final Request struct) {
        struct.validate();
        getBo().remove(struct.getEntity());
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Response find(final Serializable identificador) {
        final EntityPersistent entity = getBo().find(identificador);
        response.clear();
        response.setEntity(entity);
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response find(final Map<String, Object> params) {
        final Collection<EntityPersistent> entities = getBo().find(params);
        response.clear();
        response.getEntities().addAll(entities);
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response find(final Map<String, Object> params, final Result res) {
        final EntityPersistent entity = getBo().find(params, res);
        response.clear();
        response.setEntity(entity);
        return response;
    }

    /**
     * {@inheritDoc}
     */
    public Response find(final String propNome, final Object valor) {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put(propNome, valor);
        final EntityPersistent entity = getBo().find(params, Result.UNIQUE);
        response.clear();
        response.setEntity(entity);
        return response;
    }

    /**
     * {@inheritDoc}
     */
    public Response find(final String namedQuery, final Map<String, Object> parameters) throws DaoException {
        final Collection<EntityPersistent> entities = getBo().findByNamedQuery(namedQuery, parameters);
        response.clear();
        response.getEntities().addAll(entities);
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response listAll() {
        final Collection<EntityPersistent> entities = getBo().findAll();
        response.clear();
        response.getEntities().addAll(entities);
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response listAll(final Map<String, Object> params, final int de, final int ate) {
        final Collection<EntityPersistent> entities = getBo().findAll(de, ate);
        response.clear();
        response.getEntities().addAll(entities);
        return response;
    }


}
