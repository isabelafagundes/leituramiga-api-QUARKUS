package br.com.isabela.dao.usuario;

public class UsuarioQueries {

    public static final String OBTER_USUARIO_POR_EMAIL =
            "SELECT * FROM usuario "
                    + "WHERE email = ? "
                    + "OR username = ?;";

    public static final String VERIFICAR_EXISTENCIA =
            "SELECT COUNT(usuario.email) FROM usuario "
                    + "WHERE email = ?"
                    + "OR username = ?;";

    public static final String SALVAR_USUARIO =
            "INSERT INTO usuario (nome, username, email, tipo_usuario, senha) "
                    + "VALUES (?, ?, ?, ?, ?) "
                    + "ON DUPLICATE KEY UPDATE nome = ?, username = ?;";

    public static final String OBTER_SENHA =
            "SELECT usuario.senha FROM usuario WHERE email = ? OR username = ?";

    public static final String ATUALIZAR_TENTATIVAS =
            "UPDATE usuario SET tentativas = ? WHERE email = ?;";

    public static final String SALVAR_CODIGO_ALTERACAO =
            "UPDATE usuario SET codigo_alteracao = ? WHERE email = '';";

    public static final String BLOQUEAR_USUARIO =
            "UPDATE usuario SET bloqueado = ? WHERE email = ?;";
    public static final String EXCLUIR_USUARIO =
            "UPDATE usuario SET ativo = ? WHERE email = ?;";

    public static final String ALTERAR_SENHA =
            "UPDATE usuario SET senha = ?, codigo_alteracao = null WHERE email = ?;";

    public static final String ALTERAR_EMAIL =
            "UPDATE usuario SET email = ? WHERE email = ?;";

    public static final String VERIFICAR_USERNAME =
            "SELECT COUNT(usuario.username) FROM usuario WHERE usuario.username = ?;";

}
