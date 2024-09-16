package br.com.isabela.dto.endereco;

import br.com.isabela.model.endereco.Endereco;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class EnderecoDto {

    public Integer id;
    public String logradouro;
    public String complemento;
    public String bairro;
    public String cep;
    public String nomeCidade;

    public Integer codigoCidade;

    public String emailUsuario;

    public static EnderecoDto deModel(
            Endereco endereco
    ) {
        EnderecoDto dto = new EnderecoDto();

        dto.id = endereco.getId();
        dto.logradouro = endereco.getLogradouro();
        dto.complemento = endereco.getComplemento();
        dto.bairro = endereco.getBairro();
        dto.cep = endereco.getCep();
        dto.nomeCidade = endereco.getNomeCidade();
        dto.codigoCidade = endereco.getCodigoCidade();

        return dto;
    }

    public EnderecoDto() {
    }

    public Integer getId() {
        return id;
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
}
