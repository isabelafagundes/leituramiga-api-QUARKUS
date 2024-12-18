package br.com.leituramiga.dao.solicitacao;

import br.com.leituramiga.dao.FabricaDeConexoes;
import br.com.leituramiga.dto.livro.LivroSolicitacaoDto;
import br.com.leituramiga.dto.solicitacao.SolicitacaoDto;
import br.com.leituramiga.model.DataHora;
import br.com.leituramiga.model.endereco.Endereco;
import br.com.leituramiga.model.livro.LivroSolicitacao;
import br.com.leituramiga.model.solicitacao.NotificacaoSolicitacao;
import br.com.leituramiga.model.solicitacao.Solicitacao;
import br.com.leituramiga.model.solicitacao.TipoSolicitacao;
import br.com.leituramiga.service.autenticacao.LogService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static io.smallrye.mutiny.vertx.codegen.methods.MutinyMethodGenerator.sanitize;

@ApplicationScoped
public class SolicitacaoDao {

    @Inject
    FabricaDeConexoes bd;

    @Inject
    LogService logService;


    public List<Solicitacao> obterSolicitacaoPendentes() throws SQLException {
        try (Connection conexao = bd.obterConexao()) {
            PreparedStatement ps = conexao.prepareStatement(SolicitacaoQueries.OBTER_SOLICITACOES_PENDENTE);
            ResultSet rs = ps.executeQuery();
            List<Solicitacao> solicitacoes = new ArrayList<>();
            while (rs.next()) solicitacoes.add(obterSolicitacaoDeResult(rs, true));
            return solicitacoes;
        }
    }

    public void recusarSolicitacoesComLivroIndisponivel(List<LivroSolicitacaoDto> livrosIndisponiveis, Integer codigo, Connection conexao) throws SQLException {
            String codigosLivros = concatenarCodigosLivros(livrosIndisponiveis);
            String query = SolicitacaoQueries.RECUSAR_SOLICITACOES_COM_LIVRO.replace("CODIGOS_LIVROS", codigosLivros);
            PreparedStatement ps = conexao.prepareStatement(query);
            ps.setInt(1, codigo);
            ps.executeUpdate();
    }

    public List<Solicitacao> obterHistoricoSolicitacoesPaginadas(Integer pagina, Integer quantidade, String email, DataHora dataInicio, DataHora dataFim) throws SQLException {
        try (Connection conexao = bd.obterConexao()) {
            String filtroQuery = obterFiltroData(dataInicio, dataFim);
            String query = SolicitacaoQueries.OBTER_HISTORICO_SOLICITACOES_PAGINADAS.replaceAll("DATA_HORA", filtroQuery);


            PreparedStatement ps = conexao.prepareStatement(query);

            ps.setString(1, email);
            ps.setString(2, email);
            ps.setInt(3, quantidade);
            ps.setInt(4, pagina * quantidade);

            ResultSet rs = ps.executeQuery();
            List<Solicitacao> solicitacoes = new ArrayList<>();
            while (rs.next()) solicitacoes.add(obterSolicitacaoDeResult(rs, true));
            return solicitacoes;
        }
    }


    private String obterFiltroData(DataHora dataInicio, DataHora dataFim) {
        if (dataInicio == null || dataFim == null) return "";

        String dataInicioFormatada = sanitize(dataInicio.dataFormatada("yyyy-MM-dd"));
        String dataFimFormatada = sanitize(dataFim.dataFormatada("yyyy-MM-dd"));

        return " AND solicitacao.data_criacao >= '" + dataInicioFormatada + "' AND solicitacao.data_criacao <= '" + dataFimFormatada + "' ";

    }

    public String concatenarCodigosLivros(List<LivroSolicitacaoDto> livros) {
        if (livros == null || livros.isEmpty()) return "";
        StringBuilder codigos = new StringBuilder();
        for (LivroSolicitacaoDto livro : livros) {
            codigos.append(livro.codigoLivro).append(",");
        }
        return codigos.substring(0, codigos.length() - 1);
    }


    public List<Solicitacao> obterSolicitacoesPaginadas(Integer pagina, Integer quantidade, String email) throws SQLException {
        try (Connection conexao = bd.obterConexao()) {
            PreparedStatement ps = conexao.prepareStatement(SolicitacaoQueries.OBTER_SOLICITACOES_ANDAMENTO_PAGINADAS);
            ps.setString(1, email);
            ps.setString(2, email);
            ps.setInt(3, quantidade);
            ps.setInt(4, pagina * quantidade);

            ResultSet rs = ps.executeQuery();
            List<Solicitacao> solicitacoes = new ArrayList<>();
            while (rs.next()) solicitacoes.add(obterSolicitacaoDeResult(rs, true));
            return solicitacoes;
        }
    }

