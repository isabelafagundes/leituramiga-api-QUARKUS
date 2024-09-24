package br.com.isabela.dao.livro;

public class LivroQueries {
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

    public static final String OBTER_LIVRO_POR_USUARIO =
            "SELECT livro.codigo_livro," +
                    "       livro.nome," +
                    "       livro.estado_fisico," +
                    "       livro.email_usuario," +
                    "       usuario.nome," +
                    "       instituicao_ensino.nome," +
                    "       cidade.nome," +
                    "       categoria.nome" +
                    "FROM livro" +
                    "         INNER JOIN usuario ON usuario.email_usuario = livro.email_usuario" +
                    "         INNER JOIN instituicao_ensino ON instituicao_ensino.codigo_instituicao = usuario.codigo_instituicao" +
                    "         INNER JOIN categoria ON categoria.codigo_categoria = livro.codigo_categoria" +
                    "         INNER JOIN cidade ON cidade.codigo_cidade = livro.codigo_cidade" +
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
            " status = 3, ultima_solicitacao = ? WHERE livro.codigo_livro IN(?);";

    public static final String SALVAR_LIVRO =
            "INSERT INTO livro (codigo_livro, nome_usuario, titulo, autor, descricao, categoria, estado_fisico, nome_instituicao, nome_cidade, data_ultima_solicitacao) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) "
                    + "ON DUPLICATE KEY UPDATE nome_usuario = ?, titulo = ?, autor = ?, descricao = ?, categoria = ?, estado_fisico = ?, nome_instituicao = ?, nome_cidade = ?, data_ultima_solicitacao = ?;";

    public static final String DELETAR_LIVRO =
            "UPDATE livro SET status = 4 WHERE livro.codigo_livro = ? AND livro.email_usuario = ?;";

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
            "WHERE livro.status = 1 AND (livro.nome LIKE '%?%'\n" +
                    "OR livro.descricao LIKE '%?%'\n" +
                    "OR categoria.descricao LIKE '%?%'\n" +
                    "OR instituicao_ensino.nome LIKE '%?%'\n" +
                    "OR usuario.nome LIKE '%?%'\n" +
                    "OR instituicao_ensino.numero = ?\n" +
                    "OR categoria.numero = ?\n" +
                    "OR livro.codigo_cidade = ?);";

    public static String SALVAR_IMAGEM = "INSERT INTO imagem_livro (imagem, codigo_livro) " +
            "VALUES (?, ?)";

    public static String LIVRO_EXISTE = "SELECT COUNT(1) FROM livro WHERE livro.codigo_livro = ?;";
}
