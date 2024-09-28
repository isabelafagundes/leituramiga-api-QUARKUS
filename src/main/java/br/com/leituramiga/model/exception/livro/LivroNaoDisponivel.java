package br.com.leituramiga.model.exception.livro;

import br.com.leituramiga.model.exception.ErroDominio;

public class LivroNaoDisponivel extends ErroDominio {
    @Override
    public String toString() {
        return "Livro não disponível!!";
    }
}
