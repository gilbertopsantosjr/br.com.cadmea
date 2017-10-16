package br.com.cadmea.spring.rest;

import java.util.Collection;
import java.util.Collections;

import javax.annotation.PostConstruct;
import javax.validation.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.cadmea.comuns.dto.DomainTransferObject;
import br.com.cadmea.comuns.dto.FormDto;
import br.com.cadmea.comuns.exceptions.BusinessException;
import br.com.cadmea.comuns.orm.EntityPersistent;
import br.com.cadmea.spring.rest.exceptions.NotFoundException;
import br.com.cadmea.spring.rest.exceptions.PreConditionRequiredException;

/**
 * @author Gilberto Santos
 */
@SuppressWarnings("unchecked")
public abstract class GenericRestService<E extends EntityPersistent, Dto extends DomainTransferObject<E>>
		implements ServiceMap<E> {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

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
	 * the rest service /create to create a new entity
	 * @param formDto
	 * @return ResponseEntity<Void> Rest.Status.Ok
	 */
	@PostMapping(path = "/create")
	protected ResponseEntity<Void> create(@RequestBody Dto formDto) {
		logger.info("create an entity:" + formDto.getEntity());
		getViewForm().setEntity(formDto.getEntity());

		if (!getViewForm().validate())
			throw new PreConditionRequiredException(getViewForm().getMessage());

		try {
			beforeSave();
			getViewForm().setEntity((E) getService().insert(getViewForm().getEntity()));
			afterSave();
		} catch (ValidationException c){
			throw c;
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
	@PutMapping(path = "/update")
	protected ResponseEntity<E> update(@RequestBody Dto formDto) {
		verifyIfEntityExists(formDto.getEntity().getId());
		logger.info("update an entity:" + formDto.getEntity());
		getViewForm().setEntity(formDto.getEntity());

		if (!getViewForm().validate())
			throw new PreConditionRequiredException(getViewForm().getMessage());

		try {
			beforeUpdate();
			getService().save(getViewForm().getEntity());
			afterUpdate();
		} catch (ValidationException c){
			throw c;
		} finally {
			getViewForm().newInstance();
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
	 * the rest service /remove/ to remove an old entity
	 *
	 * @param id
	 * @return ResponseEntity<E> the entity up to date Rest.Status.Ok
	 *
	 */
	@DeleteMapping(path = "/remove/{id}")
	protected ResponseEntity<Void> exclude(@PathVariable("id") Long id) {
		verifyIfEntityExists(id);
		logger.info("excludes a entity as resource:" + id );
		beforeExclude();
		getService().remove( getService().find(id) );
		afterExclude();
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	/**
	 * throw a REST qualified exception
	 * verify if this entity exists as a server resource 
	 * the consult/select would be cached in order to avoid ask to a new resource
	 * @param id
	 */
	private void verifyIfEntityExists(Long id) {
		try {
			getService().find(id);
		} catch (BusinessException e) {
			throw new NotFoundException(e);
		}
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
	 * @param id
	 * @return ResponseEntity<E> the entity up to date Rest.Status.Ok
	 */
	@GetMapping(path = "/load/{id}")
	protected ResponseEntity<E> load(@PathVariable Long id) {
		logger.info("requesting an entity by id");
		verifyIfEntityExists(id);
		beforeGetById();
		getViewForm().newInstance();
		getViewForm().setEntity( getService().find(id) );
		afterGetById();
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
	@GetMapping
	protected ResponseEntity<Collection<E>> findAll(Pageable pageRequest) {
		logger.info("requesting all entities");
		Collection<E> list = Collections.EMPTY_LIST;
		list = getService().listAll(getViewForm().getParams(), pageRequest.getPageNumber(),  pageRequest.getPageSize());
		return new ResponseEntity<Collection<E>>(list, HttpStatus.OK);
	}

	/**
	 * ends the conversation with the client
	 */
	protected void finalizarConversacao() {

	};

}
