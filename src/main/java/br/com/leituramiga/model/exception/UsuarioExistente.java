package br.com.leituramiga.model.exception;

public class UsuarioExistente extends ErroModel {
    @Override
    public String toString() {
        return "Já existe uma conta com este email!!";
    }
}
