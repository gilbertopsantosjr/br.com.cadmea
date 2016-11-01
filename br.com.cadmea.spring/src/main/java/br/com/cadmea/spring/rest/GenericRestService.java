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

import br.com.cadmea.comuns.dto.DomainTransferObject;
import br.com.cadmea.comuns.dto.FormDto;
import br.com.cadmea.comuns.orm.EntityPersistent;
import br.com.cadmea.spring.rest.exceptions.NotFoundException;
import br.com.cadmea.spring.rest.exceptions.PreconditionRequiredException;
import br.com.cadmea.spring.rest.exceptions.RestException;

/**
 * @author Gilberto Santos
 */
@SuppressWarnings("unchecked")
public abstract class GenericRestService<E extends EntityPersistent, Dto extends DomainTransferObject<E>>
    implements ServiceMap<E> {

  /**
   * an event that occurs when starting a conversation with a client
   */
  protected void startingConversation() {

  };

  /**
   * an event that occurs just before loaded this object
   */
  protected void beforeLoadClass() {
  }

  @PostConstruct
  public void init() {
    beforeLoadClass();
    afterLoadClass();
  }

  /**
   * an event that occurs just after loaded this object
   */
  protected void afterLoadClass() {
  }

  /**
   * an event that occurs just before a client trying to save some entity
   */
  protected void beforeSave() {
  }

  /**
   * the rest service /create/ to create a new entity
   *
   * @param FormDto<E>
   * @return ResponseEntity<Void> Rest.Status.Ok
   */
  @RequestMapping(value = "/create/", method = RequestMethod.POST)
  protected ResponseEntity<Void> create(@RequestBody Dto formDto) {

    getViewForm().setEntity(formDto.getEntity());

    if (!getViewForm().validate())
      throw new PreconditionRequiredException("004");

    try {
      beforeSave();

      getViewForm()
          .setEntity((E) getService().insert(getViewForm().getEntity()));

      afterSave();

    } catch (Exception e) {
      throw new RestException(e);

    } finally {
      getViewForm().newInstance();
    }

    HttpHeaders headers = new HttpHeaders();

    return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
  }

  /**
   * an event that occurs just after a client trying to save some entity
   */
  protected void afterSave() {
  }

  /**
   * an event that occurs just before a client trying to update some entity
   */
  protected void beforeUpdate() {
  }

  /**
   * the rest service /update/ to updated a old entity
   *
   * @param formDto
   * @return ResponseEntity<E> the entity up to date Rest.Status.Ok
   */
  @RequestMapping(value = "/update/", method = RequestMethod.PUT)
  protected ResponseEntity<E> update(@RequestBody Dto formDto) {

    getViewForm().setEntity(formDto.getEntity());

    if (!getViewForm().validate())
      throw new PreconditionRequiredException("004");

    try {
      beforeUpdate();

      getService().save(getViewForm().getEntity());

      afterUpdate();

    } catch (Exception e) {
      throw new RestException(e);

    } finally {
      getViewForm().newInstance();
      getService().find(getViewForm().getId());
    }

    return new ResponseEntity<E>(getViewForm().getEntity(), HttpStatus.OK);
  }

  /**
   * an event that occurs just after a client trying to update some entity
   */
  protected void afterUpdate() {
  }

  /**
   * an event that occurs just before a client trying to exclude some entity
   */
  protected void beforeExclude() {
  }

  /**
   * the rest service /exclude/ to remove an old entity
   *
   * @param IdEntity
   * @return ResponseEntity<E> the entity up to date Rest.Status.Ok
   *
   */
  @RequestMapping(value = "/remove/{id}", method = RequestMethod.DELETE)
  protected ResponseEntity<Void> exclude(@PathVariable("id") String IdEntity) {
    E entidade = getService().find(Long.valueOf(IdEntity));
    if (entidade == null) {
      throw new NotFoundException(IdEntity);
    }
    try {
      beforeExclude();
      getService().remove(entidade);
      afterExclude();
    } catch (Exception e) {
      throw new RestException(e);
    }

    return new ResponseEntity<Void>(HttpStatus.OK);
  }

  /**
   * an event that occurs just after a client trying to exclude some entity
   */
  protected void afterExclude() {
  }

  /**
   * an event that occurs just before a client trying to get some entity
   */
  protected void beforeGetById() {
  }

  /**
   * the rest service /get/ to remove an old entity
   *
   * @param IdEntity
   * @return ResponseEntity<E> the entity up to date Rest.Status.Ok
   */
  @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
  protected ResponseEntity<E> get(String IdEntity) {
    E entidade = getService().find(Long.valueOf(IdEntity));
    if (entidade == null) {
      throw new NotFoundException(IdEntity);
    }
    try {
      beforeGetById();
      getViewForm().newInstance();
      getViewForm().setEntity(entidade);
      afterGetById();
    } catch (Exception e) {
      throw new RestException(e);
    }

    return new ResponseEntity<E>(getViewForm().getEntity(), HttpStatus.OK);
  }

  /**
   * an event that occurs just after a client trying to get some entity
   */
  protected void afterGetById() {
  }

  /**
   * get a list from the criteria setted in {@link FormDto#getParams()}
   *
   * @return ResponseEntity<Collection<E>> the entities up to date
   *         Rest.Status.Ok
   */
  @RequestMapping(value = "/get", method = RequestMethod.GET)
  protected ResponseEntity<Collection<E>> findAll() {
    Collection<E> list = getService().find(getViewForm().getParams());
    if (list.isEmpty())
      throw new NotFoundException("");

    return new ResponseEntity<Collection<E>>(list, HttpStatus.OK);
  }

  /**
   * ends the conversation with the client
   */
  protected void finalizarConversacao() {

  };

}
