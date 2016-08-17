/**
 * 
 */
package br.com.cadmea.comuns.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.cadmea.comuns.orm.enums.ComparacaoData;

/**
 * Classe projetada com patterns Builder e Singleton para o encapsulamento de como criar criterio de busca com datas
 * 
 * @author gilberto
 * 
 */
public class CriteriaDataConstrutor {

    private final List<CriteriaData> CRITERIO_DATA = new ArrayList<CriteriaData>();
    private static CriteriaDataConstrutor INSTANCIA;

    private CriteriaDataConstrutor() {
    }

    public synchronized static CriteriaDataConstrutor obter() {
        if (INSTANCIA == null)
            INSTANCIA = new CriteriaDataConstrutor();
        return INSTANCIA;
    }

    public CriteriaDataConstrutor comIgual(Date date) {
        CRITERIO_DATA.add(new CriteriaData(ComparacaoData.IGUAL, date));
        return obter();
    }

    public CriteriaDataConstrutor comIgualOuMaior(Date data) {
        CRITERIO_DATA.add(new CriteriaData(ComparacaoData.IGUAL_OU_MAIOR, data));
        return obter();
    }

    public CriteriaDataConstrutor comIgualOuMenor(Date data) {
        CRITERIO_DATA.add(new CriteriaData(ComparacaoData.IGUAL_OU_MENOR, data));
        return obter();
    }

    public CriteriaDataConstrutor comIntervalo(Date dataUm, Date dataDois) {
        CRITERIO_DATA.add(new CriteriaData(dataUm, ComparacaoData.ENTRE, dataDois));
        return obter();
    }

    public List<CriteriaData> getCriterioData() {
        return CRITERIO_DATA;
    }

    public void destruir() {
        if (INSTANCIA != null)
            INSTANCIA = null;
    }

}