    public Solicitacao obterSolicitacaoPorCodigo(Integer codigo) throws SQLException {
        try (Connection conexao = bd.obterConexao()) {
            logService.iniciar(SolicitacaoDao.class.getName(), "Iniciando a obtenção da solicitação por código");
            PreparedStatement ps = conexao.prepareStatement(SolicitacaoQueries.OBTER_SOLICITACAO);
            ps.setInt(1, codigo);
            ResultSet rs = ps.executeQuery();
            Solicitacao solicitacao = new Solicitacao();
            if (rs.next()) solicitacao = obterSolicitacaoDeResult(rs, true);
            obterSolicitacaoComLivros(solicitacao);
            logService.sucesso(SolicitacaoDao.class.getName(), "Solicitação " + solicitacao.getCodigoSolicitacao() + " obtida com sucesso");
            return solicitacao;
        }
    }

    public Integer cadastrarSolicitacao(SolicitacaoDto solicitacao, Connection conexao) throws SQLException {
        logService.iniciar(SolicitacaoDao.class.getName(), "Iniciando o cadastro da solicitação");
        PreparedStatement pstmt = conexao.prepareStatement(SolicitacaoQueries.CADASTRAR_SOLICITACAO, PreparedStatement.RETURN_GENERATED_KEYS);
        definirParametrosSolicitacao(solicitacao, pstmt);

        int linhasAfetadas = pstmt.executeUpdate();

        logService.sucesso(SolicitacaoDao.class.getName(), "Solicitação cadastrada com sucesso");
        if (linhasAfetadas > 0) {
            try (ResultSet result = pstmt.getGeneratedKeys()) {
                if (result.next()) {
                    Integer idGerado = result.getInt(1);
                    return idGerado;
                }
            }
        }
        return null;
    }

    public List<NotificacaoSolicitacao> obterNotificacoesUsuario(String email) throws SQLException {
        try (Connection conexao = bd.obterConexao()) {
            logService.iniciar(SolicitacaoDao.class.getName(), "Iniciando a obtenção das notificações do usuário " + email);
            PreparedStatement ps = conexao.prepareStatement(SolicitacaoQueries.OBTER_NOTIFICACOES_SOLICITACAO);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            List<NotificacaoSolicitacao> notificacoes = new ArrayList<>();
            while (rs.next()) notificacoes.add(obterNotificacaoDeResult(rs));
            logService.sucesso(SolicitacaoDao.class.getName(), "Notificações do usuário " + email + " obtidas com sucesso");
            return notificacoes;

        }
    }


    public boolean validarUsuarioPossuiSolicitacao(String email) throws SQLException {
        try (Connection conexao = bd.obterConexao()) {
            logService.iniciar(SolicitacaoDao.class.getName(), "Iniciando a validação da existência de solicitação do usuário " + email);
            PreparedStatement ps = conexao.prepareStatement(SolicitacaoQueries.USUARIO_POSSUI_SOLICITACAO_ABERTA);
            ps.setString(1, email);
            ps.setString(2, email);
            ResultSet rs = ps.executeQuery();
            rs.next();
            boolean existe = rs.getInt(1) > 0;
            logService.sucesso(SolicitacaoDao.class.getName(), "Sucesso na validação da existência de solicitação do usuário " + email);
            return existe;
        }
    }
    private void definirParametrosSolicitacao(SolicitacaoDto solicitacao, PreparedStatement pstmt) throws SQLException {
        DataHora dataHora = DataHora.hoje();
        pstmt.setString(1, dataHora.dataFormatada("yyyy-MM-dd"));
        pstmt.setString(2, dataHora.dataFormatada("HH:mm:ss"));
        pstmt.setString(3, dataHora.dataFormatada("yyyy-MM-dd"));
        pstmt.setString(4, dataHora.dataFormatada("HH:mm:ss"));
        pstmt.setString(5, solicitacao.getDataEntrega());
        pstmt.setString(6, solicitacao.getHoraEntrega());
        pstmt.setString(7, solicitacao.getDataDevolucao());
        pstmt.setString(8, solicitacao.getHoraDevolucao());
        pstmt.setString(9, null);
        pstmt.setString(10, null);
        pstmt.setString(11, null);
        pstmt.setString(12, solicitacao.getInformacoesAdicionais());
        pstmt.setInt(13, solicitacao.getCodigoTipoSolicitacao());
        pstmt.setInt(14, solicitacao.getCodigoStatusSolicitacao());
        pstmt.setString(15, solicitacao.getEmailUsuarioReceptor());
        pstmt.setString(16, solicitacao.getEmailUsuarioSolicitante());
        pstmt.setInt(17, solicitacao.getCodigoFormaEntrega());
        pstmt.setString(18, solicitacao.getCodigoRastreioCorreio());
    }

