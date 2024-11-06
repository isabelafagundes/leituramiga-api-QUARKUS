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
        enviarEmailSimples(destinatario, assunto, html).thenRun(() -> logService.sucesso(EmailService.class.getName(), "Email de código de verificação enviado para " + destinatario));
    }

    public void enviarEmailBoasVindas(String destinatario, String nome) {

        String assunto = "Boas-vindas ao LeiturAmiga!";
        String htmlContent = EmailStyles.MODELO_BOAS_VINDAS.replace("{{nomeUsuario}}", nome);

        String imageCid = "logo_leituramiga";

        // Substituir o 'cid' no HTML com o nome correto
        htmlContent = htmlContent.replace(
                "background-image: url('cid:logo_leituramiga.png');",
                "background-image: url('cid:" + imageCid + "');"
        );
        File arquivo = new File("src/main/resources/img/logo_leituramiga.png");

        // Adiciona a imagem com o Content-ID correto
        mailer.send(Mail.withHtml(destinatario, assunto, htmlContent).addInlineAttachment("logo_leituramiga.png", arquivo, "image/png", "logo_leituramiga"));

        logService.sucesso(EmailService.class.getName(), "Email de boas-vindas enviado para " + destinatario);
    }

    public void enviarEmailSolicitacaoDeLivros(String destinatario, SolicitacaoDto solicitacao, String nomeUsuarioReceptor, String nomeUsuarioSolicitante) {
        String assunto = "Solicitação de livros";
        String dataDevolucao = solicitacao.getDataDevolucao() == null ? "" : "<li><strong>Data de Devolução:</strong> " + solicitacao.dataDevolucaoFormatada() + "</li>";
        String dataEntrega = solicitacao.getDataEntrega() == null ? "" : "<li><strong>Data de Entrega:</strong> " + solicitacao.dataEntregaFormatada() + "</li>";
        String informacoesAdicionais = solicitacao.getInformacoesAdicionais() == null || solicitacao.getInformacoesAdicionais().isEmpty() ? "" : "<li><strong>Informações Adicionais:</strong> " + solicitacao.getInformacoesAdicionais() + "</li>";

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

        enviarEmailSimples(destinatario, assunto, html).thenRun(() -> logService.sucesso(EmailService.class.getName(), "Email de solicitação aceita enviado para " + destinatario));
    }

    public void enviarEmailSolicitacaoRecusada(String destinatario, String motivo, String nomeUsuarioReceptor, String nomeUsuarioSolicitante, String nomeUsuario) {
        String motivoFormatado = motivo == null || motivo.isEmpty() || motivo.isBlank() ? "Motivo não informado" : "Motivo: " + motivo;
        String nomeSolicitante = nomeUsuario.equals(nomeUsuarioSolicitante) ? "você" : nomeUsuarioSolicitante;
        String assunto = "Solicitação de livros recusada";
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

        String assunto = "Solicitação de livros cancelada";
        String html = EmailStyles.EMAIL_SOLICITACAO_RECUSADA.replace("{{nomeSolicitante}}", nomeSolicitante)
                .replace("{{nomeReceptor}}", nomeUsuarioReceptor)
                .replace("{{status}}", "cancelada")
                .replace("{{nomeUsuario}}", nomeUsuario)
                .replace("{{motivo}}", motivoFormatado);

        enviarEmailSimples(destinatario, assunto, html).thenRun(() -> logService.sucesso(EmailService.class.getName(), "Email de solicitação cancelada enviado para " + destinatario));
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
