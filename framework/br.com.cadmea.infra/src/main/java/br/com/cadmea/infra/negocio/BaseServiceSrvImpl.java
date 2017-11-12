package br.com.cadmea.infra.negocio;

import br.com.cadmea.comuns.dto.Request;
import br.com.cadmea.comuns.dto.Response;
import br.com.cadmea.comuns.dto.Structurable;
import br.com.cadmea.comuns.exceptions.DaoException;
import br.com.cadmea.comuns.orm.EntityPersistent;
import br.com.cadmea.comuns.orm.enums.Result;
import br.com.cadmea.comuns.srv.BaseService;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * validate the Entity
     * inset a new Entity
     *
     * @param struct
     * @return Serializable
     */

    public Response insert(final R struct) {
        struct.validate();
        final EntityPersistent entity = getBo().insert(struct.getEntity());

        final Response r = new Response() {
            @Override
            public List getEntities() {
                return null;
            }

            @Override
            public void setEntity(final EntityPersistent entity) {

            }

            @Override
            public EntityPersistent getEntity() {
                return null;
            }
        };

        return r;
    }

    /**
     * persiste (cria ou alterar uma existente) uma nova entidade e limpa o
     * estado para repetir o processo
     *
     * @param struct
     */
    @Override
    public void save(final R struct) {
        struct.validate();
        getBo().save(struct.getEntity());
    }


    /**
     * remove fisicamente uma entidade
     *
     * @param struct
     */
    @Override
    public void remove(final S struct) {
        struct.validate();
        getBo().remove(struct.getEntity());
    }


    /**
     * obtem uma entidade pelo seu identificador natural id
     *
     * @param identificador
     * @return E
     */
    @Override
    public <E extends EntityPersistent> E find(final Serializable identificador) {
        return (E) getBo().find(identificador);
    }

    /**
     * obtem uma entidade conforme os parametros de entrada
     *
     * @param params exemplo <code> params.put("nomeDaVariavel", objetoDeValor); </code>
     * @return E
     */
    @Override
    public Collection<EntityPersistent> find(final Map<String, Object> params) {
        return getBo().find(params);
    }

    /**
     * obtem uma coleção de entidades conforme os parametros de entrada <br/>
     *
     * @param params exemplo <code> params.put("nomeDaVariavel", objetoDeValor); </code>
     * @return Collection<E>
     */
    @Override
    public <E extends EntityPersistent> E find(final Map<String, Object> params, final Result res) {
        return (E) getBo().find(params, res);
    }

    /**
     * find only one entity by propName
     *
     * @param propNome
     * @param valor
     * @return E
     */
    public <E extends EntityPersistent> E find(final String propNome, final Object valor) {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put(propNome, valor);
        return (E) getBo().find(params, Result.UNIQUE);
    }

    /**
     * @param namedQuery
     * @param parameters
     * @return
     * @throws DaoException
     */
    public Collection<EntityPersistent> find(final String namedQuery, final Map<String, Object> parameters) throws DaoException {
        return getBo().findByNamedQuery(namedQuery, parameters);
    }

    /**
     * obtem todos instancias persistidas para entidade E
     * retorna todos objetos da entidade
     *
     * @return Collection<E>
     */
    @Override
    public Collection<EntityPersistent> listAll() {
        return getBo().findAll();
    }

    /**
     * obtem todos instancias persistidas para entidade E retorna todos objetos
     * da entidade
     *
     * @return Collection<E>
     */
    @Override
    public Collection<EntityPersistent> listAll(final Map<String, Object> params, final int de, final int ate) {
        return getBo().findAll(de, ate);
    }


}
