package br.com.isabela.dao.livro;

import br.com.isabela.dao.FabricaDeConexoes;
import br.com.isabela.dao.endereco.EnderecoDao;
import br.com.isabela.dao.endereco.EnderecoQueries;
import br.com.isabela.dto.livro.LivroDto;
import br.com.isabela.model.livro.Livro;
import br.com.isabela.service.autenticacao.LogService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


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

    public Livro obterLivroPorUsuario(String livroUsuario) throws SQLException {
        logService.iniciar(LivroDao.class.getName(), "Iniciando busca de livro do usuário");
        Connection conexao = bd.obterConexao();
        PreparedStatement pstmt = conexao.prepareStatement(LivroQueries.OBTER_LIVRO_POR_USUARIO);
        pstmt.setString(1, livroUsuario);
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

    //todo: Trocar String validarLivro por int numeroLivro
    public boolean validarExistenciaLivro(String validarLivro) throws SQLException {
        logService.iniciar(EnderecoDao.class.getName(), "Iniciando validação da existência do livro");
        Connection conexao = bd.obterConexao();
        PreparedStatement pstmt = conexao.prepareStatement(EnderecoQueries.VALIDAR_EXISTENCIA);
        pstmt.setString(1, validarLivro);
        ResultSet resultado = pstmt.executeQuery();
        resultado.next();
        int qtd = resultado.getInt(1);
        logService.sucesso(EnderecoDao.class.getName(), "Validação do livro existente finalizada");
        return qtd > 0;
    }

    public void definirParametrosDeSalvamento(PreparedStatement pstmt, LivroDto livro) throws SQLException {
        pstmt.setInt(1, livro.getId());
        pstmt.setString(2, livro.getTitulo());
        pstmt.setString(3, livro.getAutor());
        pstmt.setString(7, livro.getDescricao());
        pstmt.setString(8, livro.getCategoria());
        pstmt.setString(9, livro.getEstadoFisico());
        pstmt.setString(11, livro.getdata_ultima_solicitacao());
    }

//todo: usar underline no nome das colunas ex: estado_fisico
    public Livro obterLivroDeResult(ResultSet resultado) throws SQLException {
        return Livro.carregar(
                resultado.getInt("id"),
                resultado.getString("titulo"),
                resultado.getString("autor"),
                resultado.getString("editora"),
                resultado.getString("isbn"),
                resultado.getString("descricao"),
                resultado.getString("categoria"),
                resultado.getString("estadoFisico"),
                resultado.getString("dataUltimaSolicitacao")
        );
    }


}//fim do código