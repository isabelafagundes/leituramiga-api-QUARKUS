package br.com.leituramiga.model.solicitacao;

public class NotificacaoSolicitacao {
    private Integer codigoSolicitacao;
    private String nomeUsuarioSolicitante;
    private String emailUsuarioSolicitante;

    public NotificacaoSolicitacao() {
    }

    public static NotificacaoSolicitacao criar(
            Integer codigoSolicitacao,
            String nomeUsuarioSolicitante,
            String emailUsuarioSolicitante
    ) {
        NotificacaoSolicitacao notificacao = new NotificacaoSolicitacao();
        notificacao.codigoSolicitacao = codigoSolicitacao;
        notificacao.nomeUsuarioSolicitante = nomeUsuarioSolicitante;
        notificacao.emailUsuarioSolicitante = emailUsuarioSolicitante;
        return notificacao;
    }

    public Integer getCodigoSolicitacao() {
        return codigoSolicitacao;
    }

    public String getNomeUsuarioSolicitante() {
        return nomeUsuarioSolicitante;
    }

    public String getEmailUsuarioSolicitante() {
        return emailUsuarioSolicitante;
    }
}
