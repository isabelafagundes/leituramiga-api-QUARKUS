package br.com.leituramiga.dao.usuario;

public class UsuarioQueries {

    public static final String OBTER_USUARIO_POR_EMAIL_E_USUARIO =
            "SELECT usuario.nome, " +
                    "usuario.username, " +
                    "usuario.email_usuario, " +
                    "usuario.imagem, " +
                    "usuario.codigo_instituicao, " +
                    "endereco.codigo_cidade, " +
                    "cidade.nome as nome_cidade, " +
                    "instituicao.nome as nome_instituicao, " +
                    "usuario.codigo_instituicao, " +
                    "usuario.senha, " +
                    "null as tentativas, " +
                    "null as bloqueado, " +
                    "codigo_alteracao, " +
                    "(SELECT COUNT(livro.codigo_livro) FROM livro WHERE livro.email_usuario = usuario.email_usuario AND livro.codigo_status_livro = 1) as quantidade_livros, " +
                    "usuario.tipo_usuario, " +
                    "usuario.celular, " +
                    "usuario.descricao, " +
                    "usuario.imagem, " +
                    "usuario.ativo " +
                    "FROM usuario " +
                    "LEFT JOIN endereco ON usuario.email_usuario = endereco.email_usuario AND endereco.endereco_principal = 1 " +
                    "LEFT JOIN cidade ON endereco.codigo_cidade = cidade.codigo_cidade " +
                    "LEFT JOIN instituicao ON usuario.codigo_instituicao = instituicao.codigo_instituicao " +
                    "WHERE (usuario.ativo = ? AND usuario.bloqueado = 0) " +
                    "AND (usuario.email_usuario = ? " +
                    "OR usuario.username = ?); ";

    public static final String OBTER_USUARIO_POR_EMAIL_USERNAME =
            "SELECT usuario.nome, " +
                    "usuario.username, " +
                    "usuario.email_usuario, " +
                    "usuario.imagem, " +
                    "usuario.codigo_instituicao, " +
                    "endereco.codigo_cidade, " +
                    "cidade.nome as nome_cidade, " +
                    "instituicao.nome as nome_instituicao, " +
                    "usuario.codigo_instituicao, " +
                    "usuario.senha, " +
                    "null as tentativas, " +
                    "null as bloqueado, " +
                    "codigo_alteracao, " +
                    "(SELECT COUNT(livro.codigo_livro) FROM livro WHERE livro.email_usuario = usuario.email_usuario AND livro.codigo_status_livro = 1) as quantidade_livros, " +
                    "usuario.tipo_usuario, " +
                    "usuario.celular, " +
                    "usuario.descricao, " +
                    "usuario.imagem, " +
                    "usuario.ativo " +
                    "FROM usuario " +
                    "LEFT JOIN endereco ON usuario.email_usuario = endereco.email_usuario AND endereco.endereco_principal = 1 " +
                    "LEFT JOIN cidade ON endereco.codigo_cidade = cidade.codigo_cidade " +
                    "LEFT JOIN instituicao ON usuario.codigo_instituicao = instituicao.codigo_instituicao " +
                    "WHERE usuario.email_usuario = ? OR usuario.username = ?; ";

    public static final String VERIFICAR_EXISTENCIA =
            "SELECT COUNT(usuario.email_usuario) FROM usuario "
                    + "WHERE email_usuario = ?"
                    + "OR username = ?;";

    public static final String SALVAR_USUARIO =
            "INSERT INTO usuario (nome, username, email_usuario, tipo_usuario, senha, celular, descricao, codigo_instituicao, ativo, codigo_alteracao)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, 0, NULL) " +
                    "ON DUPLICATE KEY UPDATE " +
                    "    nome = VALUES(nome)," +
                    "    username = VALUES(username)," +
                    "    tipo_usuario = VALUES(tipo_usuario)," +
                    "    senha = VALUES(senha)," +
                    "    celular = VALUES(celular)," +
                    "    descricao = VALUES(descricao)," +
                    "    codigo_instituicao = VALUES(codigo_instituicao);";


