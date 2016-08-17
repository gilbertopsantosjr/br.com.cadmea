

package br.com.cadmea.baseservico;

import java.util.Collection;

import br.com.cadmea.infra.negocio.BaseNegocial;
import br.com.cadmea.model.orm.BaseEntidade;

/**
 * Classe responsável pelas regras de negocio de casos de uso com algumas
 * operações de manutenção de entidade já definidas utilizando o controle de
 * transação do framework <code>Spring</code>.
 * 
 * @version 1.0
 * @param <E>
 *            Entidade que será manipulada
 * @param <B>
 *            Objeto de negocio que será utilizado
 */
public abstract class BaseManutencaoService<E extends BaseEntidade, B extends BaseNegocial<E>>
		extends BaseConsultaService<E, B> {

	/**
	 * Retorna a instância de BO.
	 * 
	 * @return O BO que será utilizado nas operações de manutenção
	 */
	protected abstract B getBo();

	/**
	 * Insere a entidade passada por parâmetro.
	 * 
	 * @param entidade
	 *            Entidade inserida.
	 * @return chave da mensagem de sucesso da operação.
	 */
	@SuppressWarnings("unchecked")
	public E insert(E entidade) {
		return (E) getBo().insert(entidade);
	}

	/**
	 * Salva a entidade passada por parâmetro.
	 * 
	 * @param entidade
	 *            Entidade a ser salva.
	 * @return chave da mensagem de sucesso da operação.
	 */
	public void save(E entidade) {
		getBo().save(entidade);
	}
	
	/**
	 * Salva todas as entidades da coleção.
	 * 
	 * @param entidades
	 *            Coleções de entidade
	 * @return chave da mensagem de sucesso da operação.
	 */
	public void save(Collection<E> entidades) {
		getBo().save(entidades);
	}

	/**
	 * Remove a entidade passada por parâmetro.
	 * 
	 * @param entidade
	 *            Entidade que será removida.
	 * @return chave da mensagem de sucesso da operação.
	 */
	public void remove(E entidade) {
		getBo().remove(entidade);
	}

	/**
	 * Remove todas as entidades da coleção.
	 * 
	 * @param entidades
	 *            Coleções de entidade
	 * @return chave da mensagem de sucesso da operação.
	 */
	public void remove(Collection<E> entidades) {
		getBo().remove(entidades);
	}
}
