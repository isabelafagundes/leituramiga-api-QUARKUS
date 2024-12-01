package br.com.leituramiga.model.exception;

public class UsuarioComSolicitacaoAberta extends ErroModel {
    @Override
    public String toString() {
        return "Este usuário possui uma solicitação em aberto!!";
    }
}
