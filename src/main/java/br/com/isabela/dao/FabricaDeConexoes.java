package br.com.isabela.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@ApplicationScoped
public class FabricaDeConexoes {

    @Inject
    DataSource dataSource;

    public Connection obterConexao() throws SQLException {
        try {
            return dataSource.getConnection();
        } catch (SQLException erro) {
            throw erro;
        }
    }

    public void desconectar(Connection conexao) throws SQLException {
        if (conexao != null && !conexao.isClosed()) {
            try {
                conexao.close();
            } catch (SQLException erro) {
                throw erro;
            }
        }
    }
}
