package br.com.cadmea.baseservico;

import java.util.Collection;

import br.com.cadmea.infra.negocio.BaseFacadeNegocial;
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
public abstract class BaseInclusaoService<E extends BaseEntidade, B extends BaseFacadeNegocial<E>>
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
	public E inserir(E entidade) {
		return (E) getBo().inserir(entidade);
	}

	/**
	 * Salva a entidade passada por parâmetro.
	 * 
	 * @param entidade
	 *            Entidade a ser salva.
	 * @return chave da mensagem de sucesso da operação.
	 */
	public void salvar(E entidade) {
		getBo().salvar(entidade);
	}

	/**
	 * Remove a entidade passada por parâmetro.
	 * 
	 * @param entidade
	 *            Entidade que será removida.
	 * @return chave da mensagem de sucesso da operação.
	 */
	public void remover(E entidade) {
		getBo().remover(entidade);
	}

	/**
	 * Remove todas as entidades da coleção.
	 * 
	 * @param entidades
	 *            Coleções de entidade
	 * @return chave da mensagem de sucesso da operação.
	 */
	public void remover(Collection<E> entidades) {
		getBo().remover(entidades);
	}
}
