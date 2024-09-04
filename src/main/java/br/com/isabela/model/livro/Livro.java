package br.com.isabela.model.livro;

public class Livro {
    private Integer id;
    private String titulo;
    private String autor;
    private String editora;
    private Integer ano;
    private String isbn;
    private String descricao;
    private String categoria;
    private String estadoFisico;
    private String dataPublicacao;
    private String dataUltimaPublicacao;

    private Integer emailUsuario;
    private Integer codigoUltimaSolicitacao;
    private Integer codigoCategoria;
    private Integer codigoStatusLivro;

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
            String editora, Integer ano,
            String isbn,
            String descricao,
            String categoria,
            String estadoFisico,
            String dataPublicacao,
            String dataUltimaPublicacao){
        Livro livro = new Livro();
        livro.id = id;
        livro.titulo = titulo;
        livro.autor = autor;
        livro.ano = ano;
        livro.isbn = isbn;
        livro.descricao = descricao;
        livro.categoria = categoria;
        livro.estadoFisico = estadoFisico;
        livro.dataPublicacao = dataPublicacao;
        livro.dataUltimaPublicacao = dataUltimaPublicacao;
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
