package br.com.leituramiga.service;

import br.com.leituramiga.dao.usuario.UsuarioDao;
import br.com.leituramiga.dto.usuario.UsuarioDto;
import br.com.leituramiga.model.exception.*;
import br.com.leituramiga.model.usuario.Usuario;
import br.com.leituramiga.service.autenticacao.AutenticacaoService;
import br.com.leituramiga.service.autenticacao.HashService;
import br.com.leituramiga.service.autenticacao.LogService;
import jakarta.inject.Inject;

import java.sql.SQLException;
import java.util.List;

public class UsuarioService {

    @Inject
    LogService logService;

    @Inject
    UsuarioDao dao;

    public UsuarioDto obterUsuarioPorIdentificador(String identificador) throws UsuarioNaoAtivo, UsuarioNaoExistente, SQLException, ClassNotFoundException {
        String md5Email = HashService.obterMd5Email(identificador);
        try {
            logService.iniciar(AutenticacaoService.class.getName(), "Iniciando a obtenção do usuário de identificador " + md5Email);
            validarIdentificadorUsuario(identificador);
            Usuario usuario = obterUsuario(identificador);
            validarUsuarioAtivo(usuario);
            logService.sucesso(AutenticacaoService.class.getName(), "Sucesso na obtenção do usuário de identificador " + md5Email);
            return UsuarioDto.deModel(usuario);
        } catch (Exception e) {
            logService.erro(AutenticacaoService.class.getName(), "Ocorreu um erro na obtenção do usuário de identificador " + md5Email, e);
            throw e;
        }
    }

    public List<UsuarioDto> obterUsuariosPaginados(Integer numeroCidade, Integer numeroInstituicao, String pesquisa, Integer pagina, Integer tamanhoPagina) throws SQLException {
        try {
            logService.iniciar(UsuarioService.class.getName(), "Iniciando a obtenção dos usuários paginados");
            List<Usuario> usuarios = dao.obterUsuariosPaginados(numeroCidade, numeroInstituicao, pesquisa, pagina, tamanhoPagina);
            logService.sucesso(UsuarioService.class.getName(), "Sucesso na obtenção dos usuários paginados");
            return usuarios.stream().map(UsuarioDto::deModel).toList();
        } catch (Exception e) {
            logService.erro(UsuarioService.class.getName(), "Ocorreu um erro na obtenção dos usuários paginados", e);
            throw e;
        }
    }

    public Usuario obterUsuario(String identificador) throws UsuarioNaoAtivo, UsuarioNaoExistente, SQLException {
        String md5Identificador = HashService.obterMd5Email(identificador);
        try {
            logService.iniciar(AutenticacaoService.class.getName(), "Iniciando a obtenção do usuário de identificador " + md5Identificador);
            validarIdentificadorUsuario(identificador);
            Usuario usuario = dao.obterUsuario(identificador);
            validarUsuarioAtivo(usuario);
            logService.sucesso(AutenticacaoService.class.getName(), "Sucesso na obtenção do usuário de identificador " + md5Identificador);
            return usuario;
        } catch (Exception e) {
            logService.erro(AutenticacaoService.class.getName(), "Ocorreu um erro na obtenção do usuário de identificador " + md5Identificador, e);
            throw e;
        }
    }

    public void validarUsuarioAtivo(Usuario usuario) throws UsuarioNaoAtivo {
        logService.iniciar(AutenticacaoService.class.getName(), "Verificando se o usuário está ativo na base de dados");
        if (!usuario.isAtivo()) throw new UsuarioNaoAtivo();
    }

    public void validarUsuarioBloqueado(Usuario usuario) throws UsuarioBloqueado {
        logService.iniciar(AutenticacaoService.class.getName(), "Verificando se o usuário está bloqueado na base de dados");
        if (usuario.isBloqueado()) throw new UsuarioBloqueado();
    }

    public void validarIdentificadorUsuario(String login) throws UsuarioNaoExistente, SQLException {
        logService.iniciar(AutenticacaoService.class.getName(), "Verificando a existência do usuário de login " + login);
        boolean existenciaUsuario = dao.validarExistencia(login, null);
        if (!existenciaUsuario) throw new UsuarioNaoExistente();
    }

}