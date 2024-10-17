package br.com.leituramiga.model.exception.solicitacao;

import br.com.leituramiga.model.exception.ErroModel;

public class SolicitacaoNaoExistente extends ErroModel {
    @Override
    public String toString() {
        return "O endereço informado não existe!!";
    }
}
