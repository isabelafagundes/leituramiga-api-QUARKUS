package br.com.leituramiga.model.exception;

public class CodigoIncorreto extends ErroDominio {
    @Override
    public String getMessage() {
        return "Código incorreto!";
    }
}
