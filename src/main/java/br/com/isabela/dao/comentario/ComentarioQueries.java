package br.com.isabela.dao.comentario;

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
                    " WHERE comentario.codigo_comentario =? \n" + //Quando o usuario fez o comentario em outro perfil
                    " OR comentario.email_usuario_perfil = ?;";  //pensei no sentido de exibir também os comentarios que foi postado para esse perfil

}
