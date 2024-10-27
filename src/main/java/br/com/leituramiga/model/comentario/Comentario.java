package br.com.leituramiga.model.comentario;

public class Comentario {

    private Integer codigoComentario;
    private String descricao;
    private String dataCriacao;
    private String horaCriacao;

    private String emailUsuarioCriador;
    private String emailUsuarioPerfil;

    private String nomeUsuarioCriador;

    public Comentario() {

    }

    public static Comentario carregar(
            Integer id,
            String descricao,
            String dataCriacao,
            String horaCriacao,
            String emailUsuarioCriador,
            String emailUsuarioPerfil,
            String nomeUsuarioCriador

    ) {
        Comentario comentario = new Comentario();
        comentario.codigoComentario = id;
        comentario.descricao = descricao;
        comentario.dataCriacao = dataCriacao;
        comentario.horaCriacao = horaCriacao;
        comentario.emailUsuarioCriador = emailUsuarioCriador;
        comentario.emailUsuarioPerfil = emailUsuarioPerfil;
        comentario.nomeUsuarioCriador = nomeUsuarioCriador;
        return comentario;
    }

    public Integer getCodigoComentario() {
        return codigoComentario;
    }

    public void setCodigoComentario(Integer codigoComentario) {
        this.codigoComentario = codigoComentario;
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

    public String getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(String dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getHoraCriacao() {
        return horaCriacao;
    }

    public void setHoraCriacao(String horaCriacao) {
        this.horaCriacao = horaCriacao;
    }

    public String getEmailUsuarioCriador() {
        return emailUsuarioCriador;
    }

    public String getEmailUsuarioPerfil() {
        return emailUsuarioPerfil;
    }

    public String getNomeUsuarioCriador() {
        return nomeUsuarioCriador;
    }

    public void setNomeUsuarioCriador(String nomeUsuarioCriador) {
        this.nomeUsuarioCriador = nomeUsuarioCriador;
    }
}
