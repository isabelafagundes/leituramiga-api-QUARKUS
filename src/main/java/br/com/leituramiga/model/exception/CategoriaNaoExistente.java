package br.com.leituramiga.model.exception;

public class CategoriaNaoExistente extends Throwable {
    @Override
    public String getMessage() { return "A categoria não foi procurada, não existente!! "; }
}
