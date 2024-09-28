package br.com.leituramiga.service.solicitacao;

import br.com.leituramiga.dao.FabricaDeConexoes;
import br.com.leituramiga.dao.endereco.EnderecoDao;
import br.com.leituramiga.dao.solicitacao.SolicitacaoDao;
import br.com.leituramiga.dto.solicitacao.SolicitacaoDto;
import br.com.leituramiga.model.endereco.Endereco;
import br.com.leituramiga.model.exception.livro.LivroJaDesativado;
import br.com.leituramiga.model.exception.livro.LivroNaoDisponivel;
import br.com.leituramiga.model.exception.livro.LivroNaoExistente;
import br.com.leituramiga.model.exception.solicitacao.SolicitacaoExcedeuPrazoEntrega;
import br.com.leituramiga.model.exception.solicitacao.SolicitacaoNaoAberta;
import br.com.leituramiga.model.exception.solicitacao.SolicitacaoNaoExistente;
import br.com.leituramiga.model.exception.solicitacao.SolicitacaoNaoPendente;
import br.com.leituramiga.model.solicitacao.Solicitacao;
import br.com.leituramiga.service.autenticacao.LogService;
import br.com.leituramiga.service.livro.LivroService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@ApplicationScoped
public class SolicitacaoService {

    @Inject
    EnderecoDao enderecoDao;

    @Inject
    SolicitacaoDao solicitacaoDao;

    @Inject
    LivroService livroService;

    @Inject
    LogService logService;

    @Inject
    FabricaDeConexoes bd;

    public void aceitarSolicitacao(Integer codigo, String email) throws SQLException, SolicitacaoExcedeuPrazoEntrega, SolicitacaoNaoExistente, SolicitacaoNaoPendente {
        try {
            validarAceiteSolicitacao(codigo);
            logService.iniciar(SolicitacaoService.class.getName(), "Iniciando aceitação de solicitação de código " + codigo);
            solicitacaoDao.aceitarSolicitacao(codigo, email);
            logService.sucesso(SolicitacaoService.class.getName(), "Aceitação de solicitação finalizada de código " + codigo);
        } catch (Exception e) {
            logService.erro(SolicitacaoService.class.getName(), "Ocorreu um erro na aceitação de solicitação de código " + codigo, e);
            throw e;
        }
    }

    public void recusarSolicitacao(Integer codigo, String motivoRecusa, String email) throws SQLException, SolicitacaoNaoExistente, SolicitacaoNaoPendente, SolicitacaoExcedeuPrazoEntrega {
        try {
            validarAceiteSolicitacao(codigo);
            logService.iniciar(SolicitacaoService.class.getName(), "Iniciando recusa de solicitação de código " + codigo);
            solicitacaoDao.recusarSolicitacao(codigo, motivoRecusa, email);
            logService.sucesso(SolicitacaoService.class.getName(), "Recusa de solicitação finalizada de código " + codigo);
        } catch (Exception e) {
            logService.erro(SolicitacaoService.class.getName(), "Ocorreu um erro na recusa de solicitação de código " + codigo, e);
            throw e;
        }
    }

    public void cancelarSolicitacao(Integer codigo, String motivoRecusa, String email) throws SQLException, SolicitacaoNaoExistente, SolicitacaoNaoAberta {
        try {
            validarExistenciaSolicitacao(codigo);
            logService.iniciar(SolicitacaoService.class.getName(), "Iniciando validacao do status aberto da solicitação de código " + codigo);
            if (!solicitacaoDao.validarSolicitacaoAberta(codigo)) throw new SolicitacaoNaoAberta();
            logService.iniciar(SolicitacaoService.class.getName(), "Iniciando cancelamento de solicitação de código " + codigo);
            solicitacaoDao.cancelarSolicitacao(codigo, motivoRecusa, email);
            logService.sucesso(SolicitacaoService.class.getName(), "Cancelamento efetuado com sucesso da solicitação de código " + codigo);
        } catch (Exception e) {
            logService.erro(SolicitacaoService.class.getName(), "Ocorreu um erro no cancelamento de solicitação de código " + codigo, e);
            throw e;
        }
    }

    public SolicitacaoDto obterSolicitacao(Integer codigo) throws SQLException, SolicitacaoNaoExistente {
        try {
            validarExistenciaSolicitacao(codigo);
            logService.iniciar(SolicitacaoService.class.getName(), "Iniciando busca de solicitação de código " + codigo);
            Solicitacao solicitacao = solicitacaoDao.obterSolicitacaoPorCodigo(codigo);
            logService.sucesso(SolicitacaoService.class.getName(), "Busca de solicitação finalizada de código " + codigo);
            return SolicitacaoDto.deModel(solicitacao);
        } catch (Exception e) {
            logService.erro(SolicitacaoService.class.getName(), "Ocorreu um erro na busca de solicitação de código " + codigo, e);
            throw e;
        }
    }

    public List<SolicitacaoDto> obterSolicitacoesPaginadas(Integer pagina, Integer tamanhoPagina, String email) throws SQLException {
        try {
            logService.iniciar(SolicitacaoService.class.getName(), "Iniciando busca de solicitações paginadas");
            List<Solicitacao> solicitacoes = solicitacaoDao.obterSolicitacoesPaginadas(pagina, tamanhoPagina, email);
            logService.sucesso(SolicitacaoService.class.getName(), "Busca de solicitações paginadas finalizada");
            return solicitacoes.stream().map(SolicitacaoDto::deModel).toList();
        } catch (Exception e) {
            logService.erro(SolicitacaoService.class.getName(), "Ocorreu um erro na busca de solicitações paginadas", e);
            throw e;
        }
    }

