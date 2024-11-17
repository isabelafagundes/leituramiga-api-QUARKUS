package br.com.leituramiga.service.email;

import br.com.leituramiga.dto.solicitacao.SolicitacaoDto;
import br.com.leituramiga.service.autenticacao.LogService;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.io.File;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ApplicationScoped
public class EmailService {
    @Inject
    Mailer mailer;

    @Inject
    LogService logService;

    private final ExecutorService executor = Executors.newFixedThreadPool(4);

    public void enviarEmailCodigoVerificacao(String destinatario, String codigo, String nome) {
        String assunto = "Código de verificação do LeiturAmiga";
        String html = EmailStyles.MODELO_CODIGO_SEGURANCA.replace("{{codigoSeguranca}}", codigo).replace("{{nomeUsuario}}", nome);
        String logo = "icon_seguranca.png";
        String cid = "icon_seguranca";
        enviarEmailComIcone(destinatario, assunto, html, logo, cid).thenRun(() -> logService.sucesso(EmailService.class.getName(), "Email de código de verificação enviado para " + destinatario));
    }

    public void enviarEmailBoasVindas(String destinatario, String nome) {
        String assunto = "Boas-vindas ao LeiturAmiga!";
        String htmlContent = EmailStyles.MODELO_BOAS_VINDAS.replace("{{nomeUsuario}}", nome);
        String logo = "logo_leituramiga.png";
        String cid = "logo_leituramiga";
        enviarEmailComIcone(destinatario, assunto, htmlContent, logo, cid).thenRun(() -> logService.sucesso(EmailService.class.getName(), "Email de boas vindas enviado para " + destinatario));
        logService.sucesso(EmailService.class.getName(), "Email de boas-vindas enviado para " + destinatario);
    }

    public void enviarEmailSolicitacaoDeLivros(String destinatario, SolicitacaoDto solicitacao, String nomeUsuarioReceptor, String nomeUsuarioSolicitante) {
        String assunto = "Solicitação de livros";
        String dataDevolucao = solicitacao.getDataDevolucao() == null ? "" : "<li><strong>Data de Devolução:</strong> " + solicitacao.dataDevolucaoFormatada() + "</li>";
        String dataEntrega = solicitacao.getDataEntrega() == null ? "" : "<li><strong>Data de Entrega:</strong> " + solicitacao.dataEntregaFormatada() + "</li>";
        String informacoesAdicionais = solicitacao.getInformacoesAdicionais() == null || solicitacao.getInformacoesAdicionais().isEmpty() ? "" : "<li><strong>Informações Adicionais:</strong> " + solicitacao.getInformacoesAdicionais() + "</li>";

        String logo = "solicitacao_icon";
        String cid = "solicitacao_icon";

        String html = EmailStyles.EMAIL_SOLICITACAO_RECEBIDA.replace("{{nomeReceptor}}", nomeUsuarioReceptor)
                .replace("{{nomeSolicitante}}", nomeUsuarioSolicitante)
                .replace("{{emailSolicitante}}", solicitacao.getEmailUsuarioSolicitante())
                .replace("{{dataEntrega}}", dataEntrega)
                .replace("{{dataDevolucao}}", dataDevolucao)
                .replace("{{endereco}}", solicitacao.getEnderecoSolicitante().obterEnderecoFormatado())
                .replace("{{livros}}", solicitacao.obterLivrosFormatados())
                .replace("{{informacoesAdicionais}}", informacoesAdicionais);
        enviarEmailSimples(destinatario, assunto, html).thenRun(() -> logService.sucesso(EmailService.class.getName(), "Email de solicitação de livros enviado para " + destinatario));
    }

    public void enviarEmailSolicitacaoAceita(String destinatario, String nomeUsuarioReceptor, String nomeUsuarioSolicitante) {
        String assunto = "Solicitação de livros aceita";
        String html = EmailStyles.EMAIL_SOLICITACAO_ACEITA.replace("{{nomeSolicitante}}", nomeUsuarioSolicitante)
                .replace("{{nomeReceptor}}", nomeUsuarioReceptor);
        String logo = "aceito_icon";
        String cid = "aceito_icon";

        enviarEmailSimples(destinatario, assunto, html).thenRun(() -> logService.sucesso(EmailService.class.getName(), "Email de solicitação aceita enviado para " + destinatario));
    }

    public void enviarEmailSolicitacaoRecusada(String destinatario, String motivo, String nomeUsuarioReceptor, String nomeUsuarioSolicitante, String nomeUsuario) {
        String motivoFormatado = motivo == null || motivo.isEmpty() || motivo.isBlank() ? "Motivo não informado" : "Motivo: " + motivo;
        String nomeSolicitante = nomeUsuario.equals(nomeUsuarioSolicitante) ? "você" : nomeUsuarioSolicitante;
        String assunto = "Solicitação de livros recusada";

        String logo = "recusado_icon";
        String cid = "recusado_icon";

        String html = EmailStyles.EMAIL_SOLICITACAO_RECUSADA.replace("{{nomeSolicitante}}", nomeSolicitante)
                .replace("{{nomeReceptor}}", nomeUsuarioReceptor)
                .replace("{{nomeUsuario}}", nomeUsuario)
                .replace("{{status}}", "recusada")
                .replace("{{motivo}}", motivoFormatado);

        enviarEmailSimples(destinatario, assunto, html).thenRun(() -> logService.sucesso(EmailService.class.getName(), "Email de solicitação recusada enviado para " + destinatario));
    }

