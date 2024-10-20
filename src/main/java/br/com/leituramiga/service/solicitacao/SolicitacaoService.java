package br.com.leituramiga.service.solicitacao;

import br.com.leituramiga.dao.FabricaDeConexoes;
import br.com.leituramiga.dao.endereco.EnderecoDao;
import br.com.leituramiga.dao.solicitacao.SolicitacaoDao;
import br.com.leituramiga.dto.endereco.EnderecoDto;
import br.com.leituramiga.dto.solicitacao.AceiteSolicitacaoDto;
import br.com.leituramiga.dto.solicitacao.NotificacaoSolicitacaoDto;
import br.com.leituramiga.dto.solicitacao.SolicitacaoDto;
import br.com.leituramiga.dto.usuario.UsuarioDto;
import br.com.leituramiga.model.endereco.Endereco;
import br.com.leituramiga.model.exception.UsuarioNaoAtivo;
import br.com.leituramiga.model.exception.UsuarioNaoExistente;
import br.com.leituramiga.model.exception.UsuarioNaoPertenceASolicitacao;
import br.com.leituramiga.model.exception.livro.LivroJaDesativado;
import br.com.leituramiga.model.exception.livro.LivroNaoDisponivel;
import br.com.leituramiga.model.exception.livro.LivroNaoExistente;
import br.com.leituramiga.model.exception.solicitacao.SolicitacaoExcedeuPrazoEntrega;
import br.com.leituramiga.model.exception.solicitacao.SolicitacaoNaoAberta;
import br.com.leituramiga.model.exception.solicitacao.SolicitacaoNaoExistente;
import br.com.leituramiga.model.exception.solicitacao.SolicitacaoNaoPendente;
import br.com.leituramiga.model.solicitacao.NotificacaoSolicitacao;
import br.com.leituramiga.model.solicitacao.Solicitacao;
import br.com.leituramiga.service.UsuarioService;
import br.com.leituramiga.service.autenticacao.LogService;
import br.com.leituramiga.service.email.EmailService;
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
    UsuarioService usuarioService;

    @Inject
    EmailService emailService;

    @Inject
    SolicitacaoDao solicitacaoDao;

    @Inject
    LivroService livroService;

    @Inject
    LogService logService;

    @Inject
    FabricaDeConexoes bd;

    public void aceitarSolicitacao(Integer codigo, AceiteSolicitacaoDto aceiteSolicitacaoDto, String email) throws SQLException, SolicitacaoExcedeuPrazoEntrega, SolicitacaoNaoExistente, SolicitacaoNaoPendente, LivroNaoDisponivel, LivroJaDesativado, LivroNaoExistente, UsuarioNaoPertenceASolicitacao, UsuarioNaoAtivo, ClassNotFoundException, UsuarioNaoExistente {
        Connection conexao = bd.obterConexao();
        try {
            conexao.setAutoCommit(false);
            validarAceiteSolicitacao(codigo);
            SolicitacaoDto solicitacao = obterSolicitacao(codigo);
            validarUsuarioPertenceSolicitacao(solicitacao, email);
            atualizarLivrosIndisponiveisSolicitacao(solicitacao, codigo, email, conexao);
            if (aceiteSolicitacaoDto.livros != null)
                solicitacaoDao.salvarLivroSolicitacao(aceiteSolicitacaoDto.livros, solicitacao.codigoSolicitacao, conexao);
            solicitacaoDao.recusarSolicitacoesComLivroIndisponivel(solicitacao.getLivrosUsuarioSolicitante(), solicitacao.codigoSolicitacao);
            logService.iniciar(SolicitacaoService.class.getName(), "Iniciando aceitação de solicitação de código " + codigo);
            solicitacaoDao.aceitarSolicitacao(codigo, conexao);
            conexao.commit();
            UsuarioDto usuarioReceptor = usuarioService.obterUsuarioPorIdentificador(solicitacao.getEmailUsuarioReceptor());
            UsuarioDto usuarioSolicitante = usuarioService.obterUsuarioPorIdentificador(solicitacao.getEmailUsuarioSolicitante());
            emailService.enviarEmailSolicitacaoAceita(solicitacao.getEmailUsuarioReceptor(), usuarioReceptor.nome, usuarioSolicitante.nome);
            logService.sucesso(SolicitacaoService.class.getName(), "Aceitação de solicitação finalizada de código " + codigo);
        } catch (Exception e) {
            logService.erro(SolicitacaoService.class.getName(), "Ocorreu um erro na aceitação de solicitação de código " + codigo, e);
            conexao.rollback();
            throw e;
        } finally {
            bd.desconectar(conexao);
        }
    }

    public void recusarSolicitacao(Integer codigo, String motivoRecusa, String email) throws SQLException, SolicitacaoNaoExistente, SolicitacaoNaoPendente, SolicitacaoExcedeuPrazoEntrega, LivroNaoDisponivel, LivroJaDesativado, LivroNaoExistente, UsuarioNaoPertenceASolicitacao, UsuarioNaoAtivo, ClassNotFoundException, UsuarioNaoExistente {
        try (Connection conexao = bd.obterConexao()) {
            logService.iniciar(SolicitacaoService.class.getName(), "Iniciando a validação da data de entrega da solicitação " + codigo);
            if (!solicitacaoDao.validarSolicitacaoDentroPrazoEntrega(codigo))
                throw new SolicitacaoExcedeuPrazoEntrega();
            logService.iniciar(SolicitacaoService.class.getName(), "Iniciando recusa de solicitação de código " + codigo);
            SolicitacaoDto solicitacao = obterSolicitacao(codigo);
            validarUsuarioPertenceSolicitacao(solicitacao, email);
            atualizarLivrosDisponiveisSolicitacao(solicitacao, codigo, email, conexao);
            solicitacaoDao.recusarSolicitacao(codigo, motivoRecusa);
            UsuarioDto usuarioReceptor = usuarioService.obterUsuarioPorIdentificador(solicitacao.getEmailUsuarioReceptor());
            UsuarioDto usuarioSolicitante = usuarioService.obterUsuarioPorIdentificador(solicitacao.getEmailUsuarioSolicitante());
            if (email.equals(solicitacao.getEmailUsuarioSolicitante())) {
                emailService.enviarEmailSolicitacaoRecusada(solicitacao.getEmailUsuarioSolicitante(), motivoRecusa, usuarioReceptor.nome, usuarioSolicitante.nome, usuarioSolicitante.nome);
            } else {
                emailService.enviarEmailSolicitacaoRecusada(solicitacao.getEmailUsuarioReceptor(), motivoRecusa, usuarioReceptor.nome, usuarioSolicitante.nome, usuarioReceptor.nome);
            }
            logService.sucesso(SolicitacaoService.class.getName(), "Recusa de solicitação finalizada de código " + codigo);
        } catch (Exception e) {
            logService.erro(SolicitacaoService.class.getName(), "Ocorreu um erro na recusa de solicitação de código " + codigo, e);
            throw e;
        }
    }

    public void finalizarSolicitacao(Integer codigo, String email) throws SQLException, SolicitacaoNaoExistente, SolicitacaoNaoPendente, SolicitacaoExcedeuPrazoEntrega, LivroNaoDisponivel, LivroJaDesativado, LivroNaoExistente, UsuarioNaoPertenceASolicitacao {
        try {
            logService.iniciar(SolicitacaoService.class.getName(), "Iniciando a validação da data de entrega da solicitação " + codigo);
            if (!solicitacaoDao.validarSolicitacaoDentroPrazoEntrega(codigo))
                throw new SolicitacaoExcedeuPrazoEntrega();
            logService.iniciar(SolicitacaoService.class.getName(), "Iniciando finalização de solicitação de código " + codigo);
            SolicitacaoDto solicitacao = obterSolicitacao(codigo);
            validarUsuarioPertenceSolicitacao(solicitacao, email);
            solicitacaoDao.finalizarSolicitacao(codigo);
            logService.sucesso(SolicitacaoService.class.getName(), "Finalização de solicitação finalizada de código " + codigo);
        } catch (Exception e) {
            logService.erro(SolicitacaoService.class.getName(), "Ocorreu um erro na finalização de solicitação de código " + codigo, e);
            throw e;
        }
    }

    public void cancelarSolicitacao(Integer codigo, String motivoRecusa, String email) throws SQLException, SolicitacaoNaoExistente, SolicitacaoNaoAberta, LivroNaoDisponivel, LivroJaDesativado, LivroNaoExistente, UsuarioNaoPertenceASolicitacao, UsuarioNaoAtivo, ClassNotFoundException, UsuarioNaoExistente {
        try (Connection conexao = bd.obterConexao()) {
            validarExistenciaSolicitacao(codigo);
            logService.iniciar(SolicitacaoService.class.getName(), "Iniciando validacao do status aberto da solicitação de código " + codigo);
            if (!solicitacaoDao.validarSolicitacaoAberta(codigo)) throw new SolicitacaoNaoAberta();
            SolicitacaoDto solicitacao = obterSolicitacao(codigo);
            validarUsuarioPertenceSolicitacao(solicitacao, email);
            atualizarLivrosDisponiveisSolicitacao(solicitacao, codigo, email, conexao);
            logService.iniciar(SolicitacaoService.class.getName(), "Iniciando cancelamento de solicitação de código " + codigo);
            solicitacaoDao.cancelarSolicitacao(codigo, motivoRecusa);
            logService.sucesso(SolicitacaoService.class.getName(), "Cancelamento efetuado com sucesso da solicitação de código " + codigo);
            UsuarioDto usuarioReceptor = usuarioService.obterUsuarioPorIdentificador(solicitacao.getEmailUsuarioReceptor());
            UsuarioDto usuarioSolicitante = usuarioService.obterUsuarioPorIdentificador(solicitacao.getEmailUsuarioSolicitante());
            String nomeUsuario = email.equals(solicitacao.getEmailUsuarioSolicitante()) ? usuarioSolicitante.nome : usuarioReceptor.nome;
            if (email.equals(solicitacao.getEmailUsuarioSolicitante())) {
                emailService.enviarEmailSolicitacaoRecusada(solicitacao.getEmailUsuarioSolicitante(), motivoRecusa, usuarioReceptor.nome, usuarioSolicitante.nome, usuarioSolicitante.nome);
            } else {
                emailService.enviarEmailSolicitacaoRecusada(solicitacao.getEmailUsuarioReceptor(), motivoRecusa, usuarioReceptor.nome, usuarioSolicitante.nome, usuarioReceptor.nome);
            }
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

    public List<SolicitacaoDto> obterHistoricoSolicitacoesPaginadas(Integer pagina, Integer tamanhoPagina, String email) throws SQLException {
        try {
            logService.iniciar(SolicitacaoService.class.getName(), "Iniciando busca do histórico de solicitações paginadas");
            List<Solicitacao> solicitacoes = solicitacaoDao.obterHistoricoSolicitacoesPaginadas(pagina, tamanhoPagina, email);
            logService.sucesso(SolicitacaoService.class.getName(), "Busca do histórico de solicitações paginadas finalizada");
            return solicitacoes.stream().map(SolicitacaoDto::deModel).toList();
        } catch (Exception e) {
            logService.erro(SolicitacaoService.class.getName(), "Ocorreu um erro na busca do histórico de solicitações paginadas", e);
            throw e;
        }
    }

    public void cadastrarSolicitacao(SolicitacaoDto solicitacao) throws SQLException, UsuarioNaoAtivo, ClassNotFoundException, UsuarioNaoExistente {
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
            logService.sucesso(SolicitacaoService.class.getName(), "Cadastro de solicitação finalizado");
            conexao.commit();
            UsuarioDto usuarioReceptor = usuarioService.obterUsuarioPorIdentificador(solicitacao.getEmailUsuarioReceptor());
            UsuarioDto usuarioSolicitante = usuarioService.obterUsuarioPorIdentificador(solicitacao.getEmailUsuarioSolicitante());
            Endereco enderecoDto = enderecoDao.obterEnderecoPorId(endereco);
            EnderecoDto enderecoSolicitante = EnderecoDto.deModel(enderecoDto);
            solicitacao.endereco = enderecoSolicitante;
            emailService.enviarEmailSolicitacaoDeLivros(solicitacao.getEmailUsuarioReceptor(), solicitacao, usuarioReceptor.nome, usuarioSolicitante.nome);
        } catch (Exception e) {
            logService.erro(SolicitacaoService.class.getName(), "Ocorreu um erro no cadastro de solicitação", e);
            if (conexao != null) conexao.rollback();
            throw e;
        } finally {
            bd.desconectar(conexao);
        }
    }

    private void validarUsuarioPertenceSolicitacao(SolicitacaoDto solicitacao, String email) throws UsuarioNaoPertenceASolicitacao {
        logService.iniciar(SolicitacaoService.class.getName(), "Iniciando validação de pertencimento do usuário a solicitação");
        if (!solicitacao.emailUsuarioReceptor.equals(email) && !solicitacao.emailUsuarioSolicitante.equals(email)) {
            throw new UsuarioNaoPertenceASolicitacao();
        }
    }

    private Integer atualizarEnderecoSolicitacao(SolicitacaoDto solicitacao, Connection conexao) throws SQLException {
        Integer endereco = 0;
        if (solicitacao.endereco != null) {
            logService.iniciar(SolicitacaoService.class.getName(), "Iniciando cadastro de endereço do usuário de email " + solicitacao.getEmailUsuarioSolicitante());
            endereco = enderecoDao.salvarEndereco(solicitacao.getEndereco(), conexao, solicitacao.emailUsuarioSolicitante, false);
        } else if (enderecoDao.validarExistencia(solicitacao.emailUsuarioSolicitante)) {
            Endereco enderecoSolicitante = enderecoDao.obterEnderecoUsuario(solicitacao.getEmailUsuarioSolicitante());
            endereco = enderecoSolicitante.getCodigoEndereco();
        }
        return endereco;
    }

    private void atualizarLivrosIndisponiveisSolicitacao(SolicitacaoDto solicitacao, Integer numeroSolicitacao, String email, Connection conexao) throws LivroNaoDisponivel, SQLException, LivroJaDesativado, LivroNaoExistente {
        livroService.atualizarLivrosIndisponiveis(numeroSolicitacao, solicitacao.getLivrosUsuarioSolicitante(), email, conexao);
        if (solicitacao.getLivrosTroca() != null && !solicitacao.getLivrosTroca().isEmpty()) {
            livroService.atualizarLivrosIndisponiveis(numeroSolicitacao, solicitacao.getLivrosTroca(), email, conexao);
        }
    }

    private void atualizarLivrosDisponiveisSolicitacao(SolicitacaoDto solicitacao, Integer numeroSolicitacao, String email, Connection conexao) throws LivroNaoDisponivel, SQLException, LivroJaDesativado, LivroNaoExistente {
        livroService.atualizarLivrosDisponiveis(numeroSolicitacao, solicitacao.getLivrosUsuarioSolicitante(), email, conexao);
        if (solicitacao.getLivrosTroca() != null && !solicitacao.getLivrosTroca().isEmpty()) {
            livroService.atualizarLivrosDisponiveis(numeroSolicitacao, solicitacao.getLivrosTroca(), email, conexao);
        }
    }

    public void atualizarSoliciacao(SolicitacaoDto solicitacao) throws SQLException, SolicitacaoNaoExistente {
        try (Connection conexao = bd.obterConexao()) {
            validarExistenciaSolicitacao(solicitacao.getCodigoSolicitacao());
            logService.iniciar(SolicitacaoService.class.getName(), "Iniciando atualização de solicitação de código " + solicitacao.getCodigoSolicitacao());
            enderecoDao.salvarEndereco(solicitacao.getEndereco(), conexao, solicitacao.emailUsuarioSolicitante, false);
            solicitacaoDao.atualizarSolicitacao(solicitacao);
            enderecoDao.atualizarEndereco(solicitacao.getEndereco(), conexao, solicitacao.emailUsuarioSolicitante, solicitacao.getEndereco().enderecoPrincipal);
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


    public List<NotificacaoSolicitacaoDto> obterNotificacoesSolicitacao(String email) throws SQLException {
        try {
            logService.iniciar(SolicitacaoService.class.getName(), "Iniciando busca de notificações de solicitação para o email " + email);
            List<NotificacaoSolicitacao> notificacoes = solicitacaoDao.obterNotificacoesUsuario(email);
            logService.sucesso(SolicitacaoService.class.getName(), "Busca de notificações de solicitação finalizada para o email " + email);
            return notificacoes.stream().map(NotificacaoSolicitacaoDto::deModel).toList();
        } catch (Exception e) {
            logService.erro(SolicitacaoService.class.getName(), "Ocorreu um erro na busca de notificações de solicitação para o email " + email, e);
            throw e;
        }
    }

}
