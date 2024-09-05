package br.com.isabela.model.livro;

public class Livro {
    private Integer id;
    private String titulo;
    private String autor;
    private String descricao;
    private String categoria;
    private String estado_fisico;
    private String data_ultima_solicitacao;

    private Integer codigo_cidade;
    private Integer codigo_instituicao;
    private Integer emailUsuario;
    private Integer codigoUltimaSolicitacao;
    private Integer codigoCategoria;
    private Integer codigoStatusLivro;


    public Integer getCodigo_cidade() {
        return codigo_cidade;
    }

    public void setCodigo_cidade(Integer codigo_cidade) {
        this.codigo_cidade = codigo_cidade;
    }

    public Integer getCodigo_instituicao() {
        return codigo_instituicao;
    }

    public void setCodigo_instituicao(Integer codigo_instituicao) {
        this.codigo_instituicao = codigo_instituicao;
    }

    public Integer getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(Integer emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public Integer getCodigoUltimaSolicitacao() {
        return codigoUltimaSolicitacao;
    }

    public void setCodigoUltimaSolicitacao(Integer codigoUltimaSolicitacao) {
        this.codigoUltimaSolicitacao = codigoUltimaSolicitacao;
    }

    public Integer getCodigoCategoria() {
        return codigoCategoria;
    }

    public void setCodigoCategoria(Integer codigoCategoria) {
        this.codigoCategoria = codigoCategoria;
    }

    public Integer getCodigoStatusLivro() {
        return codigoStatusLivro;
    }

    public void setCodigoStatusLivro(Integer codigoStatusLivro) {
        this.codigoStatusLivro = codigoStatusLivro;
    }

    public Livro(){
    }

    public static Livro carregar(
            Integer id,
            String titulo,
            String autor,
            String descricao,
            String categoria,
            String estadoFisico,
            String dataUltimaSolicitacao, String fisico, String ultimaSolicitacao){
        Livro livro = new Livro();
        livro.id = id;
        livro.titulo = titulo;
        livro.autor = autor;
        livro.descricao = descricao;
        livro.categoria = categoria;
        livro.estado_fisico = estadoFisico;
        livro.data_ultima_solicitacao = dataUltimaSolicitacao;
        return livro;
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
        return estado_fisico;
    }

    public void setEstadoFisico(String estado_fisico) {
        this.estado_fisico = estado_fisico;
    }

    public String getData_ultima_solicitacao() {
        return data_ultima_solicitacao;
    }

    public void setData_ultima_solicitacao(String data_ultima_solicitacao) {
        this.data_ultima_solicitacao = data_ultima_solicitacao;
    }
}
