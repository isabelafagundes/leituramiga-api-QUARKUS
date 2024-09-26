package br.com.isabela.model.exception;

public class ComentarioNaoExistente extends Throwable {
    @Override
    public String getMessage() { return "O comentário não foi procurado, não existente!!";}
}
