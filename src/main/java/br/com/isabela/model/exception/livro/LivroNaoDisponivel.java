package br.com.isabela.model.exception.livro;

import br.com.isabela.model.exception.ErroDominio;

public class LivroNaoDisponivel extends ErroDominio {
    @Override
    public String toString() {
        return "Livro não disponível!!";
    }
}
