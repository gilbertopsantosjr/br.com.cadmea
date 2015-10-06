package br.com.cadmea.baseservico;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import br.com.cadmea.comuns.orm.enums.Resultado;
import br.com.cadmea.infra.negocio.BaseFacadeNegocial;
import br.com.cadmea.model.orm.BaseEntidade;

/**
 * Classe responsável pelas regras de negocio de casos de uso com algumas operações de consulta de entidade já definidas utilizando o controle de transação do framework <code>Spring</code>.
 * 
 * @version 1.0
 * @param <E>
 *            Entidade que será manipulada
 * @param <B>
 *            Objeto de negocio que será utilizado
 */
public abstract class BaseConsultaService<E extends BaseEntidade, B extends BaseFacadeNegocial<E>> {

	 /**
     * Retorna instância de BO.
     * 
     * @return O BO que será utilizado nas operações de manutenção
     */
    protected abstract B getBo();

    /**
     * Retorna a entidade solicitada.
     * 
     * @param identificador
     *            Identificador da entidade.
     * @return entidade
     */
    public E obter(Serializable identificador) {
        return getBo().obter(identificador);
    }

    /**
     * Retorna uma coleção de entidade ordenado pelo identificador
     * 
     * @param params
     * @param orderBy
     * @return Collection<E>
     */
    public Collection<E> obter(Map<String, Object> params) {
        return getBo().obter(params);
    }

    /**
     * Busca o objeto da entidade por várias propriedade igual a valor
     * 
     * @param params
     * @return E
     */
    public E obter(Map<String, Object> params, Resultado res) {
        return getBo().obter(params, res);
    }

    /**
     * Busca o objeto da entidade por uma propriedade igual a valor
     * 
     * @param propNome
     * @param valor
     * @return E
     */
    public E obter(String propNome, Object valor) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(propNome, valor);
        return getBo().obter(params, Resultado.UNICO);
    }

    /**
     * Retorna todos os objetos da entidade
     * 
     * @return Collection
     */
    public Collection<E> listar() {
        return getBo().listar();
    }

    /**
     * 
     * @param de
     * @param ate
     * @return Collection
     */
    public Collection<E> listar(Map<String, Object> params, int de, int ate) {
        return getBo().listar(de, ate);
    }

}
