package br.com.leituramiga.model.solicitacao;

public class NotificacaoSolicitacao {
    private Integer codigoSolicitacao;
    private String nomeUsuarioSolicitante;
    private String emailUsuarioSolicitante;
    private String imagem;

    public NotificacaoSolicitacao() {
    }

    public static NotificacaoSolicitacao criar(
            Integer codigoSolicitacao,
            String nomeUsuarioSolicitante,
            String emailUsuarioSolicitante,
            String imagem
    ) {
        NotificacaoSolicitacao notificacao = new NotificacaoSolicitacao();
        notificacao.codigoSolicitacao = codigoSolicitacao;
        notificacao.nomeUsuarioSolicitante = nomeUsuarioSolicitante;
        notificacao.emailUsuarioSolicitante = emailUsuarioSolicitante;
        notificacao.imagem = imagem;
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

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
}
