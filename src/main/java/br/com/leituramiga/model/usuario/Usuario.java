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
    public String caminhoImagem;
    private Integer codigoInstituicao;
    private Integer quantidadeLivros;
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
            String nomeInstituicao,
            Integer quantidadeLivros,
            String caminhoImagem
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
        usuario.quantidadeLivros = quantidadeLivros;
        usuario.caminhoImagem = caminhoImagem;
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
            String nomeInstituicao,
            Integer quantidadeLivros,
            String caminhoImagem
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
        usuario.quantidadeLivros = quantidadeLivros;
        usuario.caminhoImagem = caminhoImagem;
        return usuario;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public void setBloqueado(Boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    public void setTentativas(Integer tentativas) {
        this.tentativas = tentativas;
    }

    public void setCodigoAlteracao(String codigoAlteracao) {
        this.codigoAlteracao = codigoAlteracao;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getCaminhoImagem() {
        return caminhoImagem;
    }

    public void setCaminhoImagem(String caminhoImagem) {
        this.caminhoImagem = caminhoImagem;
    }

    public void setCodigoInstituicao(Integer codigoInstituicao) {
        this.codigoInstituicao = codigoInstituicao;
    }

    public void setQuantidadeLivros(Integer quantidadeLivros) {
        this.quantidadeLivros = quantidadeLivros;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public void setNomeCidade(String nomeCidade) {
        this.nomeCidade = nomeCidade;
    }

    public void setNomeInstituicao(String nomeInstituicao) {
        this.nomeInstituicao = nomeInstituicao;
    }

    public Boolean getBloqueado() {
        return bloqueado;
    }

    public Integer getQuantidadeLivros() {
        return quantidadeLivros;
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

    public Boolean isAtivo() {
        return ativo;
    }

    public Boolean isBloqueado() {
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

