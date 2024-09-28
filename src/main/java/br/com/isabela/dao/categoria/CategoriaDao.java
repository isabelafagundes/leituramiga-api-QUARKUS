package br.com.isabela.dao.categoria;

import br.com.isabela.dao.FabricaDeConexoes;
import br.com.isabela.dao.comentario.ComentarioDao;
import br.com.isabela.dto.categoria.CategoriaDto;
import br.com.isabela.model.categoria.Categoria;
import br.com.isabela.service.autenticacao.LogService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


@ApplicationScoped
public class CategoriaDao {

    @Inject
    FabricaDeConexoes bd;

    @Inject
    LogService logService;

    public Categoria obterCategoria(String codigoCategoria) throws SQLException {
        logService.iniciar(CategoriaDao.class.getName(), "Iniciando busca de categoria");
        Connection conexao = bd.obterConexao();
        PreparedStatement pstmt = conexao.prepareStatement(CategoriaQueries.OBTER_CODIGO_CATEGORIA);
        pstmt.setString(1, codigoCategoria);
        ResultSet resultado = pstmt.executeQuery();
        Categoria categoria = new Categoria();
        if (resultado.next()) categoria = obterCategoriaDeResult(resultado);
        logService.sucesso(CategoriaDao.class.getName(), "Busca de categoria finalizada");
        return categoria;
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

    public Integer salvarCategoria(CategoriaDto categoria, Connection conexao) throws SQLException {
        logService.iniciar(ComentarioDao.class.getName(), "Iniciando busca do categoria");
        PreparedStatement pstms = conexao.prepareStatement(CategoriaQueries.SALVAR_CATEGORIA, PreparedStatement.RETURN_GENERATED_KEYS);
        definirParametrosDeSalvamento(pstms, categoria);
        int linhasAfetadas = pstms.executeUpdate();
        if (linhasAfetadas > 0) {
            try (ResultSet resultado = pstms.getGeneratedKeys()) {
                if (resultado.next()) {
                    Integer idGerado = resultado.getInt(1);
                    return idGerado;
                }
            }
        }
        return null;
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
        pstmt.setString(3, categoria.getDescricao());

    }

    public Categoria obterCategoriaDeResult(ResultSet resultado) throws SQLException {
        return Categoria.carregar(
                resultado.getInt("id"),
                resultado.getString("descricao")
        );
    }


}


