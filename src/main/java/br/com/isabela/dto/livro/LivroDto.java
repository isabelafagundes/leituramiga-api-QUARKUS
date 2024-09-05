package br.com.isabela.dto.livro;

import br.com.isabela.model.livro.Livro;

public class LivroDto {
    public Integer id;
    public String titulo;
    public String autor;
    public String descricao;
    public String categoria;
    public String estadoFisico;
    public String data_ultima_solicitacao;

    public static LivroDto deModel(
        Livro livro
    ) {
        LivroDto dto = new LivroDto();

        dto.id = livro.getId();
        dto.titulo = livro.getTitulo();
        dto.autor = livro.getAutor();
        dto.descricao = livro.getDescricao();
        dto.categoria = livro.getCategoria();
        dto.estadoFisico = livro.getEstadoFisico();
        dto.data_ultima_solicitacao = livro.getData_ultima_solicitacao();

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

    public String getdata_ultima_solicitacao() {
        return data_ultima_solicitacao;
    }

    public void setdata_ultima_solicitacao(String data_ultima_solicitacao) {
        this.data_ultima_solicitacao = data_ultima_solicitacao;
    }

}
