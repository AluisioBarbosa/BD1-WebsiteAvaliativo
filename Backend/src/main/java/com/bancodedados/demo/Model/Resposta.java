package com.bancodedados.demo.Model;

public class Resposta {
    private Long idResposta;
    private String textoResposta;
    private double nota;

    private Long idAluno;
    private Long idAvaliacao;
    private Long idQuestao;
    private Long idAlternativa;
    public Long getIdResposta() {
        return idResposta;
    }
    public void setIdResposta(Long idResposta) {
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
    public Long getIdAluno() {
        return idAluno;
    }
    public void setIdAluno(Long idAluno) {
        this.idAluno = idAluno;
    }
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
    public Long getIdAlternativa() {
        return idAlternativa;
    }
    public void setIdAlternativa(Long idAlternativa) {
        this.idAlternativa = idAlternativa;
    }


    
}
