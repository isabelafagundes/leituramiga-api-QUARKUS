package br.com.isabela.dto.usuario;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class CriacaoUsuarioDto {
    public String nome;
    public String username;
    public String senha;

    public String email;
    public Integer tipoUsuario;

    public static CriacaoUsuarioDto deDomain(
            String nome,
            String username,
            String email,
            String senha,
            Integer tipoUsuario
    ) {
        CriacaoUsuarioDto dto = new CriacaoUsuarioDto();
        dto.email = email;
        dto.nome = nome;
        dto.username = username;
        dto.senha = senha;
        dto.tipoUsuario = tipoUsuario;
        return dto;
    }

    public CriacaoUsuarioDto() {}

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
