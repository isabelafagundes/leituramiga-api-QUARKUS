package br.com.leituramiga.dto.usuario;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class TokenDto {
    public String token;

    public TokenDto(String token) {
        this.token = token;
    }
}
