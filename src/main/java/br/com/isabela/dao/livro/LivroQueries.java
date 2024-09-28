package br.com.isabela.dao.livro;

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
                    "       cidade.nome as nome_cidade " +
                    "FROM livro " +
                    "         INNER JOIN usuario ON usuario.email_usuario = livro.email_usuario " +
                    "         INNER JOIN categoria ON categoria.codigo_categoria = livro.codigo_categoria " +
                    "         INNER JOIN instituicao ON instituicao.codigo_instituicao = usuario.codigo_instituicao " +
                    "         INNER JOIN endereco ON endereco.email_usuario = usuario.email_usuario " +
                    "         INNER JOIN cidade ON cidade.codigo_cidade = endereco.codigo_cidade " +
                    " WHERE livro.codigo_livro = ?;";

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
            "INSERT INTO livro (nome, descricao, estado_fisico, email_usuario, codigo_categoria, codigo_status_livro, autor) " +
                    "VALUES (?, ?, ?, ?, ?, 1, ?);";

    public static final String ATUALIZAR_LIVRO =
            "UPDATE livro SET nome = ?, descricao = ?, estado_fisico = ?, codigo_categoria = ?, autor = ? " +
                    "WHERE codigo_livro = ? AND email_usuario = ?;";

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