    public void atualizarSolicitacao(SolicitacaoDto solicitacao) throws SQLException {
        try (Connection conexao = bd.obterConexao()) {
            logService.iniciar(SolicitacaoDao.class.getName(), "Iniciando a atualização da solicitação");
            PreparedStatement pstmt = conexao.prepareStatement(SolicitacaoQueries.ATUALIZAR_SOLICITACAO);
            DataHora dataHora = DataHora.hoje();
            pstmt.setString(1, dataHora.dataFormatada("yyyy-MM-dd"));
            pstmt.setString(2, dataHora.dataFormatada("HH:mm:ss"));
            pstmt.setString(3, solicitacao.getDataEntrega());
            pstmt.setString(4, solicitacao.getHoraEntrega());
            pstmt.setString(5, solicitacao.getDataDevolucao());
            pstmt.setString(6, solicitacao.getHoraDevolucao());
            pstmt.setString(7, null);
            pstmt.setString(8, null);
            pstmt.setString(9, solicitacao.getMotivoRecusa());
            pstmt.setString(10, solicitacao.getInformacoesAdicionais());
            pstmt.setInt(11, solicitacao.getCodigoTipoSolicitacao());
            pstmt.setInt(12, solicitacao.getCodigoStatusSolicitacao());
            pstmt.setString(13, solicitacao.getEmailUsuarioSolicitante());
            pstmt.setString(14, solicitacao.getEmailUsuarioReceptor());
            pstmt.setInt(15, solicitacao.getCodigoFormaEntrega());
            pstmt.setString(16, solicitacao.getCodigoRastreioCorreio());
            pstmt.setInt(17, solicitacao.getCodigoSolicitacao());
            pstmt.executeUpdate();
            logService.sucesso(SolicitacaoDao.class.getName(), "Solicitação atualizada com sucesso");
        }
    }

    private void obterSolicitacaoComLivros(Solicitacao solicitacao) throws SQLException {
        List<LivroSolicitacao> livroSolicitacao = obterLivrosSolicitacaoUsuarioCriador(solicitacao.getEmailUsuarioSolicitante(), solicitacao.getCodigoSolicitacao());
        System.out.println(livroSolicitacao.size());
        logService.iniciar(SolicitacaoDao.class.getName(), "Iniciando a obtenção dos livros da solicitação " + solicitacao.getCodigoSolicitacao() + " do usuário " + solicitacao.getEmailUsuarioSolicitante());
        solicitacao.setLivrosUsuarioCriador(livroSolicitacao);
        if (solicitacao.getCodigoTipoSolicitacao() == TipoSolicitacao.TROCA.id) {
            logService.iniciar(SolicitacaoDao.class.getName(), "Iniciando a obtenção dos livros da solicitação " + solicitacao.getCodigoSolicitacao() + " do usuário " + solicitacao.getEmailUsuarioReceptor());
            livroSolicitacao = obterLivrosSolicitacaoUsuarioCriador(solicitacao.getEmailUsuarioReceptor(), solicitacao.getCodigoSolicitacao());
            solicitacao.setLivrosTroca(livroSolicitacao);
        }
    }

    public List<LivroSolicitacaoDto> obterLivrosSolicitacao(Integer codigoSolicitacao, Connection conexao) throws SQLException {
        logService.iniciar(SolicitacaoDao.class.getName(), "Iniciando a obtenção dos livros da solicitação " + codigoSolicitacao);
        PreparedStatement ps = conexao.prepareStatement(SolicitacaoQueries.OBTER_LIVROS_SOLICITACAO);
        ps.setInt(1, codigoSolicitacao);
        ResultSet rs = ps.executeQuery();
        List<LivroSolicitacaoDto> livros = new ArrayList<>();
        while (rs.next()) {
            LivroSolicitacaoDto livro = new LivroSolicitacaoDto();
            livro.codigoLivro = rs.getInt("codigo_livro");
            livro.emailUsuario = rs.getString("email_usuario");
            livros.add(livro);
        }
        logService.sucesso(SolicitacaoDao.class.getName(), "Livros da solicitação " + codigoSolicitacao + " obtidos com sucesso");
        return livros;

    }


