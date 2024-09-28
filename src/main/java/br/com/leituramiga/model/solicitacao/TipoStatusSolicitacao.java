package br.com.leituramiga.model.solicitacao;
public enum TipoStatusSolicitacao {

    EM_ANDAMENTO(1, "Aberta"),
    PENDENTE(2, "Pendente"),
    FINALIZADA(3, "Finalizada"),
    CANCELADA(4, "Cancelada"),
    RECUSADA(5, "Recusada");

    public final int id;
    public final String descricao;

    TipoStatusSolicitacao(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public static TipoStatusSolicitacao obterDeNumero(int numero) {
        for (TipoStatusSolicitacao tipoStatusSolicitacao : TipoStatusSolicitacao.values()) {
            if (tipoStatusSolicitacao.id == numero) {
                return tipoStatusSolicitacao;
            }
        }
        return TipoStatusSolicitacao.EM_ANDAMENTO;
    }
}
