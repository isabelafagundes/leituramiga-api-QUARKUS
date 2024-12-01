package br.com.leituramiga.service.livro;

import br.com.leituramiga.dao.FabricaDeConexoes;
import br.com.leituramiga.dao.livro.LivroDao;
import br.com.leituramiga.dto.livro.LivroDto;
import br.com.leituramiga.dto.livro.LivroSolicitacaoDto;
import br.com.leituramiga.model.exception.livro.LivroJaDesativado;
import br.com.leituramiga.model.exception.livro.LivroNaoDisponivel;
import br.com.leituramiga.model.exception.livro.LivroNaoExistente;
import br.com.leituramiga.model.livro.Livro;
import br.com.leituramiga.service.autenticacao.HashService;
import br.com.leituramiga.service.autenticacao.LogService;
import br.com.leituramiga.service.imagem.ImagemService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@ApplicationScoped
public class LivroService {

    private static final Logger log = LoggerFactory.getLogger(LivroService.class);
    @Inject
    LivroDao dao;

    @Inject
    LogService logService;

    @Inject
    ImagemService imagemService;

    @Inject
    FabricaDeConexoes bd;

    public LivroDto obterLivro(Integer numero, String email) throws SQLException, LivroNaoExistente, IOException, LivroNaoDisponivel {
        try {
            logService.iniciar(LivroService.class.getName(), "Iniciando a validação da existência do livro de número " + numero);
            if (!dao.verificarExistenciaLivro(numero)) throw new LivroNaoExistente();
            logService.iniciar(LivroService.class.getName(), "Iniciando a obtenção do livro de número " + numero);
            Livro livro = dao.obterLivroPorNumero(numero);
            if (!dao.verificarStatusDisponivel(numero) && !livro.getEmailUsuario().equals(email)) {
                throw new LivroNaoDisponivel();
            }
            String imagemLivro = imagemService.obterImagemLivro(livro.getCaminhoImagem());
            livro.setImagem(imagemLivro);
            return LivroDto.deModel(livro);
        } catch (Exception erro) {
            logService.erro(LivroService.class.getName(), "Ocorreu um erro na obtenção do livro de número " + numero, erro);
            throw erro;
        }
    }

    public List<LivroDto> obterLivrosPaginados(
            Integer pagina,
            Integer tamanhoPagina,
            String pesquisa,
            Integer numeroCidade,
            Integer numeroCategoria,
            Integer numeroInstituicao,
            Integer tipoSolicitacao,
            String emailUsuario
    ) throws SQLException, IOException {
        try {
            logService.iniciar(LivroService.class.getName(), "Iniciando a obtenção dos livros da página " + pagina + " e tamanho " + tamanhoPagina);
            List<Livro> livros = dao.obterLivrosPaginados(pagina, tamanhoPagina, pesquisa, numeroCategoria, numeroInstituicao, numeroCidade, tipoSolicitacao, emailUsuario);
            logService.sucesso(LivroService.class.getName(), "Sucesso na obtenção dos livros da página " + pagina + " e tamanho " + tamanhoPagina);
            for (Livro livro : livros) {
                String imagemLivro = imagemService.obterImagemLivro(livro.getCaminhoImagem());
                livro.setImagem(imagemLivro);
            }
            return livros.stream().map(LivroDto::deModel).toList();
        } catch (Exception erro) {
            logService.erro(LivroService.class.getName(), "Ocorreu um erro na obtenção dos livros da página " + pagina + " e tamanho " + tamanhoPagina, erro);
            throw erro;
        }
    }

    public List<LivroDto> obterLivrosUsuario(String email) throws SQLException, IOException {
        String md5Login = HashService.obterMd5Email(email);
        try {
            logService.iniciar(LivroService.class.getName(), "Iniciando a obtenção dos livros do usuário de email " + md5Login);
            List<Livro> livros = dao.obterLivroPorUsuario(email);
            for (Livro livro : livros) {
                String imagemLivro = imagemService.obterImagemLivro(livro.getCaminhoImagem());
                livro.setImagem(imagemLivro);
            }
            logService.sucesso(LivroService.class.getName(), "Sucesso na obtenção dos livros do usuário de email " + md5Login);
            return livros.stream().map(LivroDto::deModel).toList();
        } catch (Exception e) {
            logService.erro(LivroService.class.getName(), "Ocorreu um erro na obtenção dos livros do usuário de email " + md5Login, e);
            throw e;
        }
    }

