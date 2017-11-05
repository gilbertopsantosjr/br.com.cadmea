package br.com.cadmea.comuns.srv;

import br.com.cadmea.comuns.dto.Structurable;
import br.com.cadmea.comuns.orm.EntityPersistent;
import br.com.cadmea.comuns.orm.enums.Result;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * @param <S>
 * @author Gilberto Santos
 * determina os serviços padrões que toda structure deve implementar
 */
public interface BaseService<S extends Structurable> {


    /**
     * insere um registro e devolve sua referencia
     *
     * @param structure
     * @return Serializable
     */
    <E extends EntityPersistent> E insert(S structure);

    /**
     * persiste (cria ou alterar uma existente) uma nova structure e limpa o estado para repetir o processo
     *
     * @param structure
     */
    void save(S structure);

    /**
     * remove fisicamente uma structure
     *
     * @param structure
     */
    void remove(S structure);

    /**
     * obtem uma structure pelo seu identificador natural id
     *
     * @param id
     * @return E
     */
    <E extends EntityPersistent> E find(Serializable id);

    /**
     * obtem uma structure conforme os parametros de entrada
     *
     * @param params exemplo <code> params.put("nomeDaVariavel", objetoDeValor); </code>
     * @return E
     */
    <E extends EntityPersistent> E find(Map<String, Object> params, Result res);

    /**
     * obtem uma coleção de entidades conforme os parametros de entrada <br/>
     *
     * @param params exemplo <code> params.put("nomeDaVariavel", objetoDeValor); </code>
     * @return Collection<E>
     */
    Collection<? extends EntityPersistent> find(Map<String, Object> params);

    /**
     * obtem todos instancias persistidas para structure E
     * retorna todos objetos da structure
     *
     * @return Collection<E>
     */
    Collection<? extends EntityPersistent> listAll();

    /**
     * obtem todos instancias persistidas para structure E
     * retorna todos objetos da structure
     *
     * @return Collection<E>
     */
    Collection<? extends EntityPersistent> listAll(Map<String, Object> params, int de, int ate);


}
