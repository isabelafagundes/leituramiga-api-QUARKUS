package br.com.leituramiga.model.exception;

public class UsuarioNaoAutorizado extends ErroModel {
    @Override
    public String toString() {
        return "Não foi possível autenticar o usuário!!";
    }
}
