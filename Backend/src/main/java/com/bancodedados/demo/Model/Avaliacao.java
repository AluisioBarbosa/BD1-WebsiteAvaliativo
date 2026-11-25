package com.bancodedados.demo.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Avaliacao {
    private Long idAvaliacao;
    private String titulo;
    private String descricao;
    private LocalDate data;
    private LocalDateTime horaInicio;
    private LocalDateTime horaFim;
    
    private Long idProfessor;

    public Long getId() {
        return idAvaliacao;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDate getData() {
        return data;
    }

    public LocalDateTime getHoraInicio() {
        return horaInicio;
    }

    public LocalDateTime getHoraFim() {
        return horaFim;
    }

    public Long getIdProfessor() {
        return idProfessor;
    }

    public void setId(Long idAvaliacao) {
        this.idAvaliacao = idAvaliacao;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public void setHoraInicio(LocalDateTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public void setHoraFim(LocalDateTime horaFim) {
        this.horaFim = horaFim;
    }

    public void setIdProfessor(Long idProfessor) {
        this.idProfessor = idProfessor;
    }
    
    
}
