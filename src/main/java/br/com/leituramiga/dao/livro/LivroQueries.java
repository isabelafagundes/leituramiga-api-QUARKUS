package br.com.leituramiga.dao.livro;

public class LivroQueries {
    public static final String OBTER_LIVRO_POR_ID =
            "SELECT categoria.codigo_categoria," +
                    "       categoria.descricao as nome_categoria," +
                    "       endereco.codigo_cidade," +
                    "       livro.codigo_livro," +
                    "       livro.nome," +
                    "       livro.descricao," +
                    "       livro.estado_fisico," +
                    "       livro.ultima_solicitacao," +
                    "       livro.email_usuario," +
                    "       livro.codigo_ultima_solicitacao," +
                    "       livro.codigo_categoria," +
                    "       livro.codigo_status_livro," +
                    "       usuario.nome as nome_usuario," +
                    "       instituicao.nome as nome_instituicao," +
                    "       livro.autor," +
                    "       livro.tipo_solicitacao," +
                    "       cidade.nome as nome_cidade " +
                    "FROM livro " +
                    "         LEFT JOIN usuario ON usuario.email_usuario = livro.email_usuario " +
                    "         LEFT JOIN categoria ON categoria.codigo_categoria = livro.codigo_categoria " +
                    "         LEFT JOIN instituicao ON instituicao.codigo_instituicao = usuario.codigo_instituicao " +
                    "         LEFT JOIN endereco ON endereco.email_usuario = usuario.email_usuario AND endereco.endereco_principal = 1" +
                    "         LEFT JOIN cidade ON cidade.codigo_cidade = endereco.codigo_cidade " +
                    " WHERE livro.codigo_livro = ?;";

    public static final String OBTER_LIVRO_POR_USUARIO =
            "SELECT livro.codigo_livro," +
                    "       livro.nome," +
                    "       livro.estado_fisico," +
                    "       livro.email_usuario," +
                    "       usuario.nome," +
                    "       instituicao_ensino.nome," +
                    "       cidade.nome," +
                    "       NULL as tipo_solicitacao," +
                    "       categoria.nome" +
                    "FROM livro" +
                    "         LEFT JOIN usuario ON usuario.email_usuario = livro.email_usuario" +
                    "         LEFT JOIN instituicao_ensino ON instituicao_ensino.codigo_instituicao = usuario.codigo_instituicao" +
                    "         LEFT JOIN categoria ON categoria.codigo_categoria = livro.codigo_categoria" +
                    "         LEFT JOIN cidade ON cidade.codigo_cidade = livro.codigo_cidade" +
                    "WHERE usuario.email_usuario = ?;";

    public static final String VALIDAR_STATUS_DISPONIVEL = "SELECT COUNT(1) FROM livro " +
            "WHERE livro.codigo_livro = ? AND livro.codigo_status_livro = 1;";

    public static final String VALIDAR_STATUS_EMPRESTADO = "SELECT COUNT(1) FROM livro " +
            "WHERE livro.codigo_livro = ? AND livro.codigo_status_livro = 2;";

    public static final String VALIDAR_STATUS_INDISPONIVEL = "SELECT COUNT(1) FROM livro " +
            "WHERE livro.codigo_livro = ? AND livro.codigo_status_livro = 3;";

    public static final String VALIDAR_STATUS_DESATIVADO = "SELECT COUNT(1) FROM livro " +
            "WHERE livro.codigo_livro = ? AND livro.codigo_status_livro = 4;";

    public static final String ATUALIZAR_LIVROS_INDISPONIVEIS = "UPDATE livro SET" +
            " codigo_status_livro = 3, ultima_solicitacao = ? WHERE livro.codigo_livro IN(CODIGOS_LIVROS);";

    public static final String ATUALIZAR_LIVROS_DISPONIVEIS = "UPDATE livro SET" +
            " codigo_status_livro = 1, ultima_solicitacao = NULL WHERE livro.codigo_livro IN(CODIGOS_LIVROS);";

    public static final String SALVAR_LIVRO =
            "INSERT INTO livro (nome, descricao, estado_fisico, email_usuario, codigo_categoria, codigo_status_livro, autor, tipo_solicitacao) " +
                    "VALUES (?, ?, ?, ?, ?, 1, ?, ?);";

