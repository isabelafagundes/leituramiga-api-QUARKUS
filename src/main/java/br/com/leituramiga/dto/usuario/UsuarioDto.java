package br.com.leituramiga.dto.usuario;

import br.com.leituramiga.dto.endereco.EnderecoDto;
import br.com.leituramiga.model.usuario.Usuario;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class UsuarioDto {
    public String nome;

    public String username;

    public String email;
    public Integer tipoUsuario;
    public boolean ativo;
    public boolean bloqueado;
    public Integer tentativas;
    public String celular;
    public String descricao;
    public String imagem;
    public Integer codigoInstituicao;
    public Integer codigoEndereco;
    public EnderecoDto endereco;
    public String nomeCidade;
    public String nomeInstituicao;

    public static UsuarioDto deModel(
            Usuario usuario
    ) {
        UsuarioDto dto = new UsuarioDto();
        dto.email = usuario.getEmail();
        dto.nome = usuario.getNome();
        dto.username = usuario.getUsername();
        dto.tipoUsuario = usuario.getTipoUsuario().getId();
        dto.ativo = usuario.isAtivo();
        dto.bloqueado = usuario.isBloqueado();
        dto.tentativas = usuario.getTentativas();
        dto.celular = usuario.getCelular();
        dto.descricao = usuario.getDescricao();
        dto.imagem = usuario.getImagem();
        dto.codigoInstituicao = usuario.getCodigoInstituicao();
        dto.nomeCidade = usuario.getNomeCidade();
        dto.nomeInstituicao = usuario.getNomeInstituicao();
        dto.codigoEndereco = usuario.getEndereco().getCodigoEndereco();
        if (usuario.getEndereco() != null) dto.endereco = EnderecoDto.deModel(usuario.getEndereco());
        return dto;
    }


    public UsuarioDto() {
    }
}
