package br.com.isabela.model.solicitacao;
public enum TipoSolicitacao {

    EMPRESTIMO(1, "Empréstimo"),
    TROCA(2, "Troca"),
    DOACAO(3, "Doação");

    private final int id;
    private final String descricao;

    TipoSolicitacao(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public static TipoSolicitacao obterDeNumero(int numero) {
        for (TipoSolicitacao tipoSolicitacao : TipoSolicitacao.values()) {
            if (tipoSolicitacao.id == numero) {
                return tipoSolicitacao;
            }
        }
        return TipoSolicitacao.EMPRESTIMO;
    }

}
