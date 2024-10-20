package br.com.leituramiga.service.email;

import br.com.leituramiga.dto.solicitacao.SolicitacaoDto;
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

    public void enviarEmailSolicitacaoDeLivros(String destinatario, SolicitacaoDto solicitacao, String nomeUsuarioReceptor, String nomeUsuarioSolicitante) {
        String assunto = "Solicitação de livros";
        String dataDevolucao = solicitacao.getDataDevolucao() == null ? "" : "<li><strong>Data de Devolução:</strong> "+solicitacao.dataDevolucaoFormatada()+"</li>";
        String dataEntrega = solicitacao.getDataEntrega() == null ? "" : "<li><strong>Data de Entrega:</strong> "+solicitacao.dataEntregaFormatada()+"</li>";

        String html = EmailStyles.EMAIL_SOLICITACAO_RECEBIDA.replace("{{nomeReceptor}}", nomeUsuarioReceptor)
                .replace("{{nomeSolicitante}}", nomeUsuarioSolicitante)
                .replace("{{emailSolicitante}}", solicitacao.getEmailUsuarioSolicitante())
                .replace("{{dataEntrega}}", dataEntrega)
                .replace("{{dataDevolucao}}", dataDevolucao)
                .replace("{{endereco}}", solicitacao.getEndereco().obterEnderecoFormatado())
                .replace("{{livros}}", solicitacao.obterLivrosFormatados());
        enviarEmailSimples(destinatario, assunto, html);
    }

    public void enviarEmailSolicitacaoAceita(String destinatario, String nomeUsuarioReceptor, String nomeUsuarioSolicitante) {
        String assunto = "Solicitação de livros aceita";
        String html = EmailStyles.EMAIL_SOLICITACAO_ACEITA.replace("{{nomeSolicitante}}", nomeUsuarioSolicitante)
                .replace("{{nomeReceptor}}", nomeUsuarioReceptor);

        enviarEmailSimples(destinatario, assunto, html);
    }

    public void enviarEmailSolicitacaoRecusada(String destinatario, String motivo, String nomeUsuarioReceptor, String nomeUsuarioSolicitante, String nomeUsuario) {
        String motivoFormatado = motivo == null || motivo.isEmpty() || motivo.isBlank() ? "Motivo não informado" : "Motivo: " + motivo;
        String assunto = "Solicitação de livros recusada";
        String html = EmailStyles.EMAIL_SOLICITACAO_RECUSADA.replace("{{nomeSolicitante}}", nomeUsuarioSolicitante)
                .replace("{{nomeReceptor}}", nomeUsuarioReceptor)
                .replace("{{nomeUsuario}}", nomeUsuario)
                .replace("{{status}}", "recusada")
                .replace("{{motivo}}", motivoFormatado);

        enviarEmailSimples(destinatario, assunto, html);
    }

    public void enviarEmailSolicitacaoCancelada(String destinatario, String motivo, String nomeUsuarioReceptor, String nomeUsuarioSolicitante, String nomeUsuario) {
        String motivoFormatado = motivo == null || motivo.isEmpty() || motivo.isBlank() ? "Motivo não informado" : "Motivo: " + motivo;

        String assunto = "Solicitação de livros cancelada";
        String html = EmailStyles.EMAIL_SOLICITACAO_RECUSADA.replace("{{nomeSolicitante}}", nomeUsuarioSolicitante)
                .replace("{{nomeReceptor}}", nomeUsuarioReceptor)
                .replace("{{status}}", "cancelada")
                .replace("{{nomeUsuario}}", nomeUsuario)
                .replace("{{motivo}}", motivoFormatado);

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
