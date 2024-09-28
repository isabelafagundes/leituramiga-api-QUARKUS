package br.com.isabela.service.categoria;

import br.com.isabela.dao.FabricaDeConexoes;
import br.com.isabela.dao.categoria.CategoriaDao;
import br.com.isabela.dto.categoria.CategoriaDto;
import br.com.isabela.model.categoria.Categoria;
import br.com.isabela.model.exception.CategoriaNaoExistente;
import br.com.isabela.service.autenticacao.HashService;
import br.com.isabela.service.autenticacao.LogService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.sql.Connection;
import java.sql.SQLException;

@ApplicationScoped
public class CategoriaService {

    @Inject
    LogService logService;

    @Inject
    CategoriaDao categoriaDao;

    @Inject
    FabricaDeConexoes bd;

    public CategoriaDto obterValidarCategoria(String validarCategoria) throws SQLException, CategoriaNaoExistente {
        String md5Categoria= HashService.ObterMd5Categoria(validarCategoria);
        if (!categoriaDao.validarExistencia(validarCategoria)) {
            throw new CategoriaNaoExistente();
        }
        try {
            logService.iniciar(CategoriaService.class.getName(), "Iniciando busca de validação da categoria" + validarCategoria);
            Categoria categoria = categoriaDao.obterCategoria(validarCategoria);
            logService.sucesso(CategoriaService.class.getName(), "Busca de categoria concluida" + validarCategoria);
            return CategoriaDto.deModel(categoria);
        } catch (Exception e) {
            logService.erro(CategoriaService.class.getName(), "Ocorreu um erro na busca de categoria" + validarCategoria, e);
                    throw e;
        }
    }

    public void salvarCategoria(CategoriaDto categoria) throws SQLException {
        try (Connection conexao = bd.obterConexao()) {
            logService.iniciar(CategoriaService.class.getName(), "Iniciando salvamento de comentário" + categoria.descricao);
            categoriaDao.salvarCategoria(categoria, conexao);
            logService.sucesso(CategoriaService.class.getName(), "Salvamento de comentário finalizada com sucesso" + categoria.descricao);
        } catch (Exception e) {
            logService.erro(CategoriaService.class.getName(), "Ocorreu um erro no salvamento do comentário" + categoria.descricao, e);
            throw e;
        }
    }

    public void deletarCategoria(Integer listaCategoria) throws SQLException {
        try {
            logService.iniciar(CategoriaService.class.getName(), "Iniciando exclusão de categoria" + listaCategoria);
            categoriaDao.deletarCategoria(listaCategoria);
            logService.sucesso(CategoriaService.class.getName(), "Exclusão de categoria na lista concluída!" + listaCategoria);
        } catch (Exception e){
            logService.erro(CategoriaService.class.getName(), "Ocorreu um erro na exclusão dessa categoria na lista" + listaCategoria, e);
            throw e;
        }
    }



}
