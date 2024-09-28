package br.com.leituramiga.model.exception.livro;

import br.com.leituramiga.model.exception.ErroDominio;

public class LivroNaoExistente extends ErroDominio {
    @Override
    public String toString() {
        return "O livro selecionado não existe!!";
    }
}
