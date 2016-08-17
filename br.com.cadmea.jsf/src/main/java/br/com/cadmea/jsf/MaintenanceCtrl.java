package br.com.cadmea.jsf;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.event.AjaxBehaviorEvent;

import br.com.cadmea.comuns.excecao.BusinessException;
import br.com.cadmea.comuns.orm.Entidade;
import br.com.cadmea.infra.apresentacao.ManutencaoHelper;
import br.com.cadmea.jsf.scopos.Conversacao;
import br.com.cadmea.web.util.UtilitarioWeb;

/**
 * @author gilberto classe abstrata do tipo generica tem a funcao de 
 * disponibilizar acoes de manutencao comuns a persistencia de objetos. 
 * De um modo mais generico esse classe sera capaz de realizar persistencia da entidade generica
 */
public abstract class MaintenanceCtrl<E extends Entidade> extends PageCodeBase implements ManutencaoHelper<E> {

	protected MaintenanceCtrl() {}

    /**
     * inicializa o managedBean no escopo de conversacao
     * */
    protected void startingConversation() {
        Conversacao.instancia().iniciar();
    };

    /**
     * executa acao antes de criar o managed bean
     */
    protected void beforeLoadClass() {}
    
    @PostConstruct
    public void init(){
    	beforeLoadClass();
    	afterLoadClass();
    }
    
    /**
     * 
     */
    public void afterPreRenderView() {
      
    }
    
    /**
     * executa acao depois de criar o managed bean
     */
    protected void afterLoadClass() {
    	getViewForm().setEntities( findAll() );
    }

    protected void beforeSave() {
    };

    /**
     * chama o service para salvar uma nova Entidade
     */
    @SuppressWarnings("unchecked")
    public void save() {
        try {
        	
        	if(!getViewForm().validate())
            	throw new BusinessException("004");
        	
            beforeSave();
            
            if (getViewForm().getEntity().getId() != null)
                getService().save(getViewForm().getEntity());
            else{
                getViewForm().setEntity((E) getService().insert(getViewForm().getEntity()));
                getViewForm().createNewInstance();
            }
            
            addMessage(getMessageSaveWithSuccess(), FacesMessage.SEVERITY_INFO);
            
            afterSave();
            
        } catch (BusinessException be) {
        	addMessage(be.getMessage(), FacesMessage.SEVERITY_ERROR);
        	
        } catch (Exception e) {
            showException(e);
        }
    }
    
    public String cancel(){
    	getFacesContext().getViewRoot().getViewMap().remove( UtilitarioWeb.getManagedBeanByPage(getCurrentPage() ) );
    	return getCurrentPage() + "?faces-redirect=true";
    }

    protected String getMessageSaveWithSuccess() {
        return REGISTER_SAVE_SUCCESS;
    }

    protected void afterSave() {
    	getViewForm().setEntities(findAll());
    }

    protected void beforeExclude() {};

    /**
     * chama o servico para excluir uma Entidade ja existente
     * 
     * @param ActionEvent
     */
    public void exclude(String IdEntity) {
        try {
            E entidade = getService().find(Long.valueOf(IdEntity));
            if(entidade != null){
            	beforeExclude();
	            getService().remove(entidade);
	            afterExclude();
	            getViewForm().setEntities(findAll());
	            addMessage(getMessageExcludeWithSuccess(), FacesMessage.SEVERITY_INFO);
            } else 
            	addMessage("nao foi possivel encontrar entidade");
        } catch (Exception e) {
            showException(e);
        }
    }
    
    protected String getMessageExcludeWithSuccess() {
        return REGISTRO_EXCLUIDO_COM_SUCESSO;
    }

    protected void afterExclude() {};

    protected void beforeRetrieve() {};

    /**
     * chama o servico para obter uma Entidade ja existente
     * 
     * @param ActionEvent
     */
    public void show(String IdEntity) {
        try {
			E entidade = getService().find(Long.valueOf(IdEntity));
			if (entidade != null) {
				beforeRetrieve();
				getViewForm().createNewInstance();
				getViewForm().setEntity(entidade);
				afterRetrieve();
			}
        } catch (Exception e) {
            showException(e);
        }
    }

    protected void afterRetrieve() {};

    /**
     * realiza a busca dos ColumnModel usando como criterio 
     * as informacoes postadas no form da visao atualiza a visao com as entidades retornadas na consulta
     * 
     * @param event
     */
    public void searchByViewFormParameters(final AjaxBehaviorEvent event) {
        if (getViewForm().getParams() != null) {
            getViewForm().setEntities(findAll());
        }
    }

    /**
     * navegacao implicita que retorna para a tela de inclusao limpa os dados da visÃ£o
     * 
     * @return formInclusao
     */
    public final String novo() {
        return NOVO;
    }

    /**
     * <p>
     * navegacao implicia que retorna para a tela de ediÃ§ao manter o formulario na sessÃ£o
     * </p>
     * 
     * @return formAlterar
     */
    public final String detalhar() {
        return FORM_ALTERAR;
    }

    /**
     * <p>
     * obtem uma lista a partir dos criterio de entrada no formulario obtem as instancias atraves do seu identificador natural por padrÃ£o a ordem da consulta serÃ¡ pelo seu identificador natural
     * </p>
     * 
     * @return Collection<E>
     */
    protected Collection<E> findAll() {
        return getService().find(getViewForm().getParams());
    }


    /**
     * elimina o managedBean no escopo de conversacao
     * */
    protected void finalizarConversacao() {
        Conversacao.instancia().finalizar();
    };
}
