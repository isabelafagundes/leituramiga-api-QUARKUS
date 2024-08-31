package br.com.isabela.model.endereco;

public class Endereco {
    private Integer id;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String cep;
    private String nomeCidade;

    private Integer codigoCidade;

    public Integer getCodigoCidade() {
        return codigoCidade;
    }

    public String getNomeCidade() {
        return nomeCidade;
    }

    public void setNomeCidade(String nomeCidade) {
        this.nomeCidade = nomeCidade;
    }

    public String getNomeEstado() {
        return nomeEstado;
    }

    public void setNomeEstado(String nomeEstado) {
        this.nomeEstado = nomeEstado;
    }

    private String nomeEstado;
    private String emailUsuario;

    public Endereco() {
    }

    public static Endereco carregar(
            Integer id,
            String logradouro,
            String complemento,
            String bairro,
            String cep,
            String nomeCidade,
            String nomeEstado,
            String emailUsuario) {
        Endereco endereco = new Endereco();
        endereco.id = id;
        endereco.logradouro = logradouro;
        endereco.complemento = complemento;
        endereco.bairro = bairro;
        endereco.cep = cep;
        endereco.nomeCidade = nomeCidade;
        endereco.nomeEstado = nomeEstado;
        endereco.emailUsuario = emailUsuario;
        return endereco;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }
}
