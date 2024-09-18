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


    public List<Solicitacao> obterSolicitacoesPaginadas(Integer pagina, Integer quantidade) throws SQLException {
        try (Connection conexao = bd.obterConexao()) {
            PreparedStatement ps = conexao.prepareStatement(SolicitacaoQueries.OBTER_SOLICITACOES_ANDAMENTO_PAGINADAS);
            ps.setInt(1, (pagina - 1) * quantidade);
            ps.setInt(2, quantidade);
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

    private Solicitacao obterSolicitacaoComLivros(Solicitacao solicitacao) throws SQLException {
        List<LivroSolicitacao> livroSolicitacao = obterLivrosSolicitacaoUsuarioCriador(solicitacao.getEmailUsuarioCriador(), solicitacao.getCodigoSolicitacao());
        logService.iniciar(SolicitacaoDao.class.getName(), "Iniciando a obtenção dos livros da solicitação " + solicitacao.getCodigoSolicitacao() + " do usuário " + solicitacao.getEmailUsuarioCriador());
        solicitacao.setLivrosUsuarioCriador(livroSolicitacao);
        if (solicitacao.getCodigoTipoSolicitacao() == TipoSolicitacao.TROCA.id) {
            logService.iniciar(SolicitacaoDao.class.getName(), "Iniciando a obtenção dos livros da solicitação " + solicitacao.getCodigoSolicitacao() + " do usuário " + solicitacao.getEmailUsuarioProprietario());
            livroSolicitacao = obterLivrosSolicitacaoUsuarioCriador(solicitacao.getEmailUsuarioProprietario(), solicitacao.getCodigoSolicitacao());
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
            PreparedStatement pstmt = conexao.prepareStatement(SolicitacaoQueries.CANCELAR_SOLICITACAO);
            DataHora dataHora = DataHora.hoje();
            pstmt.setString(1, dataHora.dataFormatada("yyyy-MM-dd"));
            pstmt.setString(2, dataHora.dataFormatada("HH:mm:ss"));
            pstmt.setString(3, motivoRecusa);
            pstmt.setInt(4, codigo);
            pstmt.executeQuery();
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
