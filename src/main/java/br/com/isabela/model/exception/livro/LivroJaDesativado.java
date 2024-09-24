package br.com.isabela.model.exception.livro;

import br.com.isabela.model.exception.ErroDominio;

public class LivroJaDesativado extends ErroDominio {
    @Override
    public String toString() {
        return "Livro já desativado!!";
    }
}
