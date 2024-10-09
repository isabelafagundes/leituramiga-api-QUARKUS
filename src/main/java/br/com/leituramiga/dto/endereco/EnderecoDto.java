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
        return endereco;
    }

    public EnderecoDto() {
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
}
