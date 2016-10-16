package br.com.cadmea.baseservico;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import br.com.cadmea.comuns.exceptions.DaoException;
import br.com.cadmea.comuns.orm.EntityPersistent;
import br.com.cadmea.comuns.orm.enums.Result;
import br.com.cadmea.infra.negocio.Negocial;

/**
 * Classe responsável pelas regras de negocio de casos de uso com algumas
 * operações de consulta de entidade já definidas utilizando o controle de
 * transação do framework <code>Spring</code>.
 *
 * @version 1.0
 * @param <E>
 * @param <B>
 */
public abstract class BaseMaintenanceFindSrvImpl<E extends EntityPersistent, B extends Negocial<E>>
    implements BaseMaintenanceFindSrv<E> {

  /**
   * Retorna instância de BO.
   *
   * @return O BO que será utilizado nas operações de manutenção
   */
  protected abstract B getBo();

  /*
   * (non-Javadoc)
   *
   * @see
   * br.com.cadmea.baseservico.BaseMaintenanceFindSrv#find(java.io.Serializable)
   */
  @Override
  public E find(Serializable identificador) {
    return getBo().find(identificador);
  }

  /*
   * (non-Javadoc)
   *
   * @see br.com.cadmea.baseservico.BaseMaintenanceFindSrv#find(java.util.Map)
   */
  @Override
  public Collection<E> find(Map<String, Object> params) {
    return getBo().find(params);
  }

  /*
   * (non-Javadoc)
   *
   * @see br.com.cadmea.baseservico.BaseMaintenanceFindSrv#find(java.util.Map,
   * br.com.cadmea.comuns.orm.enums.Result)
   */
  @Override
  public E find(Map<String, Object> params, Result res) {
    return getBo().find(params, res);
  }

  /*
   * (non-Javadoc)
   *
   * @see
   * br.com.cadmea.baseservico.BaseMaintenanceFindSrv#find(java.lang.String,
   * java.lang.Object)
   */
  @Override
  public E find(String propNome, Object valor) {
    Map<String, Object> params = new HashMap<String, Object>();
    params.put(propNome, valor);
    return getBo().find(params, Result.UNIQUE);
  }

  @Override
  public Collection<E> find(String namedQuery, Map<String, Object> parameters)
      throws DaoException {
    return getBo().find(namedQuery, parameters);
  }

  /*
   * (non-Javadoc)
   *
   * @see br.com.cadmea.baseservico.BaseMaintenanceFindSrv#listAll()
   */
  @Override
  public Collection<E> listAll() {
    return getBo().findAll();
  }

  /*
   * (non-Javadoc)
   *
   * @see
   * br.com.cadmea.baseservico.BaseMaintenanceFindSrv#listAll(java.util.Map,
   * int, int)
   */
  @Override
  public Collection<E> listAll(Map<String, Object> params, int de, int ate) {
    return getBo().findAll(de, ate);
  }

}
