package br.com.isabela.model.exception.solicitacao;

import br.com.isabela.model.exception.ErroDominio;

public class SolicitacaoNaoAberta extends ErroDominio {
    @Override
    public String toString() {
        return "A solicitação informada não está aberta!!";
    }
}
