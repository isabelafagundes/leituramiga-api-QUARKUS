package br.com.leituramiga.dao.endereco;

public class EnderecoQueries {

    public static final String OBTER_ENDERECO_POR_ID =
            "SELECT endereco.codigo_endereco, " +
                    "endereco.cep, " +
                    "endereco.logradouro, " +
                    "endereco.numero, " +
                    "endereco.complemento, " +
                    "endereco.bairro, " +
                    "endereco.endereco_principal, " +
                    "endereco.codigo_cidade, " +
                    "cidade.nome as nome_cidade, " +
                    "cidade.estado as nome_estado, " +
                    "cidade.codigo_cidade, " +
                    "endereco.email_usuario " +
                    "FROM endereco " +
                    "INNER JOIN cidade ON cidade.codigo_cidade = endereco.codigo_cidade " +
                    "WHERE codigo_endereco = ? AND endereco.ativo = 1;";

    public static final String OBTER_CIDADES_POR_UF ="SELECT DISTINCT cidade.codigo_cidade, " +
            "cidade.nome, " +
            "cidade.estado " +
            "FROM cidade WHERE cidade.estado = ? " +
            "AND cidade.nome LIKE PESQUISA " +
            "ORDER BY cidade.nome ASC; ";

    public static final String OBTER_ENDERECO_POR_USUARIO =
            "SELECT endereco.codigo_endereco, " +
                    "endereco.cep, " +
                    "endereco.logradouro, " +
                    "endereco.numero, " +
                    "endereco.complemento, " +
                    "endereco.bairro, " +
                    "endereco.endereco_principal, " +
                    "endereco.codigo_cidade, " +
                    "cidade.nome as nome_cidade, " +
                    "cidade.estado as nome_estado, " +
                    "cidade.codigo_cidade, " +
                    "endereco.email_usuario " +
                    "FROM endereco " +
                    "INNER JOIN cidade ON cidade.codigo_cidade = endereco.codigo_cidade " +
                    "WHERE endereco.email_usuario = ? AND endereco.endereco_principal = 1 AND endereco.ativo = 1;";

    public static final String INSERIR_ENDERECO =
            "INSERT INTO endereco (logradouro, complemento, bairro, cep, codigo_cidade, email_usuario, endereco_principal, numero) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?) ";

    public static final String ATUALIZAR_ENDERECO = "UPDATE endereco SET " +
            "logradouro = ?, " +
            "complemento = ?, " +
            "bairro = ?," +
            "cep = ?, " +
            "codigo_cidade = ?, " +
            "endereco_principal = ?, " +
            "numero = ? " +
            "WHERE endereco.codigo_endereco = ?;";

    public static final String SALVAR_ENDERECO =
            "INSERT INTO endereco (logradouro, complemento, bairro, cep, codigo_cidade, email_usuario, endereco_principal, numero) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?) " +
                    "ON DUPLICATE KEY UPDATE " +
                    "logradouro = VALUES(logradouro), " +
                    "complemento = VALUES(complemento), " +
                    "bairro = VALUES(bairro), " +
                    "cep = VALUES(cep), " +
                    "codigo_cidade = VALUES(codigo_cidade), " +
                    "endereco_principal = VALUES(endereco_principal), " +
                    "numero = VALUES(numero);";

    public static final String INSERIR_SOLICITACAO_ENDERECO = "INSERT INTO solicitacao_endereco(codigo_endereco, codigo_solicitacao, email_usuario) " +
            "VALUES (?, ?, ?) ";

    public static final String ATUALIZAR_SOLICITACAO_ENDERECO = "UPDATE solicitacao_endereco SET " +
            "codigo_endereco = ? " +
            "WHERE email_usuario = ? AND codigo_solicitacao = ?;";

    public static final String EXCLUIR_ENDERECO =
            "UPDATE endereco SET ativo = 0, endereco_principal = 0 WHERE codigo_endereco = ?;";

    public static final String VALIDAR_EXISTENCIA_ENDERECO_EMAIL =
            "SELECT COUNT(endereco.codigo_endereco) FROM endereco WHERE endereco.email_usuario = ? AND endereco_principal = 1;";

    public static final String VALIDAR_EXISTENCIA_ENDERECO_ID =
            "SELECT COUNT(endereco.codigo_endereco) FROM endereco WHERE endereco.codigo_endereco = ?;";
}
