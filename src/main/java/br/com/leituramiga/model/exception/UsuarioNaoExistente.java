package br.com.leituramiga.model.exception;

public class UsuarioNaoExistente extends ErroModel {
    @Override
    public String toString() {
        return "Não foi possível encontrar este usuário!!";
    }
}
