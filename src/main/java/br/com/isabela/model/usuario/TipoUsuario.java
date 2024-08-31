package br.com.isabela.model.usuario;

import java.util.Arrays;

public enum TipoUsuario {
    USUARIO(1),
    ADMINISTRADOR(2);

    Integer id;

    TipoUsuario(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public static TipoUsuario obterPorId(Integer id) {
        return Arrays.stream(TipoUsuario.values()).filter((e) -> e.id == id).findAny().orElseThrow();
    }
}
