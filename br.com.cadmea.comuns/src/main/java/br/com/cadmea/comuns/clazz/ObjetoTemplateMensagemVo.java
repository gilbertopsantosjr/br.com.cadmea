package br.com.cadmea.comuns.clazz;

import br.com.cadmea.comuns.util.IdiomasDoSistema;

public class ObjetoTemplateMensagemVo {

	private String modelo;
	private String atributo;
	private String chaveMensagem;
	private String chaveTemplate; 
	private IdiomasDoSistema idiomasDoSistema;
	
	public String getChaveMensagem() {
		return chaveMensagem;
	}
	public void setChaveMensagem(String chaveMensagem) {
		this.chaveMensagem = chaveMensagem;
	}
	public String getChaveTemplate() {
		return chaveTemplate;
	}
	public void setChaveTemplate(String chaveTemplate) {
		this.chaveTemplate = chaveTemplate;
	}
	public IdiomasDoSistema getIdiomasDoSistema() {
		if(idiomasDoSistema == null)
			idiomasDoSistema = IdiomasDoSistema.PORTUGUES_BRASILEIRO;
		return idiomasDoSistema;
	}
	public void setIdiomasDoSistema(IdiomasDoSistema idiomasDoSistema) {
		this.idiomasDoSistema = idiomasDoSistema;
	}
	
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public String getAtributo() {
		return atributo;
	}
	public void setAtributo(String atributo) {
		this.atributo = atributo;
	}
	/**
	 * @return mensagem igual a chave no arquivo de propriedades
	 */
	public String obterMensagemNoPattern(){
		return modelo + "." + atributo;
	}
	
}
