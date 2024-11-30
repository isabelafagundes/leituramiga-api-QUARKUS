package br.com.leituramiga.dao.comentario;

public class ComentarioQueries {


    public final static String OBTER_COMENTARIO_POR_PERFIL =
            "SELECT comentario.codigo_comentario,\n" +
                    "          comentario.descricao,\n" +
                    "          comentario.data_criacao,\n" +
                    "          comentario.hora_criacao,\n" +
                    "          usuario.nome as nome_usuario_criador,\n" +
                    "          usuario.imagem as imagem,\n" +
                    "          comentario.email_usuario_criador,\n" +
                    "          comentario.email_usuario_perfil\n" +
                    "FROM comentario\n" +
                    "INNER JOIN usuario ON usuario.email_usuario = comentario.email_usuario_criador\n" +
                    " WHERE comentario.email_usuario_perfil = ?\n" +
                    " ORDER BY comentario.data_criacao ASC";

    public final static String OBTER_COMENTARIOS =
            "SELECT comentario.codigo_comentario,\n" +
                    "          comentario.descricao,\n" +
                    "          comentario.data_criacao,\n" +
                    "          comentario.hora_criacao,\n" +
                    "          usuario.username,\n" +
                    "          comentario.email_usuario_criador,\n" +
                    "          comentario.email_usuario_perfil\n" +
                    "FROM comentario\n" +
                    "INNER JOIN usuario ON usuario.email = comentario.email_usuario_criador\n" +
                    " WHERE comentario.codigo_comentario =? \n" +
                    " OR comentario.email_usuario_perfil = ?;";

    public final static String SALVAR_COMENTARIO =
            "INSERT INTO comentario (descricao, data_criacao, hora_criacao, email_usuario_criador, email_usuario_perfil) VALUES (?,?,?,?,?);";

    public static final String EXCLUIR_COMENTARIO =
            "DELETE FROM comentario WHERE codigo_comentario = ?;";

}
