package com.bancodedados.demo.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Usuario {
    private Integer id;
    private String nome;
    private String email;

    @JsonProperty (access = JsonProperty.Access.WRITE_ONLY)
    private String senha;

    public Usuario(){

    }

    public Usuario(Integer id, String nome, String email, String senha){
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public Integer getID(){
        return this.id;
    }

    public String getNome(){
        return this.nome;
    }

    public String getEmail(){
        return this.email;
    }

    public String getSenha(){
        return this.senha;
    }

    public void setID(Integer id){
        this.id = id;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setSenha(String senha){
        this.senha = senha;
    }
}