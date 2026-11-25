package com.bancodedados.demo.Model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Avaliacao {
    private Long idAvaliacao;
    private String titulo;
    private String descricao;
    private LocalDate data;
    private LocalTime horaInicio;
    private LocalTime horaFim;
    
    private Long idProfessor;

    public Long getIdAvaliacao() {
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

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public LocalTime getHoraFim() {
        return horaFim;
    }

    public Long getIdProfessor() {
        return idProfessor;
    }

    public void setIdAvaliacao(Long idAvaliacao) {
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

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public void setHoraFim(LocalTime horaFim) {
        this.horaFim = horaFim;
    }

    public void setIdProfessor(Long idProfessor) {
        this.idProfessor = idProfessor;
    }
    
    
}
