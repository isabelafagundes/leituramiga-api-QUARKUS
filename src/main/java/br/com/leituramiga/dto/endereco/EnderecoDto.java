package br.com.leituramiga.dto.endereco;

import br.com.leituramiga.model.endereco.Endereco;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class EnderecoDto {

    public Integer codigoEndereco;
    public String logradouro;
    public String complemento;
    public String bairro;
    public String cep;
    public String nomeCidade;
    public String numero;
    public Integer codigoCidade;
    public String estado;
    public String emailUsuario;
    public Boolean enderecoPrincipal;

    public static EnderecoDto deModel(
            Endereco endereco
    ) {
        EnderecoDto dto = new EnderecoDto();
        dto.codigoEndereco = endereco.getCodigoEndereco();
        dto.estado = endereco.getEstado();
        dto.logradouro = endereco.getLogradouro();
        dto.complemento = endereco.getComplemento();
        dto.bairro = endereco.getBairro();
        dto.cep = endereco.getCep();
        dto.numero = endereco.getNumero();
        dto.nomeCidade = endereco.getNomeCidade();
        dto.codigoCidade = endereco.getCodigoCidade();
        dto.emailUsuario = endereco.getEmailUsuario();
        dto.enderecoPrincipal = endereco.getEnderecoPrincipal();
        return dto;
    }

    public static Endereco paraModel(EnderecoDto dto) {
        Endereco endereco = new Endereco();
        endereco.setCodigoEndereco(dto.codigoEndereco);
        endereco.setEstado(dto.estado);
        endereco.setLogradouro(dto.logradouro);
        endereco.setComplemento(dto.complemento);
        endereco.setBairro(dto.bairro);
        endereco.setCep(dto.cep);
        endereco.setNomeCidade(dto.nomeCidade);
        endereco.setCodigoCidade(dto.codigoCidade);
        endereco.setEmailUsuario(dto.emailUsuario);
        endereco.setEnderecoPrincipal(dto.enderecoPrincipal);
        endereco.setNumero(dto.numero);
        return endereco;
    }

    public EnderecoDto() {
    }

    public void setCodigoEndereco(Integer codigoEndereco) {
        this.codigoEndereco = codigoEndereco;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public void setNomeCidade(String nomeCidade) {
        this.nomeCidade = nomeCidade;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setCodigoCidade(Integer codigoCidade) {
        this.codigoCidade = codigoCidade;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public void setEnderecoPrincipal(Boolean enderecoPrincipal) {
        this.enderecoPrincipal = enderecoPrincipal;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public String getComplemento() {
        return complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public String getCep() {
        return cep;
    }

    public String getNomeCidade() {
        return nomeCidade;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public Integer getCodigoCidade() {
        return codigoCidade;
    }

    public String getEstado() {
        return estado;
    }

    public Integer getCodigoEndereco() {
        return codigoEndereco;
    }

    public String getNumero() {
        return numero;
    }

    public Boolean getEnderecoPrincipal() {
        return enderecoPrincipal;
    }
}
