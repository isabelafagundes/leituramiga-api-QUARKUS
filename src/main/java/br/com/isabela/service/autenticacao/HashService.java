package br.com.isabela.service.autenticacao;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashService {

    public static String obterMd5Email(String email) {
        try {
            byte[] hash = MessageDigest.getInstance("MD5").digest(email.getBytes("UTF-8"));
            return new BigInteger(1, hash).toString(16);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String ObterMd5Comentario(String idComentario) {
        try{
            byte[] hash = MessageDigest.getInstance("MD5").digest(idComentario.getBytes("UTF-8"));
            return new BigInteger(1, hash).toString(16);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
