package br.com.isabela.model.livro;


public class Livro {
    private Integer id;
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
    private Integer emailUsuario;
    private Integer codigoUltimaSolicitacao;
    private Integer codigoCategoria;
    private Integer codigoStatusLivro;


    public Integer getCodigoCidade() {
        return codigoCidade;
    }

    public void setCodigoCidade(Integer codigoCidade) {
        this.codigoCidade = codigoCidade;
    }

    public Integer getCodigoInstituicao() {
        return codigoInstituicao;
    }

    public void setCodigoInstituicao(Integer codigoInstituicao) {
        this.codigoInstituicao = codigoInstituicao;
    }

    public Integer getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(Integer email_usuario) {
        this.emailUsuario = email_usuario;
    }

    public Integer getCodigoUltimaSolicitacao() {
        return codigoUltimaSolicitacao;
    }

    public void setCodigoUltimaSolicitacao(Integer codigo_ultima_solicitacao) {
        this.codigoUltimaSolicitacao = codigo_ultima_solicitacao;
    }

    public Integer getCodigoCategoria() {
        return codigoCategoria;
    }

    public void setCodigoCategoria(Integer codigo_categoria) {
        this.codigoCategoria = codigo_categoria;
    }

    public Integer getCodigoStatusLivro() {
        return codigoStatusLivro;
    }

    public void setCodigoStatusLivro(Integer codigo_status_livro) {
        this.codigoStatusLivro = codigo_status_livro;
    }

    public Livro(){
    }

    public static Livro carregar(
            Integer id,
            String nome_usuario,
            String titulo,
            String autor,
            String descricao,
            String categoria,
            String estado_fisico,
            String nome_instituicao,
            String nome_cidade,
            String data_ultima_solicitacao){
        Livro livro = new Livro();
        livro.id = id;
        livro.nomeUsuario = nome_usuario;
        livro.titulo = titulo;
        livro.autor = autor;
        livro.descricao = descricao;
        livro.categoria = categoria;
        livro.estadoFisico = estado_fisico;
        livro.nomeInstituicao = nome_instituicao;
        livro.nomeCidade = nome_cidade;
        livro.dataUltimaSolicitacao = data_ultima_solicitacao;
        return livro;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public void setDataUltimaSolicitacao(String dataUltimaSolicitacao) {
        this.dataUltimaSolicitacao = dataUltimaSolicitacao;
    }


}
