package br.com.cadmea.spring.rest;

import br.com.cadmea.comuns.dto.DataStructure;
import br.com.cadmea.comuns.dto.Structurable;
import br.com.cadmea.comuns.exceptions.BusinessException;
import br.com.cadmea.comuns.orm.EntityPersistent;
import br.com.cadmea.spring.rest.exceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.ValidationException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Gilberto Santos
 */
public abstract class GenericRestService<S extends Structurable<? extends EntityPersistent>>
        implements ServiceMap<S> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * an event that occurs when starting a conversation with a client
     */
    protected void startingConversation() {

    }

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
     *
     * @param struct
     * @return ResponseEntity<Void> Rest.Status.Ok
     */
    @PostMapping(path = "/create")
    protected ResponseEntity<Void> create(@RequestBody final S struct) {
        logger.info("create an entity:" + struct.getEntity().getClass().getSimpleName());

        try {
            beforeSave();
            getService().insert(struct);
            afterSave();
        } catch (final ValidationException c) {
            throw c;
        }

        final HttpHeaders headers = new HttpHeaders();

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
     * @param struct
     * @return ResponseEntity<E> the entity up to date Rest.Status.Ok
     */
    @PutMapping(path = "/update")
    protected ResponseEntity<S> update(@RequestBody final S struct) {
        verifyIfEntityExists(struct.getEntity().getId());
        logger.info("update an entity:" + struct.getEntity());

        try {
            beforeUpdate();
            getService().save(struct);
            afterUpdate();
        } catch (final ValidationException c) {
            throw c;
        }

        return new ResponseEntity<S>(struct, HttpStatus.OK);
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
     */
    @DeleteMapping(path = "/remove/{id}")
    protected ResponseEntity<Void> exclude(@PathVariable("id") final Long id) {
        verifyIfEntityExists(id);
        logger.info("excludes a entity as resource:" + id);
        beforeExclude();
        getService().remove(getService().find(id));
        afterExclude();
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    /**
     * throw a REST qualified exception
     * verify if this entity exists as a server resource
     * the consult/select would be cached in order to avoid ask to a new resource
     *
     * @param id
     */
    private void verifyIfEntityExists(final Long id) {
        try {
            /// change to spring heteoas
            getService().find(id);
        } catch (final BusinessException e) {
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
    protected ResponseEntity<S> load(@PathVariable final Long id) {
        logger.info("requesting an entity by id");
        verifyIfEntityExists(id);
        beforeGetById();
        final S entity = getService().find(id);
        afterGetById();
        return new ResponseEntity<S>(entity, HttpStatus.OK);
    }

    /**
     * an event that occurs just after a client trying to get some entity
     */
    protected void afterGetById() {
    }

    /**
     * get a list from the criteria setted in {@link DataStructure#getParams()}
     *
     * @return ResponseEntity<Collection<E>> the entities up to date
     * Rest.Status.Ok
     */
    @GetMapping
    protected ResponseEntity<Collection<S>> findAll(final Pageable pageRequest) {
        logger.info("requesting all entities");
        /// change to spring heteoas
        Collection<S> list = Collections.EMPTY_LIST;
        final Map<String, Object> params = new HashMap<>();
        list = (Collection<S>) getService().listAll(params, pageRequest.getPageNumber(), pageRequest.getPageSize());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * ends the conversation with the client
     */
    protected void finalizarConversacao() {

    }

}
