package br.com.isabela.model.exception.solicitacao;

import br.com.isabela.model.exception.ErroDominio;

public class SolicitacaoNaoExistente extends ErroDominio {
    @Override
    public String toString() {
        return "O endereço informado não existe!!";
    }
}
