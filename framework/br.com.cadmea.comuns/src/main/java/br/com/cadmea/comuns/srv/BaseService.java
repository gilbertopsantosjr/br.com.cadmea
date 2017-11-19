package br.com.cadmea.comuns.srv;

import br.com.cadmea.comuns.dto.Request;
import br.com.cadmea.comuns.dto.Response;
import br.com.cadmea.comuns.orm.EntityPersistent;
import br.com.cadmea.comuns.orm.enums.Result;

import java.io.Serializable;
import java.util.Map;

/**
 * @author Gilberto Santos
 * determina os serviços padrões que toda structure deve implementar
 */
public interface BaseService<E extends EntityPersistent> {


    /**
     * verify if the Record already and insert a new Record based on Request and return a Response
     *
     * @param {@link Request<E>}
     * @return {@link Response<E>}
     */
    <R extends Request<E>> Response<E> insert(R structure);

    /**
     * save a new Record based on Request and return a Response
     *
     * @param {@link Request}
     * @return {@link Response}
     */
    Response save(Request structure);

    /**
     * remove fisicamente uma structure
     *
     * @param structure
     */
    Boolean remove(Request structure);

    /**
     * obtem uma structure pelo seu identificador natural id
     *
     * @param id
     * @return E
     */
    Response find(Serializable id);

    /**
     * obtem uma structure conforme os parametros de entrada
     *
     * @param params exemplo <code> params.put("nomeDaVariavel", objetoDeValor); </code>
     * @return E
     */
    Response find(Map<String, Object> params, Result res);

    /**
     * obtem uma coleção de entidades conforme os parametros de entrada <br/>
     *
     * @param params exemplo <code> params.put("nomeDaVariavel", objetoDeValor); </code>
     * @return Collection<E>
     */
    Response find(Map<String, Object> params);

    /**
     * obtem todos instancias persistidas para structure E
     * retorna todos objetos da structure
     *
     * @return Collection<E>
     */
    Response listAll();

    /**
     * obtem todos instancias persistidas para structure E
     * retorna todos objetos da structure
     *
     * @param from
     * @param to
     * @return Collection<E>
     */
    Response listAll(Map<String, Object> params, int from, int to);


}
