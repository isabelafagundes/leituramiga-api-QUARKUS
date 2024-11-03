package br.com.leituramiga.dao.endereco;

import br.com.leituramiga.dao.FabricaDeConexoes;
import br.com.leituramiga.dto.endereco.EnderecoDto;
import br.com.leituramiga.model.cidade.Cidade;
import br.com.leituramiga.model.endereco.Endereco;
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
public class EnderecoDao {
    @Inject
    FabricaDeConexoes bd;

    @Inject
    LogService logService;


    public Endereco obterEnderecoUsuario(String email) throws SQLException {
        logService.iniciar(EnderecoDao.class.getName(), "Iniciando busca de endereço do usuário");
        try (Connection conexao = bd.obterConexao()) {
            PreparedStatement pstmt = conexao.prepareStatement(EnderecoQueries.OBTER_ENDERECO_POR_USUARIO);
            pstmt.setString(1, email);
            ResultSet resultado = pstmt.executeQuery();
            Endereco endereco = new Endereco();
            if (resultado.next()) endereco = obterEnderecoDeResult(resultado);
            logService.sucesso(EnderecoDao.class.getName(), "Busca de endereço do usuário finalizada");
            return endereco;
        }
    }

    public Endereco obterEnderecoPorId(int id) throws SQLException {
        logService.iniciar(EnderecoDao.class.getName(), "Iniciando busca de endereço por id");
        try (Connection conexao = bd.obterConexao()) {
            PreparedStatement pstmt = conexao.prepareStatement(EnderecoQueries.OBTER_ENDERECO_POR_ID);
            pstmt.setInt(1, id);
            ResultSet resultado = pstmt.executeQuery();
            Endereco endereco = new Endereco();
            if (resultado.next()) endereco = obterEnderecoDeResult(resultado);
            logService.sucesso(EnderecoDao.class.getName(), "Busca de endereço por id finalizada");
            return endereco;
        }
    }

    public Integer salvarEndereco(EnderecoDto endereco, Connection conexao, String email, Boolean enderecoPrincipal) throws SQLException {
        logService.iniciar(EnderecoDao.class.getName(), "Iniciando salvamento de endereço");
        PreparedStatement pstmt = conexao.prepareStatement(EnderecoQueries.INSERIR_ENDERECO, PreparedStatement.RETURN_GENERATED_KEYS);
        definirParametrosDeSalvamento(pstmt, endereco, email, enderecoPrincipal);
        int linhasAfetadas = pstmt.executeUpdate();

        if (linhasAfetadas > 0) {
            try (ResultSet result = pstmt.getGeneratedKeys()) {
                if (result.next()) {
                    Integer idGerado = result.getInt(1);
                    return idGerado;
                }
            }
        }
        return null;
    }

    public void atualizarVinculoEnderecoSolicitacao(String email, Integer codigoSolicitacao, Integer codigoEndereco, Connection conexao) throws SQLException {
        PreparedStatement pstmt = conexao.prepareStatement(EnderecoQueries.ATUALIZAR_SOLICITACAO_ENDERECO);
        pstmt.setInt(1, codigoEndereco);
        pstmt.setString(2, email);
        pstmt.setInt(3, codigoSolicitacao);
        pstmt.executeUpdate();
    }

    public void vincularEnderecoSolicitacao(Integer codigoEndereco, Integer codigoSolicitacao, String email, Connection conexao) throws SQLException {
        PreparedStatement pstmt = conexao.prepareStatement(EnderecoQueries.INSERIR_SOLICITACAO_ENDERECO);
        pstmt.setInt(1, codigoEndereco);
        pstmt.setInt(2, codigoSolicitacao);
        pstmt.setString(3, email);
        pstmt.executeUpdate();
    }

    public void atualizarEnderecoSolicitacao(EnderecoDto endereco, Connection conexao, Boolean enderecoPrincipal) throws SQLException {
        logService.iniciar(EnderecoDao.class.getName(), "Iniciando atualização de endereço da solicitação");
        PreparedStatement pstmt = conexao.prepareStatement(EnderecoQueries.ATUALIZAR_ENDERECO);
        pstmt.setString(1, endereco.getLogradouro());
        pstmt.setString(2, endereco.getComplemento());
        pstmt.setString(3, endereco.getBairro());
        pstmt.setString(4, endereco.getCep());
        pstmt.setInt(5, endereco.getCodigoCidade());
        pstmt.setBoolean(6, enderecoPrincipal);
        pstmt.setString(7, endereco.getNumero());
        pstmt.setInt(8, endereco.getCodigoEndereco());
        pstmt.executeUpdate();
        logService.sucesso(EnderecoDao.class.getName(), "Atualização de endereço da solicitação finalizada");
    }

