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
            "usuario.nome as nome_usuario_solicitante," +
            "e1.codigo_endereco as codigo_endereco_solicitante," +
            "e1.logradouro as logradouro_solicitante," +
            "e1.complemento as complemento_solicitante," +
            "e1.bairro as bairro_solicitante," +
            "e1.cep as cep_solicitante," +
            "e1.endereco_principal as endereco_principal_solicitante," +
            "e1.numero as numero_solicitante," +
            "e1.codigo_cidade as codigo_cidade_solicitante," +
            "e1.email_usuario as email_usuario_solicitante," +
            "e2.codigo_endereco as codigo_endereco_receptor," +
            "e2.logradouro as logradouro_receptor," +
            "e2.complemento as complemento_receptor," +
            "e2.bairro as bairro_receptor," +
            "e2.cep as cep_receptor," +
            "e2.endereco_principal as endereco_principal_receptor," +
            "e2.numero as numero_receptor," +
            "e2.codigo_cidade as codigo_cidade_receptor," +
            "e2.email_usuario as email_usuario_receptor," +
            "c1.nome as nome_cidade_solicitante," +
            "c1.estado as estado_solicitante, " +
            "c2.nome as nome_cidade_receptor," +
            "c2.estado as estado_receptor " +
            "FROM solicitacao " +
            "LEFT JOIN solicitacao_endereco se1 ON solicitacao.codigo_solicitacao = se1.codigo_solicitacao AND se1.email_usuario = solicitacao.email_usuario_solicitante " +
            "LEFT JOIN solicitacao_endereco se2 ON solicitacao.codigo_solicitacao = se2.codigo_solicitacao AND se2.email_usuario = solicitacao.email_usuario_receptor " +
            "LEFT JOIN endereco e1 ON e1.email_usuario = se1.email_usuario AND e1.codigo_endereco = se1.codigo_endereco " +
            "LEFT JOIN endereco e2 ON e2.email_usuario = se2.email_usuario AND e2.codigo_endereco = se2.codigo_endereco " +
            "LEFT JOIN cidade c1 ON e1.codigo_cidade = c1.codigo_cidade " +
            "LEFT JOIN cidade c2 ON e2.codigo_cidade = c2.codigo_cidade " +
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
                    "e1.codigo_endereco as codigo_endereco_solicitante," +
                    "e1.logradouro as logradouro_solicitante," +
                    "e1.complemento as complemento_solicitante," +
                    "e1.bairro as bairro_solicitante," +
                    "e1.cep as cep_solicitante," +
                    "e1.endereco_principal as endereco_principal_solicitante," +
                    "e1.numero as numero_solicitante," +
                    "e1.codigo_cidade as codigo_cidade_solicitante," +
                    "e1.email_usuario as email_usuario_solicitante," +
                    "e2.codigo_endereco as codigo_endereco_receptor," +
                    "e2.logradouro as logradouro_receptor," +
                    "e2.complemento as complemento_receptor," +
                    "e2.bairro as bairro_receptor," +
                    "e2.cep as cep_receptor," +
                    "e2.endereco_principal as endereco_principal_receptor," +
                    "e2.numero as numero_receptor," +
                    "e2.codigo_cidade as codigo_cidade_receptor," +
                    "e2.email_usuario as email_usuario_receptor," +
                    "c1.nome as nome_cidade_solicitante," +
                    "c1.estado as estado_solicitante, " +
                    "c2.nome as nome_cidade_receptor," +
                    "c2.estado as estado_receptor, " +
                    "solicitacao.codigo_rastreio_correio, " +
                    "usuario.nome as nome_usuario_solicitante " +
                    "FROM solicitacao " +
                    "LEFT JOIN solicitacao_endereco se1 ON solicitacao.codigo_solicitacao = se1.codigo_solicitacao AND se1.email_usuario = solicitacao.email_usuario_solicitante " +
                    "LEFT JOIN solicitacao_endereco se2 ON solicitacao.codigo_solicitacao = se2.codigo_solicitacao AND se2.email_usuario = solicitacao.email_usuario_receptor " +
                    "LEFT JOIN endereco e1 ON e1.email_usuario = se1.email_usuario AND e1.codigo_endereco = se1.codigo_endereco " +
                    "LEFT JOIN endereco e2 ON e2.email_usuario = se2.email_usuario AND e2.codigo_endereco = se2.codigo_endereco " +
                    "LEFT JOIN cidade c1 ON e1.codigo_cidade = c1.codigo_cidade " +
                    "LEFT JOIN cidade c2 ON e2.codigo_cidade = c2.codigo_cidade " +
                    "LEFT JOIN usuario ON solicitacao.email_usuario_solicitante = usuario.email_usuario " +
                    "WHERE solicitacao.codigo_status_solicitacao = 1 " +
                    "AND (solicitacao.email_usuario_solicitante = ? OR solicitacao.email_usuario_receptor = ?) " +
                    "ORDER BY solicitacao.data_entrega ASC, solicitacao.hora_entrega ASC " +
                    "LIMIT ? OFFSET ? ";

    public static final String RECUSAR_SOLICITACOES_COM_LIVRO = "UPDATE solicitacao s\n" +
            "JOIN livro_solicitacao ls\n" +
            "  ON ls.codigo_solicitacao = s.codigo_solicitacao\n" +
            "SET s.codigo_status_solicitacao = 5\n" +
            "WHERE s.codigo_solicitacao != ?\n" +
            "  AND s.codigo_status_solicitacao = 2\n" +
            "  AND ls.codigo_livro IN (CODIGOS_LIVROS);\n  ";

    public static final String OBTER_HISTORICO_SOLICITACOES_PAGINADAS =
            "SELECT \n" +
                    "    solicitacao.codigo_solicitacao,\n" +
                    "    solicitacao.data_entrega,\n" +
                    "    solicitacao.hora_entrega,\n" +
                    "    solicitacao.data_devolucao,\n" +
                    "    solicitacao.hora_devolucao,\n" +
                    "    solicitacao.informacoes_adicionais,\n" +
                    "    solicitacao.codigo_tipo_solicitacao,\n" +
                    "    solicitacao.codigo_status_solicitacao,\n" +
                    "    solicitacao.email_usuario_solicitante,\n" +
                    "    solicitacao.email_usuario_receptor,\n" +
                    "    solicitacao.codigo_forma_entrega,\n" +
                    "    solicitacao.data_criacao,\n" +
                    "    solicitacao.hora_criacao,\n" +
                    "    null AS data_atualizacao,\n" +
                    "    null AS hora_atualizacao,\n" +
                    "    null AS data_aceite,\n" +
                    "    null AS hora_aceite,\n" +
                    "    null AS motivo_recusa,\n" +
                    "    e1.codigo_endereco AS codigo_endereco_solicitante,\n" +
                    "    e1.logradouro AS logradouro_solicitante,\n" +
                    "    e1.complemento AS complemento_solicitante,\n" +
                    "    e1.bairro AS bairro_solicitante,\n" +
                    "    e1.cep AS cep_solicitante,\n" +
                    "    e1.endereco_principal AS endereco_principal_solicitante,\n" +
                    "    e1.numero AS numero_solicitante,\n" +
                    "    e1.codigo_cidade AS codigo_cidade_solicitante,\n" +
                    "    e1.email_usuario AS email_usuario_solicitante,\n" +
                    "    e2.codigo_endereco AS codigo_endereco_receptor,\n" +
                    "    e2.logradouro AS logradouro_receptor,\n" +
                    "    e2.complemento AS complemento_receptor,\n" +
                    "    e2.bairro AS bairro_receptor,\n" +
                    "    e2.cep AS cep_receptor,\n" +
                    "    e2.endereco_principal AS endereco_principal_receptor,\n" +
                    "    e2.numero AS numero_receptor,\n" +
                    "    e2.codigo_cidade AS codigo_cidade_receptor,\n" +
                    "    e2.email_usuario AS email_usuario_receptor,\n" +
                    "    c1.nome AS nome_cidade_solicitante,\n" +
                    "    c1.estado AS estado_solicitante,\n" +
                    "    c2.nome AS nome_cidade_receptor,\n" +
                    "    c2.estado AS estado_receptor,\n" +
                    "    u1.nome AS nome_usuario_solicitante,\n" +
                    "    solicitacao.codigo_rastreio_correio \n" +
                    "FROM \n" +
                    "    solicitacao\n" +
                    "LEFT JOIN solicitacao_endereco se1 ON solicitacao.codigo_solicitacao = se1.codigo_solicitacao AND se1.email_usuario = solicitacao.email_usuario_solicitante \n" +
                    "LEFT JOIN solicitacao_endereco se2 ON solicitacao.codigo_solicitacao = se2.codigo_solicitacao AND se2.email_usuario = solicitacao.email_usuario_receptor \n" +
                    "LEFT JOIN endereco e1 ON e1.email_usuario = se1.email_usuario AND e1.codigo_endereco = se1.codigo_endereco \n" +
                    "LEFT JOIN endereco e2 ON e2.email_usuario = se2.email_usuario AND e2.codigo_endereco = se2.codigo_endereco \n" +
                    "LEFT JOIN cidade c1 ON e1.codigo_cidade = c1.codigo_cidade \n" +
                    "LEFT JOIN cidade c2 ON e2.codigo_cidade = c2.codigo_cidade \n" +
                    "LEFT JOIN usuario u1 ON solicitacao.email_usuario_solicitante = u1.email_usuario \n" +
                    "LEFT JOIN usuario u2 ON solicitacao.email_usuario_receptor = u2.email_usuario \n" +
                    "WHERE \n" +
                    "    solicitacao.codigo_status_solicitacao IN (3, 4, 5) \n" +
                    "    AND (solicitacao.email_usuario_solicitante = ? OR solicitacao.email_usuario_receptor = ?) \n" +
                    "    AND u1.ativo = 1 \n" +
                    "    AND u2.ativo = 1\n" +
                    "ORDER BY \n" +
                    "    solicitacao.data_criacao ASC, \n" +
                    "    solicitacao.hora_criacao ASC \n" +
                    "LIMIT ? OFFSET ?;\n";

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
            "AND solicitacao.codigo_solicitacao = ? ) " +
            "OR solicitacao.data_entrega IS NULL";

    public static final String SOLICITACAO_DENTRO_PRAZO_DEVOLUCAO = "SELECT " +
            "COUNT(1) FROM solicitacao " +
            "WHERE solicitacao.data_devolucao >= ? " +
            "AND solicitacao.hora_devolucao <= ? " +
            "AND solicitacao.codigo_solicitacao = ?";

    public static final String OBTER_LIVROS_SOLICITACAO_EMAIL = "SELECT " +
            "livro_solicitacao.codigo_livro, " +
            "livro.nome, " +
            "livro.autor, " +
            "livro_solicitacao.email_usuario, " +
            "livro_solicitacao.codigo_livro_solicitacao " +
            "FROM livro_solicitacao " +
            "INNER JOIN livro ON livro.codigo_livro = livro_solicitacao.codigo_livro " +
            "WHERE livro_solicitacao.codigo_solicitacao = ? " +
            "AND livro_solicitacao.email_usuario = ?";

    public static final String OBTER_LIVROS_SOLICITACAO = "SELECT " +
            "livro_solicitacao.codigo_livro, " +
            "livro.nome, " +
            "livro.autor, " +
            "livro_solicitacao.email_usuario, " +
            "livro_solicitacao.codigo_livro_solicitacao " +
            "FROM livro_solicitacao " +
            "INNER JOIN livro ON livro.codigo_livro = livro_solicitacao.codigo_livro " +
            "WHERE livro_solicitacao.codigo_solicitacao = ? ";


    public static final String OBTER_NOTIFICACOES_SOLICITACAO = "SELECT " +
            "solicitacao.email_usuario_solicitante," +
            "solicitacao.codigo_solicitacao," +
            "usuario.imagem, " +
            "usuario.nome " +
            "FROM solicitacao " +
            "INNER JOIN usuario ON solicitacao.email_usuario_solicitante = usuario.email_usuario " +
            "WHERE solicitacao.codigo_status_solicitacao = 2 " +
            "AND solicitacao.email_usuario_receptor = ?";

    public static final String USUARIO_POSSUI_SOLICITACAO_ABERTA = "SELECT " +
            "COUNT(1) FROM solicitacao " +
            "WHERE (solicitacao.email_usuario_solicitante = ? OR solicitacao.email_usuario_receptor = ?) " +
            "AND solicitacao.codigo_status_solicitacao = 1";

}
