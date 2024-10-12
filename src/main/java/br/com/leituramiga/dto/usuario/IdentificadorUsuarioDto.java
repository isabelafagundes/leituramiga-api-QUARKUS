package br.com.leituramiga.dto.usuario;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class IdentificadorUsuarioDto {
    public String username;
    public String email;

    public IdentificadorUsuarioDto(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
