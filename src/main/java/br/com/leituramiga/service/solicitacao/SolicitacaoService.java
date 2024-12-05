package br.com.leituramiga.service.solicitacao;

import br.com.leituramiga.dao.FabricaDeConexoes;
import br.com.leituramiga.dao.solicitacao.SolicitacaoDao;
import br.com.leituramiga.dto.endereco.EnderecoDto;
import br.com.leituramiga.dto.livro.LivroSolicitacaoDto;
import br.com.leituramiga.dto.solicitacao.AceiteSolicitacaoDto;
import br.com.leituramiga.dto.solicitacao.NotificacaoSolicitacaoDto;
import br.com.leituramiga.dto.solicitacao.SolicitacaoDto;
import br.com.leituramiga.dto.usuario.UsuarioDto;
import br.com.leituramiga.model.DataHora;
import br.com.leituramiga.model.exception.EnderecoNaoExistente;
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
import br.com.leituramiga.model.solicitacao.TipoSolicitacao;
import br.com.leituramiga.service.UsuarioService;
import br.com.leituramiga.service.autenticacao.LogService;
import br.com.leituramiga.service.email.EmailService;
import br.com.leituramiga.service.endereco.EnderecoService;
import br.com.leituramiga.service.imagem.ImagemService;
import br.com.leituramiga.service.livro.LivroService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@ApplicationScoped
public class SolicitacaoService {

    @Inject
    EnderecoService enderecoService;

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
    ImagemService imagemService;

    @Inject
    FabricaDeConexoes bd;

    public void aceitarSolicitacao(Integer codigo, AceiteSolicitacaoDto aceiteSolicitacaoDto, String email) throws SQLException, SolicitacaoExcedeuPrazoEntrega, SolicitacaoNaoExistente, SolicitacaoNaoPendente, LivroNaoDisponivel, LivroJaDesativado, LivroNaoExistente, UsuarioNaoPertenceASolicitacao, UsuarioNaoAtivo, ClassNotFoundException, UsuarioNaoExistente, EnderecoNaoExistente, IOException {
        Connection conexao = bd.obterConexao();
        try {
            conexao.setAutoCommit(false);
            validarAceiteSolicitacao(codigo);
            SolicitacaoDto solicitacao = obterSolicitacao(codigo);
            validarUsuarioPertenceSolicitacao(solicitacao, email);
            atualizarLivrosDoAceite(aceiteSolicitacaoDto, codigo, conexao, solicitacao, email);
            solicitacaoDao.recusarSolicitacoesComLivroIndisponivel(solicitacao.getLivrosUsuarioSolicitante(), solicitacao.codigoSolicitacao, conexao);
            if (aceiteSolicitacaoDto.livros != null)
                solicitacaoDao.recusarSolicitacoesComLivroIndisponivel(aceiteSolicitacaoDto.livros, solicitacao.codigoSolicitacao, conexao);
            solicitacao.enderecoReceptor = aceiteSolicitacaoDto.endereco;
            if (aceiteSolicitacaoDto.endereco != null)
                atualizarEnderecoAceiteSolicitacao(solicitacao, conexao, email, codigo);
            logService.iniciar(SolicitacaoService.class.getName(), "Iniciando aceitação de solicitação de código " + codigo);
            solicitacaoDao.aceitarSolicitacao(codigo, conexao);
            atualizarCodigoUltimaSolicitacaoLivros(codigo, conexao);
            conexao.commit();
            enviarEmailSolicitacaoAceita(solicitacao);
            logService.sucesso(SolicitacaoService.class.getName(), "Aceitação de solicitação finalizada de código " + codigo);
        } catch (Exception e) {
            logService.erro(SolicitacaoService.class.getName(), "Ocorreu um erro na aceitação de solicitação de código " + codigo, e);
            conexao.rollback();
            throw e;
        } finally {
            bd.desconectar(conexao);
        }

    }

