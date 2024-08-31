package br.com.isabela.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class LoginDto {
    public String email;
    public String senha;

    public LoginDto(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }
}
