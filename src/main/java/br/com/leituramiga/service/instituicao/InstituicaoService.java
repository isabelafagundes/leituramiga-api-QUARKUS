package br.com.leituramiga.service.instituicao;


import br.com.leituramiga.dao.FabricaDeConexoes;
import br.com.leituramiga.dao.instituicao.InstituicaoDao;
import br.com.leituramiga.dto.Instituicao.InstituicaoDto;
import br.com.leituramiga.model.instituicao.Instituicao;
import br.com.leituramiga.service.xlsx.LeituraXlsxService;
import br.com.leituramiga.service.autenticacao.LogService;
import br.com.leituramiga.service.categoria.CategoriaService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class InstituicaoService {

    private static final Logger log = LoggerFactory.getLogger(InstituicaoService.class);
    @Inject
    LogService logService;

    @Inject
    LeituraXlsxService leituraXlsxService;

    @Inject
    InstituicaoDao instituicaoDao;

    @Inject
    FabricaDeConexoes bd;

    public InstituicaoDto obterInstituicoesPorEstado(String uf) throws SQLException {
        logService.iniciar(InstituicaoService.class.getName(), " Iniciando busca de instuição pelo estado" + uf);
        try {
            Instituicao instituicao = instituicaoDao.obterInstituicaoPorEstado(uf);
            logService.sucesso(InstituicaoService.class.getName(), "Busca de instituições pelo estado concluído" + uf);
            return InstituicaoDto.deModel(instituicao);
        } catch (Exception e) {
            logService.erro(InstituicaoService.class.getName(), "Ocorreu um erro na busca de instuições por estado" + uf, e);
            throw e;
        }
    }

    public List<InstituicaoDto> obterInstituicoes(String pesquisa) throws SQLException {
        logService.iniciar(InstituicaoService.class.getName(), "Iniciando a busca de instuições");
        try {
            List<Instituicao> instituicoes = instituicaoDao.obterTodasInstituicoes(pesquisa);
            logService.sucesso(InstituicaoService.class.getName(), "Busca de todas instituições finalizada");
            return instituicoes.stream().map(InstituicaoDto::deModel).toList();
        } catch (Exception e) {
            logService.erro(InstituicaoService.class.getName(), "Ocorreu um erro na busca das instituições", e);
            throw e;
        }
    }

    public void salvarInstituicao(InstituicaoDto instituicao) throws SQLException {
        try {
            logService.iniciar(InstituicaoService.class.getName(), "Iniciando salvamento de instituição" + instituicao.nome);
            instituicaoDao.salvarInstituicao(instituicao);
            logService.sucesso(InstituicaoService.class.getName(), "Salvamento de instituição finalizada com sucesso" + instituicao.nome);
        } catch (Exception e) {
            logService.erro(InstituicaoService.class.getName(), "Ocorreu um erro no salvamento da instituição" + instituicao.nome, e);
            throw e;
        }
    }

    public void deletarInstituicao(Integer deletarInstituicao) throws SQLException {
        try {
            logService.iniciar(CategoriaService.class.getName(), "Iniciando exclusão de instituição" + deletarInstituicao);
            instituicaoDao.deletarInstituicao(deletarInstituicao);
            logService.sucesso(CategoriaService.class.getName(), "Exclusão de Instituição concluída!" + deletarInstituicao);
        } catch (Exception e) {
            logService.erro(CategoriaService.class.getName(), "Ocorreu um erro na exclusão dessa instituição " + deletarInstituicao, e);
            throw e;
        }
    }


    public void salvarInstituicoesDeXlsx(InputStream inputStream) throws SQLException, IOException {
        try {
            logService.iniciar(InstituicaoService.class.getName(), "Iniciando salvamento de instituições por XLSX");
            Map<String, List<String>> instituicoes = leituraXlsxService.obterDadosPlanilha(inputStream);
            logService.iniciar(InstituicaoService.class.getName(), "Iniciando salvamento de instituições por XLSX");
            instituicaoDao.salvarInstituicoesDeMapa(instituicoes);
            logService.sucesso(InstituicaoService.class.getName(), "Salvamento de instituições por XLSX finalizado com sucesso");
        } catch (Exception erro) {
            logService.erro(InstituicaoService.class.getName(), "Ocorreu um erro no salvamento de instituições por XLSX", erro);
            throw erro;
        }
    }


}