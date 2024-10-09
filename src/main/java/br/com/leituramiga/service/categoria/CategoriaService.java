package br.com.leituramiga.service.categoria;

import br.com.leituramiga.dao.FabricaDeConexoes;
import br.com.leituramiga.dao.categoria.CategoriaDao;
import br.com.leituramiga.dto.categoria.CategoriaDto;
import br.com.leituramiga.model.categoria.Categoria;
import br.com.leituramiga.model.exception.CategoriaNaoExistente;
import br.com.leituramiga.service.autenticacao.LogService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.SQLException;
import java.util.List;

@ApplicationScoped
public class CategoriaService {

    @Inject
    LogService logService;

    @Inject
    CategoriaDao categoriaDao;

    @Inject
    FabricaDeConexoes bd;

    public List<CategoriaDto> obterCategorias() throws SQLException {
        try {
            logService.iniciar(CategoriaService.class.getName(), "Iniciando busca de categorias");
            List<Categoria> categorias = categoriaDao.obterCategoria();
            logService.sucesso(CategoriaService.class.getName(), "Sucesso na busca de " + categorias.size() + " categorias");
            return categorias.stream().map(CategoriaDto::deModel).toList();
        } catch (Exception e) {
            logService.erro(CategoriaService.class.getName(), "Ocorreu um erro na busca de categorias", e);
            throw e;
        }
    }

    public void salvarCategoria(CategoriaDto categoria) throws SQLException {
        try {
            logService.iniciar(CategoriaService.class.getName(), "Iniciando salvamento de comentário" + categoria.descricao);
            categoriaDao.salvarCategoria(categoria);
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
        } catch (Exception e) {
            logService.erro(CategoriaService.class.getName(), "Ocorreu um erro na exclusão dessa categoria na lista" + listaCategoria, e);
            throw e;
        }
    }


}
