package br.com.leituramiga.dao.instituicao;

import br.com.leituramiga.dao.FabricaDeConexoes;
import br.com.leituramiga.dto.Instituicao.InstituicaoDto;
import br.com.leituramiga.model.instituicao.Instituicao;
import br.com.leituramiga.service.autenticacao.LogService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class InstituicaoDao {

    @Inject
    FabricaDeConexoes bd;

    @Inject
    LogService logService;

    public Instituicao obterInstituicaoPorEstado(String uf) throws SQLException {
        logService.iniciar(InstituicaoDao.class.getName(), "Iniciando a busca de instituição por estado");
        Connection conexao = bd.obterConexao();
        PreparedStatement pstmt = conexao.prepareStatement(InstituicaoQueries.OBTER_INSTITUICAO_POR_ESTADO);
        pstmt.setString(1, uf);
        ResultSet resultado = pstmt.executeQuery();
        Instituicao instituicao = new Instituicao();
        if (resultado.next()) instituicao = obterInstituicaoDeResult(resultado);
        logService.sucesso(InstituicaoDao.class.getName(), "Busca de instituições por estado finalizada");
        return instituicao;
    }

    public Instituicao obterTodasInstituicoes() throws SQLException {
        logService.iniciar(InstituicaoDao.class.getName(), "Iniciando busca de todas instituicoes");
        Connection conexao = bd.obterConexao();
        PreparedStatement pstmt = conexao.prepareStatement(InstituicaoQueries.OBTER_TODAS_INSTITUICOES);
        ResultSet resultado = pstmt.executeQuery();
        Instituicao instituicao = new Instituicao();
        if (resultado.next()) instituicao = obterInstituicaoDeResult(resultado);
        logService.sucesso(InstituicaoDao.class.getName(), "Busca de todas instituições finalizada");
        return instituicao;
    }

    public boolean validarExistencia(String instituicao) throws SQLException {
        logService.iniciar(InstituicaoDao.class.getName(), "Iniciando validação de existência de instituição");
        try (Connection conexao = bd.obterConexao()) {
            PreparedStatement pstmt = conexao.prepareStatement(InstituicaoQueries.VALIDAR_EXISTENCIA_INSTITUICAO);
            pstmt.setString(1, instituicao);
            ResultSet resultado = pstmt.executeQuery();
            resultado.next();
            int quantidade = resultado.getInt(1);
            logService.sucesso(InstituicaoDao.class.getName(), "Validação de existência de instituição finalizada");
            return quantidade > 0;
        }
    }

    public void salvarInstituicao(InstituicaoDto instituicao) throws SQLException {
        logService.iniciar(InstituicaoDao.class.getName(), "Iniciando salvamento de instituição");

        try (Connection conexao = bd.obterConexao()) {
            PreparedStatement pstms = conexao.prepareStatement(InstituicaoQueries.SALVAR_INSTITUICAO, PreparedStatement.RETURN_GENERATED_KEYS);
            definirParametrosDeSalvamento(pstms, instituicao);

            int linhasAfetadas = pstms.executeUpdate();

            if (linhasAfetadas > 0) {
                logService.sucesso(InstituicaoDao.class.getName(), "Salvamento de instituicao finalizado com sucesso");
            } else {
                logService.erro(InstituicaoDao.class.getName(), "Nenhuma linha foi afetada ao salvar a instituicao ", null);  //Passei o null como erro
            }
        } catch (SQLException e) {
            logService.erro(InstituicaoDao.class.getName(), "Erro ao salvar a instituicao", e);
            throw e;
        }
    }


    public void deletarInstituicao(int numeroInstituicao) throws SQLException {
        logService.iniciar(InstituicaoDao.class.getName(), "Iniciando a exclusão de instituição" + numeroInstituicao);
        try (Connection conexao = bd.obterConexao()) {
            PreparedStatement pstmt = conexao.prepareStatement(InstituicaoQueries.EXCLUIR_INSTITUICAO);
            pstmt.setInt(1, numeroInstituicao);
            pstmt.executeUpdate();
            logService.sucesso(InstituicaoDao.class.getName(), "Exclusao de instituição concluido" + numeroInstituicao);
        }
    }

    public void salvarInstituicoesDeMapa(Map<String, List<String>> instituicoes) throws SQLException {
        StringBuilder queryInsert = new StringBuilder("INSERT INTO instituicao (nome, sigla) VALUES ");
        for (Map.Entry<String, List<String>> entry : instituicoes.entrySet()) {
            List<String> value = entry.getValue();
            String sigla = value.get(0).toLowerCase().contains("fatec") ? "FATEC" : "ETEC";
            queryInsert.append("('").append(value.get(0)).append("', '" + sigla + "'),");
        }
        queryInsert.deleteCharAt(queryInsert.length() - 1);
        System.out.println(queryInsert);
//        executarUpdate(String.valueOf(queryInsert));
    }

    private void executarUpdate(String query) throws SQLException {
        try (Connection conexao = bd.obterConexao()) {
            PreparedStatement pstmt = conexao.prepareStatement(query);
            pstmt.executeUpdate();
        }
    }

    public void definirParametrosDeSalvamento(PreparedStatement pstmt, InstituicaoDto instituicao) throws SQLException {
        pstmt.setInt(1, instituicao.getId());
        pstmt.setString(2, instituicao.getNome());
        pstmt.setString(3, instituicao.getSigla());
    }

    public Instituicao obterInstituicaoDeResult(ResultSet resultado) throws SQLException {
        return Instituicao.carregar(
                resultado.getInt("id"),
                resultado.getString("nome"),
                resultado.getString("sigla")
        );
    }


}