package br.com.isabela.model.comentario;

public class Comentario {

    private Integer id;
    private String descricao;
    private String dataCriacao;
    private String horaCriacao;

    private Integer emailUsuarioCriador;
    private Integer emailUsuarioPerfil;

    public Integer getemailUsuarioCriador() {
        return emailUsuarioCriador;
    }

    public void setemailUsuarioCriador(Integer email_usuario) {
        this.emailUsuarioCriador = emailUsuarioCriador;
    }

    public Integer getemailUsuarioPerfil() {
        return emailUsuarioPerfil;
    }

    public void setemailUsuarioPerfil(Integer emailUsuarioPerfil) {
        this.emailUsuarioPerfil = emailUsuarioPerfil;
    }

    public Comentario() {

    }

    public static Comentario carregar(
            Integer id,
            String descricao,
            String dataCriacao,
            String horaCriacao) {
        Comentario comentario = new Comentario();
        comentario.id = id;
        comentario.descricao = descricao;
        comentario.dataCriacao = dataCriacao;
        comentario.horaCriacao = horaCriacao;
        return comentario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getdataCriacao() {
        return dataCriacao;
    }

    public void setdataCriacao(String dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String gethoraCriacao() {
        return horaCriacao;
    }

    public void sethoraCriacao(String horaCriacao) {
        this.horaCriacao = horaCriacao;
    }

}
