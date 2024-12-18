package br.com.leituramiga.dao.comentario;

import br.com.leituramiga.dao.FabricaDeConexoes;
import br.com.leituramiga.dto.comentario.ComentarioDto;
import br.com.leituramiga.model.DataHora;
import br.com.leituramiga.model.comentario.Comentario;
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
public class ComentarioDao {

    @Inject
    FabricaDeConexoes bd;

    @Inject
    LogService logService;

    public List<Comentario> obterComentarioPorPerfilUsuario(String comentarioPerfil) throws SQLException {
        logService.iniciar(ComentarioDao.class.getName(), "Iniciando busca do comentario por usuário");
        try (Connection conexao = bd.obterConexao()) {
            PreparedStatement pstmt = conexao.prepareStatement(ComentarioQueries.OBTER_COMENTARIO_POR_PERFIL);
            pstmt.setString(1, comentarioPerfil);
            ResultSet resultado = pstmt.executeQuery();
            List<Comentario> comentarios = new ArrayList<>();
            while (resultado.next()) comentarios.add(obterComentarioDeResult(resultado));
            logService.sucesso(ComentarioDao.class.getName(), "Busca do comentario finalizada");
            return comentarios;
        }
    }

    public void deletarComentario(int comentario) throws SQLException {
        logService.iniciar(ComentarioDao.class.getName(), "Iniciando exclusão do comentário" + comentario);
        try (Connection conexao = bd.obterConexao()) {
            PreparedStatement pstmt = conexao.prepareStatement(ComentarioQueries.EXCLUIR_COMENTARIO);
            pstmt.setInt(1, comentario);
            pstmt.executeUpdate();
            logService.sucesso(ComentarioDao.class.getName(), "Exclusão do comentário finalizada " + comentario);
        }
    }

    public void salvarComentario(ComentarioDto comentario) throws SQLException {
        logService.iniciar(ComentarioDao.class.getName(), "Iniciando salvamento de comentário");

        try (Connection conexao = bd.obterConexao()) {
            PreparedStatement pstmst = conexao.prepareStatement(ComentarioQueries.SALVAR_COMENTARIO, PreparedStatement.RETURN_GENERATED_KEYS);
            definirParametrosDeSalvamento(pstmst, comentario);

            int linhasAfetadas = pstmst.executeUpdate();

            if (linhasAfetadas > 0) {
                logService.sucesso(ComentarioDao.class.getName(), "Salvamento de comentário finalizado com sucesso");
            } else {
                logService.erro(ComentarioDao.class.getName(), "Nenhuma linha foi afetada ao salvar um comentário", null); //Passei o null como erro
            }
        } catch (SQLException e) {
            logService.erro(ComentarioDao.class.getName(), "Erro ao salvar comentário", e);
            throw e;
        }
    }


    public Comentario obterComentariosFeitos(String emailUsuarioPerfil) throws SQLException {
        logService.iniciar(ComentarioDto.class.getName(), "Iniciando busca de comentarios feitos ou recebidos do usuário");
        try (Connection conexao = bd.obterConexao()) {
            PreparedStatement pstmt = conexao.prepareStatement(ComentarioQueries.OBTER_COMENTARIOS);
            pstmt.setString(1, emailUsuarioPerfil);
            ResultSet resultado = pstmt.executeQuery();
            Comentario comentario = new Comentario();
            if (resultado.next()) comentario = obterComentarioDeResult(resultado);
            logService.sucesso(ComentarioDao.class.getName(), "Busca de comentarios feitos finalizada");
            return comentario;
        }
    }

    public void definirParametrosDeSalvamento(PreparedStatement pstmt, ComentarioDto comentario) throws SQLException {
        DataHora dataHora = DataHora.hoje();
        pstmt.setString(1, comentario.getDescricao());
        pstmt.setString(2, dataHora.dataFormatada("yyyy-MM-dd"));
        pstmt.setString(3, dataHora.dataFormatada("HH:mm:ss"));
        pstmt.setString(4, comentario.getEmailUsuarioCriador());
        pstmt.setString(5, comentario.getEmailUsuarioPerfil());
    }

    public Comentario obterComentarioDeResult(ResultSet resultado) throws SQLException {
        return Comentario.carregar(
                resultado.getInt("codigo_comentario"),
                resultado.getString("descricao"),
                resultado.getString("data_criacao"),
                resultado.getString("hora_criacao"),
                resultado.getString("email_usuario_criador"),
                resultado.getString("email_usuario_perfil"),
                resultado.getString("nome_usuario_criador"),
                resultado.getString("imagem")
        );
    }


}
