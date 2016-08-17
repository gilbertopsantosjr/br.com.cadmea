package br.com.cadmea.baseservico;

import java.util.Collection;

import br.com.cadmea.infra.negocio.Negocial;
import br.com.cadmea.model.orm.BaseEntidade;

/**
 * Classe responsável pelas regras de negocio de casos de uso com algumas
 * operações de manutenção de entidade já definidas utilizando o controle de
 * transação do framework <code>Spring</code>.
 * 
 * @version 1.0
 * @param <E>
 * @param <B>
 */
public abstract class BaseManutencaoServico<E extends BaseEntidade, B extends Negocial<E>>
		extends BaseConsultaService<E, B> {

	/**
	 * Retorna a instância de BO.
	 * @return O BO que será utilizado nas operações de manutenção
	 */
	protected abstract B getBo();

	/**
	 * Insere a entidade passada por parâmetro.
	 * @param entidade
	 * @return chave da mensagem de sucesso da operação.
	 */
	public E insert(E entidade) {
		return (E) getBo().insert(entidade);
	}

	/**
	 * Salva a entidade passada por parâmetro.
	 * @param entidade
	 * @return chave da mensagem de sucesso da operação.
	 */
	public void save(E entidade) {
		getBo().save(entidade);
	}
	
	/**
	 * 
	 * @param entidades
	 */
	public void save(Collection<E> entidades) {
		getBo().save(entidades);
	}

	/**
	 * Remove a entidade passada por parâmetro.
	 * @param entidade
	 * @return chave da mensagem de sucesso da operação.
	 */
	public void remove(E entidade) {
		getBo().remove(entidade);
	}

	/**
	 * Remove todas as entidades da coleção.
	 * @param entities
	 * @return chave da mensagem de sucesso da operação.
	 */
	public void remove(Collection<E> entities) {
		for (E entity : entities) {
			getBo().remove(entity);	
		}
	}
}
