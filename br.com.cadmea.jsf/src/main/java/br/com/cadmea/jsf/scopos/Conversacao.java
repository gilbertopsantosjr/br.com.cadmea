package br.com.cadmea.jsf.scopos;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.faces.context.FacesContext;
import javax.faces.event.PostConstructCustomScopeEvent;
import javax.faces.event.PreDestroyCustomScopeEvent;
import javax.faces.event.ScopeContext;

public class Conversacao extends ConcurrentHashMap<String,Object> {

	private static final long serialVersionUID = 7556965369432050706L;
	 
	public static final String NOME_ESCOPO = "conversacao";
 
	private static final String CONVERSACAO_ATUAL = "ConversacaoAtual";
 
	private boolean conversacaoNaoIniciada = true;
	
	
	private Conversacao() {
	}
 
	public static Conversacao instancia()
	{
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		Conversacao conversacao = (Conversacao) sessionMap.get(CONVERSACAO_ATUAL);
		if(conversacao == null)
		{
			conversacao = new Conversacao();
			sessionMap.put(CONVERSACAO_ATUAL, conversacao);
		}
		return conversacao;
	}
 
	public Object get(Object propriedade)
	{
		//se a conversacao nao for iniciada funciona como request
		if(conversacaoNaoIniciada)
		{
			return obterDoRequest(propriedade);
		}
 
		return super.get(propriedade);
	}
 
	@SuppressWarnings("unchecked")
	private Object obterDoRequest(Object propriedade)
	{
		Map<String, Object> requestConversation = (Map<String, Object>) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get(CONVERSACAO_ATUAL);
		if(requestConversation != null)
		{
			return requestConversation.get(propriedade);
		}
		return null;
	}
 
	@Override
	public Object put(String key, Object value)
	{
		//se a conversacao nao for iniciada funciona como request
		if(conversacaoNaoIniciada)
		{
			return colocarNoRequest(key, value);
		}
 
		return super.put(key, value);
	}
 
	@SuppressWarnings("unchecked")
	private Object colocarNoRequest(String key, Object value)
	{
 
		Map<String, Object> requestMap = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
		Map<String, Object> requestConversation = (Map<String, Object>) requestMap.get(CONVERSACAO_ATUAL);
		if(requestConversation == null)
		{
			requestConversation = new ConcurrentHashMap<String, Object>();
			requestMap.put(CONVERSACAO_ATUAL, requestConversation);
			return requestConversation.put(key, value);
		}
 
		return requestConversation.put(key, value);
	}
 
	public void iniciar()
	{
		conversacaoNaoIniciada = false;
		promoverRequestParaConversacao();
		notificarCriacao();
	}
 
	@SuppressWarnings("unchecked")
	private void promoverRequestParaConversacao()
	{
		Map<String, Object> requestConversation = (Map<String, Object>) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get(CONVERSACAO_ATUAL);
		if(requestConversation != null)
			super.putAll(requestConversation);
	}
 
	private void notificarCriacao()
	{
		ScopeContext context = new ScopeContext(NOME_ESCOPO, this);
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.getApplication().publishEvent(facesContext, PostConstructCustomScopeEvent.class, context);
	}
 
	public void finalizar()
	{
		notificarFinalizacao();
		conversacaoNaoIniciada = true;
		rebaixarConversacaoParaRequest();
 
	}
 
	@SuppressWarnings("unchecked")
	private void rebaixarConversacaoParaRequest()
	{
		Map<String, Object> requestMap = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
		Map<String, Object> requestConversation = (Map<String, Object>) requestMap.get(CONVERSACAO_ATUAL);
		if(requestConversation == null)
		{
			requestConversation = new ConcurrentHashMap<String, Object>();
			requestMap.put(CONVERSACAO_ATUAL, requestConversation);
		}
		requestConversation.putAll(this);
		this.clear();
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(CONVERSACAO_ATUAL);
	}
 
	private void notificarFinalizacao()
	{
		ScopeContext context = new ScopeContext(NOME_ESCOPO, this);
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.getApplication().publishEvent(facesContext, PreDestroyCustomScopeEvent.class, context);
	}
}
