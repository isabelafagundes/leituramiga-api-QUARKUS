package br.com.leituramiga.model.exception;

public class EnderecoNaoExistente extends ErroDominio {
    @Override
    public String toString() {
        return "O endereço informado não existe!!";
    }
}
