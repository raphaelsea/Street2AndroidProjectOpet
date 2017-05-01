package com.example.rafaelmatucheski.street2androidproject;

/**
 * Created by Rafael Matucheski on 30/04/2017.
 */

public class Cadastro {
    public Long ID;
    public String nome;
    public String usuario;
    public String senha;


    public Cadastro() {

    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Cadastro(Long ID, String nome, String usuario, String senha)
    {
        this.ID = ID;

        this.nome = nome;
        this.usuario = usuario;
        this.senha = senha;
    }
}