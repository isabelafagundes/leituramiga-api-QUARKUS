package br.com.leituramiga.model.livro;


public class Livro {
    private Integer codigoLivro;
    private String nomeUsuario;
    private String titulo;
    private String autor;
    private String descricao;
    private String categoria;
    private String estadoFisico;
    private String nomeInstituicao;
    private String nomeCidade;
    private String dataUltimaSolicitacao;
    private Integer codigoCidade;
    private Integer codigoInstituicao;
    private String emailUsuario;
    private Integer codigoUltimaSolicitacao;
    private Integer codigoCategoria;
    private Integer codigoStatusLivro;
    public String tipoSolicitacao;

    public Livro() {
    }

    public static Livro carregar(
            Integer codigoLivro,
            String nomeUsuario,
            String titulo,
            String autor,
            String descricao,
            String categoria,
            String estadoFisico,
            String nomeInstituicao,
            String nomeCidade,
            String dataUltimaSolicitacao,
            String emailUsuario,
            Integer codigoUltimaSolicitacao,
            Integer codigoCategoria,
            Integer codigoStatusLivro,
            Integer codigoCidade,
            String tipoSolicitacao
    ) {
        Livro livro = new Livro();
        livro.codigoLivro = codigoLivro;
        livro.nomeUsuario = nomeUsuario;
        livro.titulo = titulo;
        livro.autor = autor;
        livro.descricao = descricao;
        livro.categoria = categoria;
        livro.estadoFisico = estadoFisico;
        livro.nomeInstituicao = nomeInstituicao;
        livro.nomeCidade = nomeCidade;
        livro.dataUltimaSolicitacao = dataUltimaSolicitacao;
        livro.emailUsuario = emailUsuario;
        livro.codigoUltimaSolicitacao = codigoUltimaSolicitacao;
        livro.codigoCategoria = codigoCategoria;
        livro.codigoStatusLivro = codigoStatusLivro;
        livro.codigoCidade = codigoCidade;
        livro.tipoSolicitacao = tipoSolicitacao;
        return livro;
    }


    public String getTipoSolicitacao() {
        return tipoSolicitacao;
    }

    public Integer getCodigoLivro() {
        return codigoLivro;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getEstadoFisico() {
        return estadoFisico;
    }

    public String getNomeInstituicao() {
        return nomeInstituicao;
    }

    public String getNomeCidade() {
        return nomeCidade;
    }

    public String getDataUltimaSolicitacao() {
        return dataUltimaSolicitacao;
    }

    public Integer getCodigoCidade() {
        return codigoCidade;
    }

    public Integer getCodigoInstituicao() {
        return codigoInstituicao;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public Integer getCodigoUltimaSolicitacao() {
        return codigoUltimaSolicitacao;
    }

    public Integer getCodigoCategoria() {
        return codigoCategoria;
    }

    public Integer getCodigoStatusLivro() {
        return codigoStatusLivro;
    }
}
