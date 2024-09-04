package br.com.isabela.dto.livro;

import br.com.isabela.model.livro.Livro;

public class LivroDto {
    public Integer id;
    public String titulo;
    public String autor;
    public String editora;
    public Integer ano;
    public String isbn;
    public String descricao;
    public String categoria;
    public String estadoFisico;
    public String dataPublicacao;
    public String dataUltimaPublicacao;

    public static LivroDto deModel(
        Livro livro
    ) {
        LivroDto dto = new LivroDto();

        dto.id = livro.getId();
        dto.titulo = livro.getTitulo();
        dto.autor = livro.getAutor();
        dto.editora = livro.getEditora();
        dto.ano = livro.getAno();
        dto.isbn = livro.getIsbn();
        dto.descricao = livro.getDescricao();
        dto.categoria = livro.getCategoria();
        dto.estadoFisico = livro.getEstadoFisico();
        dto.dataPublicacao = livro.getDataPublicacao();
        dto.dataUltimaPublicacao = livro.getDataUltimaPublicacao();

        return dto;
    }

    public LivroDto() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getEstadoFisico() {
        return estadoFisico;
    }

    public void setEstadoFisico(String estadoFisico) {
        this.estadoFisico = estadoFisico;
    }

    public String getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(String dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public String getDataUltimaPublicacao() {
        return dataUltimaPublicacao;
    }

    public void setDataUltimaPublicacao(String dataUltimaPublicacao) {
        this.dataUltimaPublicacao = dataUltimaPublicacao;
    }

}
