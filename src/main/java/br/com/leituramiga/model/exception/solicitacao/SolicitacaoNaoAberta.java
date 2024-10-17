package br.com.leituramiga.model.exception.solicitacao;

import br.com.leituramiga.model.exception.ErroModel;

public class SolicitacaoNaoAberta extends ErroModel {
    @Override
    public String toString() {
        return "A solicitação informada não está aberta!!";
    }
}
