package br.com.cadmea.comuns.orm;

/**
 * @author Gilberto Santos
 */
@SuppressWarnings("serial")
public class BaseEntity implements EntityPersistent {

  Long id;

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = id;
  }

}
