package br.com.isabela.dao.solicitacao;

import br.com.isabela.dao.FabricaDeConexoes;
import br.com.isabela.model.DataHora;
import br.com.isabela.model.endereco.Endereco;
import br.com.isabela.model.livro.LivroSolicitacao;
import br.com.isabela.model.solicitacao.Solicitacao;
import br.com.isabela.model.solicitacao.TipoSolicitacao;
import br.com.isabela.service.autenticacao.LogService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
            while (rs.next()) solicitacoes.add(obterSolicitacaoDeResult(rs));
            return solicitacoes;
        }
    }

    public List<Solicitacao> obterSolicitacoesPaginadas(Integer pagina, Integer quantidade, String email) throws SQLException {
        try (Connection conexao = bd.obterConexao()) {
            PreparedStatement ps = conexao.prepareStatement(SolicitacaoQueries.OBTER_SOLICITACOES_ANDAMENTO_PAGINADAS);
            DataHora dataHora = DataHora.hoje();
            ps.setInt(1, (pagina - 1) * quantidade);
            ps.setInt(2, quantidade);
            ps.setString(3, email);
            ps.setString(4, email);
            ps.setString(5, dataHora.dataFormatada("yyyy-MM-dd"));
            ps.setString(6, dataHora.dataFormatada("HH:mm:ss"));
            ResultSet rs = ps.executeQuery();
            List<Solicitacao> solicitacoes = new ArrayList<>();
            while (rs.next()) solicitacoes.add(obterSolicitacaoDeResult(rs));
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
            if (rs.next()) solicitacao = obterSolicitacaoDeResult(rs);
            solicitacao = obterSolicitacaoComLivros(solicitacao);
            logService.sucesso(SolicitacaoDao.class.getName(), "Solicitação " + solicitacao.getCodigoSolicitacao() + " obtida com sucesso");
            return solicitacao;
        }
    }

    public void cadastrarSolicitacao(Solicitacao solicitacao, Integer codigoEndereco) throws SQLException {
        try (Connection conexao = bd.obterConexao()) {
            logService.iniciar(SolicitacaoDao.class.getName(), "Iniciando o cadastro da solicitação");
            PreparedStatement pstmt = conexao.prepareStatement(SolicitacaoQueries.CADASTRAR_SOLICITACAO);
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
            pstmt.setInt(18, codigoEndereco);
            pstmt.setString(19, solicitacao.getCodigoRastreioCorreio());
            pstmt.executeQuery();
            logService.sucesso(SolicitacaoDao.class.getName(), "Solicitação cadastrada com sucesso");
        }
    }

    public void atualizarSolicitacao(Solicitacao solicitacao) throws SQLException {
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
            pstmt.setString(7, solicitacao.getDataAceite());
            pstmt.setString(8, solicitacao.getHoraAceite());
            pstmt.setString(9, solicitacao.getMotivoRecusa());
            pstmt.setString(10, solicitacao.getInformacoesAdicionais());
            pstmt.setInt(11, solicitacao.getCodigoTipoSolicitacao());
            pstmt.setInt(12, solicitacao.getCodigoStatusSolicitacao());
            pstmt.setString(13, solicitacao.getEmailUsuarioSolicitante());
            pstmt.setString(14, solicitacao.getEmailUsuarioReceptor());
            pstmt.setInt(15, solicitacao.getCodigoFormaEntrega());
            pstmt.setInt(16, solicitacao.getEndereco().getCodigo());
            pstmt.setString(17, solicitacao.getCodigoRastreioCorreio());
            pstmt.setInt(18, solicitacao.getCodigoSolicitacao());
            pstmt.executeQuery();
            logService.sucesso(SolicitacaoDao.class.getName(), "Solicitação atualizada com sucesso");
        }
    }

    private Solicitacao obterSolicitacaoComLivros(Solicitacao solicitacao) throws SQLException {
        List<LivroSolicitacao> livroSolicitacao = obterLivrosSolicitacaoUsuarioCriador(solicitacao.getEmailUsuarioSolicitante(), solicitacao.getCodigoSolicitacao());
        logService.iniciar(SolicitacaoDao.class.getName(), "Iniciando a obtenção dos livros da solicitação " + solicitacao.getCodigoSolicitacao() + " do usuário " + solicitacao.getEmailUsuarioSolicitante());
        solicitacao.setLivrosUsuarioCriador(livroSolicitacao);
        if (solicitacao.getCodigoTipoSolicitacao() == TipoSolicitacao.TROCA.id) {
            logService.iniciar(SolicitacaoDao.class.getName(), "Iniciando a obtenção dos livros da solicitação " + solicitacao.getCodigoSolicitacao() + " do usuário " + solicitacao.getEmailUsuarioReceptor());
            livroSolicitacao = obterLivrosSolicitacaoUsuarioCriador(solicitacao.getEmailUsuarioReceptor(), solicitacao.getCodigoSolicitacao());
            solicitacao.setLivrosTroca(livroSolicitacao);
        }
        return solicitacao;
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
            pstmt.executeQuery();
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
            pstmt.executeQuery();
            logService.sucesso(SolicitacaoDao.class.getName(), "Sucesso no cancelamento da solicitação de código " + codigo);
        }
    }

    public void aceitarSolicitacao(Integer codigo) throws SQLException {
        try (Connection conexao = bd.obterConexao()) {
            logService.iniciar(SolicitacaoDao.class.getName(), "Iniciando a aceitação da solicitação de código " + codigo);
            PreparedStatement pstmt = conexao.prepareStatement(SolicitacaoQueries.ACEITAR_SOLICITACAO);
            DataHora dataHora = DataHora.hoje();
            pstmt.setString(1, dataHora.dataFormatada("yyyy-MM-dd"));
            pstmt.setString(2, dataHora.dataFormatada("HH:mm:ss"));
            pstmt.setString(3, dataHora.dataFormatada("yyyy-MM-dd"));
            pstmt.setString(4, dataHora.dataFormatada("HH:mm:ss"));
            pstmt.setInt(4, codigo);
            pstmt.executeQuery();
            logService.sucesso(SolicitacaoDao.class.getName(), "Sucesso na aceitação da solicitação de código " + codigo);
        }
    }

    private List<LivroSolicitacao> obterLivrosSolicitacaoUsuarioCriador(String emailUsuario, Integer codigoSolicitacao) throws SQLException {
        try (Connection conexao = bd.obterConexao()) {
            logService.iniciar(SolicitacaoDao.class.getName(), "Iniciando a obtenção dos livros da solicitação " + codigoSolicitacao + " do usuário " + emailUsuario);
            PreparedStatement ps = conexao.prepareStatement(SolicitacaoQueries.OBTER_LIVROS_SOLICITACAO);
            ps.setString(1, emailUsuario);
            ps.setInt(2, codigoSolicitacao);
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
            ps.setString(2, dataHora.dataFormatada("HH:mm:ss"));
            ps.setInt(3, numero);
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


    public Solicitacao obterSolicitacaoDeResult(ResultSet result) throws SQLException {
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
                result.getString("email_usuario_criador"),
                result.getString("email_usuario"),
                result.getInt("codigo_forma_entrega"),
                result.getString("codigo_rastreio_correio"),
                obterEnderecoDeResult(result),
                null,
                null

        );
    }


    private LivroSolicitacao obterLivroSolicitacaoDeResult(ResultSet result) throws SQLException {
        return LivroSolicitacao.carregar(
                result.getInt("codigo_livro"),
                result.getString("titulo"),
                result.getString("autor"),
                result.getString("email_usuario")
        );
    }


    public Endereco obterEnderecoDeResult(ResultSet result) throws SQLException {
        return Endereco.carregar(
                result.getInt("codigo_endereco"),
                result.getString("logradouro"),
                result.getString("complemento"),
                result.getString("bairro"),
                result.getString("cep"),
                result.getString("nome_cidade"),
                result.getString("email_usuario"),
                result.getString("estado")
        );
    }
}
