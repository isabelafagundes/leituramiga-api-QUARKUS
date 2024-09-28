package br.com.leituramiga.model.exception;

public class RefreshTokenInvalido extends ErroDominio{
    @Override
    public String toString() {
        return "O refresh token enviado está incorreto!!";
    }
}
