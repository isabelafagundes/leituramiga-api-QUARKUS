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

@ApplicationScoped
public class ComentarioService {

    @Inject
    LogService logService;

    @Inject
    ComentarioDao comentarioDao;

    @Inject
    FabricaDeConexoes bd;

    public ComentarioDto obterComentarioPorUsuario(String emailComentario) throws SQLException, ComentarioNaoExistente {
        try {
            logService.iniciar(ComentarioService.class.getName(), "Iniciando busca de validação do comentário por email do destinatario" + emailComentario);

            Comentario comentario = comentarioDao.obterComentarioPorPerfilUsuario(emailComentario);

            if (comentario != null) {
                throw new ComentarioNaoExistente();
            }
            logService.sucesso(ComentarioService.class.getName(), "Busca de comentário por email concluído" + emailComentario);
            return ComentarioDto.deModel(comentario);
        } catch (Exception e) {
            logService.erro(ComentarioService.class.getName(), "Ocorreu um erro na busca do comentário pelo email" + emailComentario, e);
            throw e;
        }
    }

    public void salvarComentario(ComentarioDto comentario) throws SQLException, ComentarioNaoExistente {

        try {
            logService.iniciar(ComentarioService.class.getName(), "Iniciando salvamento de comentário" + comentario.emailUsuarioPerfil);
            comentarioDao.salvarComentario(comentario);
            logService.sucesso(ComentarioService.class.getName(), "Salvamento de comentário finalizada com sucesso" + comentario.emailUsuarioPerfil);
        } catch (Exception e) {
            logService.erro(ComentarioService.class.getName(), "Ocorreu um erro no salvamento do comentário" + comentario.emailUsuarioPerfil, e);
            throw e;
        }
    }

    public void deletarComentario(Integer perfilComentario) throws SQLException, ComentarioNaoExistente {
        try {
            logService.iniciar(ComentarioService.class.getName(), "Iniciando exclusão de comentário" + perfilComentario);
            comentarioDao.deletarComentario(perfilComentario);
            logService.sucesso(ComentarioService.class.getName(), "Exclusão do comentário concluído com sucesso!" + perfilComentario);
        } catch (Exception e) {
            logService.erro(ComentarioService.class.getName(), "Ocorreu um erro na exclusão do comentário" + perfilComentario, e);
            throw e;
        }
    }

}
