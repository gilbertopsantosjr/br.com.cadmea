package br.com.cadmea.model.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import br.com.cadmea.comuns.excecao.DaoException;
import br.com.cadmea.comuns.excecao.NoneExistException;
import br.com.cadmea.comuns.orm.enums.Result;
import br.com.cadmea.model.orm.BaseEntityPersistent;

/**
 * Interface para acesso a base de dados já com métodos de manutenção (CRUD)
 * pré-definidos.
 * 
 * @param <E>
 *            Entidade a ser manipulado
 * @author Gilberto Santos
 * @version 1.0
 */
public interface DaoGenerico<E extends BaseEntityPersistent> {

	/**
	 * insere uma nova instancia no banco de dados,
	 * 
	 * @param entidade
	 */
	public abstract void insert(E entidade);

	/**
	 * altera o estado da entidade no banco de dados, mantem seu estado e
	 * devolver ao requisitante
	 * 
	 * @param entidade
	 */
	public abstract E save(E entidade) throws DaoException;

	/**
	 * altera o estado de várias entidades com uma transação
	 * 
	 * @param entidades
	 */
	public abstract void save(Collection<E> entidades);

	/**
	 * Remove uma entidade do banco
	 * 
	 * @param entidade
	 */
	public abstract void remove(E entidade) throws DaoException;

	/**
	 * Remove uma coleção de entidades uma transaão
	 */
	public abstract void remove(Collection<E> entidade);

	/**
	 * 
	 * @param id
	 * @return
	 */
	public abstract E find(Serializable id) throws DaoException;

	/**
	 * obtem uma coleção de entidades com base nos critérios
	 * 
	 * @param Map
	 *            <String,Object> params
	 * @return Collection<T>
	 * @throws RuntimeException
	 */
	public abstract Collection<E> find(Map<String, Object> params) throws DaoException;

	/**
	 * Obtem uma unida instancia com um mapa de criterios e resultado
	 * 
	 * @param params
	 * @param resultado
	 * @return
	 */
	public E find(Map<String, Object> params, Result resultado);
	

	/**
	 * obtem uma colecao a partir de uma namedQuery
	 * 
	 * @param namedQuery
	 * @param parameters
	 * @return Collection<E>
	 */
	public Collection<E> find(String namedQuery, Map<String, Object> parameters) throws DaoException;

	/**
	 * obtem uma entidade a partir de uma namedQuery
	 * 
	 * @param namedQuery
	 * @param parameters
	 * @param resultado
	 * @return E
	 */
	public E find(String namedQuery, Map<String, Object> parameters, Result resultado) throws DaoException;

	/**
	 * lista uma coleção de entidade criterios de pesquisa e com paginação
	 * 
	 * @param int de
	 * @param int ate
	 * @return Collection<E>
	 */
	public Collection<E> listAll(Map<String, Object> params, int de, int ate);

	/**
	 * lista uma coleção de entidade
	 * 
	 * @return Collection<E>
	 */
	public Collection<E> listAll();

	/**
	 * obtem passandos os valores diretamente, caso so exista um criterio
	 * 
	 * @param String
	 *            propriedade
	 * @param Object
	 *            valor
	 * @param Result
	 *            res
	 * @return apenas uma única instancia de <E>
	 */
	public E find(String propriedade, Object valor, Result res) throws NoneExistException;

	/**
	 * obtem passandos os valores diretamente, caso so exista um criterio
	 * 
	 * @param String
	 *            propriedade
	 * @param Object
	 *            valor
	 * @param Result
	 *            res
	 * @return uma coleção de instancias de <E>
	 */
	public Collection<E> find(String propriedade, Object valor);

}