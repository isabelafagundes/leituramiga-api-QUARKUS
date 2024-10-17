package br.com.leituramiga.model.exception;

public class CodigoIncorreto extends ErroModel {
    @Override
    public String getMessage() {
        return "Código incorreto!";
    }
}
