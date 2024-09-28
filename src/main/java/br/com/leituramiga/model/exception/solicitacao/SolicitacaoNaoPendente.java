package br.com.leituramiga.model.exception.solicitacao;

import br.com.leituramiga.model.exception.ErroDominio;

public class SolicitacaoNaoPendente extends ErroDominio {
    @Override
    public String toString() {
        return "A solicitação informada não está pendente!!";
    }
}
