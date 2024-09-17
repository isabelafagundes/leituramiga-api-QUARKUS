package br.com.isabela.dao.livro;

public class LivroQueries {

    //essa query faz inner join para obter informações de outras tabelas
    //como o nome da cidade e do usuário.
    public static final String OBTER_LIVRO_POR_ID =
            "SELECT livro_solicitacao.codigo,\n" +
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

    public static final String OBTER_LIVRO_POR_TITULO =
            "SELECT codigo_livro, nome " +
                    "FROM livro " +
                    "WHERE nome = ?;";

    public static final String OBTER_LIVRO_POR_CATEGORIA =
            "SELECT livro.codigo_livro,\n" +
                    "    categoria.codigo_categoria,\n" +
                    "    categoria.descricao,\n" +
                    "FROM livro\n" +
                    "        INNER JOIN categoria ON categoria.codigo_categoria = livro.codigo_categoria \n" +
                    "WHERE categoria.descricao = ?;";

    public static final String OBTER_LIVRO_POR_USUARIO =
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
                    "WHERE usuario.email_usuario = ?;";

    public static final String SALVAR_LIVRO =
            "INSERT INTO livro (codigo_livro, nome_usuario, titulo, autor, descricao, categoria, estado_fisico, nome_instituicao, nome_cidade, data_ultima_solicitacao) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) "
                    + "ON DUPLICATE KEY UPDATE nome_usuario = ?, titulo = ?, autor = ?, descricao = ?, categoria = ?, estado_fisico = ?, nome_instituicao = ?, nome_cidade = ?, data_ultima_solicitacao = ?;";

    public static final String DELETAR_LIVRO =
            "DELETE FROM livro"
                    + "WHERE codigo_livro = ?;";

    public static final String VALIDAR_EXISTENCIA =
            "SELECT COUNT(livro.codigo_livro) FROM livro WHERE numero_livro = ?;";

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
                    "OR categoria.numero = ?\n" +
                    "OR livro.codigo_cidade = ?;";

    public static String SALVAR_IMAGEM = "INSERT INTO imagem_livro (imagem, codigo_livro) " +
            "VALUES (?, ?)";

}
