package br.com.leituramiga.dto.livro;

import br.com.leituramiga.model.livro.Livro;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class LivroDto {
    public Integer codigoLivro;
    public String nomeUsuario;
    public String emailUsuario;
    public String titulo;
    public String autor;
    public String descricao;
    public String categoria;
    public Integer codigoCategoria;
    public String estadoFisico;
    public String nomeInstituicao;
    public String nomeCidade;
    public Integer codigoCidade;
    public String dataUltimaSolicitacao;
    public String tipoSolicitacao;
    public Integer codigoStatusLivro;

    public static LivroDto deModel(
            Livro livro
    ) {
        LivroDto dto = new LivroDto();
        dto.codigoLivro = livro.getCodigoLivro();
        dto.nomeUsuario = livro.getNomeUsuario();
        dto.emailUsuario = livro.getEmailUsuario();
        dto.codigoCategoria = livro.getCodigoCategoria();
        dto.codigoCidade = livro.getCodigoCidade();
        dto.titulo = livro.getTitulo();
        dto.autor = livro.getAutor();
        dto.descricao = livro.getDescricao();
        dto.categoria = livro.getCategoria();
        dto.estadoFisico = livro.getEstadoFisico();
        dto.nomeInstituicao = livro.getNomeInstituicao();
        dto.nomeCidade = livro.getNomeCidade();
        dto.dataUltimaSolicitacao = livro.getDataUltimaSolicitacao();
        dto.tipoSolicitacao = livro.getTipoSolicitacao();
        dto.codigoStatusLivro = livro.getCodigoStatusLivro();
        return dto;
    }

    public LivroDto() {
    }

    public Integer getCodigoCategoria() {
        return codigoCategoria;
    }

    public Integer getCodigoLivro() {
        return codigoLivro;
    }

    public void setCodigoLivro(Integer codigoLivro) {
        this.codigoLivro = codigoLivro;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
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

    public String getNomeInstituicao() {
        return nomeInstituicao;
    }

    public void setNomeInstituicao(String nomeInstituicao) {
        this.nomeInstituicao = nomeInstituicao;
    }

    public String getNomeCidade() {
        return nomeCidade;
    }

    public void setNomeCidade(String nomeCidade) {
        this.nomeCidade = nomeCidade;
    }

    public String getDataUltimaSolicitacao() {
        return dataUltimaSolicitacao;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public Integer getCodigoCidade() {
        return codigoCidade;
    }

    public void setDataUltimaSolicitacao(String dataUltimaSolicitacao) {
        this.dataUltimaSolicitacao = dataUltimaSolicitacao;
    }

    @Override
    public String toString() {
        return "LivroDto{" +
                "id=" + codigoLivro +
                ", nomeUsuario='" + nomeUsuario + '\'' +
                ", emailUsuario='" + emailUsuario + '\'' +
                ", titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", descricao='" + descricao + '\'' +
                ", categoria='" + categoria + '\'' +
                ", codigoCategoria=" + codigoCategoria +
                ", estadoFisico='" + estadoFisico + '\'' +
                ", nomeInstituicao='" + nomeInstituicao + '\'' +
                ", nomeCidade='" + nomeCidade + '\'' +
                ", codigoCidade=" + codigoCidade +
                ", dataUltimaSolicitacao='" + dataUltimaSolicitacao + '\'' +
                '}';
    }
}
