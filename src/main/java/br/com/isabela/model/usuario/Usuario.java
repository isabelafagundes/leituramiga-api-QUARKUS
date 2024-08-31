package br.com.isabela.model.usuario;

public class Usuario {

    private String nome;
    private String username;
    private String email;
    private String senha;
    private TipoUsuario tipoUsuario;
    private boolean ativo;
    private boolean bloqueado;
    private Integer tentativas;
    private String codigoAlteracao;

    public Usuario() {}

    public static Usuario carregar(
            String nome,
            String username,
            String senha,
            String email,
            Integer tipoUsuario,
            Integer tentativas,
            boolean bloqueado,
            boolean ativo,
            String codigoAlteracao
    ) {
        Usuario usuario = new Usuario();
        usuario.nome = nome;
        usuario.username = username;
        usuario.email = email;
        usuario.senha = senha;
        usuario.tipoUsuario = TipoUsuario.obterPorId(tipoUsuario);
        usuario.ativo = ativo;
        usuario.tentativas = tentativas;
        usuario.bloqueado = bloqueado;
        usuario.codigoAlteracao = codigoAlteracao;
        return usuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public boolean isBloqueado() {
        return bloqueado;
    }

    public Integer getTentativas() {
        return tentativas;
    }

    public String getCodigoAlteracao() {
        return codigoAlteracao;
    }
}