    public static final String ATUALIZAR_USUARIO = "UPDATE usuario SET nome = ?, " +
            "celular = ?, " +
            "descricao = ?, " +
            "codigo_instituicao = ? " +
            "WHERE usuario.email_usuario = ?; ";

    public static final String USUARIO_ATIVO = "SELECT ativo FROM usuario WHERE email_usuario = ?;";

    public static final String OBTER_USUARIOS_PAGINADOS = "SELECT " +
            "usuario.nome, " +
            "usuario.username, " +
            "usuario.email_usuario, " +
            "usuario.imagem, " +
            "usuario.codigo_instituicao, " +
            "endereco.codigo_cidade, " +
            "cidade.nome as nome_cidade, " +
            "instituicao.nome as nome_instituicao, " +
            "usuario.codigo_instituicao, " +
            "usuario.descricao, " +
            "(SELECT COUNT(livro.codigo_livro) FROM livro WHERE livro.email_usuario = usuario.email_usuario AND livro.codigo_status_livro = 1) as quantidade_livros, " +
            "usuario.ativo " +
            "FROM usuario " +
            "LEFT JOIN endereco ON usuario.email_usuario = endereco.email_usuario AND endereco.endereco_principal = 1 " +
            "LEFT JOIN cidade ON endereco.codigo_cidade = cidade.codigo_cidade " +
            "LEFT JOIN instituicao ON usuario.codigo_instituicao = instituicao.codigo_instituicao " +
            "WHERE usuario.ativo = 1 AND usuario.bloqueado = 0  " +
            "AND (" +
            "(usuario.nome LIKE PESQUISA OR usuario.username LIKE PESQUISA) " +
            "AND (instituicao.codigo_instituicao = ? OR ? IS NULL)" +
            "AND (endereco.codigo_cidade = ? OR ? IS NULL)" +
            ") " +
            "LIMIT ? OFFSET ?;";

    public static final String OBTER_IDENTIFICADORES_USUARIO =
            "SELECT usuario.email_usuario, usuario.username FROM usuario WHERE email_usuario = ? OR username = ?;";

    public static final String OBTER_SENHA =
            "SELECT usuario.senha FROM usuario WHERE email_usuario = ? OR username = ?";

    public static final String ATUALIZAR_TENTATIVAS =
            "UPDATE usuario SET tentativas = ? WHERE email_usuario = ?;";

    public static final String SALVAR_CODIGO_ALTERACAO =
            "UPDATE usuario SET codigo_alteracao = ? WHERE email_usuario = ?;";

    public static final String BLOQUEAR_USUARIO =
            "UPDATE usuario SET bloqueado = ? WHERE email_usuario = ?;";
    public static final String EXCLUIR_USUARIO =
            "UPDATE usuario SET ativo = ? WHERE email_usuario = ?;";

    public static final String ALTERAR_SENHA =
            "UPDATE usuario SET senha = ?, codigo_alteracao = null WHERE email_usuario = ?;";

    public static final String ATUALIZAR_EMAIL_USUARIO =
            "UPDATE usuario SET email_usuario = ? WHERE email_usuario = ?;";

    public static final String ATIVAR_USUARIO =
            "UPDATE usuario SET ativo = ? WHERE email_usuario = ?;";

    public static final String VERIFICAR_USERNAME =
            "SELECT COUNT(usuario.username) FROM usuario WHERE usuario.username = ?;";

    public static final String USUARIO_POSSUI_IMAGEM =
            "SELECT COUNT(usuario.imagem) FROM usuario WHERE usuario.email_usuario = ?;";

    public static final String ATUALIZAR_IMAGEM_USUARIO =
            "UPDATE usuario SET imagem = ? WHERE email_usuario = ?;";

}
