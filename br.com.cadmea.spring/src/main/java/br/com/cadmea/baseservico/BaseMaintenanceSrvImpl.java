package br.com.cadmea.baseservico;

import java.util.Collection;

import br.com.cadmea.comuns.orm.EntityPersistent;
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
    extends BaseFindSrvImpl<E, B> implements BaseMaintenanceSrv<E> {

  /**
   * Retorna a instância de BO.
   *
   * @return O BO que será utilizado nas operações de manutenção
   */
  @Override
  protected abstract B getBo();

  @Override
  public E insert(E entidade) {
    return getBo().insert(entidade);
  }

  @Override
  public void save(E entidade) {
    getBo().save(entidade);
  }

  @Override
  public void save(Collection<E> entidades) {
    getBo().save(entidades);
  }

  @Override
  public void remove(E entidade) {
    getBo().remove(entidade);
  }

  @Override
  public void remove(Collection<E> entities) {
    for (E entity : entities) {
      getBo().remove(entity);
    }
  }
}
