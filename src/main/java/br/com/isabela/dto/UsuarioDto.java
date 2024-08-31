package br.com.isabela.dto;

import br.com.isabela.model.TipoUsuario;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class UsuarioDto {
    public String nome;

    public String username;

    public String email;
    public TipoUsuario tipoUsuario;

    public static UsuarioDto deDomain(
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
