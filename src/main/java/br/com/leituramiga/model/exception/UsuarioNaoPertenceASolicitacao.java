package br.com.leituramiga.model.exception;

public class UsuarioNaoPertenceASolicitacao extends ErroModel {

    @Override
    public String toString() {
        return "O usuário não pertence a esta solicitação!!";
    }
}
