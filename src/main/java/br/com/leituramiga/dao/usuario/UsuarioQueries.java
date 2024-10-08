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
                    "null as codigo_alteracao, " +
                    "usuario.tipo_usuario, " +
                    "usuario.celular, " +
                    "usuario.descricao, " +
                    "usuario.imagem, " +
                    "usuario.ativo " +
                    "FROM usuario " +
                    "INNER JOIN endereco ON usuario.email_usuario = endereco.email_usuario " +
                    "INNER JOIN cidade ON endereco.codigo_cidade = cidade.codigo_cidade " +
                    "INNER JOIN instituicao ON usuario.codigo_instituicao = instituicao.codigo_instituicao " +
                    "WHERE (usuario.ativo = 1 AND usuario.bloqueado = 0) " +
                    "AND usuario.email_usuario = ? " +
                    "OR usuario.username = ?; ";

    public static final String VERIFICAR_EXISTENCIA =
            "SELECT COUNT(usuario.email_usuario) FROM usuario "
                    + "WHERE email_usuario = ?"
                    + "OR username = ?;";

    public static final String SALVAR_USUARIO =
            "INSERT INTO usuario (nome, username, email_usuario, tipo_usuario, senha, celular, descricao, imagem, codigo_instituicao, ativo)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, 0) " +
                    "ON DUPLICATE KEY UPDATE " +
                    "    nome = VALUES(nome)," +
                    "    username = VALUES(username)," +
                    "    tipo_usuario = VALUES(tipo_usuario)," +
                    "    senha = VALUES(senha)," +
                    "    celular = VALUES(celular)," +
                    "    descricao = VALUES(descricao)," +
                    "    imagem = VALUES(imagem)," +
                    "    codigo_instituicao = VALUES(codigo_instituicao);";

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
            "usuario.ativo " +
            "FROM usuario " +
            "LEFT JOIN endereco ON usuario.email_usuario = endereco.email_usuario " +
            "LEFT JOIN cidade ON endereco.codigo_cidade = cidade.codigo_cidade " +
            "LEFT JOIN instituicao ON usuario.codigo_instituicao = instituicao.codigo_instituicao " +
            "WHERE usuario.ativo = 1 AND usuario.bloqueado = 0 " +
            "AND (usuario.nome LIKE PESQUISA " +
            "OR usuario.username LIKE PESQUISA " +
            "OR instituicao.codigo_instituicao = ? " +
            "OR endereco.codigo_cidade = ?) " +
            "LIMIT ? OFFSET ?;";

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

}
