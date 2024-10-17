package br.com.leituramiga.model.exception.livro;

import br.com.leituramiga.model.exception.ErroModel;

public class LivroNaoDisponivel extends ErroModel {
    @Override
    public String toString() {
        return "Livro não disponível!!";
    }
}
