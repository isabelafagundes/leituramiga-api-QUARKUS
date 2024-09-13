package br.com.isabela.model.comentario;

public class Comentario {

    private int id;
    private String descricao;
    private String data_criacao;
    private String hora_criacao;

    private Integer email_usuario;
    private Integer email_usuario_perfil;

    public Integer getEmail_usuario() {
        return email_usuario;
    }

    public void setEmail_usuario(Integer email_usuario) {
        this.email_usuario = email_usuario;
    }

    public Integer getEmail_usuario_perfil() {
        return email_usuario_perfil;
    }

    public void setEmail_usuario_perfil(Integer email_usuario_perfil) {
        this.email_usuario_perfil = email_usuario_perfil;
    }

    public Comentario() {

    }

    public static Comentario carregar(
            Integer id,
            String descricao,
            String data_criacao,
            String hora_criacao) {
        Comentario comentario = new Comentario();
        comentario.id = id;
        comentario.descricao = descricao;
        comentario.data_criacao = data_criacao;
        comentario.hora_criacao = hora_criacao;
        return comentario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getData_criacao() {
        return data_criacao;
    }

    public void setData_criacao(String data_criacao) {
        this.data_criacao = data_criacao;
    }

    public String getHora_criacao() {
        return hora_criacao;
    }

    public void setHora_criacao(String hora_criacao) {
        this.hora_criacao = hora_criacao;
    }

}
