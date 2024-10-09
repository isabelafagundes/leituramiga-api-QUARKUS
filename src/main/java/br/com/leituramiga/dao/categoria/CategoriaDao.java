package br.com.leituramiga.dao.categoria;

import br.com.leituramiga.dao.FabricaDeConexoes;
import br.com.leituramiga.dto.categoria.CategoriaDto;
import br.com.leituramiga.model.categoria.Categoria;
import br.com.leituramiga.service.autenticacao.LogService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@ApplicationScoped
public class CategoriaDao {

    @Inject
    FabricaDeConexoes bd;

    @Inject
    LogService logService;

    public List<Categoria> obterCategoria() throws SQLException {
        logService.iniciar(CategoriaDao.class.getName(), "Iniciando busca de categoria");
        Connection conexao = bd.obterConexao();
        PreparedStatement pstmt = conexao.prepareStatement(CategoriaQueries.OBTER_CATEGORIAS);
        ResultSet resultado = pstmt.executeQuery();
        List<Categoria> categorias = new ArrayList<>();
        while (resultado.next()) categorias.add(obterCategoriaDeResult(resultado));
        logService.sucesso(CategoriaDao.class.getName(), "Busca de categoria finalizada");
        return categorias;
    }


    public boolean validarExistencia(String categoria) throws SQLException {
        logService.iniciar(CategoriaDao.class.getName(), "Iniciando validação de existência da categoria");
        try (Connection conexao = bd.obterConexao()) {
            PreparedStatement pstmt = conexao.prepareStatement(CategoriaQueries.VALIDAR_EXISTENCIA_CATEGORIA);
            pstmt.setString(1, categoria);
            ResultSet resultado = pstmt.executeQuery();
            resultado.next();
            int quantidade = resultado.getInt(1);
            logService.sucesso(CategoriaDao.class.getName(), "Validação de existência de categoria finalizada");
            return quantidade > 0;
        }
    }

    public void salvarCategoria(CategoriaDto categoria) throws SQLException {
        logService.iniciar(CategoriaDao.class.getName(), "Iniciando salvamento de comentário");

        try (Connection conexao = bd.obterConexao()) {
            PreparedStatement pstms = conexao.prepareStatement(CategoriaQueries.SALVAR_CATEGORIA, PreparedStatement.RETURN_GENERATED_KEYS);
            definirParametrosDeSalvamento(pstms, categoria);

            int linhasAfetadas = pstms.executeUpdate();

            if (linhasAfetadas > 0) {
                logService.sucesso(CategoriaDao.class.getName(), "Salvamento de categoria finalizada com sucesso");
            } else {
                logService.erro(CategoriaDao.class.getName(), "Nenhuma linha foi afetada ao salvar a categoria ", null);  //Passei o null como erro
            }
        } catch (SQLException e) {
            logService.erro(CategoriaDao.class.getName(), "Erro ao salvar a categoria", e);
            throw e;
        }
    }

    public void deletarCategoria(int numeroCategoria) throws SQLException {
        logService.iniciar(CategoriaDao.class.getName(), "Iniciando exclusão de categoria" + numeroCategoria);
        try (Connection conexao = bd.obterConexao()) {
            PreparedStatement pstmt = conexao.prepareStatement(CategoriaQueries.EXCLUIR_CATEGORIA);
            pstmt.setInt(1, numeroCategoria);
            pstmt.executeUpdate();
            logService.sucesso(CategoriaDao.class.getName(), "Exclusão de categoria finalizada" + numeroCategoria);
        }
    }


    public void definirParametrosDeSalvamento(PreparedStatement pstmt, CategoriaDto categoria) throws SQLException {
        pstmt.setInt(1, categoria.getId());
        pstmt.setString(2, categoria.getDescricao());

    }

    public Categoria obterCategoriaDeResult(ResultSet resultado) throws SQLException {
        return Categoria.carregar(
                resultado.getInt("id"),
                resultado.getString("descricao")
        );
    }


}


