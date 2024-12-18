package br.com.leituramiga.dto.solicitacao;

import br.com.leituramiga.model.solicitacao.NotificacaoSolicitacao;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class NotificacaoSolicitacaoDto {

    public Integer codigoSolicitacao;
    public String nomeUsuarioSolicitante;
    public String emailUsuarioSolicitante;
    public String imagem;

    public NotificacaoSolicitacaoDto(Integer codigoSolicitacao, String nomeUsuarioSolicitante, String emailUsuarioSolicitante, String imagem) {
        this.codigoSolicitacao = codigoSolicitacao;
        this.nomeUsuarioSolicitante = nomeUsuarioSolicitante;
        this.emailUsuarioSolicitante = emailUsuarioSolicitante;
        this.imagem = imagem;
    }

    public static NotificacaoSolicitacaoDto deModel(NotificacaoSolicitacao solicitacao) {
        return new NotificacaoSolicitacaoDto(solicitacao.getCodigoSolicitacao(), solicitacao.getNomeUsuarioSolicitante(), solicitacao.getEmailUsuarioSolicitante(), solicitacao.getImagem());
    }
}
