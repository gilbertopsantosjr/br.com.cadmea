package br.com.cadmea.spring.rest;

import java.util.Collection;

import javax.annotation.PostConstruct;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.cadmea.comuns.dto.DomainTransferObject;
import br.com.cadmea.comuns.orm.EntityPersistent;
import br.com.cadmea.spring.rest.exceptions.NotFoundException;
import br.com.cadmea.spring.rest.exceptions.PreconditionRequiredException;

/**
 * @author Gilberto Santos
 */
@SuppressWarnings("unchecked")
public abstract class GenericRestService<E extends EntityPersistent, Dto extends DomainTransferObject>
    implements ServiceMap<E> {

  /**
   * inicializa o managedBean no escopo de conversacao
   */
  protected void startingConversation() {

  };

  @PostConstruct
  public void init() {
    beforeLoadClass();
    afterLoadClass();
  }

  /**
   * chama o service para salvar uma nova Entidade
   */
  @RequestMapping(value = "/save/", method = RequestMethod.POST)
  public ResponseEntity<Void> save(@RequestBody Dto formDto,
      UriComponentsBuilder ucBuilder) {

    setViewForm(formDto);

    if (!getViewForm().validate())
      throw new PreconditionRequiredException("004");

    try {
      beforeSave();

      if (getViewForm().getEntity().getId() != null)
        getService().save(getViewForm().getEntity());
      else {
        getViewForm()
            .setEntity((E) getService().insert(getViewForm().getEntity()));
      }
      afterSave();

    } finally {
      getViewForm().createNewInstance();
    }

    HttpHeaders headers = new HttpHeaders();
    headers.setLocation(ucBuilder.path("/get/{id}")
        .buildAndExpand(getViewForm().getId()).toUri());

    return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
  }

  /**
   * chama o servico para excluir uma Entidade ja existente
   *
   */
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<Void> exclude(@PathVariable("id") String IdEntity) {
    E entidade = getService().find(Long.valueOf(IdEntity));
    if (entidade == null) {
      throw new NotFoundException(IdEntity);
    }

    beforeExclude();
    getService().remove(entidade);
    afterExclude();

    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
  }

  @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
  public ResponseEntity<E> get(String IdEntity) {
    E entidade = getService().find(Long.valueOf(IdEntity));
    if (entidade == null) {
      throw new NotFoundException(IdEntity);
    }

    beforeRetrieve();
    getViewForm().createNewInstance();
    getViewForm().setEntity(entidade);
    afterRetrieve();

    return new ResponseEntity<E>(getViewForm().getEntity(), HttpStatus.OK);
  }

  /**
   * <p>
   * obtem uma lista a partir dos criterio de entrada no formulario obtem as
   * instancias atraves do seu identificador natural por padrÃ£o a ordem da
   * consulta serÃ¡ pelo seu identificador natural
   * </p>
   *
   * @return Collection<E>
   */
  @RequestMapping(value = "/find/all/", method = RequestMethod.GET)
  protected ResponseEntity<Collection<E>> findAll() {
    Collection<E> list = getService().find(getViewForm().getParams());
    if (list.isEmpty())
      throw new NotFoundException("");

    return new ResponseEntity<Collection<E>>(HttpStatus.OK);
  }

  /**
   * elimina o managedBean no escopo de conversacao
   */
  protected void finalizarConversacao() {

  };

}
