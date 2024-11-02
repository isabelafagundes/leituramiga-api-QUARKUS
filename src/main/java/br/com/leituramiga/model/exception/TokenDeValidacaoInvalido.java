package br.com.leituramiga.model.exception;

public class TokenDeValidacaoInvalido extends ErroModel {
    @Override
    public String toString() {
        return "O token de validação é inválido.";
    }
}
