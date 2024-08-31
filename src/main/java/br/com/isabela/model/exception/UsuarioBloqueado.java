package br.com.isabela.model.exception;

public class UsuarioBloqueado extends ErroDominio {
    @Override
    public String toString() {
        return "Este usuário esta bloqueado por tentativas excessivas de login!!";
    }
}
