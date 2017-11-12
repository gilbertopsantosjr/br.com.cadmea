package br.com.cadmea.comuns.srv;

import br.com.cadmea.comuns.dto.Request;
import br.com.cadmea.comuns.dto.Response;
import br.com.cadmea.comuns.orm.enums.Result;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * @author Gilberto Santos
 * determina os serviços padrões que toda structure deve implementar
 */
public interface BaseService {


    /**
     * insere um registro e devolve sua referencia
     *
     * @param structure
     * @return Serializable
     */
    <R extends Request> Response insert(R structure);

    /**
     * persiste (cria ou alterar uma existente) uma nova structure e limpa o estado para repetir o processo
     *
     * @param structure
     */
    <R extends Request> void save(R structure);

    /**
     * remove fisicamente uma structure
     *
     * @param structure
     */
    <R extends Request> void remove(R structure);

    /**
     * obtem uma structure pelo seu identificador natural id
     *
     * @param id
     * @return E
     */
    <E extends Response> E find(Serializable id);

    /**
     * obtem uma structure conforme os parametros de entrada
     *
     * @param params exemplo <code> params.put("nomeDaVariavel", objetoDeValor); </code>
     * @return E
     */
    <E extends Response> E find(Map<String, Object> params, Result res);

    /**
     * obtem uma coleção de entidades conforme os parametros de entrada <br/>
     *
     * @param params exemplo <code> params.put("nomeDaVariavel", objetoDeValor); </code>
     * @return Collection<E>
     */
    Collection<? extends Response> find(Map<String, Object> params);

    /**
     * obtem todos instancias persistidas para structure E
     * retorna todos objetos da structure
     *
     * @return Collection<E>
     */
    Collection<? extends Response> listAll();

    /**
     * obtem todos instancias persistidas para structure E
     * retorna todos objetos da structure
     *
     * @param from
     * @param to
     * @return Collection<E>
     */
    Collection<? extends Response> listAll(Map<String, Object> params, int from, int to);


}
