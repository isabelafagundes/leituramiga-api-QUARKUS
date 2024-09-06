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
            "INSERT INTO livro (codigo_livro, titulo, autor, descricao, categoria, estado_fisico, data_ultima_solicitacao) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) "
                    + "ON DUPLICATE KEY UPDATE titulo = ?, autor = ?, editora = ?, ano = ?, isbn = ?, descricao = ?, categoria = ?, estadoFisico = ?, dataPublicacao = ?, datasUltimaPublicacao = ?;";

    public static final String DELETAR_LIVRO =
            "DELETE FROM livro"
                    + "WHERE codigo_livro = ?;";

    public static final String VALIDAR_EXISTENCIA =
            "SELECT COUNT(livro.codigo_livro) FROM livro WHERE numero_livro = ?;";


    public static String OBTER_LIVRO = "SELECT livro_solicitacao.codigo,\n" +
            "       livro_solicitacao.descricao,\n" +
            "       categoria.codigo_categoria,\n" +
            "       categoria.descricao,\n" +
            "       livro.codigo_livro,\n" +
            "       livro.nome,\n" +
            "       livro.descricao,\n" +
            "       livro.estado_fisico,\n" +
            "       livro.ultima_solicitacao,\n" +
            "       livro.email_usuario,\n" +
            "       livro.codigo_ultima_solicitacao,\n" +
            "       livro.codigo_categoria,\n" +
            "       livro.codigo_status_livro,\n" +
            "       usuario.nome,\n" +
            "       instituicao_ensino.nome,\n" +
            "       cidade.nome\n" +
            "FROM livro\n" +
            "         INNER JOIN livro ON usuario.email_usuario = livro.email_usuario\n" +
            "         INNER JOIN categoria ON categoria.codigo_categoria = livro.codigo_categoria\n" +
            "         INNER JOIN instituicao_ensino ON instituicao_ensino.codigo_instituicao = usuario.codigo_instituicao\n" +
            "         INNER JOIN cidade ON cidade.codigo_cidade = livro.codigo_cidade\n" +
            "WHERE livro.codigo_livro = ?;";


    public static String OBTER_LIVROS_PAGINADOS =
            "SELECT livro.codigo_livro,\n" +
                    "       livro.nome,\n" +
                    "       livro.estado_fisico,\n" +
                    "       livro.email_usuario,\n" +
                    "       usuario.nome,\n" +
                    "       instituicao_ensino.nome,\n" +
                    "       cidade.nome,\n" +
                    "       categoria.nome\n" +
                    "FROM livro\n" +
                    "         INNER JOIN usuario ON usuario.email_usuario = livro.email_usuario\n" +
                    "         INNER JOIN instituicao_ensino ON instituicao_ensino.codigo_instituicao = usuario.codigo_instituicao\n" +
                    "         INNER JOIN categoria ON categoria.codigo_categoria = livro.codigo_categoria\n" +
                    "         INNER JOIN cidade ON cidade.codigo_cidade = livro.codigo_cidade\n" +
                    "WHERE FILTROS_LIVRO " +
                    "OFFSET ? LIMIT ?;";

    public static String FILTROS_LIVRO =
            "WHERE livro.nome LIKE '%?%'\n" +
                    "OR livro.descricao LIKE '%?%'\n" +
                    "OR categoria.descricao LIKE '%?%'\n" +
                    "OR instituicao_ensino.nome LIKE '%?%'\n" +
                    "OR usuario.nome LIKE '%?%'\n" +
                    "OR instituicao_ensino.numero = ?\n" +
                    "OR livro.codigocidade = ?;";

}