    private void atualizarCodigoUltimaSolicitacaoLivros(Integer codigoSolicitacao, Connection conexao) throws SQLException {
        List<LivroSolicitacaoDto> livros = obterLivrosSolicitacao(codigoSolicitacao, conexao);
        livroService.atualizarCodigoUltimaSolicitacao(livros, codigoSolicitacao, conexao);
    }

    private void atualizarLivrosDoAceite(AceiteSolicitacaoDto aceiteSolicitacaoDto, Integer codigo, Connection conexao, SolicitacaoDto solicitacao, String email) throws SQLException, LivroNaoDisponivel, LivroJaDesativado, LivroNaoExistente {
        if (aceiteSolicitacaoDto.livros != null) {
            solicitacaoDao.salvarLivroSolicitacao(aceiteSolicitacaoDto.livros, solicitacao.codigoSolicitacao, conexao);
        }
        solicitacao.livrosTroca = aceiteSolicitacaoDto.livros;
        if (solicitacao.codigoTipoSolicitacao == TipoSolicitacao.EMPRESTIMO.id) {
            atualizarLivrosEmprestadosSolicitacao(solicitacao, codigo, email, conexao);
        } else {
            atualizarLivrosIndisponiveisSolicitacao(solicitacao, codigo, email, conexao);
        }
    }

    private void enviarEmailSolicitacaoAceita(SolicitacaoDto solicitacao) throws SQLException, UsuarioNaoAtivo, UsuarioNaoExistente, IOException {
        UsuarioDto usuarioReceptor = usuarioService.obterUsuarioPorIdentificador(solicitacao.getEmailUsuarioReceptor());
        UsuarioDto usuarioSolicitante = usuarioService.obterUsuarioPorIdentificador(solicitacao.getEmailUsuarioSolicitante());
        emailService.enviarEmailSolicitacaoAceita(solicitacao.getEmailUsuarioSolicitante(), usuarioReceptor.nome, usuarioSolicitante.nome);
    }

    public void recusarSolicitacao(Integer codigo, String motivoRecusa, String email) throws SQLException, SolicitacaoNaoExistente, SolicitacaoNaoPendente, SolicitacaoExcedeuPrazoEntrega, LivroNaoDisponivel, LivroJaDesativado, LivroNaoExistente, UsuarioNaoPertenceASolicitacao, UsuarioNaoAtivo, ClassNotFoundException, UsuarioNaoExistente, IOException {
        try (Connection conexao = bd.obterConexao()) {
            logService.iniciar(SolicitacaoService.class.getName(), "Iniciando a validação da data de entrega da solicitação " + codigo);
            if (!solicitacaoDao.validarSolicitacaoDentroPrazoEntrega(codigo))
                throw new SolicitacaoExcedeuPrazoEntrega();
            logService.iniciar(SolicitacaoService.class.getName(), "Iniciando recusa de solicitação de código " + codigo);
            SolicitacaoDto solicitacao = obterSolicitacao(codigo);
            validarUsuarioPertenceSolicitacao(solicitacao, email);
            atualizarLivrosDisponiveisSolicitacao(solicitacao, codigo, email, conexao);
            solicitacaoDao.recusarSolicitacao(codigo, motivoRecusa);
            enviarEmailSolicitacaoRecusada(solicitacao, email, motivoRecusa);
            logService.sucesso(SolicitacaoService.class.getName(), "Recusa de solicitação finalizada de código " + codigo);
        } catch (Exception e) {
            logService.erro(SolicitacaoService.class.getName(), "Ocorreu um erro na recusa de solicitação de código " + codigo, e);
            throw e;
        }
    }

