package br.com.leituramiga.dto.usuario;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class AtualizacaoSenhaDto {
    public String senha;
    public String email;

    public AtualizacaoSenhaDto() {
    }

    public AtualizacaoSenhaDto(String senha, String email) {
        this.senha = senha;
        this.email = email;
    }
}
