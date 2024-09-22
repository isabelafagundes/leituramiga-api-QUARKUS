package br.com.isabela.dao.endereco;

public class EnderecoQueries {

    public static final String OBTER_ENDERECO_POR_ID =
            "SELECT * FROM endereco "
                    + "WHERE codigo_endereco = ?;";

    public static final String OBTER_ENDERECO_POR_USUARIO =
            "SELECT * FROM endereco "
                    + "WHERE email_usuario = ? AND endereco_principal = 1;";

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


    public static final String EXCLUIR_ENDERECO =
            "DELETE FROM endereco WHERE codigo_endereco = ?;";

    public static final String EXCLUIR_ENDERECO_POR_USUARIO =
            "DELETE FROM endereco WHERE email_usuario = ?;";

    public static final String VALIDAR_EXISTENCIA =
            "SELECT COUNT(endereco.codigo_endereco) FROM endereco WHERE endereco.email_usuario = ? AND endereco_principal = 1;";
}
