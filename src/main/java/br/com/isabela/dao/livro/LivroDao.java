package br.com.isabela.dao.livro;

import br.com.isabela.dao.FabricaDeConexoes;
import br.com.isabela.dao.endereco.EnderecoDao;
import br.com.isabela.dto.livro.LivroDto;
import br.com.isabela.model.livro.Livro;
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
public class LivroDao {
    @Inject
    FabricaDeConexoes bd;

    @Inject
    LogService logService;

    public Livro obterLivroPorNumero(String livroId) throws SQLException {
        logService.iniciar(LivroDao.class.getName(), "Iniciando busca do livro");
        Connection conexao = bd.obterConexao();
        PreparedStatement pstmt = conexao.prepareStatement(LivroQueries.OBTER_LIVRO_POR_ID);
        pstmt.setString(1, livroId);
        ResultSet resultado = pstmt.executeQuery();
        Livro livro = new Livro();
        if (resultado.next()) livro = obterLivroDeResult(resultado);
        logService.sucesso(LivroDao.class.getName(), "Busca do livro finalizada");
        return livro;
    }

    public Livro obterLivroPorUsuario(String emailUsuario) throws SQLException {
        logService.iniciar(LivroDao.class.getName(), "Iniciando busca de livro do usuário");
        Connection conexao = bd.obterConexao();
        PreparedStatement pstmt = conexao.prepareStatement(LivroQueries.OBTER_LIVRO_POR_USUARIO);
        pstmt.setString(1, emailUsuario);
        ResultSet resultado = pstmt.executeQuery();
        Livro livro = new Livro();
        if (resultado.next()) livro = obterLivroDeResult(resultado);
        logService.sucesso(LivroDao.class.getName(), "Busca do livro do usuário finalizada");
        return livro;
    }

    public Livro obterLivroPorTitulo(String livroTitulo) throws SQLException {
        logService.iniciar(LivroDao.class.getName(), "Iniciando busca de livro por titulo");
        Connection conexao = bd.obterConexao();
        PreparedStatement pstmt = conexao.prepareStatement(LivroQueries.OBTER_LIVRO_POR_TITULO);
        pstmt.setString(1, livroTitulo);
        ResultSet resultado = pstmt.executeQuery();
        Livro titulo = new Livro();
        if (resultado.next()) titulo = obterLivroDeResult(resultado);
        logService.sucesso(LivroDao.class.getName(), "Busca do livro por titulo finalizada");
        return titulo;
    }

    public Livro obterLivroPorCategoria(String livroCategoria) throws SQLException {
        logService.iniciar(LivroDao.class.getName(), "Iniciando busca de livro por categoria");
        Connection conexao = bd.obterConexao();
        PreparedStatement pstmt = conexao.prepareStatement(LivroQueries.OBTER_LIVRO_POR_CATEGORIA);
        pstmt.setString(1, livroCategoria);
        ResultSet resultado = pstmt.executeQuery();
        Livro categoria = new Livro();
        if (resultado.next()) categoria = obterLivroDeResult(resultado);
        logService.sucesso(LivroDao.class.getName(), "Busca do livro por categoria finalizada");
        return categoria;
    }

    public void salvarLivro(LivroDto livro) throws SQLException {
        logService.iniciar(LivroDao.class.getName(), "Iniciando o salvamento do livro");
        Connection conexao = bd.obterConexao();
        PreparedStatement pstmt = conexao.prepareStatement(LivroQueries.SALVAR_LIVRO);
        definirParametrosDeSalvamento(pstmt, livro);
        pstmt.executeUpdate();
        logService.sucesso(LivroDao.class.getName(), "Salvamento do livro concluído");
    }

    public void deletarLivro(int numeroLivro) throws SQLException {
        logService.iniciar(EnderecoDao.class.getName(), "Iniciando exclusão do livro " + numeroLivro);
        Connection conexao = bd.obterConexao();
        PreparedStatement pstmt = conexao.prepareStatement(LivroQueries.DELETAR_LIVRO);
        pstmt.setInt(1, numeroLivro);
        pstmt.executeUpdate();
        logService.sucesso(EnderecoDao.class.getName(), "Exclusão de livro finalizada " + numeroLivro);
    }

