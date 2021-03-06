package br.com.cadmea.model.dao;

import br.com.cadmea.comuns.exceptions.DaoException;
import br.com.cadmea.comuns.orm.EntityPersistent;
import br.com.cadmea.comuns.orm.enums.Result;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * Interface para acesso a base de dados já com métodos de manutenção (CRUD)
 * pré-definidos.
 *
 * @param <E> entity a ser manipulado
 * @author Gilberto Santos
 * @version 1.0
 */
public interface DaoGenerico<E extends EntityPersistent> {

    /**
     * insere uma nova instancia no banco de dados,
     *
     * @param entity
     */
    void insert(E entity);

    /**
     * altera o estado da entity no banco de dados, mantem seu estado e devolver
     * ao requisitante
     *
     * @param entity
     */
    E save(E entity) throws DaoException;

    /**
     * altera o estado de várias entidades com uma transação
     *
     * @param entidades
     */
    void save(Collection<E> entidades);

    /**
     * Remove uma entity do banco
     *
     * @param entity
     */
    void remove(E entity) throws DaoException;

    /**
     * Remove uma coleção de entidades uma transaão
     */
    void remove(Collection<E> entity);

    /**
     * @param id
     * @return
     */
    E find(Serializable id) throws DaoException;

    /**
     * obtem uma coleção de entidades com base nos critérios
     *
     * @return Collection<T>
     * @throws RuntimeException
     */
    Collection<E> find(Map<String, Object> params)
            throws DaoException;

    /**
     * Obtem uma unida instancia com um mapa de criterios e resultado
     *
     * @param params
     * @param resultado
     * @return
     */
    E find(Map<String, Object> params, Result resultado);

    /**
     * obtem uma colecao a partir de uma namedQuery
     *
     * @param namedQuery
     * @param parameters
     * @return Collection<E>
     */
    Collection<E> findByNamedQuery(String namedQuery, Map<String, Object> parameters)
            throws DaoException;

    /**
     * obtem uma entity a partir de uma namedQuery
     *
     * @param namedQuery
     * @param parameters
     * @param resultado
     * @return E
     */
    E findByNamedQuery(String namedQuery, Map<String, Object> parameters,
                       Result resultado) throws DaoException;

    /**
     * lista uma coleção de entity criterios de pesquisa e com paginação
     *
     * @return Collection<E>
     */
    Collection<E> listAll(Map<String, Object> params, int de, int ate);

    /**
     * lista uma coleção de entity
     *
     * @return Collection<E>
     */
    Collection<E> listAll();

    /**
     * obtem passandos os valores diretamente, caso so exista um criterio
     *
     * @return apenas uma única instancia de <E>
     */
    E find(String propriedade, Object valor, Result res)
            throws DaoException;

    /**
     * obtem passandos os valores diretamente, caso so exista um criterio
     *
     * @return uma coleção de instancias de <E>
     */
    Collection<E> find(String propriedade, Object valor);

}