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
    public String imagem;
    public String caminhoImagem;

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
            String tipoSolicitacao,
            String imagem,
            String caminhoImagem
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
        livro.imagem = imagem;
        livro.caminhoImagem = caminhoImagem;
        return livro;
    }

    public String getImagem() {
        return imagem;
    }

    public void setCodigoLivro(Integer codigoLivro) {
        this.codigoLivro = codigoLivro;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setEstadoFisico(String estadoFisico) {
        this.estadoFisico = estadoFisico;
    }

    public void setNomeInstituicao(String nomeInstituicao) {
        this.nomeInstituicao = nomeInstituicao;
    }

    public void setNomeCidade(String nomeCidade) {
        this.nomeCidade = nomeCidade;
    }

    public void setDataUltimaSolicitacao(String dataUltimaSolicitacao) {
        this.dataUltimaSolicitacao = dataUltimaSolicitacao;
    }

    public void setCodigoCidade(Integer codigoCidade) {
        this.codigoCidade = codigoCidade;
    }

    public void setCodigoInstituicao(Integer codigoInstituicao) {
        this.codigoInstituicao = codigoInstituicao;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public void setCodigoUltimaSolicitacao(Integer codigoUltimaSolicitacao) {
        this.codigoUltimaSolicitacao = codigoUltimaSolicitacao;
    }

    public void setCodigoCategoria(Integer codigoCategoria) {
        this.codigoCategoria = codigoCategoria;
    }

    public void setCodigoStatusLivro(Integer codigoStatusLivro) {
        this.codigoStatusLivro = codigoStatusLivro;
    }

    public void setTipoSolicitacao(String tipoSolicitacao) {
        this.tipoSolicitacao = tipoSolicitacao;
    }

    public String getCaminhoImagem() {
        return caminhoImagem;
    }

    public void setCaminhoImagem(String caminhoImagem) {
        this.caminhoImagem = caminhoImagem;
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

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
}
