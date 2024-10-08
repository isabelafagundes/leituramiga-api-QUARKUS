package br.com.leituramiga.model.usuario;

import br.com.leituramiga.model.endereco.Endereco;

public class Usuario {

    private String nome;
    private String username;
    private String email;
    private String senha;
    private TipoUsuario tipoUsuario;
    private Boolean ativo;
    private Boolean bloqueado;
    private Integer tentativas;
    private String codigoAlteracao;
    private String celular;
    private String descricao;
    private String imagem;
    private Integer codigoInstituicao;
    private Endereco endereco;
    private String nomeCidade;
    private String nomeInstituicao;

    public Usuario() {
    }

    public static Usuario carregarResumo(
            String nome,
            String username,
            String email,
            String descricao,
            String imagem,
            Integer codigoInstituicao,
            String nomeCidade,
            String nomeInstituicao
    ) {
        Usuario usuario = new Usuario();
        usuario.nome = nome;
        usuario.username = username;
        usuario.email = email;
        usuario.descricao = descricao;
        usuario.imagem = imagem;
        usuario.codigoInstituicao = codigoInstituicao;
        usuario.nomeCidade = nomeCidade;
        usuario.nomeInstituicao = nomeInstituicao;

        return usuario;
    }

    public static Usuario carregar(
            String nome,
            String username,
            String senha,
            String email,
            Integer tipoUsuario,
            Integer tentativas,
            boolean bloqueado,
            boolean ativo,
            String codigoAlteracao,
            String celular,
            String descricao,
            String imagem,
            Integer codigoInstituicao,
            Endereco endereco,
            String nomeCidade,
            String nomeInstituicao
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
        usuario.celular = celular;
        usuario.descricao = descricao;
        usuario.imagem = imagem;
        usuario.codigoInstituicao = codigoInstituicao;
        usuario.endereco = endereco;
        usuario.nomeCidade = nomeCidade;
        usuario.nomeInstituicao = nomeInstituicao;

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

    public String getCelular() {
        return celular;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getImagem() {
        return imagem;
    }

    public Integer getCodigoInstituicao() {
        return codigoInstituicao;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public String getNomeCidade() {
        return nomeCidade;
    }

    public String getNomeInstituicao() {
        return nomeInstituicao;
    }

    public String getCodigoAlteracao() {
        return codigoAlteracao;
    }
}

