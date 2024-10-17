package br.com.leituramiga.model.exception;

public class UsernameExiste extends ErroModel {

    @Override
    public String toString() {
        return "Username já existe";
    }
}
