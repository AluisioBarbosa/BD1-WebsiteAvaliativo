package com.bancodedados.demo.Model;

public class Resposta {
    private Integer idResposta;
    private String textoResposta;
    private double nota;

    private Integer idAluno;
    private Integer idAvaliacao;
    private Integer idQuestao;
    private Integer idAlternativa;
    public Integer getIdResposta() {
        return idResposta;
    }
    public void setIdResposta(Integer idResposta) {
        this.idResposta = idResposta;
    }
    public String getTextoResposta() {
        return textoResposta;
    }
    public void setTextoResposta(String textoResposta) {
        this.textoResposta = textoResposta;
    }
    public double getNota() {
        return nota;
    }
    public void setNota(double nota) {
        this.nota = nota;
    }
    public Integer getIdAluno() {
        return idAluno;
    }
    public void setIdAluno(Integer idAluno) {
        this.idAluno = idAluno;
    }
    public Integer getIdAvaliacao() {
        return idAvaliacao;
    }
    public void setIdAvaliacao(Integer idAvaliacao) {
        this.idAvaliacao = idAvaliacao;
    }
    public Integer getIdQuestao() {
        return idQuestao;
    }
    public void setIdQuestao(Integer idQuestao) {
        this.idQuestao = idQuestao;
    }
    public Integer getIdAlternativa() {
        return idAlternativa;
    }
    public void setIdAlternativa(Integer idAlternativa) {
        this.idAlternativa = idAlternativa;
    }


    
}
