package br.com.leituramiga.dto.endereco;

import br.com.leituramiga.model.endereco.Endereco;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class EnderecoDto {

    public Integer id;
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
        dto.id = endereco.getCodigo();
        dto.estado = endereco.getEstado();
        dto.logradouro = endereco.getLogradouro();
        dto.complemento = endereco.getComplemento();
        dto.bairro = endereco.getBairro();
        dto.cep = endereco.getCep();
        dto.numero = endereco.getNumero();
        dto.nomeCidade = endereco.getNomeCidade();
        dto.codigoCidade = endereco.getCodigoCidade();
        return dto;
    }

    public static Endereco paraModel(EnderecoDto dto) {
        Endereco endereco = new Endereco();
        endereco.setCodigo(dto.id);
        endereco.setEstado(dto.estado);
        endereco.setLogradouro(dto.logradouro);
        endereco.setComplemento(dto.complemento);
        endereco.setBairro(dto.bairro);
        endereco.setCep(dto.cep);
        endereco.setNomeCidade(dto.nomeCidade);
        endereco.setCodigoCidade(dto.codigoCidade);
        return endereco;
    }

    public EnderecoDto() {
    }

    public Integer getCodigoEndereco() {
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

    public String getEstado() {
        return estado;
    }

    public Integer getId() {
        return id;
    }

    public String getNumero() {
        return numero;
    }
}
