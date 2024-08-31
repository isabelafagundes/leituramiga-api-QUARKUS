package br.com.isabela.service.autenticacao;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LogService {


    public void sucesso(String local, String mensagem) {
        Log.info("[" + local + "] " + mensagem + "!");
    }

    public void erro(String local, String mensagem, Exception erro) {
        Log.info("[" + local + "] " + mensagem + "!!", erro);
    }

    public void iniciar(String local, String mensagem) {
        Log.info("[" + local + "] " + mensagem + "...");
    }


}
