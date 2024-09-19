package br.com.isabela.model.exception.solicitacao;

import br.com.isabela.model.exception.ErroDominio;

public class SolicitacaoExcedeuPrazoDevolucao extends ErroDominio {
    @Override
    public String toString() {
        return "A solicitação excedeu o prazo de devolução!!";
    }
}