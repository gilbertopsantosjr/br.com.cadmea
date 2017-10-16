package br.com.cadmea.comuns;

public interface UnitaryTest {

	public static final String FAIL_GET = "Falha ao obter uma instância \t";
	public static final String FALHA_OBTER_MUITAS = "Falha ao obter muitas instâncias \n";
	public static final String FAIL_SAVE = "A Persistencia da entidade falhou \t";
	public static final String FALHA_REMOVER = "Falha ao remover uma instância \n";
	public static final String FALHA_ALTERAR = "Falha ao alterar uma instância \n";
	public static final String FALHA_OBTER_NENHUM = "A consulta não pode trazer nenhum resultado\n";
	public static final String FALHA_ERA_ESPERADA = "Uma falha era esperada, mas nao aconteceu \n";

	public static final String SPACE_LOG = "\n============================================================\n";

	public abstract void beforeAllTest();

}
