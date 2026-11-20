package com.bancodedados.demo.Model;

public class Alternativa {
    private Integer idAlternativa;
    private String descricao;
    private boolean correta;
    
    public Integer getIdAlternativa() {
        return idAlternativa;
    }
    public void setIdAlternativa(Integer idAlternativa) {
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
