package br.com.isabela.service.autenticacao;

import br.com.isabela.dto.CriacaoUsuarioDto;
import br.com.isabela.dto.UsuarioAutenticadoDto;
import br.com.isabela.dto.UsuarioDto;
import br.com.isabela.dao.UsuarioDao;
import br.com.isabela.model.Usuario;
import br.com.isabela.model.exception.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.sql.SQLException;

@ApplicationScoped
public class AutenticacaoService {

    @Inject
    LogService logService;

    @Inject
    TokenService tokenService;

    @Inject
    UsuarioDao dao;

    @Inject
    TokenService service;

    public UsuarioAutenticadoDto autenticarUsuario(String login, String senha) throws UsuarioNaoAutorizado, UsuarioNaoExistente, UsuarioNaoAtivo, UsuarioBloqueado, SQLException, ClassNotFoundException {
        String md5Login = HashService.obterMd5Email(login);
        try {
            logService.iniciar(AutenticacaoService.class.getName(), "Iniciando a obtenção do usuário de login " + md5Login);
            validarEmail(login);
            Usuario usuario = dao.obterUsuario(login);
            logService.sucesso(AutenticacaoService.class.getName(), "Sucesso na obtenção do usuário de login " + md5Login);
            validarUsuarioAtivo(usuario);
            validarUsuarioBloqueado(usuario);
            validarSenhaUsuario(senha, usuario.getSenha(), login);
            UsuarioAutenticadoDto usuarioDto = service.obterUsuarioAutenticado(usuario);
            return usuarioDto;
        } catch (Exception e) {
            logService.erro(AutenticacaoService.class.getName(), "Ocorreu um erro na obtenção do usuário de login " + md5Login, e);
            throw e;
        }
    }


    public UsuarioDto obterUsuarioDto(String email) throws UsuarioNaoAtivo, UsuarioNaoExistente, SQLException, ClassNotFoundException {
        String md5Email = HashService.obterMd5Email(email);
        try {
            logService.iniciar(AutenticacaoService.class.getName(), "Iniciando a obtenção do usuário de email " + md5Email);
            validarEmail(email);
            Usuario usuario = obterUsuario(email);
            validarUsuarioAtivo(usuario);
            logService.sucesso(AutenticacaoService.class.getName(), "Sucesso na obtenção do usuário de email " + md5Email);
            return UsuarioDto.deDomain(usuario.getNome(), usuario.getUsername(), usuario.getEmail(), usuario.getTipoUsuario());
        } catch (Exception e) {
            logService.erro(AutenticacaoService.class.getName(), "Ocorreu um erro na obtenção do usuário de email " + md5Email, e);
            throw e;
        }
    }

    public Usuario obterUsuario(String email) throws UsuarioNaoAtivo, UsuarioNaoExistente, SQLException, ClassNotFoundException {
        String md5Email = HashService.obterMd5Email(email);
        try {
            logService.iniciar(AutenticacaoService.class.getName(), "Iniciando a obtenção do usuário de email " + md5Email);
            validarEmail(email);
            Usuario usuario = dao.obterUsuario(email);
            validarUsuarioAtivo(usuario);
            logService.sucesso(AutenticacaoService.class.getName(), "Sucesso na obtenção do usuário de email " + md5Email);
            return usuario;
        } catch (Exception e) {
            logService.erro(AutenticacaoService.class.getName(), "Ocorreu um erro na obtenção do usuário de email " + md5Email, e);
            throw e;
        }
    }

    public UsuarioAutenticadoDto atualizarTokens(String email, String tipoToken) throws RefreshTokenInvalido, UsuarioNaoAtivo, UsuarioNaoExistente, SQLException, ClassNotFoundException {
        String md5Email = HashService.obterMd5Email(email);
        try {
            logService.iniciar(AutenticacaoService.class.getName(), "Iniciando a atualização dos tokens do usuário de email " + md5Email);
            Usuario usuario = dao.obterUsuario(email);
            UsuarioAutenticadoDto dto = tokenService.atualizarToken(usuario, tipoToken);
            logService.iniciar(AutenticacaoService.class.getName(), "Sucesso na atualização dos tokens do usuário de email " + md5Email);
            return dto;
        } catch (Exception e) {
            logService.erro(AutenticacaoService.class.getName(), "Ocorreu um erro na atualização dos tokens do usuário de email " + md5Email, e);
            throw e;
        }
    }

