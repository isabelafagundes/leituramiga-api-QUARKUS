package br.com.isabela.dao.comentario;

import br.com.isabela.dao.FabricaDeConexoes;
import br.com.isabela.dao.livro.LivroDao;
import br.com.isabela.dto.comentario.ComentarioDto;
import br.com.isabela.model.comentario.Comentario;
import br.com.isabela.service.autenticacao.LogService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@ApplicationScoped
public class ComentarioDao {

    @Inject
    FabricaDeConexoes bd;

    @Inject
    LogService logService;

    public Comentario obterComentarioPorID(String comentarioId) throws SQLException {
        logService.iniciar(LivroDao.class.getName(), "Iniciando busca do comentario por usuário");
        Connection conexao = bd.obterConexao();
        PreparedStatement pstmt = conexao.prepareStatement(ComentarioQueries.OBTER_COMENTARIO_POR_PERFIL);
        pstmt.setString(1, comentarioId);
        ResultSet resultado = pstmt.executeQuery();
        Comentario comentario = new Comentario();
        if (resultado.next()) comentario = obterComentarioDeResult(resultado);
        logService.sucesso(LivroDao.class.getName(), "Busca do comentario finalizada");
        return comentario;
    }

    public Comentario obterComentariosFeitos(String emailUsuarioPerfil) throws SQLException {
        logService.iniciar(LivroDao.class.getName(), "Iniciando busca de comentarios feitos ou recebidos do usuário");
        Connection conexao = bd.obterConexao();
        PreparedStatement pstmt = conexao.prepareStatement(ComentarioQueries.OBTER_COMENTARIO_FEITOS_OU_RECEBIDOS);
        pstmt.setString(1, emailUsuarioPerfil);
        ResultSet resultado = pstmt.executeQuery();
        Comentario comentario = new Comentario();
        if (resultado.next()) comentario = obterComentarioDeResult(resultado);
        logService.sucesso(LivroDao.class.getName(), "Busca de comentarios feitos finalizada");
        return comentario;
    }

    public void definirParametrosDeSalvamento(PreparedStatement pstmt, ComentarioDto comentario) throws SQLException {
        pstmt.setInt(1, comentario.getId());
        pstmt.setString(2, comentario.getDescricao());
        pstmt.setString(3, comentario.getData_criacao());
        pstmt.setString(4, comentario.getHora_criacao());
    }

    public Comentario obterComentarioDeResult(ResultSet resultado) throws SQLException {
        return Comentario.carregar(
                resultado.getInt("id"),
                resultado.getString("descricao"),
                resultado.getString("data_criacao"),
                resultado.getString("hora_criacao")
        );
    }


}