    public void salvarLivro(LivroDto livro, String email) throws SQLException, LivroNaoDisponivel, LivroJaDesativado, LivroNaoExistente, IOException {
        String md5Login = HashService.obterMd5Email(email);
        try {
            if (livro.getCodigoLivro() != null) validarStatusLivro(livro.getCodigoLivro(), email);
            logService.iniciar(LivroService.class.getName(), "Iniciando a obtenção dos livros do usuário de email " + md5Login);
            Integer id = dao.salvarLivro(livro);
            salvarImagemLivro(livro, email, id);
            logService.sucesso(LivroService.class.getName(), "Sucesso na obtenção dos livros do usuário de email " + md5Login);
        } catch (Exception e) {
            logService.erro(LivroService.class.getName(), "Ocorreu um erro na obtenção dos livros do usuário de email " + md5Login, e);
            throw e;
        }
    }

    public void salvarImagemLivro(LivroDto livro, String email, Integer id) throws IOException, SQLException {
        try {
            logService.iniciar(ImagemService.class.getName(), "Iniciando o salvamento da imagem do livro");
            String caminhoImagem;
            if (livro.imagem != null) {
                boolean imagemExiste = dao.validarExistenciaImagem(id);
                caminhoImagem = imagemService.salvarImagemLivro(livro.imagem, email, id);
                if (caminhoImagem == null) return;
                if (imagemExiste) {
                    logService.iniciar(LivroService.class.getName(), "Sucesso em atualizar a imagem do livro do usuário de email " + email);
                    dao.atualizarImagemLivro(caminhoImagem, id);
                } else {
                    logService.iniciar(LivroService.class.getName(), "Sucesso em salvar a imagem do livro do usuário de email " + email);
                    dao.salvarImagemLivro(caminhoImagem, id);
                }
            }
        } catch (Exception e) {
            logService.erro(ImagemService.class.getName(), "Ocorreu um erro ao salvar a imagem do livro", e);
            throw e;
        }
    }

    public void atualizarLivro(LivroDto livro, String email) throws SQLException, LivroNaoDisponivel, LivroJaDesativado, LivroNaoExistente, IOException {
        String md5Login = HashService.obterMd5Email(email);
        try {
            if (livro.getCodigoLivro() != null) validarStatusLivro(livro.getCodigoLivro(), email);
            logService.iniciar(LivroService.class.getName(), "Iniciando a obtenção dos livros do usuário de email " + md5Login);
            dao.atualizarLivro(livro, email);
            salvarImagemLivro(livro, email, livro.getCodigoLivro());
            logService.sucesso(LivroService.class.getName(), "Sucesso na obtenção dos livros do usuário de email " + md5Login);
        } catch (Exception e) {
            logService.erro(LivroService.class.getName(), "Ocorreu um erro na obtenção dos livros do usuário de email " + md5Login, e);
            throw e;
        }
    }

    public void deletarLivro(Integer numeroLivro, String email) throws SQLException, LivroNaoDisponivel, LivroJaDesativado, LivroNaoExistente {
        String md5Login = HashService.obterMd5Email(email);
        try {
            validarStatusLivro(numeroLivro, email);
            logService.iniciar(LivroService.class.getName(), "Iniciando a deleção do livro de número " + numeroLivro + " do usuário de email " + md5Login);
            dao.deletarLivro(numeroLivro, email);
            logService.sucesso(LivroService.class.getName(), "Sucesso na deleção do livro de número " + numeroLivro + " do usuário de email " + md5Login);
        } catch (Exception e) {
            logService.erro(LivroService.class.getName(), "Ocorreu um erro na deleção do livro de número " + numeroLivro + " do usuário de email " + md5Login, e);
            throw e;
        }
    }

    private void validarStatusLivro(Integer numeroLivro, String email) throws LivroNaoExistente, SQLException, LivroJaDesativado, LivroNaoDisponivel {
        String md5Login = HashService.obterMd5Email(email);
        logService.iniciar(LivroService.class.getName(), "Iniciando a validação da existência do livro de número " + numeroLivro + " do usuário de email " + md5Login);
        if (!dao.verificarExistenciaLivro(numeroLivro)) throw new LivroNaoExistente();
        logService.iniciar(LivroService.class.getName(), "Iniciando a validação do status desativado do livro de número " + numeroLivro + " do usuário de email " + md5Login);
        if (dao.verificarStatusDesativado(numeroLivro)) throw new LivroJaDesativado();
        logService.iniciar(LivroService.class.getName(), "Iniciando a validação do status disponível do livro de número " + numeroLivro + " do usuário de email " + md5Login);
        if (!dao.verificarStatusDisponivel(numeroLivro)) throw new LivroNaoDisponivel();
    }


