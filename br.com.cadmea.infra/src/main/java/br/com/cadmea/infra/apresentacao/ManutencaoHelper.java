/**
 * 
 */
package br.com.cadmea.infra.apresentacao;

import java.util.logging.Logger;

import br.com.cadmea.comuns.orm.Entidade;
import br.com.cadmea.comuns.srv.BaseServico;
import br.com.cadmea.comuns.to.Formulario;

/**
 * Encapsula ações comuns de casos de uso como também o workflow básico
 * @author Gilberto Santos
 * @version 1.0
 */
public interface ManutencaoHelper<E extends Entidade> extends WorkFlowUseCase {

	public static final Logger logger = Logger.getAnonymousLogger();
	
	public static final String REGISTER_SAVE_SUCCESS = "save.success";
    public static final String REGISTRO_EXCLUIDO_COM_SUCESSO = "remove.success";
    
    public static final String OBJID = "objId";
    
    public abstract Formulario<E> getViewForm();
	public abstract BaseServico<E> getService();
	
	
}
