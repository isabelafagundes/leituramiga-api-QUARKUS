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


@ApplicationScoped
public class LivroDao {
    @Inject
    FabricaDeConexoes bd;

    @Inject
    LogService logService;

    public Livro obterLivroUsuario(String livroId) throws SQLException {
        logService.iniciar(LivroDao.class.getName(), "Iniciando busca de livro do usuário");
        Connection conexao = bd.obterConexao();
        PreparedStatement pstmt = conexao.prepareStatement(LivroQueries.OBTER_LIVRO_POR_ID);
        pstmt.setString(1, livroId);
        ResultSet resultado = pstmt.executeQuery();
        Livro livro = new Livro();
        if (resultado.next()) livro = obterLivroDeResult(resultado);
        logService.sucesso(LivroDao.class.getName(), "Busca do livro do usuário finalizada");
        return livro;
    }

    public Livro obterLivroituloDeResult(String livroTitulo) throws SQLException {
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

    public Livro obterLivroCategoriaDeResult(String livroCategoria) throws SQLException {
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

    public void deletarLivro(String nomeLivro, Integer usuarioId) throws SQLException {
        logService.iniciar(EnderecoDao.class.getName(), "Iniciando exclusão do livro " + nomeLivro);
        Connection conexao = bd.obterConexao();

        String query = usuarioId != null ? LivroQueries.DELETAR_LIVRO_POR_USUARIO : LivroQueries.DELETAR_LIVRO;
        PreparedStatement pstmt = conexao.prepareStatement(query);

        if(usuarioId != null) {
            pstmt.setInt(1, usuarioId);
        } else {
            pstmt.setString(1, nomeLivro);
        }

        pstmt.executeUpdate();
        logService.sucesso(EnderecoDao.class.getName(), "Exclusão de livro finalizada " + nomeLivro);
    }


    public boolean validarExistenciaLivro(int numeroLivro) throws SQLException {
        logService.iniciar(LivroDao.class.getName(), "Iniciando a validação de existência de livros no código ");
        Connection conexao = bd.obterConexao();
        PreparedStatement pstmt = conexao.prepareStatement(LivroQueries.VALIDAR_EXISTENCIA);
        pstmt.setInt(1, numeroLivro);
        ResultSet resultado = pstmt.executeQuery();

        if (resultado.next()) {
            int quantidade = resultado.getInt(1);
            logService.sucesso(LivroDao.class.getName(), "Validação de existência do livro no código ");
            return quantidade > 0;

        } else {
            return false;
        }
    }

    public void definirParametrosDeSalvamento(PreparedStatement pstmt, LivroDto livro) throws SQLException {
        pstmt.setInt(1, livro.getId());
        pstmt.setString(2, livro.getTitulo());
        pstmt.setString(3, livro.getAutor());
        pstmt.setString(4, livro.getEditora());
        pstmt.setInt(5, livro.getAno());
        pstmt.setString(6, livro.getIsbn());
        pstmt.setString(7, livro.getDescricao());
        pstmt.setString(8, livro.getCategoria());
        pstmt.setString(9, livro.getEstadoFisico());
        pstmt.setString(10, livro.getDataPublicacao());
        pstmt.setString(11, livro.getDataUltimaPublicacao());

    }


    public Livro obterLivroDeResult(ResultSet resultado) throws SQLException {
        return Livro.carregar(
                resultado.getInt("id"),
                resultado.getString("titulo"),
                resultado.getString("autor"),
                resultado.getString("editora"),
                resultado.getInt("ano"),
                resultado.getString("isbn"),
                resultado.getString("descricao"),
                resultado.getString("categoria"),
                resultado.getString("estadoFisico"),
                resultado.getString("dataPublicacao"),
                resultado.getString("dataUltimaPublicacao")
        );
    }


}//fim do código