    public void atualizarLivrosIndisponiveis(Integer numeroSolicitacao, List<LivroSolicitacaoDto> livroSolicitacaoDtos, String email, Connection conexao) throws SQLException, LivroNaoDisponivel, LivroJaDesativado, LivroNaoExistente {
        try {
            logService.iniciar(LivroService.class.getName(), "Iniciando a validação dos livros da solicitação");
            for (LivroSolicitacaoDto livroSolicitacaoDto : livroSolicitacaoDtos) {
                validarStatusLivro(livroSolicitacaoDto.codigoLivro, email);
            }
            if (livroSolicitacaoDtos.isEmpty()) return;
            logService.iniciar(LivroService.class.getName(), "Iniciando a atualização dos livros indisponíveis");
            dao.atualizarLivrosIndisponiveis(numeroSolicitacao, livroSolicitacaoDtos, conexao);
            logService.sucesso(LivroService.class.getName(), "Sucesso na atualização dos livros indisponíveis");
        } catch (Exception e) {
            logService.erro(LivroService.class.getName(), "Ocorreu um erro na atualização dos livros indisponíveis", e);
            throw e;
        }
    }

    public void atualizarLivrosEmprestados(Integer numeroSolicitacao, List<LivroSolicitacaoDto> livroSolicitacaoDtos, String email, Connection conexao) throws SQLException, LivroNaoDisponivel, LivroJaDesativado, LivroNaoExistente {
        try {
            logService.iniciar(LivroService.class.getName(), "Iniciando a validação dos livros da solicitação");
            for (LivroSolicitacaoDto livroSolicitacaoDto : livroSolicitacaoDtos) {
                validarStatusLivro(livroSolicitacaoDto.codigoLivro, email);
            }
            if (livroSolicitacaoDtos.isEmpty()) return;
            logService.iniciar(LivroService.class.getName(), "Iniciando a atualização dos livros emprestados");
            dao.atualizarLivrosEmprestados(numeroSolicitacao, livroSolicitacaoDtos, conexao);
            logService.sucesso(LivroService.class.getName(), "Sucesso na atualização dos livros emprestados");
        } catch (Exception e) {
            logService.erro(LivroService.class.getName(), "Ocorreu um erro na atualização dos livros emprestados", e);
            throw e;
        }
    }

    public void atualizarLivrosDisponiveis(Integer numeroSolicitacao, List<LivroSolicitacaoDto> livroSolicitacaoDtos, String email, Connection conexao) throws SQLException, LivroNaoDisponivel, LivroJaDesativado, LivroNaoExistente {
        try {
            logService.iniciar(LivroService.class.getName(), "Iniciando a validação dos livros da solicitação");
            for (LivroSolicitacaoDto livroSolicitacaoDto : livroSolicitacaoDtos) {
                Integer numeroLivro = livroSolicitacaoDto.codigoLivro;
                logService.iniciar(LivroService.class.getName(), "Iniciando a validação da existência do livro de número " + numeroLivro + " do usuário de email " + email);
                if (!dao.verificarExistenciaLivro(numeroLivro)) throw new LivroNaoExistente();
            }
            if (livroSolicitacaoDtos.isEmpty()) return;
            logService.iniciar(LivroService.class.getName(), "Iniciando a atualização dos livros indisponíveis");
            dao.atualizarLivrosDisponiveis(numeroSolicitacao, livroSolicitacaoDtos, conexao);
            logService.sucesso(LivroService.class.getName(), "Sucesso na atualização dos livros indisponíveis");
        } catch (Exception e) {
            logService.erro(LivroService.class.getName(), "Ocorreu um erro na atualização dos livros indisponíveis", e);
            throw e;
        }
    }

    public void atualizarCodigoUltimaSolicitacao(List<LivroSolicitacaoDto> livros, Integer codigoSolicitacao, Connection conexao) throws SQLException {
        try {
            logService.iniciar(LivroService.class.getName(), "Iniciando a atualização do código da última solicitação dos livros");
            dao.atualzarCodigoUltimaSolicitacao(livros, codigoSolicitacao, conexao);
            logService.sucesso(LivroService.class.getName(), "Sucesso na atualização do código da última solicitação dos livros");
        } catch (Exception e) {
            logService.erro(LivroService.class.getName(), "Ocorreu um erro na atualização do código da última solicitação dos livros", e);
            throw e;
        }
    }
}
