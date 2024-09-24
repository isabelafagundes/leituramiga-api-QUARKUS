package br.com.isabela.service.livro;

import br.com.isabela.dao.livro.LivroDao;
import br.com.isabela.dto.livro.LivroDto;
import br.com.isabela.dto.livro.LivroSolicitacaoDto;
import br.com.isabela.model.exception.livro.LivroJaDesativado;
import br.com.isabela.model.exception.livro.LivroNaoDisponivel;
import br.com.isabela.model.exception.livro.LivroNaoExistente;
import br.com.isabela.model.livro.Livro;
import br.com.isabela.service.autenticacao.HashService;
import br.com.isabela.service.autenticacao.LogService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import liquibase.util.MD5Util;

import java.sql.SQLException;
import java.util.List;

@ApplicationScoped
public class LivroService {

    @Inject
    LivroDao dao;

    @Inject
    LogService logService;

    public LivroDto obterLivro(Integer numero) throws SQLException, LivroNaoExistente {
        try {
            logService.iniciar(LivroService.class.getName(), "Iniciando a validação da existência do livro de número " + numero);
            if (!dao.verificarExistenciaLivro(numero)) throw new LivroNaoExistente();
            logService.iniciar(LivroService.class.getName(), "Iniciando a obtenção do livro de número " + numero);
            Livro livro = dao.obterLivroPorNumero(numero);
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
            Integer numeroInstituicao) throws SQLException {
        try {
            logService.iniciar(LivroService.class.getName(), "Iniciando a obtenção dos livros da página " + pagina + " e tamanho " + tamanhoPagina);
            List<Livro> livros = dao.obterLivrosPaginados(pagina, tamanhoPagina, pesquisa, numeroCategoria, numeroInstituicao, numeroCidade);
            logService.sucesso(LivroService.class.getName(), "Sucesso na obtenção dos livros da página " + pagina + " e tamanho " + tamanhoPagina);
            return livros.stream().map(LivroDto::deModel).toList();
        } catch (Exception erro) {
            logService.erro(LivroService.class.getName(), "Ocorreu um erro na obtenção dos livros da página " + pagina + " e tamanho " + tamanhoPagina, erro);
            throw erro;
        }
    }

    public List<LivroDto> obterLivrosUsuario(String email) throws SQLException {
        String md5Login = HashService.obterMd5Email(email);
        try {
            logService.iniciar(LivroService.class.getName(), "Iniciando a obtenção dos livros do usuário de email " + md5Login);
            List<Livro> livros = dao.obterLivroPorUsuario(email);
            logService.sucesso(LivroService.class.getName(), "Sucesso na obtenção dos livros do usuário de email " + md5Login);
            return livros.stream().map(LivroDto::deModel).toList();
        } catch (Exception e) {
            logService.erro(LivroService.class.getName(), "Ocorreu um erro na obtenção dos livros do usuário de email " + md5Login, e);
            throw e;
        }
    }

    public void salvarLivro(LivroDto livro, String email) throws SQLException, LivroNaoDisponivel, LivroJaDesativado, LivroNaoExistente {
        String md5Login = HashService.obterMd5Email(email);
        try {
            if (livro.getId() != null) validarStatusLivro(livro.getId(), email);
            logService.iniciar(LivroService.class.getName(), "Iniciando a obtenção dos livros do usuário de email " + md5Login);
            dao.salvarLivro(livro);
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


    public void atualizarLivrosIndisponiveis(Integer numeroSolicitacao, List<LivroSolicitacaoDto> livroSolicitacaoDtos, String email) throws SQLException, LivroNaoDisponivel, LivroJaDesativado, LivroNaoExistente {
        try {
            logService.iniciar(LivroService.class.getName(), "Iniciando a validação dos livros da solicitação");
            for (LivroSolicitacaoDto livroSolicitacaoDto : livroSolicitacaoDtos) {
                validarStatusLivro(livroSolicitacaoDto.codigoLivro, email);
            }
            logService.iniciar(LivroService.class.getName(), "Iniciando a atualização dos livros indisponíveis");
            dao.atualizarLivrosIndisponiveis(numeroSolicitacao, livroSolicitacaoDtos);
            logService.sucesso(LivroService.class.getName(), "Sucesso na atualização dos livros indisponíveis");
        } catch (Exception e) {
            logService.erro(LivroService.class.getName(), "Ocorreu um erro na atualização dos livros indisponíveis", e);
            throw e;
        }
    }

}
