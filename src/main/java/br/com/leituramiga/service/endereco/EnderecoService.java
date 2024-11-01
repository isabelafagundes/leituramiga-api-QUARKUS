package br.com.leituramiga.service.endereco;

import br.com.leituramiga.dao.FabricaDeConexoes;
import br.com.leituramiga.dao.endereco.EnderecoDao;
import br.com.leituramiga.dto.endereco.CidadeDto;
import br.com.leituramiga.dto.endereco.EnderecoDto;
import br.com.leituramiga.model.cidade.Cidade;
import br.com.leituramiga.model.endereco.Endereco;
import br.com.leituramiga.model.exception.EnderecoNaoExistente;
import br.com.leituramiga.service.autenticacao.HashService;
import br.com.leituramiga.service.autenticacao.LogService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@ApplicationScoped
public class EnderecoService {

    @Inject
    LogService logService;

    @Inject
    EnderecoDao enderecoDao;

    @Inject
    FabricaDeConexoes bd;

    public EnderecoDto obterEnderecoUsuario(String email) throws SQLException, EnderecoNaoExistente {
        String md5Email = HashService.obterMd5Email(email);
        try {
            logService.iniciar(EnderecoService.class.getName(), "Iniciando a validação do endereço do usuário de email " + md5Email);
            if (!enderecoDao.validarExistencia(email)) throw new EnderecoNaoExistente();
            logService.iniciar(EnderecoService.class.getName(), "Iniciando busca de endereço do usuário de email " + md5Email);
            Endereco endereco = enderecoDao.obterEnderecoUsuario(email);
            logService.sucesso(EnderecoService.class.getName(), "Busca de endereço do usuário finalizada de email " + md5Email);
            return EnderecoDto.deModel(endereco);
        } catch (Exception e) {
            logService.erro(EnderecoService.class.getName(), "Ocorreu um erro na busca de endereço do usuário de email " + md5Email, e);
            throw e;
        }
    }


    public void cadastrarEndereco(EnderecoDto endereco, String email) throws SQLException {
        try (Connection conexao = bd.obterConexao()) {
            logService.iniciar(EnderecoService.class.getName(), "Iniciando salvamento de endereço do usuário de email " + endereco.emailUsuario);
            enderecoDao.salvarEndereco(endereco, conexao, email, true);
            logService.sucesso(EnderecoService.class.getName(), "Salvamento de endereço do usuário finalizado de email " + endereco.emailUsuario);
        } catch (Exception e) {
            logService.erro(EnderecoService.class.getName(), "Ocorreu um erro no salvamento de endereço do usuário de email " + endereco.emailUsuario, e);
            throw e;
        }
    }

    public void cadastrarEnderecoSolicitacao(EnderecoDto endereco, String email, Integer codigoSolicitacao) throws SQLException {
        try (Connection conexao = bd.obterConexao()) {
            logService.iniciar(EnderecoService.class.getName(), "Iniciando salvamento de endereço do usuário de email " + endereco.emailUsuario);
            Integer codigoEndereco = enderecoDao.salvarEndereco(endereco, conexao, email, true);
            enderecoDao.salvarEnderecoSolicitacao(codigoEndereco, codigoSolicitacao, email, conexao);
            logService.sucesso(EnderecoService.class.getName(), "Salvamento de endereço do usuário finalizado de email " + endereco.emailUsuario);
        } catch (Exception e) {
            logService.erro(EnderecoService.class.getName(), "Ocorreu um erro no salvamento de endereço do usuário de email " + endereco.emailUsuario, e);
            throw e;
        }
    }

    public void salvarEnderecoSolicitacao(EnderecoDto endereco, String email, Integer codigoSolicitacao, Connection conexao, boolean enderecoPrincipal) throws SQLException {
        try {
            logService.iniciar(EnderecoService.class.getName(), "Iniciando salvamento de endereço do usuário de email " + endereco.emailUsuario);
            enderecoDao.atualizarEndereco(endereco, conexao, email, enderecoPrincipal);
            enderecoDao.atualizarEnderecoSolicitacao(email, codigoSolicitacao, endereco.codigoEndereco, conexao);
            logService.sucesso(EnderecoService.class.getName(), "Salvamento de endereço do usuário finalizado de email " + endereco.emailUsuario);
        } catch (Exception e) {
            logService.erro(EnderecoService.class.getName(), "Ocorreu um erro no salvamento de endereço do usuário de email " + endereco.emailUsuario, e);
            throw e;
        }
    }

