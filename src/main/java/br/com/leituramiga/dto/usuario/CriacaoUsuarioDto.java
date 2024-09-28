package br.com.leituramiga.dto.usuario;

import br.com.leituramiga.dto.endereco.EnderecoDto;
import br.com.leituramiga.model.usuario.Usuario;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class CriacaoUsuarioDto {
    public String nome;
    public String username;
    public String senha;

    public String email;
    public Integer tipoUsuario;
    public String celular;
    public String descricao;
    public String imagem;
    public Integer codigoInstituicao;
    public EnderecoDto endereco;

    public static CriacaoUsuarioDto deModel(Usuario usuario) {
        CriacaoUsuarioDto dto = new CriacaoUsuarioDto();
        dto.nome = usuario.getNome();
        dto.username = usuario.getUsername();
        dto.email = usuario.getEmail();
        dto.tipoUsuario = usuario.getTipoUsuario().getId();
        dto.celular = usuario.getCelular();
        dto.descricao = usuario.getDescricao();
        dto.imagem = usuario.getImagem();
        dto.codigoInstituicao = usuario.getCodigoInstituicao();
        dto.endereco = EnderecoDto.deModel(usuario.getEndereco());

        return dto;
    }

    public static Usuario paraModel(CriacaoUsuarioDto dto) {
        return Usuario.carregar(
                dto.nome,
                dto.username,
                dto.senha,
                dto.email,
                dto.tipoUsuario,
                0,
                false,
                true,
                null,
                dto.celular,
                dto.descricao,
                dto.imagem,
                dto.codigoInstituicao,
                EnderecoDto.paraModel(dto.endereco)
        );
    }

    public CriacaoUsuarioDto() {
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public String getUsername() {
        return username;
    }

    public String getSenha() {
        return senha;
    }

    public String getEmail() {
        return email;
    }

    public Integer getTipoUsuario() {
        return tipoUsuario;
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

    public EnderecoDto getEndereco() {
        return endereco;
    }
}