    public void criarUsuario(CriacaoUsuarioDto usuarioDto) throws UsuarioExistente, InformacoesInvalidas, SQLException, ClassNotFoundException {
        String md5Email = HashService.obterMd5Email(usuarioDto.email);
        try {
            logService.iniciar(AutenticacaoService.class.getName(), "Iniciando o processo de salvar o usuário de email " + md5Email);
            if (!usuarioDto.email.contains("@")) throw new InformacoesInvalidas();
            boolean existencia = dao.validarExistencia(usuarioDto.email, usuarioDto.username);
            if (existencia) throw new UsuarioExistente();
            String senhaCriptografada = obterSenhaCriptografada(usuarioDto.senha);
            usuarioDto.setSenha(senhaCriptografada);
            dao.salvarUsuario(usuarioDto);
            logService.sucesso(AutenticacaoService.class.getName(), "Sucesso no processo de salvar o usuário de email " + md5Email);
        } catch (Exception e) {
            logService.erro(AutenticacaoService.class.getName(), "Ocorreu um erro no processo de salvar o usuário de email " + md5Email, e);
            throw e;
        }
    }

    public void salvarUsuario(CriacaoUsuarioDto usuarioDto) throws SQLException, ClassNotFoundException {
        String md5Email = HashService.obterMd5Email(usuarioDto.email);
        try {
            logService.iniciar(AutenticacaoService.class.getName(), "Iniciando o processo de salvar o usuário de email " + md5Email);
            dao.salvarUsuario(usuarioDto);
            logService.sucesso(AutenticacaoService.class.getName(), "Sucesso no processo de salvar o usuário de email " + md5Email);
        } catch (Exception e) {
            logService.erro(AutenticacaoService.class.getName(), "Ocorreu um erro no processo de salvar o usuário de email " + md5Email, e);
            throw e;
        }
    }

    public void desativarUsuario(String email) throws UsuarioNaoExistente, SQLException, ClassNotFoundException {
        String md5Email = HashService.obterMd5Email(email);
        try {
            logService.iniciar(AutenticacaoService.class.getName(), "Iniciando o processo de exclusão do usuário de email " + md5Email);
            validarEmail(email);
            dao.desativarUsuario(email);
            logService.sucesso(AutenticacaoService.class.getName(), "Sucesso no processo de exclusão do usuário de email " + md5Email);
        } catch (Exception e) {
            logService.erro(AutenticacaoService.class.getName(), "Ocorreu um erro no processo de excluir o usuário de email " + md5Email, e);
            throw e;
        }
    }

    private void validarSenhaUsuario(String senhaEnviada, String senhaSalva, String login) throws UsuarioNaoAutorizado, SQLException, ClassNotFoundException {
        String md5Login = HashService.obterMd5Email(login);
        try {
            logService.iniciar(AutenticacaoService.class.getName(), "Iniciando a verificação da senha do usuário de login " + md5Login);
            boolean senhaValida = verificarSenha(senhaEnviada, senhaSalva);
            if (!senhaValida) {
                atualizarTentativas(login);
                throw new UsuarioNaoAutorizado();
            }
            dao.atualizarTentativas(login, true);
            logService.sucesso(AutenticacaoService.class.getName(), "Sucesso na verificação da senha, usuário autorizado");
        } catch (Exception e) {
            logService.erro(AutenticacaoService.class.getName(), "Ocorreu um erro na verificação da senha, usuário não autorizado", e);
            throw e;
        }
    }

    private void validarUsuarioAtivo(Usuario usuario) throws UsuarioNaoAtivo {
        logService.iniciar(AutenticacaoService.class.getName(), "Verificando se o usuário está ativo na base de dados");
        if (!usuario.isAtivo()) throw new UsuarioNaoAtivo();
    }

    private void validarUsuarioBloqueado(Usuario usuario) throws UsuarioBloqueado {
        logService.iniciar(AutenticacaoService.class.getName(), "Verificando se o usuário está bloqueado na base de dados");
        if (usuario.isBloqueado()) throw new UsuarioBloqueado();
    }

    private void validarEmail(String login) throws UsuarioNaoExistente, SQLException, ClassNotFoundException {
        logService.iniciar(AutenticacaoService.class.getName(), "Verificando a existência do usuário de login " + login);
        boolean existenciaUsuario = dao.validarExistencia(login, null);
        if (!existenciaUsuario) throw new UsuarioNaoExistente();
    }

    private void atualizarTentativas(String login) throws SQLException, ClassNotFoundException {
        String md5Login = HashService.obterMd5Email(login);
        logService.iniciar(AutenticacaoService.class.getName(), "Atualizando tentativas de login do usuário de login " + md5Login);
        dao.atualizarTentativas(login, false);
    }

    public boolean verificarSenha(String senhaEnviada, String senhaSalva) {
        logService.iniciar(AutenticacaoService.class.getName(), "Iniciando a verificação das senhas");
        boolean senhaValida = BCrypt.checkpw(senhaEnviada, senhaSalva);
        logService.sucesso(AutenticacaoService.class.getName(), "Sucesso na verificação das senhas");
        return senhaValida;
    }

    public String obterSenhaCriptografada(String senha) {
        logService.iniciar(AutenticacaoService.class.getName(), "Iniciando a criptografia da senha");
        return BCrypt.hashpw(senha, BCrypt.gensalt());
    }
}
