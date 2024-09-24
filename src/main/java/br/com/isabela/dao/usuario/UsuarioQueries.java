package br.com.isabela.dao.usuario;

public class UsuarioQueries {

    public static final String OBTER_USUARIO_POR_EMAIL_E_USUARIO =
            "SELECT * FROM usuario "
                    + "WHERE email_usuario = ? "
                    + "OR username = ?;";

    public static final String VERIFICAR_EXISTENCIA =
            "SELECT COUNT(usuario.email_usuario) FROM usuario "
                    + "WHERE email_usuario = ?"
                    + "OR username = ?;";

    public static final String SALVAR_USUARIO =
            "INSERT INTO usuario (nome, username, email_usuario, tipo_usuario, senha, celular, descricao, imagem, codigo_instituicao)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                    "ON DUPLICATE KEY UPDATE " +
                    "    nome = VALUES(nome)," +
                    "    username = VALUES(username)," +
                    "    tipo_usuario = VALUES(tipo_usuario)," +
                    "    senha = VALUES(senha)," +
                    "    celular = VALUES(celular)," +
                    "    descricao = VALUES(descricao)," +
                    "    imagem = VALUES(imagem)," +
                    "    codigo_instituicao = VALUES(codigo_instituicao);";

    public static final String OBTER_SENHA =
            "SELECT usuario.senha FROM usuario WHERE email_usuario = ? OR username = ?";

    public static final String ATUALIZAR_TENTATIVAS =
            "UPDATE usuario SET tentativas = ? WHERE email_usuario = ?;";

    public static final String SALVAR_CODIGO_ALTERACAO =
            "UPDATE usuario SET codigo_alteracao = ? WHERE email_usuario = '';";

    public static final String BLOQUEAR_USUARIO =
            "UPDATE usuario SET bloqueado = ? WHERE email_usuario = ?;";
    public static final String EXCLUIR_USUARIO =
            "UPDATE usuario SET ativo = ? WHERE email_usuario = ?;";

    public static final String ALTERAR_SENHA =
            "UPDATE usuario SET senha = ?, codigo_alteracao = null WHERE email_usuario = ?;";

    public static final String ATUALIZAR_EMAIL_USUARIO =
            "UPDATE usuario SET email_usuario = ? WHERE email_usuario = ?;";

    public static final String VERIFICAR_USERNAME =
            "SELECT COUNT(usuario.username) FROM usuario WHERE usuario.username = ?;";

}
