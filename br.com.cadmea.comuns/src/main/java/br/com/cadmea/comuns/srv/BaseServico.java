package br.com.cadmea.comuns.srv;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import br.com.cadmea.comuns.orm.EntityPersistent;
import br.com.cadmea.comuns.orm.enums.Result;

/**
 * 
 * @author Gilberto Santos
 * determina os serviços padrões que toda entidade deve implementar
 * @param <E>
 */
public interface BaseServico<E extends EntityPersistent> {
	
	/**
	 * insere um registro e devolve sua referencia 
	 * @param entidade
	 * @return Serializable
	 */
    EntityPersistent insert(E entidade);
	
	/**
	 * persiste (cria ou alterar uma existente) uma nova entidade e limpa o estado para repetir o processo
	 * @param entidade
	 */
	void save(E entidade);
	
	/**
	 * 
	 * @param entidade
	 */
	void save(Collection<E> entidade);
	
	/**
	 * remove fisicamente uma entidade	
	 * @param entidade
	 */
	void remove(E entidade);
	/**
	 * obtem uma entidade pelo seu identificador natural id
	 * @param id
	 * @return E
	 */
	E find(Serializable id);
	
	/**
	 * obtem uma entidade conforme os parametros de entrada
	 * @param params exemplo <code> params.put("nomeDaVariavel", objetoDeValor); </code>
	 * @return E
	 */
	E find(Map<String, Object> params, Result res);
	
	/**
	 * obtem uma coleção de entidades conforme os parametros de entrada <br/>
     * @param params exemplo <code> params.put("nomeDaVariavel", objetoDeValor); </code>
     * @param orderBy pode ser ordenada exemplo <code> "nomeDaVariavel" </code>
	 * @param order informa se a ordenação sera ASC ou DESC exemplo <code> true para ASC , false para DESC </code>
	 * @return Collection<E>
	 */
	Collection<E> find(Map<String, Object> params);
	
	/**
	 * obtem todos instancias persistidas para entidade E 
	 * retorna todos objetos da entidade
	 * @return Collection<E>
	 */
	Collection<E> listAll();
	
	/**
	 * obtem todos instancias persistidas para entidade E 
	 * retorna todos objetos da entidade
	 * @return Collection<E>
	 */
	Collection<E> listAll(Map<String, Object> params, int de, int ate);
	
	
	
}