    private void enviarEmailSolicitacaoRecusada(SolicitacaoDto solicitacao, String email, String motivoRecusa) throws SQLException, UsuarioNaoAtivo, UsuarioNaoExistente, IOException {
        UsuarioDto usuarioReceptor = usuarioService.obterUsuarioPorIdentificador(solicitacao.getEmailUsuarioReceptor());
        UsuarioDto usuarioSolicitante = usuarioService.obterUsuarioPorIdentificador(solicitacao.getEmailUsuarioSolicitante());
        if (email.equals(solicitacao.getEmailUsuarioSolicitante())) {
            emailService.enviarEmailSolicitacaoRecusada(solicitacao.getEmailUsuarioReceptor(), motivoRecusa, usuarioReceptor.nome, usuarioSolicitante.nome, usuarioReceptor.nome);
        } else {
            emailService.enviarEmailSolicitacaoRecusada(solicitacao.getEmailUsuarioSolicitante(), motivoRecusa, usuarioReceptor.nome, usuarioSolicitante.nome, usuarioSolicitante.nome);
        }
    }

    public void finalizarSolicitacao(Integer codigo, String email) throws SQLException, SolicitacaoNaoExistente, SolicitacaoNaoPendente, SolicitacaoExcedeuPrazoEntrega, UsuarioNaoPertenceASolicitacao, LivroNaoDisponivel, LivroJaDesativado, LivroNaoExistente {
        try (Connection conexao = bd.obterConexao()) {
            logService.iniciar(SolicitacaoService.class.getName(), "Iniciando a validação da data de entrega da solicitação " + codigo);
            if (!solicitacaoDao.validarSolicitacaoDentroPrazoEntrega(codigo))
                throw new SolicitacaoExcedeuPrazoEntrega();
            logService.iniciar(SolicitacaoService.class.getName(), "Iniciando finalização de solicitação de código " + codigo);
            SolicitacaoDto solicitacao = obterSolicitacao(codigo);
            validarUsuarioPertenceSolicitacao(solicitacao, email);
            solicitacaoDao.finalizarSolicitacao(codigo);
            if (solicitacao.codigoTipoSolicitacao == TipoSolicitacao.EMPRESTIMO.id) {
                atualizarLivrosDisponiveisSolicitacao(solicitacao, codigo, email, conexao);
            }
            logService.sucesso(SolicitacaoService.class.getName(), "Finalização de solicitação finalizada de código " + codigo);
        } catch (Exception e) {
            logService.erro(SolicitacaoService.class.getName(), "Ocorreu um erro na finalização de solicitação de código " + codigo, e);
            throw e;
        }
    }

