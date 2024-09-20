package br.com.isabela.dao.solicitacao;

import br.com.isabela.model.solicitacao.TipoStatusSolicitacao;

public class SolicitacaoQueries {

    public static final String CADASTRAR_SOLICITACAO = "INSERT INTO solicitacao (" +
            "data_criacao, " +
            "hora_criacao, " +
            "data_atualizacao, " +
            "hora_atualizacao, " +
            "data_entrega, " +
            "hora_entrega, " +
            "data_devolucao, " +
            "hora_devolucao, " +
            "data_aceite, " +
            "hora_aceite, " +
            "motivo_recusa, " +
            "informacoes_adicionais, " +
            "codigo_tipo_solicitacao, " +
            "codigo_status_solicitacao, " +
            "email_usuario, " +
            "email_usuario_criador, " +
            "codigo_forma_entrega, " +
            "codigo_endereco, " +
            "codigo_rastreio_correio" +
            ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";


    public static final String ATUALIZAR_SOLICITACAO = "UPDATE solicitacao SET " +
            "data_atualizacao = ?, " +
            "hora_atualizacao = ?, " +
            "data_entrega = ?, " +
            "hora_entrega = ?, " +
            "data_devolucao = ?, " +
            "hora_devolucao = ?, " +
            "data_aceite = ?, " +
            "hora_aceite = ?, " +
            "motivo_recusa = ?, " +
            "informacoes_adicionais = ?, " +
            "codigo_tipo_solicitacao = ?, " +
            "codigo_status_solicitacao = ?, " +
            "email_usuario_criador = ?, " +
            "email_usuario = ?, " +
            "codigo_forma_entrega = ?, " +
            "codigo_endereco = ?, " +
            "codigo_rastreio_correio = ? " +
            "WHERE codigo_solicitacao = ? ";


    public static final String OBTER_SOLICITACAO = "SELECT solicitacao.codigo_solicitacao," +
            "solicitacao.data_criacao," +
            "solicitacao.hora_criacao," +
            "solicitacao.data_atualizacao," +
            "solicitacao.hora_atualizacao," +
            "solicitacao.data_entrega," +
            "solicitacao.hora_entrega," +
            "solicitacao.data_devolucao," +
            "solicitacao.hora_devolucao," +
            "solicitacao.data_aceite," +
            "solicitacao.hora_aceite," +
            "solicitacao.motivo_recusa," +
            "solicitacao.informacoes_adicionais," +
            "solicitacao.codigo_tipo_solicitacao," +
            "solicitacao.codigo_status_solicitacao," +
            "solicitacao.email_usuario," +
            "solicitacao.codigo_forma_entrega," +
            "solicitacao.codigo_endereco," +
            "solicitacao.codigo_rastreio_correio " +
            "endereco.codigo_endereco," +
            "endereco.logradouro," +
            "endereco.complemento," +
            "endereco.bairro," +
            "endereco.cep," +
            "endereco.codigo_cidade," +
            "cidade.nome," +
            "cidade.estado " +
            "FROM solicitacao " +
            "INNER JOIN endereco ON solicitacao.codigo_endereco = endereco.codigo_endereco " +
            "INNER JOIN cidade ON endereco.codigo_cidade = cidade.codigo_cidade " +
            "WHERE solicitacao.codigo_solicitacao = ? ";

    public static final String OBTER_SOLICITACOES_PENDENTE =
            "SELECT usuario.nome_usuario," +
                    "FROM usuario INNER JOIN solicitacao ON solicitacao.email_usuario_solicitante = usuario.email_usuario " +
                    "WHERE solicitacao.codigo_status_solicitacao = 2 " +
                    "AND solicitacao.codigo_solicitacao = ?";

