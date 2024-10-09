package br.com.leituramiga.service.comentario;


import br.com.leituramiga.dao.FabricaDeConexoes;
import br.com.leituramiga.dao.comentario.ComentarioDao;
import br.com.leituramiga.dto.comentario.ComentarioDto;
import br.com.leituramiga.model.comentario.Comentario;
import br.com.leituramiga.model.exception.ComentarioNaoExistente;
import br.com.leituramiga.service.autenticacao.HashService;
import br.com.leituramiga.service.autenticacao.LogService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@ApplicationScoped
public class ComentarioService {

    @Inject
    LogService logService;

    @Inject
    ComentarioDao comentarioDao;

    @Inject
    FabricaDeConexoes bd;

    public List<ComentarioDto> obterComentarioPorUsuario(String emailComentario) throws SQLException, ComentarioNaoExistente {
        try {
            logService.iniciar(ComentarioService.class.getName(), "Iniciando busca de comentários por email " + emailComentario);

            List<Comentario> comentarios = comentarioDao.obterComentarioPorPerfilUsuario(emailComentario);

            logService.sucesso(ComentarioService.class.getName(), "Busca de comentáriod por email " + emailComentario + " concluído");
            return comentarios.stream().map(ComentarioDto::deModel).toList();
        } catch (Exception e) {
            logService.erro(ComentarioService.class.getName(), "Ocorreu um erro na busca do comentário pelo email " + emailComentario, e);
            throw e;
        }
    }

    public void salvarComentario(ComentarioDto comentario) throws SQLException {

        try {
            logService.iniciar(ComentarioService.class.getName(), "Iniciando salvamento de comentário do usuário de email " + comentario.emailUsuarioPerfil);
            comentarioDao.salvarComentario(comentario);
            logService.sucesso(ComentarioService.class.getName(), "Salvamento de comentário finalizada com sucesso do usuário de email " + comentario.emailUsuarioPerfil);
        } catch (Exception e) {
            logService.erro(ComentarioService.class.getName(), "Ocorreu um erro no salvamento do comentário do usuário de email " + comentario.emailUsuarioPerfil, e);
            throw e;
        }
    }

    public void deletarComentario(Integer numeroComentario) throws SQLException {
        try {
            logService.iniciar(ComentarioService.class.getName(), "Iniciando exclusão de comentário de número " + numeroComentario);
            comentarioDao.deletarComentario(numeroComentario);
            logService.sucesso(ComentarioService.class.getName(), "Exclusão do comentário de número " + numeroComentario + " concluída");
        } catch (Exception e) {
            logService.erro(ComentarioService.class.getName(), "Ocorreu um erro na exclusão do comentário de número " + numeroComentario, e);
            throw e;
        }
    }

}
