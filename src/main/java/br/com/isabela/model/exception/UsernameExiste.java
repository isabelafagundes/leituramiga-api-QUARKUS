package br.com.isabela.model.exception;

public class UsernameExiste extends ErroDominio {

    @Override
    public String toString() {
        return "Username já existe";
    }
}
