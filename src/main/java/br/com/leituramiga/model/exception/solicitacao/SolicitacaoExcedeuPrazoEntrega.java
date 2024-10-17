package br.com.leituramiga.model.exception.solicitacao;

import br.com.leituramiga.model.exception.ErroModel;

public class SolicitacaoExcedeuPrazoEntrega extends ErroModel {
    @Override
    public String toString() {
        return "A solicitação excedeu o prazo de entrega!!";
    }
}
