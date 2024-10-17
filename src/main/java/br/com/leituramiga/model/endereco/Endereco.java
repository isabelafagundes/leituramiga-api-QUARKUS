package br.com.leituramiga.model.endereco;

import br.com.leituramiga.dto.endereco.EnderecoDto;

public class Endereco {
    private Integer codigoEndereco;
    private String logradouro;
    private String complemento;
    private String numero;
    private String bairro;
    private String cep;
    private String nomeCidade;
    private Integer codigoCidade;
    private String estado;
    private Boolean enderecoPrincipal;

    public Integer getCodigoCidade() {
        return codigoCidade;
    }

    public String getNomeCidade() {
        return nomeCidade;
    }

    public void setNomeCidade(String nomeCidade) {
        this.nomeCidade = nomeCidade;
    }

    private String emailUsuario;

    public Endereco() {
    }

    public static Endereco deDto(EnderecoDto enderecoDto) {
        Endereco endereco = new Endereco();
        endereco.logradouro = enderecoDto.getLogradouro();
        endereco.complemento = enderecoDto.getComplemento();
        endereco.bairro = enderecoDto.getBairro();
        endereco.cep = enderecoDto.getCep();
        endereco.nomeCidade = enderecoDto.getNomeCidade();
        endereco.codigoCidade = enderecoDto.getCodigoCidade();
        endereco.estado = enderecoDto.getEstado();
        endereco.emailUsuario = enderecoDto.getEmailUsuario();
        endereco.numero = enderecoDto.getNumero();
        endereco.codigoEndereco = enderecoDto.getCodigoEndereco();
        endereco.enderecoPrincipal = enderecoDto.getEnderecoPrincipal();
        return endereco;
    }

    public static Endereco carregar(
            Integer id,
            String logradouro,
            String complemento,
            String bairro,
            String cep,
            String nomeCidade,
            String emailUsuario,
            String estado,
            String numero,
            Integer codigoCidade,
            Boolean enderecoPrincipal) {
        Endereco endereco = new Endereco();
        endereco.codigoEndereco = id;
        endereco.logradouro = logradouro;
        endereco.complemento = complemento;
        endereco.bairro = bairro;
        endereco.cep = cep;
        endereco.nomeCidade = nomeCidade;
        endereco.emailUsuario = emailUsuario;
        endereco.estado = estado;
        endereco.numero = numero;
        endereco.codigoCidade = codigoCidade;
        endereco.enderecoPrincipal = enderecoPrincipal;
        return endereco;
    }

    public Boolean getEnderecoPrincipal() {
        return enderecoPrincipal;
    }

    public void setCodigoCidade(Integer codigoCidade) {
        this.codigoCidade = codigoCidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getCodigoEndereco() {
        return codigoEndereco;
    }

    public void setCodigoEndereco(Integer codigoEndereco) {
        this.codigoEndereco = codigoEndereco;
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

    public String getNumero() {
        return numero;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setEnderecoPrincipal(Boolean enderecoPrincipal) {
        this.enderecoPrincipal = enderecoPrincipal;
    }
}
