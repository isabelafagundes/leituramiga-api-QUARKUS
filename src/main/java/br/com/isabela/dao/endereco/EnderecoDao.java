package br.com.isabela.dao.endereco;

import br.com.isabela.dao.FabricaDeConexoes;
import br.com.isabela.dto.endereco.EnderecoDto;
import br.com.isabela.model.endereco.Endereco;
import br.com.isabela.service.autenticacao.LogService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@ApplicationScoped
public class EnderecoDao {
    @Inject
    FabricaDeConexoes bd;

    @Inject
    LogService logService;


    public Endereco obterEnderecoUsuario(String email) throws SQLException {
        logService.iniciar(EnderecoDao.class.getName(), "Iniciando busca de endereço do usuário");
        Connection conexao = bd.obterConexao();
        PreparedStatement pstmt = conexao.prepareStatement(EnderecoQueries.OBTER_ENDERECO_POR_USUARIO);
        pstmt.setString(1, email);
        ResultSet resultado = pstmt.executeQuery();
        Endereco endereco = new Endereco();
        if (resultado.next()) endereco = obterEnderecoDeResult(resultado);
        logService.sucesso(EnderecoDao.class.getName(), "Busca de endereço do usuário finalizada");
        return endereco;
    }

    public void salvarEndereco(EnderecoDto endereco) throws SQLException {
        logService.iniciar(EnderecoDao.class.getName(), "Iniciando salvamento de endereço");
        Connection conexao = bd.obterConexao();
        PreparedStatement pstmt = conexao.prepareStatement(EnderecoQueries.SALVAR_ENDERECO);
        definirParametrosDeSalvamento(pstmt, endereco);
        pstmt.executeUpdate();
        logService.sucesso(EnderecoDao.class.getName(), "Salvamento de endereço finalizado");
    }

    public void deletarEndereco(int numeroEndereco) throws SQLException {
        logService.iniciar(EnderecoDao.class.getName(), "Iniciando exclusão de endereço de numero " + numeroEndereco);
        Connection conexao = bd.obterConexao();
        PreparedStatement pstmt = conexao.prepareStatement(EnderecoQueries.EXCLUIR_ENDERECO);
        pstmt.setInt(1, numeroEndereco);
        pstmt.executeUpdate();
        logService.sucesso(EnderecoDao.class.getName(), "Exclusão de endereço finalizada de numero " + numeroEndereco);
    }

    public boolean validarExistencia(String email) throws SQLException {
        logService.iniciar(EnderecoDao.class.getName(), "Iniciando validação de existência de endereço");
        Connection conexao = bd.obterConexao();
        PreparedStatement pstmt = conexao.prepareStatement(EnderecoQueries.VALIDAR_EXISTENCIA);
        pstmt.setString(1, email);
        ResultSet resultado = pstmt.executeQuery();
        resultado.next();
        int quantidade = resultado.getInt(1);
        logService.sucesso(EnderecoDao.class.getName(), "Validação de existência de endereço finalizada");
        return quantidade > 0;
    }

    public void definirParametrosDeSalvamento(PreparedStatement pstmt, EnderecoDto endereco) throws SQLException {
        pstmt.setInt(1, endereco.getId());
        pstmt.setString(2, endereco.getLogradouro());
        pstmt.setString(3, endereco.getComplemento());
        pstmt.setString(4, endereco.getBairro());
        pstmt.setString(5, endereco.getCep());
        pstmt.setInt(6, endereco.getCodigoCidade());
        pstmt.setString(7, endereco.getEmailUsuario());
        pstmt.setBoolean(7, true);
        pstmt.setString(8, endereco.getLogradouro());
        pstmt.setString(9, endereco.getComplemento());
        pstmt.setString(10, endereco.getBairro());
        pstmt.setString(11, endereco.getCep());
        pstmt.setInt(12, endereco.getCodigoCidade());
        pstmt.setBoolean(13, true);
    }


    public Endereco obterEnderecoDeResult(ResultSet resultado) throws SQLException {
        return Endereco.carregar(
                resultado.getInt("id"),
                resultado.getString("logradouro"),
                resultado.getString("complemento"),
                resultado.getString("bairro"),
                resultado.getString("cep"),
                resultado.getString("nome_cidade"),
                resultado.getString("nome_estado"),
                resultado.getString("email_usuario")
        );
    }

}
