package br.com.leituramiga.model.exception.livro;

import br.com.leituramiga.model.exception.ErroModel;

public class LivroNaoExistente extends ErroModel {
    @Override
    public String toString() {
        return "O livro selecionado não existe!!";
    }
}
