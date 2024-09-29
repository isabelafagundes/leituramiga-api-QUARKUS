package br.com.leituramiga.service.autenticacao;

import org.springframework.security.crypto.bcrypt.BCrypt;

import java.security.SecureRandom;

public class CodigoUtil {

    public static String obterCodigo() {
        SecureRandom random = new SecureRandom();
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < 5; i++) {
            int digito = random.nextInt(10);
            builder.append(digito);
        }

        return BCrypt.hashpw(builder.toString(), BCrypt.gensalt());
    }

    public static boolean verificarCodigo(String tokenSalvo, String tokenEnviado) {
        return BCrypt.checkpw(tokenEnviado, tokenSalvo);
    }


}
