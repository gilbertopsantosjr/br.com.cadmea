package br.com.cadmea.baseservico;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import br.com.cadmea.comuns.exceptions.DaoException;
import br.com.cadmea.comuns.orm.EntityPersistent;
import br.com.cadmea.comuns.orm.enums.Result;
import br.com.cadmea.infra.negocio.Negocial;

/**
 * Classe responsável pelas regras de negocio de casos de uso com algumas
 * operações de consulta de entidade já definidas utilizando o controle de
 * transação do framework <code>Spring</code>.
 *
 * @version 1.0
 * @param <E>
 * @param <B>
 */
public abstract class BaseMaintenanceFindSrvImpl<E extends EntityPersistent, B extends Negocial<E>> {

	/**
	 * Retorna instância de BO.
	 *
	 * @return O BO que será utilizado nas operações de manutenção
	 */
	protected abstract B getBo();

	/**
	 * obtem uma entidade pelo seu identificador natural id
	 * 
	 * @param id
	 * @return E
	 */
	public E find(Serializable identificador) {
		return getBo().find(identificador);
	}

	/**
	 * obtem uma entidade conforme os parametros de entrada
	 * @param params exemplo <code> params.put("nomeDaVariavel", objetoDeValor); </code>
	 * @return E
	 */
	public Collection<E> find(Map<String, Object> params) {
		return getBo().find(params);
	}

	/**
	 * obtem uma coleção de entidades conforme os parametros de entrada <br/>
     * @param params exemplo <code> params.put("nomeDaVariavel", objetoDeValor); </code>
     * @param orderBy pode ser ordenada exemplo <code> "nomeDaVariavel" </code>
	 * @param order informa se a ordenação sera ASC ou DESC exemplo <code> true para ASC , false para DESC </code>
	 * @return Collection<E>
	 */
	public E find(Map<String, Object> params, Result res) {
		return getBo().find(params, res);
	}

	/**
	 * find only one entity by propName
	 * @param propNome
	 * @param valor
	 * @return E
	 */
	public E find(String propNome, Object valor) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(propNome, valor);
		return getBo().find(params, Result.UNIQUE);
	}

	/**
	 * 
	 * @param namedQuery
	 * @param parameters
	 * @return
	 * @throws DaoException
	 */
	public Collection<E> find(String namedQuery, Map<String, Object> parameters) throws DaoException {
		return getBo().findByNamedQuery(namedQuery, parameters);
	}

	/**
	 * obtem todos instancias persistidas para entidade E 
	 * retorna todos objetos da entidade
	 * @return Collection<E>
	 */
	
	public Collection<E> listAll() {
		return getBo().findAll();
	}

	/**
	 * obtem todos instancias persistidas para entidade E retorna todos objetos
	 * da entidade
	 * 
	 * @return Collection<E>
	 */
	public Collection<E> listAll(Map<String, Object> params, int de, int ate) {
		return getBo().findAll(de, ate);
	}

}
