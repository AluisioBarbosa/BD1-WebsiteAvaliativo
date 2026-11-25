package com.bancodedados.demo.Model;

public class Questao {
    private Long idQuestao;
    private String enunciado;
    private String tipo;

    public Long getId() {
        return idQuestao;
    }
    public void setId(Long idQuestao) {
        this.idQuestao = idQuestao;
    }
    public String getEnunciado() {
        return enunciado;
    }
    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
}
