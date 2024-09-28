package br.com.leituramiga.model.exception;

public class UsuarioNaoAtivo extends ErroDominio{
    @Override
    public String toString() {
        return "O usuário não está mais ativo!!";
    }
}
