package br.com.cadmea.spring.rest;

import java.util.logging.Logger;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.cadmea.comuns.dto.DomainTransferObject;
import br.com.cadmea.comuns.dto.FormDto;
import br.com.cadmea.comuns.orm.EntityPersistent;
import br.com.cadmea.comuns.srv.BaseServico;

/**
 * @author Gilberto Santos
 *
 */
@CrossOrigin(origins = "*")
@RequestMapping(consumes = "application/json", produces = "application/json")
public interface ServiceMap<E extends EntityPersistent> {

  public static final Logger logger = Logger.getAnonymousLogger();

  public static final String REGISTER_SAVE_SUCCESS = "save.success";
  public static final String REGISTRO_EXCLUIDO_COM_SUCESSO = "remove.success";

  public static final String OBJID = "objId";

  public abstract FormDto<E> getViewForm();

  public abstract <Dto extends DomainTransferObject> void setViewForm(
      Dto formDto);

  public abstract BaseServico<E> getService();

  void beforeRetrieve();

  void afterRetrieve();

  void afterExclude();

  void beforeExclude();

  void afterSave();

  void beforeSave();

  void afterLoadClass();

  void beforeLoadClass();

}
