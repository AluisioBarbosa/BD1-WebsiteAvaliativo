package com.bancodedados.demo.Model;

public class Alternativa {
    private Long idAlternativa;
    private String descricao;
    private boolean correta;
    
    public Long getIdAlternativa() {
        return idAlternativa;
    }
    public void setIdAlternativa(Long idAlternativa) {
        this.idAlternativa = idAlternativa;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public boolean isCorreta() {
        return correta;
    }
    public void setCorreta(boolean correta) {
        this.correta = correta;
    }

    
}
