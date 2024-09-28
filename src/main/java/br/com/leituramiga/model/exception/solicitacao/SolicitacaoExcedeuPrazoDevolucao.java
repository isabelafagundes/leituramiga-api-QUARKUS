package br.com.leituramiga.model.exception.solicitacao;

import br.com.leituramiga.model.exception.ErroDominio;

public class SolicitacaoExcedeuPrazoDevolucao extends ErroDominio {
    @Override
    public String toString() {
        return "A solicitação excedeu o prazo de devolução!!";
    }
}