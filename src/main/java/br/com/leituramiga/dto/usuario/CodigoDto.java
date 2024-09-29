package br.com.leituramiga.dto.usuario;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class CodigoDto {
    public String email;
    public String codigo;

    public CodigoDto(String email, String senha) {
        this.email = email;
        this.codigo = senha;
    }
}
