package br.com.isabela.dao.livro;

public class LivroQueries {

    public static final String OBTER_LIVRO_POR_ID =
            "SELECT * FROM livro "
                    + "WHERE codigo_livro = ?;";

    public static final String OBTER_LIVRO_POR_TITULO =
            "SELECT * FROM livro "
            + "WHERE livro_titulo = ?;";

    public static final String OBTER_LIVRO_POR_CATEGORIA =
            "SELECT * FROM livro "
            + "WHERE livro_categoria = ?;";

    public static final String OBTER_LIVRO_POR_USUARIO =
            "SELECT * FROM livro "
            + "WHERE livro_usuario = ?;";

    public static final String SALVAR_LIVRO =
            "INSERT INTO livro (codigo_livro, titulo, autor, editora, ano, isbn, descricao, categoria, estadoFisico, dataPublicacao, datasUltimaPublicacao) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) "
            + "ON DUPLICATE KEY UPDATE titulo = ?, autor = ?, editora = ?, ano = ?, isbn = ?, descricao = ?, categoria = ?, estadoFisico = ?, dataPublicacao = ?, datasUltimaPublicacao = ?;";

    public static final String DELETAR_LIVRO =
            "DELETE FROM livro"
            + "WHERE codigo_livro = ?;";

    public static final String VALIDAR_EXISTENCIA =
            "SELECT COUNT(livro.codigo_livro) FROM livro WHERE numero_livro = ?;";

}
