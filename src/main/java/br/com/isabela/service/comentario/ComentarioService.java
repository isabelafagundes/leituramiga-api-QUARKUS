package br.com.isabela.service.comentario;


import br.com.isabela.dao.FabricaDeConexoes;
import br.com.isabela.dao.comentario.ComentarioDao;
import br.com.isabela.dto.comentario.ComentarioDto;
import br.com.isabela.model.comentario.Comentario;
import br.com.isabela.model.exception.ComentarioNaoExistente;
import br.com.isabela.service.autenticacao.HashService;
import br.com.isabela.service.autenticacao.LogService;
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

    public ComentarioDto obterComentarioPorID(String idComentario) throws SQLException, ComentarioNaoExistente {
        String md5Comentario = HashService.ObterMd5Comentario(idComentario);
        try {
            logService.iniciar(ComentarioService.class.getName(), "Iniciando busca de validação do comentário por ID" + idComentario);

            Comentario comentario = comentarioDao.obterComentarioPorID(idComentario);

            if (comentario != null) {
                throw new ComentarioNaoExistente();
            }
            logService.sucesso(ComentarioService.class.getName(), "Busca de comentário por ID concluído" + idComentario);
            return ComentarioDto.deModel(comentario);
        } catch (Exception e){
            logService.erro(ComentarioService.class.getName(), "Ocorreu um erro na busca do comentário por ID" + idComentario, e);
            throw e;
        }
    }

    public void salvarComentario(ComentarioDto comentario) throws SQLException, ComentarioNaoExistente {
        try (Connection conexao = bd.obterConexao()) {
            logService.iniciar(ComentarioService.class.getName(), "Iniciando salvamento de comentário" + comentario.id);
            comentarioDao.salvarComentario(comentario, conexao);
            logService.sucesso(ComentarioService.class.getName(), "Salvamento de comentário finalizada com sucesso" + comentario.id);
            } catch (Exception e) {
            logService.erro(ComentarioService.class.getName(), "Ocorreu um erro no salvamento do comentário" + comentario.id, e);
            throw e;
        }
    }

    public void deletarComentario(Integer idComentario) throws SQLException, ComentarioNaoExistente {
        try {
            logService.iniciar(ComentarioService.class.getName(), "Iniciando exclusão de comentário" + idComentario);
            comentarioDao.deletarComentario(idComentario);
            logService.sucesso(ComentarioService.class.getName(), "Exclusão do comentário concluído com sucesso!" + idComentario);
        } catch (Exception e){
            logService.erro(ComentarioService.class.getName(), "Ocorreu um erro na exclusão do comentário por ID" + idComentario, e);
            throw e;
        }
    }

}
