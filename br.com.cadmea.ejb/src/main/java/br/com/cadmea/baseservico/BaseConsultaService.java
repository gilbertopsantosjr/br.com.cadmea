package br.com.cadmea.baseservico;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import br.com.cadmea.comuns.orm.enums.Result;
import br.com.cadmea.infra.negocio.BaseNegocial;
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
public abstract class BaseConsultaService<E extends BaseEntidade, B extends BaseNegocial<E>> {

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
    public E find(Serializable identificador) {
        return getBo().find(identificador);
    }

    /**
     * Retorna uma coleção de entidade ordenado pelo identificador
     * 
     * @param params
     * @param orderBy
     * @return Collection<E>
     */
    public Collection<E> find(Map<String, Object> params) {
        return getBo().find(params);
    }

    /**
     * Busca o objeto da entidade por várias propriedade igual a valor
     * 
     * @param params
     * @return E
     */
    public E find(Map<String, Object> params, Result res) {
        return getBo().find(params, res);
    }

    /**
     * Busca o objeto da entidade por uma propriedade igual a valor
     * 
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
     * Retorna todos os objetos da entidade
     * 
     * @return Collection
     */
    public Collection<E> listAll() {
        return getBo().findAll();
    }

    /**
     * 
     * @param from
     * @param til
     * @return Collection
     */
    public Collection<E> listAll(Map<String, Object> params, int from, int til) {
        return getBo().findAll(from, til);
    }

}
