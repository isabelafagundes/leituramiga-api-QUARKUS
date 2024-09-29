package br.com.leituramiga.service.email;

import br.com.leituramiga.service.autenticacao.LogService;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class EmailService {
    @Inject
    Mailer mailer;

    @Inject
    LogService logService;

    public void enviarEmailCodigoVerificacao(String destinatario, String codigo, String nome) {
        String assunto = "Código de verificação do LeiturAmiga";
        String html = EmailStyles.MODELO_CODIGO_SEGURANCA.replace("{{codigoSeguranca}}", codigo).replace("{{nomeUsuario}}", nome);
        enviarEmailSimples(destinatario, assunto, html);
    }

    public void enviarEmailBoasVindas(String destinatario, String nome) {
        String assunto = "Boas vindas ao LeiturAmiga!";
        String html = EmailStyles.MODELO_BOAS_VINDAS.replace("{{nomeUsuario}}", nome);
        enviarEmailSimples(destinatario, assunto, html);
    }

    private void enviarEmailSimples(String destinatario, String assunto, String html) {
        try {
            logService.iniciar(EmailService.class.getName(), "Iniciando envio de email para " + destinatario);
            mailer.send(Mail.withHtml(destinatario, assunto, html));
            logService.sucesso(EmailService.class.getName(), "Email enviado para " + destinatario);
        } catch (Exception e) {
            logService.erro(EmailService.class.getName(), "Ocorreu um erro no envio de email para " + destinatario, e);
            throw e;
        }
    }

}
