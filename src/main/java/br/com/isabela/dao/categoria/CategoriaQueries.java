package br.com.isabela.dao.categoria;

public class CategoriaQueries {

    public final static String OBTER_CATEGORIA_POR_ID =
            "SELECT categoria.codigo_categoria,\n" +
                    "       categoria.descricao\n" +
                    "FROM categoria\n" +
                    "WHERE categoria.codigo_categoria =?;" +
                    "ORDER BY categoria.codigo_categoria ASC;";
}
