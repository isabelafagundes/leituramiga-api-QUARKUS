package br.com.leituramiga.model.exception.livro;

import br.com.leituramiga.model.exception.ErroDominio;

public class LivroJaDesativado extends ErroDominio {
    @Override
    public String toString() {
        return "Livro já desativado!!";
    }
}
