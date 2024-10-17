package br.com.leituramiga.model.exception;

public class UsuarioBloqueado extends ErroModel {
    @Override
    public String toString() {
        return "Este usuário esta bloqueado por tentativas excessivas de login!!";
    }
}
