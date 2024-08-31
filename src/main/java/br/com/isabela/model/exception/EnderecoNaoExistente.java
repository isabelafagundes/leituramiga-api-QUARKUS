package br.com.isabela.model.exception;

public class EnderecoNaoExistente extends ErroDominio {
    @Override
    public String toString() {
        return "O endereço informado não existe!!";
    }
}
