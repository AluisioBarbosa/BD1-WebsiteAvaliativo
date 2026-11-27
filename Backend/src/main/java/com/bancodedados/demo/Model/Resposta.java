package com.bancodedados.demo.Model;

public class Resposta {
    private Long idResposta;
    private String textoResposta;
    private Double nota;

    private Long idAluno;
    private Long idAvaliacao;
    private Long idQuestao;
    private Long idAlternativa;
    public Long getId() {
        return idResposta;
    }
    public void setId(Long idResposta) {
        this.idResposta = idResposta;
    }
    public String getTextoResposta() {
        return textoResposta;
    }
    public void setTextoResposta(String textoResposta) {
        this.textoResposta = textoResposta;
    }
    public Double getNota() {
        return nota;
    }
    public void setNota(Double nota) {
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
