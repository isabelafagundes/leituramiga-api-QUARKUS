package br.com.isabela.model.endereco;

import br.com.isabela.dto.endereco.EnderecoDto;

public class Endereco {
    private Integer codigo;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String cep;
    private String nomeCidade;
    private Integer codigoCidade;
    private String estado;

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
            String estado) {
        Endereco endereco = new Endereco();
        endereco.codigo = id;
        endereco.logradouro = logradouro;
        endereco.complemento = complemento;
        endereco.bairro = bairro;
        endereco.cep = cep;
        endereco.nomeCidade = nomeCidade;
        endereco.emailUsuario = emailUsuario;
        endereco.estado = estado;

        return endereco;
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

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
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
