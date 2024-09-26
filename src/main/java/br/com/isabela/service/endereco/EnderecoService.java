package br.com.isabela.service.endereco;

import br.com.isabela.dao.FabricaDeConexoes;
import br.com.isabela.dao.endereco.EnderecoDao;
import br.com.isabela.dto.endereco.EnderecoDto;
import br.com.isabela.model.endereco.Endereco;
import br.com.isabela.model.exception.EnderecoNaoExistente;
import br.com.isabela.service.autenticacao.HashService;
import br.com.isabela.service.autenticacao.LogService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.Connection;
import java.sql.SQLException;

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


    public void salvarEndereco(EnderecoDto endereco) throws SQLException {
        try (Connection conexao = bd.obterConexao()) {
            logService.iniciar(EnderecoService.class.getName(), "Iniciando salvamento de endereço do usuário de email " + endereco.emailUsuario);
            enderecoDao.salvarEndereco(endereco, conexao);
            logService.sucesso(EnderecoService.class.getName(), "Salvamento de endereço do usuário finalizado de email " + endereco.emailUsuario);
        } catch (Exception e) {
            logService.erro(EnderecoService.class.getName(), "Ocorreu um erro no salvamento de endereço do usuário de email " + endereco.emailUsuario, e);
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
}