    public void atualizarEndereco(EnderecoDto endereco, Boolean enderecoPrincipal) throws SQLException {
        try (Connection conexao = bd.obterConexao()) {
            logService.iniciar(EnderecoDao.class.getName(), "Iniciando atualização de endereço");
            PreparedStatement pstmt = conexao.prepareStatement(EnderecoQueries.ATUALIZAR_ENDERECO);
            pstmt.setString(1, endereco.getLogradouro());
            pstmt.setString(2, endereco.getComplemento());
            pstmt.setString(3, endereco.getBairro());
            pstmt.setString(4, endereco.getCep());
            pstmt.setInt(5, endereco.getCodigoCidade());
            pstmt.setBoolean(6, enderecoPrincipal);
            pstmt.setString(7, endereco.getNumero());
            pstmt.setInt(8, endereco.getCodigoEndereco());
            pstmt.executeUpdate();
            logService.sucesso(EnderecoDao.class.getName(), "Atualização de endereço finalizada");
        }
    }


    public void deletarEndereco(int numeroEndereco) throws SQLException {
        logService.iniciar(EnderecoDao.class.getName(), "Iniciando exclusão de endereço de numero " + numeroEndereco);
        try (Connection conexao = bd.obterConexao()) {
            PreparedStatement pstmt = conexao.prepareStatement(EnderecoQueries.EXCLUIR_ENDERECO);
            pstmt.setInt(1, numeroEndereco);
            pstmt.executeUpdate();
            logService.sucesso(EnderecoDao.class.getName(), "Exclusão de endereço finalizada de numero " + numeroEndereco);
        }
    }

    public boolean validarExistencia(String email) throws SQLException {
        logService.iniciar(EnderecoDao.class.getName(), "Iniciando validação de existência de endereço");
        try (Connection conexao = bd.obterConexao()) {
            PreparedStatement pstmt = conexao.prepareStatement(EnderecoQueries.VALIDAR_EXISTENCIA_ENDERECO_EMAIL);
            pstmt.setString(1, email);
            ResultSet resultado = pstmt.executeQuery();
            resultado.next();
            int quantidade = resultado.getInt(1);
            logService.sucesso(EnderecoDao.class.getName(), "Validação de existência de endereço finalizada");
            return quantidade > 0;
        }
    }

    public boolean validarExistenciaPorId(Integer id) throws SQLException {
        logService.iniciar(EnderecoDao.class.getName(), "Iniciando validação de existência de endereço");
        try (Connection conexao = bd.obterConexao()) {
            PreparedStatement pstmt = conexao.prepareStatement(EnderecoQueries.VALIDAR_EXISTENCIA_ENDERECO_ID);
            pstmt.setInt(1, id);
            ResultSet resultado = pstmt.executeQuery();
            resultado.next();
            int quantidade = resultado.getInt(1);
            logService.sucesso(EnderecoDao.class.getName(), "Validação de existência de endereço finalizada");
            return quantidade > 0;
        }
    }

    public List<Cidade> obterCidades(String uf, String pesquisa) throws SQLException {
        logService.iniciar(EnderecoDao.class.getName(), "Iniciando a obtenção das cidades da UF " + uf);
        try (Connection conexao = bd.obterConexao()) {
            String query = EnderecoQueries.OBTER_CIDADES_POR_UF;
            String pesquisaQuery = pesquisa == null ? "" : pesquisa;
            query = query.replaceAll("PESQUISA", "'%" + pesquisaQuery + "%'");
            PreparedStatement pstmt = conexao.prepareStatement(query);
            pstmt.setString(1, uf);
            ResultSet resultado = pstmt.executeQuery();
            List<Cidade> cidades = new ArrayList<>();
            while (resultado.next()) cidades.add(obterCidadeDeResult(resultado));
            logService.sucesso(EnderecoDao.class.getName(), "Sucesso na obtenção das cidades da UF " + uf);
            return cidades;
        }
    }

    public void definirParametrosDeSalvamento(PreparedStatement pstmt, EnderecoDto endereco, String email, Boolean enderecoPrincipal) throws SQLException {
        pstmt.setString(1, endereco.getLogradouro());
        pstmt.setString(2, endereco.getComplemento());
        pstmt.setString(3, endereco.getBairro());
        pstmt.setString(4, endereco.getCep());
        pstmt.setInt(5, endereco.getCodigoCidade());
        pstmt.setString(6, email);
        pstmt.setBoolean(7, enderecoPrincipal);
        pstmt.setString(8, endereco.getNumero());
    }

    public Cidade obterCidadeDeResult(ResultSet resultado) throws SQLException {
        return Cidade.carregar(
                resultado.getInt("codigo_cidade"),
                resultado.getString("nome"),
                resultado.getString("estado")
        );
    }

    public Endereco obterEnderecoDeResult(ResultSet resultado) throws SQLException {
        return Endereco.carregar(
                resultado.getInt("codigo_endereco"),
                resultado.getString("logradouro"),
                resultado.getString("complemento"),
                resultado.getString("bairro"),
                resultado.getString("cep"),
                resultado.getString("nome_cidade"),
                resultado.getString("email_usuario"),
                resultado.getString("nome_estado"),
                resultado.getString("numero"),
                resultado.getInt("codigo_cidade"),
                resultado.getBoolean("endereco_principal")
        );
    }

}
