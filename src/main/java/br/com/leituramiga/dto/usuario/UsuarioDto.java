package br.com.leituramiga.dto.usuario;

import br.com.leituramiga.model.usuario.TipoUsuario;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class UsuarioDto {
    public String nome;

    public String username;

    public String email;
    public TipoUsuario tipoUsuario;

    public static UsuarioDto deModel(
            String nome,
            String username,
            String email,
            TipoUsuario tipoUsuario
    ) {
        UsuarioDto dto = new UsuarioDto();
        dto.email = email;
        dto.nome = nome;
        dto.username = username;
        dto.tipoUsuario = tipoUsuario;
        return dto;
    }


    public UsuarioDto() {}
}
