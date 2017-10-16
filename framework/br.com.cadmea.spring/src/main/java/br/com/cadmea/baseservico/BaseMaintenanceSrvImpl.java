package br.com.cadmea.baseservico;

import java.util.Collection;

import br.com.cadmea.comuns.orm.EntityPersistent;
import br.com.cadmea.comuns.srv.BaseServico;
import br.com.cadmea.infra.negocio.Negocial;

/**
 * Classe responsável pelas regras de negocio de casos de uso com algumas
 * operações de manutenção de entidade já definidas utilizando o controle de
 * transação do framework <code>Spring</code>.
 *
 * @version 1.0
 * @param <E>
 * @param <B>
 */
public abstract class BaseMaintenanceSrvImpl<E extends EntityPersistent, B extends Negocial<E>>
		extends BaseMaintenanceFindSrvImpl<E, B> implements BaseServico<E>   {

	/**
	 * Retorna a instância de BO.
	 *
	 * @return O BO que será utilizado nas operações de manutenção
	 */
	@Override
	protected abstract B getBo();

	/**
	 * insere um registro e devolve sua referencia
	 * 
	 * @param entidade
	 * @return Serializable
	 */
	public E insert(E entidade) {
		return getBo().insert(entidade);
	}

	/**
	 * persiste (cria ou alterar uma existente) uma nova entidade e limpa o
	 * estado para repetir o processo
	 * 
	 * @param entidade
	 */
	public void save(E entidade) {
		getBo().save(entidade);
	}

	/**
	 * 
	 * @param entidade
	 */
	public void save(Collection<E> entidades) {
		getBo().save(entidades);
	}

	/**
	 * remove fisicamente uma entidade
	 * 
	 * @param entidade
	 */
	public void remove(E entidade) {
		getBo().remove(entidade);
	}

	/**
	 * 
	 * @param entities
	 */
	public void remove(Collection<E> entities) {
		for (E entity : entities) {
			getBo().remove(entity);
		}
	}
}
