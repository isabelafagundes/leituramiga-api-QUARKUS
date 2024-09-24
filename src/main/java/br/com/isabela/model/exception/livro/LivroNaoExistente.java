package br.com.isabela.model.exception.livro;

import br.com.isabela.model.exception.ErroDominio;

public class LivroNaoExistente extends ErroDominio {
    @Override
    public String toString() {
        return "O livro selecionado não existe!!";
    }
}
