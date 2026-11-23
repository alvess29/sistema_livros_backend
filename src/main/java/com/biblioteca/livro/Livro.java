package com.biblioteca.livro;

import com.google.cloud.firestore.annotation.DocumentId;

public class Livro {

    // Preenchido pelo Firestore com o ID do documento e não gravado como campo
    @DocumentId
    private String id;
    private String titulo;
    private String autor;
    private String genero;
    private String descricao;
    private String capa;

    public Livro() {
    }

    public Livro(String titulo, String autor, String genero, String descricao, String capa) {
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.descricao = descricao;
        this.capa = capa;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCapa() {
        return capa;
    }

    public void setCapa(String capa) {
        this.capa = capa;
    }
}