    public boolean validarExistenciaLivro(int numeroLivro) throws SQLException {
        logService.iniciar(EnderecoDao.class.getName(), "Iniciando validação da existência do livro");
        Connection conexao = bd.obterConexao();
        PreparedStatement pstmt = conexao.prepareStatement(LivroQueries.VALIDAR_EXISTENCIA);
        pstmt.setInt(1, numeroLivro);
        ResultSet resultado = pstmt.executeQuery();
        resultado.next();
        int qtd = resultado.getInt(1);
        logService.sucesso(EnderecoDao.class.getName(), "Sucesso na validação de existência do livro");
        return qtd > 0;
    }

    public List<Livro> obterLivrosPaginados(int pagina, int tamanhoPagina, String pesquisa, int numeroCategoria, int numeroInstituicao, int numeroCidade) throws SQLException {
        logService.iniciar(LivroDao.class.getName(), "Iniciando busca de livros paginados");
        Connection conexao = bd.obterConexao();
        String query = LivroQueries.OBTER_LIVROS_PAGINADOS.replaceAll("FILTROS_LIVRO", LivroQueries.FILTROS_LIVRO);
        PreparedStatement pstmt = conexao.prepareStatement(LivroQueries.OBTER_LIVROS_PAGINADOS);
        pstmt.setString(1, pesquisa);
        pstmt.setString(2, pesquisa);
        pstmt.setString(3, pesquisa);
        pstmt.setString(4, pesquisa);
        pstmt.setInt(5, numeroInstituicao);
        pstmt.setInt(6, numeroCategoria);
        pstmt.setInt(7, numeroCidade);
        pstmt.setInt(8, pagina * tamanhoPagina);
        pstmt.setInt(9, tamanhoPagina);
        ResultSet resultado = pstmt.executeQuery();
        List<Livro> livros = new ArrayList<>();
        while (resultado.next()) livros.add(obterLivroDeResult(resultado));
        logService.sucesso(LivroDao.class.getName(), "Sucesso em obter os livros paginados");
        return livros;
    }

    public void definirParametrosDeSalvamento(PreparedStatement pstmt, LivroDto livro) throws SQLException {
        pstmt.setInt(1, livro.getId());
        pstmt.setString(2, livro.getTitulo());
        pstmt.setString(3, livro.getAutor());
        pstmt.setString(7, livro.getDescricao());
        pstmt.setString(8, livro.getCategoria());
        pstmt.setString(9, livro.getEstado_fisico());
        pstmt.setString(10, livro.getNome_instituicao());
        pstmt.setString(11, livro.getNome_cidade());
        pstmt.setString(12, livro.getData_ultima_solicitacao());
    }

    public void salvarImagemLivro(String caminhoImagem, Integer codigoLivro) throws SQLException {
        logService.iniciar(LivroDao.class.getName(), "Iniciando o salvamento da imagem do livro de código" + codigoLivro);
        Connection conexao = bd.obterConexao();
        PreparedStatement pstmt = conexao.prepareStatement(LivroQueries.SALVAR_IMAGEM);
        pstmt.setString(1, caminhoImagem);
        pstmt.setInt(2, codigoLivro);
        pstmt.executeQuery();
        logService.sucesso(LivroDao.class.getName(), "Sucesso em salvar a imagem do livro de código " + codigoLivro);
    }

    public Livro obterLivroDeResult(ResultSet resultado) throws SQLException {
        return Livro.carregar(
                resultado.getInt("id"),
                resultado.getString("nome_usuario"),
                resultado.getString("titulo"),
                resultado.getString("autor"),
                resultado.getString("descricao"),
                resultado.getString("categoria"),
                resultado.getString("estado_fisico"),
                resultado.getString("nome_instituicao"),
                resultado.getString("nome_cidade"),
                resultado.getString("data_ultima_solicitacao")
        );
    }

}