package com.bancodedados.demo.Model;

public class AvaliacaoQuestao {

    private Long idAvaliacao;
    private Long idQuestao;
    private Double valor;
    private Integer ordemQuestoes;

    public Long getIdAvaliacao() {
        return idAvaliacao;
    }

    public void setIdAvaliacao(Long idAvaliacao) {
        this.idAvaliacao = idAvaliacao;
    }

    public Long getIdQuestao() {
        return idQuestao;
    }

    public void setIdQuestao(Long idQuestao) {
        this.idQuestao = idQuestao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Integer getOrdemQuestoes() {
        return ordemQuestoes;
    }

    public void setOrdemQuestoes(Integer ordemQuestoes) {
        this.ordemQuestoes = ordemQuestoes;
    }
}