package br.com.isabela.model.livro;
public enum TipoStatusLivro {

    DISPONIVEL(1, "Disponivel"),
    EMPRESTADO(2, "Emprestado"),
    INDISPONIVEL(3, "Indisponivel"),
    DESATIVADO(4, "Desativado");

    private final int id;
    private final String descricao;

    TipoStatusLivro(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public static TipoStatusLivro obterDeNumero(int numero) {
        for (TipoStatusLivro tipoStatusLivro : TipoStatusLivro.values()) {
            if (tipoStatusLivro.id == numero) {
                return tipoStatusLivro;
            }
        }
        return TipoStatusLivro.DISPONIVEL;
    }
}
