package br.com.leituramiga.model.exception.livro;

import br.com.leituramiga.model.exception.ErroModel;

public class LivroJaDesativado extends ErroModel {
    @Override
    public String toString() {
        return "Livro já desativado!!";
    }
}
