package br.com.cadmea.comuns.util;

import java.util.Date;

import br.com.cadmea.comuns.orm.enums.ComparacaoData;

public class CriteriaData {
    private Date dataUm;
    private ComparacaoData comparacao;
    private Date dataDois;

    public CriteriaData(ComparacaoData comparacao, Date dataUm) {
        this.dataUm = dataUm;
        this.comparacao = comparacao;
    }

    public CriteriaData(Date dataUm, ComparacaoData comparacao, Date dataDois) {
        this.dataUm = dataUm;
        this.comparacao = comparacao;
        this.dataDois = dataDois;
    }

    public ComparacaoData getComparacao() {
        return comparacao;
    }

    public void setComparacao(ComparacaoData comparacao) {
        this.comparacao = comparacao;
    }

    public Date getDataUm() {
        return dataUm;
    }

    public void setDataUm(Date dataUm) {
        this.dataUm = dataUm;
    }

    public Date getDataDois() {
        return dataDois;
    }

    public void setDataDois(Date dataDois) {
        this.dataDois = dataDois;
    }
}
