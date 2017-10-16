/**
 * 
 */
package br.com.cadmea.infra.apresentacao;


/**
 * @author gilberto.junior
 * <p> Contrato para padronizar os fluxos principais dos casos de uso </p>
 */
public interface WorkFlowUseCase {
	
	/**
	 * <p> Ação FALHA  </p>
	 * <p> Apresenta mensagem de erro e ou informação na mesma pagina </p>
	 */
	public static final String FALHA = "";
	
	/**
	 * <p> Ação CONSULTAR -> ir para 'formConsulta.jsp' </p>
	 * <p> Apresenta tela de consulta com grid preenchida com ultima consulta </p>
	 */
	public static final String CONSULTAR = "formConsulta";
	
	/**
	 * <p> Ação DETALHAR -> ir para 'detalhar.jsp' </p>
	 * <p> Busca instancia ORM atraves do seu identificador natural <br/> 
	 * Apresenta pagina com os detalhes do instancia ORM </p>
	 */
	public static final String DETALHAR = "detalhar";
	
	/**
	 * <p> Ação SUCESSO -> ir para 'formConsulta.jsp' </p>
	 * <p> Apresenta tela de consulta com grid preenchida com ultima consulta </p>
	 */
	public static final String SUCESSO 	= "formConsulta";
	
	/**
	 * <p> Ação Visualizar Edição -> ir para 'formEdicao.jsp </p>
	 * <p> busca instancia ORM no controle atraves do identificador natural <br/>
	 * Apresenta formulario de edição com os valores preenchidos e habilitados para edição
	 * </p>
	 */
	public static final String FORM_ALTERAR = "formAlterar";
	
	/**
	 * <p> Ação INICIO -> ir para 'formConsulta.jsp' </p>
	 * <p> O sistema apresenta a tela de pesquisa de instancia ORM. O ator pode pesquisar se já existe a instancia ORM desejada, <br/> 
	 * caso verdadeiro, mostrar grid com botões de ação que possibilita executar Ação Edição e ou Ação Exclusão <br/> </p>
	 * <p> apresentar botão para Acão NOVO </p>
	 */
	public static final String INICIO = "formConsulta";
 
	/**
	 * <p> Ação NOVO -> ir para 'formInclusao.jsp' </p>
	 * <p> Apresenta formulario de inclusão de nova instancia ORM </p>
	 */
	public static final String NOVO	= "formInclusao";
}