    public void recusarSolicitacao(Integer codigo, String motivoRecusa) throws SQLException {
        try (Connection conexao = bd.obterConexao()) {
            logService.iniciar(SolicitacaoDao.class.getName(), "Iniciando a recusa da solicitação de código " + codigo);
            PreparedStatement pstmt = conexao.prepareStatement(SolicitacaoQueries.RECUSAR_SOLICITACAO);
            DataHora dataHora = DataHora.hoje();
            pstmt.setString(1, dataHora.dataFormatada("yyyy-MM-dd"));
            pstmt.setString(2, dataHora.dataFormatada("HH:mm:ss"));
            pstmt.setString(3, motivoRecusa);
            pstmt.setInt(4, codigo);
            pstmt.executeUpdate();
            logService.sucesso(SolicitacaoDao.class.getName(), "Sucesso na recusa da solicitação de código " + codigo);
        }

    }

    public void cancelarSolicitacao(Integer codigo, String motivoRecusa) throws SQLException {
        try (Connection conexao = bd.obterConexao()) {
            logService.iniciar(SolicitacaoDao.class.getName(), "Iniciando o cancelamento da solicitação de código " + codigo);
            PreparedStatement pstmt = conexao.prepareStatement(SolicitacaoQueries.CANCELAR_SOLICITACAO);
            DataHora dataHora = DataHora.hoje();
            pstmt.setString(1, dataHora.dataFormatada("yyyy-MM-dd"));
            pstmt.setString(2, dataHora.dataFormatada("HH:mm:ss"));
            pstmt.setString(3, motivoRecusa);
            pstmt.setInt(4, codigo);
            pstmt.executeUpdate();
            logService.sucesso(SolicitacaoDao.class.getName(), "Sucesso no cancelamento da solicitação de código " + codigo);
        }
    }

    public void aceitarSolicitacao(Integer codigo, Connection conexao) throws SQLException {
        logService.iniciar(SolicitacaoDao.class.getName(), "Iniciando a aceitação da solicitação de código " + codigo);
        PreparedStatement pstmt = conexao.prepareStatement(SolicitacaoQueries.ACEITAR_SOLICITACAO);
        DataHora dataHora = DataHora.hoje();
        pstmt.setString(1, dataHora.dataFormatada("yyyy-MM-dd"));
        pstmt.setString(2, dataHora.dataFormatada("HH:mm:ss"));
        pstmt.setString(3, dataHora.dataFormatada("yyyy-MM-dd"));
        pstmt.setString(4, dataHora.dataFormatada("HH:mm:ss"));
        pstmt.setInt(5, codigo);
        pstmt.executeUpdate();
        logService.sucesso(SolicitacaoDao.class.getName(), "Sucesso na aceitação da solicitação de código " + codigo);
    }

    public void finalizarSolicitacao(Integer codigo) throws SQLException {
        try (Connection conexao = bd.obterConexao()) {
            logService.iniciar(SolicitacaoDao.class.getName(), "Iniciando a finalização da solicitação de código " + codigo);
            PreparedStatement pstmt = conexao.prepareStatement(SolicitacaoQueries.FINALIZAR_SOLICITACAO);
            DataHora dataHora = DataHora.hoje();
            pstmt.setString(1, dataHora.dataFormatada("yyyy-MM-dd"));
            pstmt.setString(2, dataHora.dataFormatada("HH:mm:ss"));
            pstmt.setInt(3, codigo);
            pstmt.executeUpdate();
            logService.sucesso(SolicitacaoDao.class.getName(), "Sucesso na finalização da solicitação de código " + codigo);
        }
    }

    private List<LivroSolicitacao> obterLivrosSolicitacaoUsuarioCriador(String emailUsuario, Integer codigoSolicitacao) throws SQLException {
        try (Connection conexao = bd.obterConexao()) {
            logService.iniciar(SolicitacaoDao.class.getName(), "Iniciando a obtenção dos livros da solicitação " + codigoSolicitacao + " do usuário " + emailUsuario);
            PreparedStatement ps = conexao.prepareStatement(SolicitacaoQueries.OBTER_LIVROS_SOLICITACAO_EMAIL);
            ps.setInt(1, codigoSolicitacao);
            ps.setString(2, emailUsuario);
            ResultSet rs = ps.executeQuery();
            List<LivroSolicitacao> livros = new ArrayList<>();
            while (rs.next()) livros.add(obterLivroSolicitacaoDeResult(rs));
            logService.sucesso(SolicitacaoDao.class.getName(), "Livros da solicitação " + codigoSolicitacao + " do usuário " + emailUsuario + " obtidos com sucesso");
            return livros;
        }
    }

