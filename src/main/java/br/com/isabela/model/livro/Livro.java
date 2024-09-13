package br.com.isabela.model.livro;


public class Livro {
    private Integer id;
    private String nome_usuario;
    private String titulo;
    private String autor;
    private String descricao;
    private String categoria;
    private String estado_fisico;
    private String nome_instituicao;
    private String nome_cidade;
    private String data_ultima_solicitacao;


    private Integer codigo_cidade;
    private Integer codigo_instituicao;
    private Integer email_usuario;
    private Integer codigo_ultima_solicitacao;
    private Integer codigo_categoria;
    private Integer codigo_status_livro;


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
        return email_usuario;
    }

    public void setEmailUsuario(Integer email_usuario) {
        this.email_usuario = email_usuario;
    }

    public Integer getCodigoUltimaSolicitacao() {
        return codigo_ultima_solicitacao;
    }

    public void setCodigoUltimaSolicitacao(Integer codigo_ultima_solicitacao) {
        this.codigo_ultima_solicitacao = codigo_ultima_solicitacao;
    }

    public Integer getCodigoCategoria() {
        return codigo_categoria;
    }

    public void setCodigoCategoria(Integer codigo_categoria) {
        this.codigo_categoria = codigo_categoria;
    }

    public Integer getCodigoStatusLivro() {
        return codigo_status_livro;
    }

    public void setCodigoStatusLivro(Integer codigo_status_livro) {
        this.codigo_status_livro = codigo_status_livro;
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
        livro.nome_usuario = nome_usuario;
        livro.titulo = titulo;
        livro.autor = autor;
        livro.descricao = descricao;
        livro.categoria = categoria;
        livro.estado_fisico = estado_fisico;
        livro.nome_instituicao = nome_instituicao;
        livro.nome_cidade = nome_cidade;
        livro.data_ultima_solicitacao = data_ultima_solicitacao;
        return livro;
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
