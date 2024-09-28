package br.com.leituramiga.model.exception;

public class UsuarioNaoExistente extends ErroDominio {
    @Override
    public String toString() {
        return "Não foi possível encontrar este usuário!!";
    }
}
