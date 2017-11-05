package br.com.cadmea.spring.rest;

import br.com.cadmea.comuns.dto.Structurable;
import br.com.cadmea.comuns.srv.BaseService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.logging.Logger;

/**
 * @author Gilberto Santos
 */
@CrossOrigin(origins = "*")
@RequestMapping(consumes = "application/json", produces = "application/json")
public interface ServiceMap<E extends Structurable> {

    Logger logger = Logger.getAnonymousLogger();

    String REGISTER_SAVE_SUCCESS = "save.success";
    String REGISTRO_EXCLUIDO_COM_SUCESSO = "remove.success";

    String OBJID = "objId";

    BaseService<E> getService();

}
