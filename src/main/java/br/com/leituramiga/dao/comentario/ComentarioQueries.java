package br.com.leituramiga.dao.comentario;

public class ComentarioQueries {


    public final static String OBTER_COMENTARIO_POR_PERFIL =
            "SELECT comentario.codigo_comentario,\n" +
                    "          comentario.descricao,\n" +
                    "          comentario.data_criacao,\n" +
                    "          comentario.hora_criacao,\n" +
                    "          comentario.email_usuario,\n" +
                    "          comentario.nome_usuario_perfil,\n" +
                    "FROM comentario\n" +
                    " WHERE comentario.email_usuario = ?\n" +
                    " ORDER BY comentario.data_criacao ASC";

    public final static String OBTER_COMENTARIO_FEITOS_OU_RECEBIDOS =
            "SELECT comentario.codigo_comentario,\n" +
                    "          comentario.descricao,\n" +
                    "          comentario.data_criacao,\n" +
                    "          comentario.hora_criacao,\n" +
                    "          comentario.email_usuario,\n" +
                    "          comentario.nome_usuario_perfil,\n" +
                    "FROM comentario\n" +
                    " WHERE comentario.codigo_comentario =? \n" +
                    " OR comentario.email_usuario_perfil = ?;";

    public final static String SALVAR_COMENTARIO =
            "INSERT INTO comentario (descricao, data_criacao, hora_criacao, email_usuario, nome_usuario_perfil)" +
            "        VALUES (?,?,?,?,?)" +
            " ON DUPLICATE KEY UPDATE " +
            "descricao = VALUES(descricao), "  +
            "data_criacao = VALUES(data_criacao), " +
            "hora_criacao = VALUES (hora_criacao);";

    public static final String EXCLUIR_COMENTARIO =
            "DELETE FROM comentario WHERE codigo_comentario = ?;";

}