    public static final String ATUALIZAR_LIVRO =
            "UPDATE livro SET nome = ?, descricao = ?, estado_fisico = ?, codigo_categoria = ?, autor = ?, tipo_solicitacao = ? " +
                    "WHERE codigo_livro = ? AND email_usuario = ?;";

    public static final String DELETAR_LIVRO =
            "UPDATE livro SET codigo_status_livro = 4 WHERE livro.codigo_livro = ? AND livro.email_usuario = ?;";

    public static final String VALIDAR_EXISTENCIA =
            "SELECT COUNT(livro.codigo_livro) FROM livro WHERE numero_livro = ?;";

    public static String OBTER_LIVROS_PAGINADOS =
            "SELECT livro.codigo_livro,\n" +
                    "       livro.nome,\n" +
                    "       livro.estado_fisico,\n" +
                    "       livro.email_usuario,\n" +
                    "       usuario.nome        AS nome_usuario,\n" +
                    "       instituicao.nome    AS nome_instituicao,\n" +
                    "       cidade.nome         AS nome_cidade,\n" +
                    "       cidade.codigo_cidade,\n" +
                    "       livro.autor,\n" +
                    "       livro.descricao,\n" +
                    "       NULL                AS ultima_solicitacao,\n" +
                    "       NULL                AS codigo_ultima_solicitacao,\n" +
                    "       NULL                AS tipo_solicitacao,\n" +
                    "       livro.descricao,\n" +
                    "       categoria.codigo_categoria,\n" +
                    "       livro.codigo_status_livro,\n" +
                    "       livro.descricao,\n" +
                    "       categoria.descricao AS nome_categoria\n" +
                    "FROM livro\n" +
                    "LEFT JOIN usuario ON usuario.email_usuario = livro.email_usuario\n" +
                    "LEFT JOIN instituicao ON instituicao.codigo_instituicao = usuario.codigo_instituicao\n" +
                    "LEFT JOIN categoria ON categoria.codigo_categoria = livro.codigo_categoria\n" +
                    "LEFT JOIN endereco ON endereco.email_usuario = usuario.email_usuario AND endereco.endereco_principal = 1\n" +
                    "LEFT JOIN cidade ON cidade.codigo_cidade = endereco.codigo_cidade\n" +
                    "WHERE livro.codigo_status_livro = 1\n" +
                    "  AND (\n" +
                    "      (livro.nome LIKE PESQUISA OR usuario.nome LIKE PESQUISA OR livro.autor LIKE PESQUISA)\n" +
                    "      AND (instituicao.codigo_instituicao = ? OR ? IS NULL)\n" +
                    "      AND (cidade.codigo_cidade = ? OR ? IS NULL)\n" +
                    "      AND (categoria.codigo_categoria = ? OR ? IS NULL)\n" +
                    "      AND (livro.tipo_solicitacao LIKE TIPO_SOLICITACAO OR ? IS NULL)\n" +
                    "      AND (usuario.email_usuario = ? OR ? IS NULL)\n" +
                    "  )\n" +
                    "LIMIT ? OFFSET ?;\n";

    public static String FILTROS_LIVRO =
            "WHERE livro.codigo_status_livro = 1 AND (livro.nome LIKE PESQUISA\n" +
                    "OR livro.descricao LIKE PESQUISA\n" +
                    "OR categoria.descricao LIKE PESQUISA\n" +
                    "OR instituicao.nome LIKE PESQUISA\n" +
                    "OR usuario.nome LIKE PESQUISA\n" +
                    "OR instituicao.codigo_instituicao = ?\n" +
                    "OR categoria.codigo_categoria = ?\n" +
                    "OR cidade.codigo_cidade = ?\n" +
                    "OR livro.tipo_solicitacao LIKE TIPO_SOLICITACAO)";

    public static String FILTRO_EMAIL_USUARIO = " AND usuario.email_usuario = EMAIL_USUARIO";

    public static String SALVAR_IMAGEM = "INSERT INTO imagem_livro (imagem, codigo_livro) " +
            "VALUES (?, ?)";

    public static String LIVRO_EXISTE = "SELECT COUNT(1) FROM livro WHERE livro.codigo_livro = ?;";

}
