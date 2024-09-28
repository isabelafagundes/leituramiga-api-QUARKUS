package br.com.leituramiga.dao.categoria;

import br.com.leituramiga.dao.FabricaDeConexoes;
import br.com.leituramiga.dao.livro.LivroDao;
import br.com.leituramiga.dto.categoria.CategoriaDto;
import br.com.leituramiga.model.categoria.Categoria;
import br.com.leituramiga.service.autenticacao.LogService;
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

    public Categoria obterCategoriaPorNumero(String categoriaId) throws SQLException {
        logService.iniciar(CategoriaDao.class.getName(), "Iniciando busca de categoria por id");
        Connection conexao = bd.obterConexao();
        PreparedStatement pstmt = conexao.prepareStatement(CategoriaQueries.OBTER_CATEGORIA_POR_ID);
        pstmt.setString(1, categoriaId);
        ResultSet resultado = pstmt.executeQuery();
        Categoria categoria = new Categoria();
        if (resultado.next()) categoria = obterCategoriaDeResult(resultado);
        logService.sucesso(LivroDao.class.getName(), "Busca de categoria finalizada");
        return categoria;
    }




    public void definirParametrosDeSalvamento(PreparedStatement pstmt, CategoriaDto categoria) throws SQLException {
        pstmt.setInt(1, categoria.getId());
        pstmt.setString(2, categoria.getNome_categoria());
        pstmt.setString(3, categoria.getDescricao_categoria());

    }

    public Categoria obterCategoriaDeResult(ResultSet resultado) throws SQLException {
        return Categoria.carregar(
                resultado.getInt("id"),
                resultado.getString("nome_categoria"),
                resultado.getString("descricao_categoria")
        );
    }


}