    public void enviarEmailSolicitacaoCancelada(String destinatario, String motivo, String nomeUsuarioReceptor, String nomeUsuarioSolicitante, String nomeUsuario) {
        String motivoFormatado = motivo == null || motivo.isEmpty() || motivo.isBlank() ? "Motivo não informado" : "Motivo: " + motivo;
        String nomeSolicitante = nomeUsuario.equals(nomeUsuarioSolicitante) ? "você" : nomeUsuarioSolicitante;

        String logo = "recusado_icon";
        String cid = "recusado_icon";

        String assunto = "Solicitação de livros cancelada";
        String html = EmailStyles.EMAIL_SOLICITACAO_RECUSADA.replace("{{nomeSolicitante}}", nomeSolicitante)
                .replace("{{nomeReceptor}}", nomeUsuarioReceptor)
                .replace("{{status}}", "cancelada")
                .replace("{{nomeUsuario}}", nomeUsuario)
                .replace("{{motivo}}", motivoFormatado);

        enviarEmailSimples(destinatario, assunto, html).thenRun(() -> logService.sucesso(EmailService.class.getName(), "Email de solicitação cancelada enviado para " + destinatario));
    }

//    private CompletableFuture<Void> enviarEmailSimplesComLogo(String destinatario, String assunto, String html) {
//        return CompletableFuture.runAsync(() -> {
//            try {
//                logService.iniciar(EmailService.class.getName(), "Iniciando envio de email para " + destinatario);
//                String htmlContent = html;
//                String logo = "logo_leituramiga.png";
//                String cid = "logo_leituramiga";
//                File arquivoLogo = new File("src/main/resources/img/logo_leituramiga.png");
//
//                mailer.send(Mail.withHtml(destinatario, assunto, htmlContent).addInlineAttachment(logo, arquivoLogo, "image/png", cid));
//                logService.sucesso(EmailService.class.getName(), "Email enviado para " + destinatario);
//            } catch (Exception e) {
//                logService.erro(EmailService.class.getName(), "Ocorreu um erro no envio de email para " + destinatario, e);
//                throw e;
//            }
//        }, executor);
//    }
//
//    private CompletableFuture<Void> enviarEmailComIconeSeguranca(String destinatario, String assunto, String html) {
//        return CompletableFuture.runAsync(() -> {
//            try {
//                logService.iniciar(EmailService.class.getName(), "Iniciando envio de email para " + destinatario);
//                String htmlContent = html;
//                String logo = "icon_seguranca.png";
//                String cid = "icon_seguranca";
//                File arquivoLogo = new File("src/main/resources/img/icon_seguranca.png");
//
//                mailer.send(Mail.withHtml(destinatario, assunto, htmlContent).addInlineAttachment(logo, arquivoLogo, "image/png", cid));
//                logService.sucesso(EmailService.class.getName(), "Email enviado para " + destinatario);
//            } catch (Exception e) {
//                logService.erro(EmailService.class.getName(), "Ocorreu um erro no envio          de email para " + destinatario, e);
//                throw e;
//            }
//        }, executor);
//    }

    private CompletableFuture<Void> enviarEmailComIcone(String destinatario, String assunto, String html, String logo, String cid) {
        return CompletableFuture.runAsync(() -> {
            try {
                logService.iniciar(EmailService.class.getName(), "Iniciando envio de email para " + destinatario);
                String htmlContent = html;
                File arquivoLogo = new File("src/main/resources/img/" + logo);
                mailer.send(Mail.withHtml(destinatario, assunto, htmlContent).addInlineAttachment(logo, arquivoLogo, "image/png", cid));
                logService.sucesso(EmailService.class.getName(), "Email enviado para " + destinatario);
            } catch (Exception e) {
                logService.erro(EmailService.class.getName(), "Ocorreu um erro no envio de email para " + destinatario, e);
                throw e;
            }
        }, executor);
    }


    private CompletableFuture<Void> enviarEmailSimples(String destinatario, String assunto, String html) {
        return CompletableFuture.runAsync(() -> {
            try {
                logService.iniciar(EmailService.class.getName(), "Iniciando envio de email para " + destinatario);
                mailer.send(Mail.withHtml(destinatario, assunto, html));
                logService.sucesso(EmailService.class.getName(), "Email enviado para " + destinatario);
            } catch (Exception e) {
                logService.erro(EmailService.class.getName(), "Ocorreu um erro no envio de email para " + destinatario, e);
                throw e;
            }
        }, executor);
    }

}