    public void cadastrarSolicitacao(SolicitacaoDto solicitacao, String email) throws SQLException, LivroNaoDisponivel, LivroJaDesativado, LivroNaoExistente {
        Connection conexao = null;
        try {
            conexao = bd.obterConexao();
            conexao.setAutoCommit(false);
            logService.iniciar(SolicitacaoService.class.getName(), "Iniciando validação do endereço do usuário de email " + solicitacao.getEmailUsuarioSolicitante());
            Integer endereco = atualizarEnderecoSolicitacao(solicitacao, conexao);
            logService.iniciar(SolicitacaoService.class.getName(), "Iniciando cadastro de solicitação");
            Integer numeroSolicitacao = solicitacaoDao.cadastrarSolicitacao(solicitacao, endereco, conexao);
            solicitacaoDao.salvarLivroSolicitacao(solicitacao.getLivrosUsuarioSolicitante(), numeroSolicitacao, conexao);
            solicitacaoDao.salvarLivroSolicitacao(solicitacao.getLivrosTroca(), numeroSolicitacao, conexao);
            atualizarLivrosSolicitacao(solicitacao, numeroSolicitacao, email, conexao);
            logService.sucesso(SolicitacaoService.class.getName(), "Cadastro de solicitação finalizado");
            conexao.commit();
        } catch (Exception e) {
            logService.erro(SolicitacaoService.class.getName(), "Ocorreu um erro no cadastro de solicitação", e);
            if (conexao != null) conexao.rollback();
            throw e;
        } finally {
            bd.desconectar(conexao);
        }
    }

    private Integer atualizarEnderecoSolicitacao(SolicitacaoDto solicitacao, Connection conexao) throws SQLException {
        Integer endereco = 0;
        if (!enderecoDao.validarExistencia(solicitacao.getEmailUsuarioSolicitante())) {
            logService.iniciar(SolicitacaoService.class.getName(), "Iniciando cadastro de endereço do usuário de email " + solicitacao.getEmailUsuarioSolicitante());
            endereco = enderecoDao.salvarEndereco(solicitacao.getEndereco(), conexao, solicitacao.emailUsuarioSolicitante);
        } else {
            Endereco enderecoSolicitante = enderecoDao.obterEnderecoUsuario(solicitacao.getEmailUsuarioSolicitante());
            endereco = enderecoSolicitante.getCodigo();
        }
        return endereco;
    }

    private void atualizarLivrosSolicitacao(SolicitacaoDto solicitacao, Integer numeroSolicitacao, String email, Connection conexao) throws LivroNaoDisponivel, SQLException, LivroJaDesativado, LivroNaoExistente {
        livroService.atualizarLivrosIndisponiveis(numeroSolicitacao, solicitacao.getLivrosUsuarioSolicitante(), email, conexao);
        if (solicitacao.getLivrosTroca() != null && !solicitacao.getLivrosTroca().isEmpty()) {
            livroService.atualizarLivrosIndisponiveis(numeroSolicitacao, solicitacao.getLivrosTroca(), email, conexao);
        }
    }

    public void atualizarSoliciacao(SolicitacaoDto solicitacao) throws SQLException, SolicitacaoNaoExistente {
        try {
            validarExistenciaSolicitacao(solicitacao.getCodigoSolicitacao());
            Connection conexao = bd.obterConexao();
            logService.iniciar(SolicitacaoService.class.getName(), "Iniciando atualização de solicitação de código " + solicitacao.getCodigoSolicitacao());
            enderecoDao.salvarEndereco(solicitacao.getEndereco(), conexao, solicitacao.emailUsuarioSolicitante);
            solicitacaoDao.atualizarSolicitacao(solicitacao);
            logService.sucesso(SolicitacaoService.class.getName(), "Atualização de solicitação finalizada de código " + solicitacao.getCodigoSolicitacao());
        } catch (Exception e) {
            logService.erro(SolicitacaoService.class.getName(), "Ocorreu um erro na atualização de solicitação de código " + solicitacao.getCodigoSolicitacao(), e);
            throw e;
        }
    }

    private void validarExistenciaSolicitacao(Integer codigo) throws SQLException, SolicitacaoNaoExistente {
        logService.iniciar(SolicitacaoService.class.getName(), "Iniciando a validação da existência da solicitação de código " + codigo);
        if (!solicitacaoDao.validarExistenciaSolicitacao(codigo)) throw new SolicitacaoNaoExistente();
    }

    private void validarAceiteSolicitacao(Integer codigo) throws SQLException, SolicitacaoNaoExistente, SolicitacaoNaoPendente, SolicitacaoExcedeuPrazoEntrega {
        validarExistenciaSolicitacao(codigo);
        logService.iniciar(SolicitacaoService.class.getName(), "Iniciando a validação do status pendente da solicitação " + codigo);
        if (!solicitacaoDao.validarSolicitacaoPendente(codigo)) throw new SolicitacaoNaoPendente();
        logService.iniciar(SolicitacaoService.class.getName(), "Iniciando a validação da data de entrega da solicitação " + codigo);
        if (!solicitacaoDao.validarSolicitacaoDentroPrazoEntrega(codigo)) throw new SolicitacaoExcedeuPrazoEntrega();
    }


}
