package br.com.leituramiga.model.exception;

public class UsuarioNaoAtivo extends ErroModel {
    @Override
    public String toString() {
        return "O usuário não está mais ativo!!";
    }
}
