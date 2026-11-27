package com.bancodedados.demo.Model;
import java.util.List;

public class Questao {
    private Long idQuestao;
    private String enunciado;
    private String tipo;
    private List <Alternativa> alternativas;

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
    public Long getIdQuestao() {
        return idQuestao;
    }
    public void setIdQuestao(Long idQuestao) {
        this.idQuestao = idQuestao;
    }
    public List<Alternativa> getAlternativas() {
        return alternativas;
    }
    public void setAlternativas(List<Alternativa> alternativas) {
        this.alternativas = alternativas;
    }

    
    
}
