package br.com.leituramiga.dao.solicitacao;

import br.com.leituramiga.model.solicitacao.TipoStatusSolicitacao;

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
            "email_usuario_receptor, " +
            "email_usuario_solicitante, " +
            "codigo_forma_entrega, " +
            "codigo_rastreio_correio" +
            ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";


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
            "email_usuario_solicitante = ?, " +
            "email_usuario_receptor = ?, " +
            "codigo_forma_entrega = ?, " +
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
            "solicitacao.email_usuario_solicitante," +
            "solicitacao.email_usuario_receptor," +
            "solicitacao.codigo_forma_entrega," +
            "solicitacao.codigo_rastreio_correio, " +
            "endereco.codigo_endereco," +
            "endereco.logradouro," +
            "endereco.complemento," +
            "endereco.bairro," +
            "endereco.cep," +
            "endereco.numero," +
            "endereco.codigo_cidade," +
            "endereco.endereco_principal," +
            "endereco.email_usuario," +
            "usuario.nome as nome_usuario_solicitante," +
            "cidade.nome as nome_cidade," +
            "cidade.estado " +
            "FROM solicitacao " +
            "INNER JOIN endereco_solicitacao ON solicitacao.codigo_solicitacao = endereco_solicitacao.codigo_solicitacao " +
            "INNER JOIN endereco ON endereco_solicitacao.codigo_endereco = endereco.codigo_endereco " +
            "INNER JOIN cidade ON endereco.codigo_cidade = cidade.codigo_cidade " +
            "INNER JOIN usuario ON solicitacao.email_usuario_solicitante = usuario.email_usuario " +
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
                    "solicitacao.email_usuario_solicitante," +
                    "solicitacao.email_usuario_receptor," +
                    "solicitacao.codigo_forma_entrega," +
                    "null as data_criacao," +
                    "null as hora_criacao," +
                    "null as data_atualizacao," +
                    "null as hora_atualizacao," +
                    "null as data_aceite," +
                    "null as hora_aceite," +
                    "null as motivo_recusa," +
                    "endereco.codigo_endereco," +
                    "endereco.logradouro," +
                    "endereco.complemento," +
                    "endereco.bairro," +
                    "endereco.endereco_principal," +
                    "endereco.cep," +
                    "endereco.numero," +
                    "endereco.codigo_cidade," +
                    "endereco.email_usuario," +
                    "cidade.nome as nome_cidade," +
                    "cidade.estado, " +
                    "solicitacao.codigo_rastreio_correio, " +
                    "usuario.nome as nome_usuario_solicitante " +
                    "FROM solicitacao " +
                    "LEFT JOIN endereco_solicitacao ON solicitacao.codigo_solicitacao = endereco_solicitacao.codigo_solicitacao " +
                    "LEFT JOIN endereco ON endereco_solicitacao.codigo_endereco = endereco.codigo_endereco " +
                    "LEFT JOIN cidade ON endereco.codigo_cidade = cidade.codigo_cidade " +
                    "LEFT JOIN usuario ON solicitacao.email_usuario_solicitante = usuario.email_usuario " +
                    "WHERE solicitacao.codigo_status_solicitacao = 1 " +
                    "AND (solicitacao.email_usuario_solicitante = ? OR solicitacao.email_usuario_receptor = ?) " +
                    "ORDER BY solicitacao.data_entrega ASC, solicitacao.hora_entrega ASC " +
                    "LIMIT ? OFFSET ? ";

    public static final String RECUSAR_SOLICITACOES_COM_LIVRO = "UPDATE solicitacao SET codigo_status_solicitacao = 5 " +
            "WHERE ( " +
            "SELECT livro_solicitacao.codigo_livro FROM livro_solicitacao " +
            "WHERE livro_solicitacao.codigo_livro IN (CODIGOS_LIVROS) LIMIT 1) " +
            "AND solicitacao.codigo_solicitacao != ?; ";

    public static final String OBTER_HISTORICO_SOLICITACOES_PAGINADAS =
            "SELECT solicitacao.codigo_solicitacao," +
                    "solicitacao.data_entrega," +
                    "solicitacao.hora_entrega," +
                    "solicitacao.data_devolucao," +
                    "solicitacao.hora_devolucao," +
                    "solicitacao.informacoes_adicionais," +
                    "solicitacao.codigo_tipo_solicitacao," +
                    "solicitacao.codigo_status_solicitacao," +
                    "solicitacao.email_usuario_solicitante," +
                    "solicitacao.email_usuario_receptor," +
                    "solicitacao.codigo_forma_entrega," +
                    "solicitacao.data_criacao," +
                    "solicitacao.hora_criacao," +
                    "null as data_atualizacao," +
                    "null as hora_atualizacao," +
                    "null as data_aceite," +
                    "null as hora_aceite," +
                    "null as motivo_recusa," +
                    "endereco.codigo_endereco," +
                    "endereco.logradouro," +
                    "endereco.complemento," +
                    "endereco.bairro," +
                    "endereco.cep," +
                    "endereco.endereco_principal," +
                    "endereco.numero," +
                    "endereco.codigo_cidade," +
                    "endereco.email_usuario," +
                    "cidade.nome as nome_cidade," +
                    "cidade.estado, " +
                    "usuario.nome as nome_usuario_solicitante," +
                    "solicitacao.codigo_rastreio_correio " +
                    "FROM solicitacao " +
                    "INNER JOIN endereco_solicitacao ON solicitacao.codigo_solicitacao = endereco_solicitacao.codigo_solicitacao " +
                    "INNER JOIN endereco ON endereco_solicitacao.codigo_endereco = endereco.codigo_endereco " +
                    "INNER JOIN cidade ON endereco.codigo_cidade = cidade.codigo_cidade " +
                    "INNER JOIN usuario ON solicitacao.email_usuario_solicitante = usuario.email_usuario " +
                    "WHERE solicitacao.codigo_status_solicitacao = 3 " +
                    "OR solicitacao.codigo_status_solicitacao = 4 " +
                    "OR solicitacao.codigo_status_solicitacao = 5 " +
                    "AND (solicitacao.email_usuario_solicitante = ? OR solicitacao.email_usuario_receptor = ?) " +
                    "ORDER BY solicitacao.data_criacao ASC, solicitacao.hora_criacao ASC " +
                    "LIMIT ? OFFSET ? ";

    public static final String RECUSAR_SOLICITACAO = "UPDATE solicitacao SET " +
            "data_atualizacao = ?, " +
            "hora_atualizacao = ?, " +
            "motivo_recusa = ?, " +
            "codigo_status_solicitacao = " + TipoStatusSolicitacao.RECUSADA.id +
            " WHERE codigo_solicitacao = ?";

    public static final String ACEITAR_SOLICITACAO = "UPDATE solicitacao SET " +
            "data_atualizacao = ?, " +
            "hora_atualizacao = ?, " +
            "data_aceite = ?, " +
            "hora_aceite = ?, " +
            "codigo_status_solicitacao = " + TipoStatusSolicitacao.EM_ANDAMENTO.id +
            " WHERE codigo_solicitacao = ?";

    public static final String FINALIZAR_SOLICITACAO = "UPDATE solicitacao SET " +
            "data_atualizacao = ?, " +
            "hora_atualizacao = ?, " +
            "codigo_status_solicitacao = " + TipoStatusSolicitacao.FINALIZADA.id +
            " WHERE codigo_solicitacao = ?";

    public static final String CANCELAR_SOLICITACAO = "UPDATE solicitacao SET " +
            "data_atualizacao = ?, " +
            "hora_atualizacao = ?, " +
            "codigo_status_solicitacao = " + TipoStatusSolicitacao.CANCELADA.id +
            ", motivo_recusa = ?" +
            " WHERE codigo_solicitacao = ?";

    public static final String SOLICITACAO_EXISTE = "SELECT " +
            "COUNT(1) FROM solicitacao " +
            "WHERE solicitacao.codigo_solicitacao = ?";

    public static final String SOLICITACAO_EM_ABERTO = "SELECT " +
            "COUNT(1) FROM solicitacao " +
            "WHERE solicitacao.codigo_status_solicitacao = 1 " +
            "OR solicitacao.codigo_status_solicitacao = 2 " +
            "AND solicitacao.codigo_tipo_solicitacao = ?";

    public static final String SOLICITACAO_PENDENTE = "SELECT " +
            "COUNT(1) FROM solicitacao " +
            "WHERE solicitacao.codigo_solicitacao = ? " +
            "AND solicitacao.codigo_status_solicitacao = 2";

    public static final String SOLICITACAO_DENTRO_PRAZO_ENTREGA = "SELECT " +
            "COUNT(1) FROM solicitacao " +
            "WHERE (solicitacao.data_entrega >= ? " +
            "AND solicitacao.hora_entrega <= ? " +
            "AND solicitacao.codigo_solicitacao = ? ) " +
            "OR solicitacao.data_entrega IS NULL";

    public static final String SOLICITACAO_DENTRO_PRAZO_DEVOLUCAO = "SELECT " +
            "COUNT(1) FROM solicitacao " +
            "WHERE solicitacao.data_devolucao >= ? " +
            "AND solicitacao.hora_devolucao <= ? " +
            "AND solicitacao.codigo_solicitacao = ?";


    public static final String CADASTRAR_LIVRO_SOLICITACAO = "INSERT INTO livro_solicitacao (" +
            "codigo_solicitacao, " +
            "codigo_livro, " +
            "codigo_status_solicitacao " +
            ") VALUES ";

    public static final String OBTER_LIVROS_SOLICITACAO = "SELECT " +
            "livro_solicitacao.codigo_livro, " +
            "livro.nome, " +
            "livro.autor, " +
            "livro_solicitacao.email_usuario, " +
            "livro_solicitacao.codigo_livro_solicitacao " +
            "FROM livro_solicitacao " +
            "INNER JOIN livro ON livro.codigo_livro = livro_solicitacao.codigo_livro " +
            "WHERE livro_solicitacao.codigo_solicitacao = ? " +
            "AND livro_solicitacao.email_usuario = ?";


    public static final String OBTER_NOTIFICACOES_SOLICITACAO = "SELECT " +
            "solicitacao.email_usuario_solicitante," +
            "solicitacao.codigo_solicitacao," +
            "usuario.nome " +
            "FROM solicitacao " +
            "INNER JOIN usuario ON solicitacao.email_usuario_solicitante = usuario.email_usuario " +
            "WHERE solicitacao.codigo_status_solicitacao = 2 " +
            "AND solicitacao.email_usuario_receptor = ?";

}
