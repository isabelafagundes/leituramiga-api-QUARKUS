package br.com.leituramiga.model.exception.solicitacao;

import br.com.leituramiga.model.exception.ErroDominio;

public class SolicitacaoNaoExistente extends ErroDominio {
    @Override
    public String toString() {
        return "O endereço informado não existe!!";
    }
}