    public void cancelarSolicitacao(Integer codigo, String motivoRecusa, String email) throws SQLException, SolicitacaoNaoExistente, SolicitacaoNaoAberta, LivroNaoDisponivel, LivroJaDesativado, LivroNaoExistente, UsuarioNaoPertenceASolicitacao, UsuarioNaoAtivo, ClassNotFoundException, UsuarioNaoExistente, IOException {
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
            enviarCancelamentoSolicitacao(solicitacao, email, motivoRecusa);
        } catch (Exception e) {
            logService.erro(SolicitacaoService.class.getName(), "Ocorreu um erro no cancelamento de solicitação de código " + codigo, e);
            throw e;
        }
    }

    private void enviarCancelamentoSolicitacao(SolicitacaoDto solicitacao, String email, String motivoRecusa) throws SQLException, UsuarioNaoAtivo, UsuarioNaoExistente, IOException {
        UsuarioDto usuarioReceptor = usuarioService.obterUsuarioPorIdentificador(solicitacao.getEmailUsuarioReceptor());
        UsuarioDto usuarioSolicitante = usuarioService.obterUsuarioPorIdentificador(solicitacao.getEmailUsuarioSolicitante());
        if (email.equals(solicitacao.getEmailUsuarioSolicitante())) {
            emailService.enviarEmailSolicitacaoCancelada(solicitacao.getEmailUsuarioReceptor(), motivoRecusa, usuarioReceptor.nome, usuarioSolicitante.nome, usuarioReceptor.nome);
        } else {
            emailService.enviarEmailSolicitacaoCancelada(solicitacao.getEmailUsuarioSolicitante(), motivoRecusa, usuarioReceptor.nome, usuarioSolicitante.nome, usuarioSolicitante.nome);
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

    private List<LivroSolicitacaoDto> obterLivrosSolicitacao(Integer codigo, Connection conexao) throws SQLException {
        try {
            logService.iniciar(SolicitacaoService.class.getName(), "Iniciando busca de livros da solicitação de código " + codigo);
            List<LivroSolicitacaoDto> livros = solicitacaoDao.obterLivrosSolicitacao(codigo, conexao);
            logService.sucesso(SolicitacaoService.class.getName(), "Busca de livros da solicitação finalizada de código " + codigo);
            return livros;
        } catch (Exception e) {
            logService.erro(SolicitacaoService.class.getName(), "Ocorreu um erro na busca de livros da solicitação de código " + codigo, e);
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

    public List<SolicitacaoDto> obterHistoricoSolicitacoesPaginadas(Integer pagina, Integer tamanhoPagina, String email, String dataInicio, String dataFim) throws SQLException, DataHora.DataInvalida {
        try {
            logService.iniciar(SolicitacaoService.class.getName(), "Iniciando busca do histórico de solicitações paginadas");
            DataHora dataInicioFormatada = dataInicio != null ? DataHora.deString(dataInicio, "yyyy-MM-dd") : null;
            DataHora dataFimFormatada = dataFim != null ? DataHora.deString(dataFim, "yyyy-MM-dd") : null;
            List<Solicitacao> solicitacoes = solicitacaoDao.obterHistoricoSolicitacoesPaginadas(pagina, tamanhoPagina, email, dataInicioFormatada, dataFimFormatada);
            logService.sucesso(SolicitacaoService.class.getName(), "Busca do histórico de solicitações paginadas finalizada");
            return solicitacoes.stream().map(SolicitacaoDto::deModel).toList();
        } catch (Exception e) {
            logService.erro(SolicitacaoService.class.getName(), "Ocorreu um erro na busca do histórico de solicitações paginadas", e);
            throw e;
        }
    }

    public void cadastrarSolicitacao(SolicitacaoDto solicitacao, String email) throws SQLException, UsuarioNaoAtivo, UsuarioNaoExistente, EnderecoNaoExistente, IOException {
        Connection conexao = null;
        try {
            conexao = bd.obterConexao();
            conexao.setAutoCommit(false);
            logService.iniciar(SolicitacaoService.class.getName(), "Iniciando cadastro de solicitação");
            Integer numeroSolicitacao = solicitacaoDao.cadastrarSolicitacao(solicitacao, conexao);
            solicitacaoDao.salvarLivroSolicitacao(solicitacao.getLivrosUsuarioSolicitante(), numeroSolicitacao, conexao);
            solicitacaoDao.salvarLivroSolicitacao(solicitacao.getLivrosTroca(), numeroSolicitacao, conexao);
            logService.iniciar(SolicitacaoService.class.getName(), "Iniciando validação do endereço do usuário de email " + solicitacao.getEmailUsuarioSolicitante());
            Integer numeroEndereco = atualizarEnderecoCadastroSolicitacao(solicitacao, conexao, email, numeroSolicitacao);
            logService.sucesso(SolicitacaoService.class.getName(), "Cadastro de solicitação finalizado");
            conexao.commit();
            enviarEmailSolicitacao(solicitacao, numeroEndereco);
        } catch (Exception e) {
            logService.erro(SolicitacaoService.class.getName(), "Ocorreu um erro no cadastro de solicitação", e);
            if (conexao != null) conexao.rollback();
            throw e;
        } finally {
            bd.desconectar(conexao);
        }
    }

    private void enviarEmailSolicitacao(SolicitacaoDto solicitacao, Integer endereco) throws SQLException, UsuarioNaoAtivo, UsuarioNaoExistente, IOException {
        UsuarioDto usuarioReceptor = usuarioService.obterUsuarioPorIdentificador(solicitacao.getEmailUsuarioReceptor());
        UsuarioDto usuarioSolicitante = usuarioService.obterUsuarioPorIdentificador(solicitacao.getEmailUsuarioSolicitante());
        EnderecoDto enderecoDto = enderecoService.obterEnderecoPorId(endereco);
        solicitacao.enderecoSolicitante = enderecoDto;
        emailService.enviarEmailSolicitacaoDeLivros(solicitacao.getEmailUsuarioReceptor(), solicitacao, usuarioReceptor.nome, usuarioSolicitante.nome);
    }

    private void validarUsuarioPertenceSolicitacao(SolicitacaoDto solicitacao, String email) throws UsuarioNaoPertenceASolicitacao {
        logService.iniciar(SolicitacaoService.class.getName(), "Iniciando validação de pertencimento do usuário a solicitação");
        if (!solicitacao.emailUsuarioReceptor.equals(email) && !solicitacao.emailUsuarioSolicitante.equals(email)) {
            throw new UsuarioNaoPertenceASolicitacao();
        }
    }

    private Integer atualizarEnderecoCadastroSolicitacao(SolicitacaoDto solicitacao, Connection conexao, String email, Integer codigoSolicitacao) throws SQLException, EnderecoNaoExistente {
        Integer numeroEndereco = solicitacao.getEnderecoSolicitante() == null ? null : solicitacao.getEnderecoSolicitante().getCodigoEndereco();
        if (solicitacao.enderecoSolicitante != null && numeroEndereco == null) {
            // Se o endereço for nulo e o número do endereço for nulo, cadastra o endereço da solicitação
            numeroEndereco = cadastrarEnderecoSolicitacaoSolicitante(solicitacao, email, conexao, codigoSolicitacao);
        } else if (enderecoService.validarExistenciaPorEmail(email)) {
            // Se o endereço do usuário já existir, atualiza o endereço da solicitação e o relacionamento do endereço com a solicitação
            logService.iniciar(SolicitacaoService.class.getName(), "Iniciando busca de endereço do usuário de email " + email);
            EnderecoDto endereco = enderecoService.obterEnderecoUsuario(email);
            enderecoService.vincularEnderecoSolicitacao(endereco.codigoEndereco, codigoSolicitacao, email, conexao);
            numeroEndereco = endereco.codigoEndereco;
        }
        return numeroEndereco;
    }

    private Integer atualizarEnderecoAceiteSolicitacao(SolicitacaoDto solicitacao, Connection conexao, String email, Integer codigoSolicitacao) throws SQLException, EnderecoNaoExistente {
        Integer numeroEndereco = solicitacao.getEnderecoReceptor() == null ? null : solicitacao.getEnderecoReceptor().getCodigoEndereco();
        if (solicitacao.getEnderecoReceptor() != null && numeroEndereco == null) {
            // Se o endereço for nulo e o número do endereço for nulo, cadastra o endereço da solicitação
            numeroEndereco = cadastrarEnderecoSolicitacaoReceptor(solicitacao, email, conexao, codigoSolicitacao);
        } else if (enderecoService.validarExistenciaPorEmail(email)) {
            // Se o endereço do usuário já existir, atualiza o endereço da solicitação e o relacionamento do endereço com a solicitação
            logService.iniciar(SolicitacaoService.class.getName(), "Iniciando busca de endereço do usuário de email " + email);
            EnderecoDto endereco = enderecoService.obterEnderecoUsuario(email);
            enderecoService.vincularEnderecoSolicitacao(endereco.codigoEndereco, codigoSolicitacao, email, conexao);
            numeroEndereco = endereco.codigoEndereco;
        }
        return numeroEndereco;
    }

    private Integer cadastrarEnderecoSolicitacaoReceptor(SolicitacaoDto solicitacao, String email, Connection conexao, Integer codigoSolicitacao) throws SQLException {
        logService.iniciar(SolicitacaoService.class.getName(), "Iniciando cadastro de endereço do usuário de email " + solicitacao.getEmailUsuarioSolicitante());
        return enderecoService.salvarEnderecoSolicitacao(solicitacao.getEnderecoReceptor(), email, codigoSolicitacao, conexao, false);
    }

    private Integer cadastrarEnderecoSolicitacaoSolicitante(SolicitacaoDto solicitacao, String email, Connection conexao, Integer codigoSolicitacao) throws SQLException {
        logService.iniciar(SolicitacaoService.class.getName(), "Iniciando cadastro de endereço do usuário de email " + solicitacao.getEmailUsuarioSolicitante());
        return enderecoService.salvarEnderecoSolicitacao(solicitacao.getEnderecoSolicitante(), email, codigoSolicitacao, conexao, false);
    }

    private void atualizarLivrosIndisponiveisSolicitacao(SolicitacaoDto solicitacao, Integer numeroSolicitacao, String email, Connection conexao) throws LivroNaoDisponivel, SQLException, LivroJaDesativado, LivroNaoExistente {
        livroService.atualizarLivrosIndisponiveis(numeroSolicitacao, solicitacao.getLivrosUsuarioSolicitante(), email, conexao);
        if (solicitacao.getLivrosTroca() != null && !solicitacao.getLivrosTroca().isEmpty()) {
            livroService.atualizarLivrosIndisponiveis(numeroSolicitacao, solicitacao.getLivrosTroca(), email, conexao);
        }
    }

    private void atualizarLivrosEmprestadosSolicitacao(SolicitacaoDto solicitacao, Integer numeroSolicitacao, String email, Connection conexao) throws LivroNaoDisponivel, SQLException, LivroJaDesativado, LivroNaoExistente {
        livroService.atualizarLivrosEmprestados(numeroSolicitacao, solicitacao.getLivrosUsuarioSolicitante(), email, conexao);
        if (solicitacao.getLivrosTroca() != null && !solicitacao.getLivrosTroca().isEmpty()) {
            livroService.atualizarLivrosEmprestados(numeroSolicitacao, solicitacao.getLivrosTroca(), email, conexao);
        }
    }

    private void atualizarLivrosDisponiveisSolicitacao(SolicitacaoDto solicitacao, Integer numeroSolicitacao, String email, Connection conexao) throws LivroNaoDisponivel, SQLException, LivroJaDesativado, LivroNaoExistente {
        livroService.atualizarLivrosDisponiveis(numeroSolicitacao, solicitacao.getLivrosUsuarioSolicitante(), email, conexao);
        if (solicitacao.getLivrosTroca() != null && !solicitacao.getLivrosTroca().isEmpty()) {
            livroService.atualizarLivrosDisponiveis(numeroSolicitacao, solicitacao.getLivrosTroca(), email, conexao);
        }
    }

    public void atualizarSolicitacao(SolicitacaoDto solicitacao, String email) throws SQLException, SolicitacaoNaoExistente, EnderecoNaoExistente {
        try (Connection conexao = bd.obterConexao()) {
            validarExistenciaSolicitacao(solicitacao.getCodigoSolicitacao());
            logService.iniciar(SolicitacaoService.class.getName(), "Iniciando atualização de solicitação de código " + solicitacao.getCodigoSolicitacao());
            solicitacaoDao.atualizarSolicitacao(solicitacao);
            atualizarEnderecoDaSolicitacao(solicitacao, conexao, email);
            logService.sucesso(SolicitacaoService.class.getName(), "Atualização de solicitação finalizada de código " + solicitacao.getCodigoSolicitacao());
        } catch (Exception e) {
            logService.erro(SolicitacaoService.class.getName(), "Ocorreu um erro na atualização de solicitação de código " + solicitacao.getCodigoSolicitacao(), e);
            throw e;
        }
    }

    public void atualizarEnderecoDaSolicitacao(SolicitacaoDto solicitacao, Connection conexao, String email) throws SQLException, EnderecoNaoExistente {
        if (email.equals(solicitacao.getEnderecoSolicitante().emailUsuario)) {
            atualizarEnderecoSolicitante(solicitacao, conexao, email);
        } else {
            atualizarEnderecoReceptor(solicitacao, conexao, email);
        }
    }

    private void atualizarEnderecoSolicitante(SolicitacaoDto solicitacao, Connection conexao, String email) throws EnderecoNaoExistente, SQLException {
        EnderecoDto enderecoDto = solicitacao.getEnderecoSolicitante();
        if (enderecoDto.getEnderecoPrincipal() == null || !enderecoDto.getEnderecoPrincipal()) {
            atualizarEnderecoSolicitacaoQuandoNaoPrincipal(solicitacao, email, conexao, enderecoDto);
        } else {
            enderecoService.atualizarVinculoEnderecoSolicitacao(email, solicitacao.getCodigoSolicitacao(), enderecoDto.getCodigoEndereco(), conexao);
        }
    }

    private void atualizarEnderecoReceptor(SolicitacaoDto solicitacao, Connection conexao, String email) throws EnderecoNaoExistente, SQLException {
        EnderecoDto enderecoDto = solicitacao.getEnderecoReceptor();
        if (enderecoDto.getEnderecoPrincipal() == null || !enderecoDto.getEnderecoPrincipal()) {
            atualizarEnderecoSolicitacaoQuandoNaoPrincipal(solicitacao, email, conexao, enderecoDto);
        } else {
            enderecoService.atualizarVinculoEnderecoSolicitacao(email, solicitacao.getCodigoSolicitacao(), enderecoDto.getCodigoEndereco(), conexao);
        }
    }

    private void atualizarEnderecoSolicitacaoQuandoNaoPrincipal(SolicitacaoDto solicitacao, String email, Connection conexao, EnderecoDto enderecoDto) throws SQLException, EnderecoNaoExistente {
        EnderecoDto endereco = enderecoService.obterEnderecoPorId(enderecoDto.getCodigoEndereco());
        if (endereco.enderecoPrincipal) {
            enderecoService.salvarEnderecoSolicitacaoExistente(enderecoDto, email, solicitacao.getCodigoSolicitacao(), conexao, false);
        } else {
            enderecoService.atualizarEnderecoSolicitacao(enderecoDto, conexao, solicitacao.getCodigoSolicitacao(), false);
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


    public List<NotificacaoSolicitacaoDto> obterNotificacoesSolicitacao(String email) throws SQLException, IOException {
        try {
            logService.iniciar(SolicitacaoService.class.getName(), "Iniciando busca de notificações de solicitação para o email " + email);
            List<NotificacaoSolicitacao> notificacoes = solicitacaoDao.obterNotificacoesUsuario(email);
            for (NotificacaoSolicitacao notificacao : notificacoes) {
                String imagem = imagemService.obterImagemUsuario(notificacao.getImagem());
                notificacao.setImagem(imagem);
            }
            logService.sucesso(SolicitacaoService.class.getName(), "Busca de notificações de solicitação finalizada para o email " + email);
            return notificacoes.stream().map(NotificacaoSolicitacaoDto::deModel).toList();
        } catch (Exception e) {
            logService.erro(SolicitacaoService.class.getName(), "Ocorreu um erro na busca de notificações de solicitação para o email " + email, e);
            throw e;
        }
    }

    public boolean validarUsuarioPossuiSolicitacaoAberta(String email) throws SQLException {
        try {
            logService.iniciar(SolicitacaoService.class.getName(), "Iniciando validação de solicitação aberta para o email " + email);
            boolean possuiSolicitacaoAberta = solicitacaoDao.validarUsuarioPossuiSolicitacao(email);
            logService.sucesso(SolicitacaoService.class.getName(), "Validação de solicitação aberta finalizada para o email " + email);
            return possuiSolicitacaoAberta;
        } catch (Exception e) {
            logService.erro(SolicitacaoService.class.getName(), "Ocorreu um erro na validação de solicitação aberta para o email " + email, e);
            throw e;
        }
    }


}