    public static final String OBTER_SOLICITACOES_ANDAMENTO_PAGINADAS =
            "SELECT solicitacao.codigo_solicitacao," +
                    "solicitacao.data_entrega," +
                    "solicitacao.hora_entrega," +
                    "solicitacao.data_devolucao," +
                    "solicitacao.hora_devolucao," +
                    "solicitacao.informacoes_adicionais," +
                    "solicitacao.codigo_tipo_solicitacao," +
                    "solicitacao.codigo_status_solicitacao," +
                    "solicitacao.email_usuario," +
                    "solicitacao.codigo_forma_entrega," +
                    "solicitacao.codigo_endereco," +
                    "solicitacao.codigo_rastreio_correio " +
                    "FROM solicitacao WHERE solicitacao.codigo_status_solicitacao = 2 " +
                    "ORDER BY solicitacao.data_entrega DESC, solicitacao.hora_entrega DESC " +
                    "WHERE solicitacao.email_usuario_solicitante = ? OR solicitacao.email_usuario_receptor = ?" +
                    "AND solicitacao.data_entrega <= ?  AND solicitacao.hora_entrega <= ?" +
                    "LIMIT ? OFFSET ? ";

    public static final String RECUSAR_SOLICITACAO = "UPDATE solicitacao SET " +
            "data_atualizacao = ?, " +
            "hora_atualizacao = ?, " +
            "motivo_recusa = ?, " +
            "codigo_status_solicitacao = " + TipoStatusSolicitacao.RECUSADA.id +
            ", WHERE codigo_solicitacao = ?";

    public static final String ACEITAR_SOLICITACAO = "UPDATE solicitacao SET " +
            "data_atualizacao = ?, " +
            "hora_atualizacao = ?, " +
            "data_aceite = ?, " +
            "hora_aceite = ?, " +
            "codigo_status_solicitacao = " + TipoStatusSolicitacao.EM_ANDAMENTO.id +
            ", WHERE codigo_solicitacao = ?";

    public static final String CANCELAR_SOLICITACAO = "UPDATE solicitacao SET " +
            "data_atualizacao = ?, " +
            "hora_atualizacao = ?, " +
            "codigo_status_solicitacao = ?, " +
            "motivo_recusa = " + TipoStatusSolicitacao.CANCELADA.id +
            ", WHERE codigo_solicitacao = ?";

    public static final String SOLICITACAO_EXISTE = "SELECT " +
            "COUNT(1) FROM solicitacao " +
            "WHERE solicitacao.codigo_solicitacao = ?";

    public static final String SOLICITACAO_EM_ABERTO = "SELECT " +
            "COUNT (1) FROM solicitacao " +
            "WHERE solicitacao.status = 1 " +
            "OR solicitacao.status = 2" +
            "AND solicitacao.codigo_tipo_solicitacao = ?";

    public static final String SOLICITACAO_PENDENTE = "SELECT " +
            "COUNT (1) FROM solicitacao " +
            "WHERE solicitacao.codigo_solicitacao = ? " +
            "AND solicitacao.codigo_status_solicitacao = 2";

    public static final String SOLICITACAO_DENTRO_PRAZO_ENTREGA = "SELECT " +
            "COUNT (1) FROM solicitacao " +
            "WHERE solicitacao.data_entrega >= ? " +
            "AND solicitacao.hora_entrega <= ? " +
            "AND solicitacao.numero = ?";

    public static final String SOLICITACAO_DENTRO_PRAZO_DEVOLUCAO = "SELECT " +
            "COUNT (1) FROM solicitacao " +
            "WHERE solicitacao.data_devolucao >= ? " +
            "AND solicitacao.hora_devolucao <= ? " +
            "AND solicitacao.numero = ?";


    public static final String CADASTRAR_LIVRO_SOLICITACAO = "INSERT INTO livro_solicitacao (" +
            "codigo_solicitacao, " +
            "codigo_livro, " +
            "codigo_status_livro_solicitacao " +
            ") VALUES (?,?,?) ";

    public static final String OBTER_LIVROS_SOLICITACAO = "SELECT " +
            "livro_solicitacao.codigo_livro, " +
            "livro_solicitacao.codigo_status_livro_solicitacao " +
            "FROM livro_solicitacao " +
            "WHERE livro_solicitacao.codigo_solicitacao = ? " +
            "AND livro_solicitacao.email_usuario = ?";


}
