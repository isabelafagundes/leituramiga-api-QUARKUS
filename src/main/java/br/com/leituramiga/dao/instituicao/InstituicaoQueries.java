package br.com.leituramiga.dao.instituicao;


public class InstituicaoQueries {

    public final static String OBTER_INSTITUICAO_POR_ESTADO =
            "SELECT instituicao.codigo_instituicao,\n" +
                    "    instituicao.nome,\n" +
                    "    instituicao.sigla\n" +
                    "FROM instituicao\n" +
                    "ORDER BY instituicao.codigo_instituicao = ? ASC;";

    public final static String OBTER_TODAS_INSTITUICOES =
            "SELECT instituicao.codigo_instituicao, " +
                    "    instituicao.nome, " +
                    "    instituicao.sigla  " +
                    "FROM instituicao " +
                    "ORDER BY instituicao.nome ASC;";

    public final static String OBTER_TODAS_INSTITUICOES_COM_PESQUISA =
            "SELECT instituicao.codigo_instituicao,\n " +
                    "    instituicao.nome,\n " +
                    "    instituicao.sigla\n " +
                    "FROM instituicao\n " +
                    "WHERE LOWER(instituicao.nome) LIKE PESQUISA\n " +
                    "ORDER BY instituicao.nome ASC;";

    public static final String VALIDAR_EXISTENCIA_INSTITUICAO =
            "SELECT COUNT(instituicao.codigo_instituicao) FROM instituicao WHERE instituicao.codigo_instituicao = ?;";

    public final static String SALVAR_INSTITUICAO =
            "INSERT INTO instituicao (codigo_instituicao, nome, sigla) \n" +
                    "VALUES (?,?,?)\n" +
                    "ON DUPLICATE KEY UPDATE \n" +
                    "nome = VALUES(nome),\n" +
                    "sigla = VALUES(sigla);";

    public final static String EXCLUIR_INSTITUICAO =
            "DELETE FROM instituicao WHERE codigo_instituicao = ?";

}