    public void atualizarEnderecoSolicitacao(EnderecoDto endereco, String email, Connection conexao, boolean enderecoPrincipal) throws SQLException, EnderecoNaoExistente {
        try {
            if (!enderecoDao.validarExistenciaPorId(endereco.getCodigoEndereco())) throw new EnderecoNaoExistente();
            logService.iniciar(EnderecoService.class.getName(), "Iniciando salvamento de endereço do usuário de email " + endereco.emailUsuario);
            enderecoDao.atualizarEndereco(endereco, conexao, email, enderecoPrincipal);
            logService.sucesso(EnderecoService.class.getName(), "Salvamento de endereço do usuário finalizado de email " + endereco.emailUsuario);
        } catch (Exception e) {
            logService.erro(EnderecoService.class.getName(), "Ocorreu um erro no salvamento de endereço do usuário de email " + endereco.emailUsuario, e);
            throw e;
        }
    }

    public void atualizarEndereco(EnderecoDto endereco, String email) throws SQLException, EnderecoNaoExistente {
        try (Connection conexao = bd.obterConexao()) {
            logService.iniciar(EnderecoService.class.getName(), "Iniciando atualização de endereço do usuário de email " + endereco.emailUsuario);
            if (!validarExistenciaPorId(endereco.codigoEndereco)) throw new EnderecoNaoExistente();
            enderecoDao.atualizarEndereco(endereco, conexao, email, endereco.enderecoPrincipal);
            logService.sucesso(EnderecoService.class.getName(), "Atualização de endereço do usuário finalizada de email " + endereco.emailUsuario);
        } catch (Exception e) {
            logService.erro(EnderecoService.class.getName(), "Ocorreu um erro na atualização de endereço do usuário de email " + endereco.emailUsuario, e);
            throw e;
        }
    }

    public void deletarEndereco(int numeroEndereco) throws SQLException {
        try {
            logService.iniciar(EnderecoService.class.getName(), "Iniciando exclusão de endereço do usuário de email " + numeroEndereco);
            enderecoDao.deletarEndereco(numeroEndereco);
            logService.sucesso(EnderecoService.class.getName(), "Exclusão de endereço do usuário finalizada de email " + numeroEndereco);
        } catch (Exception e) {
            logService.erro(EnderecoService.class.getName(), "Ocorreu um erro na exclusão de endereço do usuário de email " + numeroEndereco, e);
            throw e;
        }
    }

    public List<CidadeDto> obterCidades(String uf, String pesquisa) throws SQLException {
        try {
            logService.sucesso(EnderecoService.class.getName(), "Iniciando a obtenção das cidades da UF " + uf);
            List<Cidade> cidades = enderecoDao.obterCidades(uf, pesquisa);
            logService.sucesso(EnderecoService.class.getName(), "Sucesso na obtenção das cidades da UF " + uf);
            return cidades.stream().map(CidadeDto::deModel).toList();
        } catch (Exception e) {
            logService.erro(EnderecoService.class.getName(), "Ocorreu um erro na obtenção das cidades da UF " + uf, e);
            throw e;
        }
    }

    public EnderecoDto obterEnderecoPorId(Integer codigoEndereco) throws SQLException {
        try {
            logService.sucesso(EnderecoService.class.getName(), "Iniciando a obtenção do endereço de código " + codigoEndereco);
            Endereco endereco = enderecoDao.obterEnderecoPorId(codigoEndereco);
            logService.sucesso(EnderecoService.class.getName(), "Sucesso na obtenção do endereço de código " + codigoEndereco);
            return EnderecoDto.deModel(endereco);
        } catch (Exception e) {
            logService.erro(EnderecoService.class.getName(), "Ocorreu um erro na obtenção do endereço de código " + codigoEndereco, e);
            throw e;
        }
    }

    public boolean validarExistenciaPorId(Integer id) throws SQLException {
        try {
            logService.iniciar(EnderecoService.class.getName(), "Iniciando validação de existência de endereço");
            boolean existe = enderecoDao.validarExistenciaPorId(id);
            logService.sucesso(EnderecoService.class.getName(), "Validação de existência de endereço finalizada");
            return existe;
        } catch (Exception e) {
            logService.erro(EnderecoService.class.getName(), "Ocorreu um erro na validação de existência de endereço", e);
            throw e;
        }
    }

    public boolean validarExistenciaPorEmail(String email) throws SQLException {
        try {
            logService.iniciar(EnderecoService.class.getName(), "Iniciando validação de existência de endereço");
            boolean existe = enderecoDao.validarExistencia(email);
            logService.sucesso(EnderecoService.class.getName(), "Validação de existência de endereço finalizada");
            return existe;
        } catch (Exception e) {
            logService.erro(EnderecoService.class.getName(), "Ocorreu um erro na validação de existência de endereço", e);
            throw e;
        }
    }
}