    public boolean validarExistenciaSolicitacao(Integer codigo) throws SQLException {
        try (Connection conexao = bd.obterConexao()) {
            logService.iniciar(SolicitacaoDao.class.getName(), "Iniciando a validação da existência da solicitação de código " + codigo);
            PreparedStatement ps = conexao.prepareStatement(SolicitacaoQueries.SOLICITACAO_EXISTE);
            ps.setInt(1, codigo);
            ResultSet rs = ps.executeQuery();
            rs.next();
            boolean existe = rs.getInt(1) > 0;
            logService.sucesso(SolicitacaoDao.class.getName(), "Sucesso na validação da existência da solicitação de código " + codigo);
            return existe;
        }
    }

    public boolean validarSolicitacaoAberta(Integer numero) throws SQLException {
        try (Connection conexao = bd.obterConexao()) {
            logService.iniciar(SolicitacaoDao.class.getName(), "Iniciando a validação da existência de solicitação aberta de código " + numero);
            PreparedStatement ps = conexao.prepareStatement(SolicitacaoQueries.SOLICITACAO_EM_ABERTO);
            ps.setInt(1, numero);
            ResultSet rs = ps.executeQuery();
            rs.next();
            boolean existe = rs.getInt(1) > 0;
            logService.sucesso(SolicitacaoDao.class.getName(), "Sucesso na validação da existência de solicitação aberta de código " + numero);
            return existe;
        }
    }

    public boolean validarSolicitacaoPendente(Integer codigo) throws SQLException {
        try (Connection conexao = bd.obterConexao()) {
            logService.iniciar(SolicitacaoDao.class.getName(), "Iniciando a validação da existência de solicitação pendente de código " + codigo);
            PreparedStatement ps = conexao.prepareStatement(SolicitacaoQueries.SOLICITACAO_PENDENTE);
            ps.setInt(1, codigo);
            ResultSet rs = ps.executeQuery();
            rs.next();
            boolean existe = rs.getInt(1) > 0;
            logService.sucesso(SolicitacaoDao.class.getName(), "Sucesso na validação da existência de solicitação pendente de código " + codigo);
            return existe;
        }
    }

    public boolean validarSolicitacaoDentroPrazoEntrega(Integer numero) throws SQLException {
        try (Connection conexao = bd.obterConexao()) {
            logService.iniciar(SolicitacaoDao.class.getName(), "Iniciando a validação da existência de solicitação dentro do prazo de entrega");
            PreparedStatement ps = conexao.prepareStatement(SolicitacaoQueries.SOLICITACAO_DENTRO_PRAZO_ENTREGA);
            DataHora dataHora = DataHora.hoje();
            ps.setString(1, dataHora.dataFormatada("yyyy-MM-dd"));
            ps.setInt(2, numero);
            ResultSet rs = ps.executeQuery();
            rs.next();
            boolean existe = rs.getInt(1) > 0;
            logService.sucesso(SolicitacaoDao.class.getName(), "Sucesso na validação da existência de solicitação dentro do prazo de entrega");
            return existe;
        }
    }

    public boolean validarSolicitacaoDentroPrazoDevolucao(String dataDevolucao, String horaDevolucao, Integer numero) throws SQLException {
        try (Connection conexao = bd.obterConexao()) {
            logService.iniciar(SolicitacaoDao.class.getName(), "Iniciando a validação da existência de solicitação dentro do prazo de devolução");
            PreparedStatement ps = conexao.prepareStatement(SolicitacaoQueries.SOLICITACAO_DENTRO_PRAZO_DEVOLUCAO);
            ps.setString(1, dataDevolucao);
            ps.setString(2, horaDevolucao);
            ps.setInt(3, numero);
            ResultSet rs = ps.executeQuery();
            rs.next();
            boolean existe = rs.getInt(1) > 0;
            logService.sucesso(SolicitacaoDao.class.getName(), "Sucesso na validação da existência de solicitação dentro do prazo de devolução");
            return existe;
        }
    }


