package br.com.isabela.dto.livro;

import br.com.isabela.model.livro.Livro;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class LivroDto {
    public Integer id;
    public String nome_usuario;
    public String titulo;
    public String autor;
    public String descricao;
    public String categoria;
    public String estado_fisico;
    public String nome_instituicao;
    public String nome_cidade;
    public String data_ultima_solicitacao;

    public static LivroDto deModel(
            Livro livro
    ) {
        LivroDto dto = new LivroDto();

        dto.id = livro.getId();
        dto.nome_usuario = livro.getNome_usuario();
        dto.titulo = livro.getTitulo();
        dto.autor = livro.getAutor();
        dto.descricao = livro.getDescricao();
        dto.categoria = livro.getCategoria();
        dto.estado_fisico = livro.getEstado_fisico();
        dto.nome_instituicao = livro.getNome_instituicao();
        dto.nome_cidade = livro.getNome_cidade();
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

    public String getNome_usuario() {
        return nome_usuario;
    }

    public void setNome_usuario(String nome_usuario) {
        this.nome_usuario = nome_usuario;
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

    public String getEstado_fisico() {
        return estado_fisico;
    }

    public void setEstado_fisico(String estado_fisico) {
        this.estado_fisico = estado_fisico;
    }

    public String getNome_instituicao() {
        return nome_instituicao;
    }

    public void setNome_instituicao(String nome_instituicao) {
        this.nome_instituicao = nome_instituicao;
    }

    public String getNome_cidade() {
        return nome_cidade;
    }

    public void setNome_cidade(String nome_cidade) {
        this.nome_cidade = nome_cidade;
    }

    public String getData_ultima_solicitacao() {
        return data_ultima_solicitacao;
    }

    public void setData_ultima_solicitacao(String data_ultima_solicitacao) {
        this.data_ultima_solicitacao = data_ultima_solicitacao;
    }

}
