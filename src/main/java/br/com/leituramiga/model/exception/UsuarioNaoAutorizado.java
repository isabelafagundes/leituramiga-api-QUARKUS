package br.com.leituramiga.model.exception;

public class UsuarioNaoAutorizado extends ErroDominio {
    @Override
    public String toString() {
        return "Não foi possível autenticar o usuário!!";
    }
}