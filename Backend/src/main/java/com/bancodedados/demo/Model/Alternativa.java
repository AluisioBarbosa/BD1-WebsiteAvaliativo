package com.bancodedados.demo.Model;

public class Alternativa {
    private Long idAlternativa;
    private String descricao;
    private boolean correta;
    private long idQuestao;
    
    public Long getId() {
        return idAlternativa;
    }
    public void setId(Long idAlternativa) {
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
    public long getIdQuestao() {
        return idQuestao;
    }
    public void setIdQuestao(long idQuestao) {
        this.idQuestao = idQuestao;
    }

    
}
