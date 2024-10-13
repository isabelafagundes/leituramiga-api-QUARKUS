package br.com.leituramiga.dao.categoria;

public class CategoriaQueries {

    public final static String OBTER_CATEGORIAS =
            "SELECT categoria.codigo_categoria,\n" +
                    "       categoria.descricao\n" +
                    "FROM categoria\n" +
                    "ORDER BY categoria.descricao ASC;";

    public static final String VALIDAR_EXISTENCIA_CATEGORIA =
            "SELECT COUNT(categoria.codigo_categoria) FROM categoria WHERE categoria.codigo_categoria = ?;";

    public static final String SALVAR_CATEGORIA =
            "INSERT INTO categoria (codigo_categoria, descricao)" +
                    "     VALUES(?,?,?)" +
                    " ON DUPLICATE KEY UPDATE " +
                    "descricao = VALUES(descricao);";

    public static final String EXCLUIR_CATEGORIA =
            "DELETE FROM comentario WHERE codigo_comentario = ?;";

}