    public Solicitacao obterSolicitacaoDeResult(ResultSet result, boolean endereco) throws SQLException {
        return Solicitacao.criar(
                result.getInt("codigo_solicitacao"),
                result.getString("data_criacao"),
                result.getString("hora_criacao"),
                result.getString("data_atualizacao"),
                result.getString("hora_atualizacao"),
                result.getString("data_entrega"),
                result.getString("hora_entrega"),
                result.getString("data_devolucao"),
                result.getString("hora_devolucao"),
                result.getString("data_aceite"),
                result.getString("hora_aceite"),
                result.getString("motivo_recusa"),
                result.getString("informacoes_adicionais"),
                result.getInt("codigo_tipo_solicitacao"),
                result.getInt("codigo_status_solicitacao"),
                result.getString("email_usuario_solicitante"),
                result.getString("email_usuario_receptor"),
                result.getInt("codigo_forma_entrega"),
                result.getString("codigo_rastreio_correio"),
                obterEnderecoSolictanteDeResult(result),
                obterEnderecoReceptorDeResult(result),
                null,
                null,
                result.getString("nome_usuario_solicitante")

        );
    }


    private LivroSolicitacao obterLivroSolicitacaoDeResult(ResultSet result) throws SQLException {
        return LivroSolicitacao.carregar(
                result.getInt("codigo_livro"),
                result.getString("nome"),
                result.getString("autor"),
                result.getString("email_usuario")
        );
    }


    public Endereco obterEnderecoSolictanteDeResult(ResultSet result) throws SQLException {
        Integer codigoEnderecoSolicitante = result.getInt("codigo_endereco_solicitante");
        if (codigoEnderecoSolicitante == 0 || codigoEnderecoSolicitante == null) return null;
        return Endereco.carregar(
                result.getInt("codigo_endereco_solicitante"),
                result.getString("logradouro_solicitante"),
                result.getString("complemento_solicitante"),
                result.getString("bairro_solicitante"),
                result.getString("cep_solicitante"),
                result.getString("nome_cidade_solicitante"),
                result.getString("email_usuario_solicitante"),
                result.getString("estado_solicitante"),
                result.getString("numero_solicitante"),
                result.getInt("codigo_cidade_solicitante"),
                result.getBoolean("endereco_principal_solicitante")
        );
    }

    public Endereco obterEnderecoReceptorDeResult(ResultSet result) throws SQLException {
        Integer codigoEnderecoReceptor = result.getInt("codigo_endereco_receptor");
        if (codigoEnderecoReceptor == 0 || codigoEnderecoReceptor == null) return null;
        return Endereco.carregar(
                result.getInt("codigo_endereco_receptor"),
                result.getString("logradouro_receptor"),
                result.getString("complemento_receptor"),
                result.getString("bairro_receptor"),
                result.getString("cep_receptor"),
                result.getString("nome_cidade_receptor"),
                result.getString("email_usuario_receptor"),
                result.getString("estado_receptor"),
                result.getString("numero_receptor"),
                result.getInt("codigo_cidade_receptor"),
                result.getBoolean("endereco_principal_receptor")
        );
    }


    public NotificacaoSolicitacao obterNotificacaoDeResult(ResultSet result) throws SQLException {
        return NotificacaoSolicitacao.criar(
                result.getInt("codigo_solicitacao"),
                result.getString("nome"),
                result.getString("email_usuario_solicitante"),
                result.getString("imagem")
        );
    }

    public void salvarLivroSolicitacao(List<LivroSolicitacaoDto> livros, Integer codigoSolicitacao, Connection conexao) throws SQLException {
        if (livros == null || livros.isEmpty()) return;
        logService.iniciar(SolicitacaoDao.class.getName(), "Iniciando a inserção dos livros da solicitação");
        conexao.setAutoCommit(false);
        String insert = obterInsertLivrosSolicitacao(livros, codigoSolicitacao);
        PreparedStatement pstmt = conexao.prepareStatement(insert);
        pstmt.executeUpdate();
        logService.sucesso(SolicitacaoDao.class.getName(), "Livros da solicitação inseridos com sucesso");
    }

    private String obterInsertLivrosSolicitacao(List<LivroSolicitacaoDto> livros, Integer codigoSolicitacao) {
        StringBuilder insert = new StringBuilder("INSERT INTO livro_solicitacao (codigo_livro, email_usuario, codigo_solicitacao) VALUES ");
        for (LivroSolicitacaoDto livro : livros) {
            insert.append("(").append(livro.codigoLivro).append(",'").append(livro.emailUsuario).append("',").append(codigoSolicitacao).append("),");
        }
        return insert.substring(0, insert.length() - 1);
    }
}
