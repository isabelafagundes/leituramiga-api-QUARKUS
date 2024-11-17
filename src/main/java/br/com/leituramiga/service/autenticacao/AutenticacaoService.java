package br.com.leituramiga.service.autenticacao;

import br.com.leituramiga.dao.FabricaDeConexoes;
import br.com.leituramiga.dao.endereco.EnderecoDao;
import br.com.leituramiga.dao.usuario.UsuarioDao;
import br.com.leituramiga.dto.usuario.CriacaoUsuarioDto;
import br.com.leituramiga.dto.usuario.IdentificadorUsuarioDto;
import br.com.leituramiga.dto.usuario.UsuarioAutenticadoDto;
import br.com.leituramiga.model.exception.*;
import br.com.leituramiga.model.usuario.Usuario;
import br.com.leituramiga.service.UsuarioService;
import br.com.leituramiga.service.email.EmailService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.io.IOException;
import java.sql.Connection;
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
    EnderecoDao enderecoDao;

    @Inject
    TokenService service;

    @Inject
    EmailService emailService;

    @Inject
    FabricaDeConexoes bd;

    @Inject
    UsuarioService usuarioService;

    public UsuarioAutenticadoDto autenticarUsuario(String login, String senha) throws UsuarioNaoAutorizado, UsuarioNaoExistente, UsuarioNaoAtivo, UsuarioBloqueado, SQLException, ClassNotFoundException {
        String md5Login = HashService.obterMd5Email(login);
        try {
            logService.iniciar(AutenticacaoService.class.getName(), "Iniciando a obtenção do usuário de login " + md5Login);
            usuarioService.validarIdentificadorUsuario(login);
            Usuario usuario = dao.obterUsuario(login);
            logService.sucesso(AutenticacaoService.class.getName(), "Sucesso na obtenção do usuário de login " + md5Login);
            usuarioService.validarUsuarioAtivo(usuario);
            usuarioService.validarUsuarioBloqueado(usuario);
            validarSenhaUsuario(senha, usuario.getSenha(), login);
            UsuarioAutenticadoDto usuarioDto = service.obterUsuarioAutenticado(usuario);
            return usuarioDto;
        } catch (Exception e) {
            logService.erro(AutenticacaoService.class.getName(), "Ocorreu um erro na obtenção do usuário de login " + md5Login, e);
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

    private void validarUsuario(CriacaoUsuarioDto usuarioDto) throws SQLException, UsuarioNaoExistente, UsernameExiste {
        logService.iniciar(AutenticacaoService.class.getName(), "Iniciando a validação da existência do usuário");
        if (dao.validarExistencia(usuarioDto.getEmail(), null)) throw new UsuarioNaoExistente();
        logService.iniciar(AutenticacaoService.class.getName(), "Iniciando a validação do username do usuário");
        if (dao.validarUsername(usuarioDto.getUsername())) throw new UsernameExiste();
    }

    public void salvarUsuario(CriacaoUsuarioDto usuarioDto) throws SQLException, UsernameExiste, UsuarioNaoExistente {
        String md5Email = HashService.obterMd5Email(usuarioDto.email);
        try (Connection conexao = bd.obterConexao()) {
            logService.iniciar(AutenticacaoService.class.getName(), "Iniciando o processo de salvar o usuário de email " + md5Email);
            if (dao.validarExistencia(usuarioDto.getEmail(), null)) throw new UsuarioNaoExistente();
            if (dao.validarUsername(usuarioDto.getUsername())) throw new UsernameExiste();
            dao.salvarUsuario(usuarioDto, conexao);
            logService.sucesso(AutenticacaoService.class.getName(), "Sucesso no processo de salvar o usuário de email " + md5Email);
        } catch (Exception e) {
            logService.erro(AutenticacaoService.class.getName(), "Ocorreu um erro no processo de salvar o usuário de email " + md5Email, e);
            throw e;
        }
    }

    public void desativarUsuario(String email) throws UsuarioNaoExistente, SQLException {
        String md5Email = HashService.obterMd5Email(email);
        try {
            logService.iniciar(AutenticacaoService.class.getName(), "Iniciando o processo de exclusão do usuário de email " + md5Email);
            usuarioService.validarIdentificadorUsuario(email);
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

    public String salvarCodigo(String email, Connection conexao) throws SQLException {
        String md5Email = HashService.obterMd5Email(email);
        try {
            logService.iniciar(AutenticacaoService.class.getName(), "Iniciando o processo de salvar o código de email " + md5Email);
            String codigo = dao.salvarCodigoAlteracao(email, conexao);
            logService.sucesso(AutenticacaoService.class.getName(), "Sucesso no processo de salvar o código de email " + md5Email);
            return codigo;
        } catch (Exception e) {
            logService.erro(AutenticacaoService.class.getName(), "Ocorreu um erro no processo de salvar o código de email " + md5Email, e);
            throw e;
        }
    }

    public void criarUsuario(CriacaoUsuarioDto usuarioDto) throws UsuarioExistente, InformacoesInvalidas, SQLException, ClassNotFoundException, UsernameExiste, UsuarioNaoExistente, IOException {
        String md5Email = HashService.obterMd5Email(usuarioDto.email);
        Connection conexao = null;
        try {
            conexao = bd.obterConexao();
            conexao.setAutoCommit(false);
            logService.iniciar(AutenticacaoService.class.getName(), "Iniciando o processo de salvar o usuário de email " + md5Email);
            validarCriacaoUsuario(usuarioDto);
            String senhaCriptografada = obterSenhaCriptografada(usuarioDto.senha);
            usuarioDto.setSenha(senhaCriptografada);
            validarUsuario(usuarioDto);
            dao.salvarUsuario(usuarioDto, conexao);
            if (usuarioDto.endereco != null)
                enderecoDao.salvarEndereco(usuarioDto.endereco, conexao, usuarioDto.email, true);
            salvarCodigoAlteracaoUsuario(usuarioDto.email, usuarioDto.nome, conexao);
            logService.sucesso(AutenticacaoService.class.getName(), "Sucesso no processo de salvar o usuário de email " + md5Email);
            conexao.commit();
        } catch (Exception e) {
            logService.erro(AutenticacaoService.class.getName(), "Ocorreu um erro no processo de salvar o usuário de email " + md5Email, e);
            if (conexao != null) conexao.rollback();
            throw e;
        } finally {
            bd.desconectar(conexao);
        }
    }

    private void salvarCodigoAlteracaoUsuario(String email, String nome, Connection conexao) throws SQLException, IOException {
        String md5Email = HashService.obterMd5Email(email);
        try {
            logService.iniciar(AutenticacaoService.class.getName(), "Iniciando o processo de salvar o código de alteração do usuário de email " + md5Email);
            String codigo = dao.salvarCodigoAlteracao(email, conexao);
            logService.sucesso(AutenticacaoService.class.getName(), "Sucesso no processo de salvar o código de alteração do usuário de email " + md5Email);
            emailService.enviarEmailCodigoVerificacao(email, codigo, nome);
        } catch (Exception e) {
            logService.erro(AutenticacaoService.class.getName(), "Ocorreu um erro no processo de salvar o código de alteração do usuário de email " + md5Email, e);
            throw e;
        }
    }

    public void enviarCodigoUsuario(String email) throws SQLException, UsuarioNaoExistente, IOException {
        String md5Email = HashService.obterMd5Email(email);
        try (Connection conexao = bd.obterConexao()) {
            logService.iniciar(AutenticacaoService.class.getName(), "Iniciando o processo de envio do código de criação do usuário de email " + md5Email);
            usuarioService.validarIdentificadorUsuario(email);
            Usuario usuario = dao.obterUsuarioPorEmail(email);
            salvarCodigoAlteracaoUsuario(email, usuario.getNome(), conexao);
            logService.sucesso(AutenticacaoService.class.getName(), "Sucesso no processo de envio do código de criação do usuário de email " + md5Email);
        } catch (Exception e) {
            logService.erro(AutenticacaoService.class.getName(), "Ocorreu um erro no processo de envio do código de criação do usuário de email " + md5Email, e);
            throw e;
        }
    }

    public String obterTokenUsuario(String email) throws SQLException {
        try {
            logService.iniciar(AutenticacaoService.class.getName(), "Iniciando a obtenção do token de alteração do usuário de email " + email);
            Usuario usuario = dao.obterUsuarioPorEmail(email);
            logService.sucesso(AutenticacaoService.class.getName(), "Sucesso na obtenção do token de alteração do usuário de email " + email);
            return service.gerarTokenUsuario(usuario);
        } catch (Exception e) {
            logService.erro(AutenticacaoService.class.getName(), "Ocorreu um erro na obtenção do token de alteração do usuário de email " + email, e);
            throw e;
        }
    }

    public String obterTokenSenha(String email) throws SQLException {
        try {
            logService.iniciar(AutenticacaoService.class.getName(), "Iniciando a obtenção do token de alteração do usuário de email " + email);
            Usuario usuario = dao.obterUsuarioPorEmail(email);
            logService.sucesso(AutenticacaoService.class.getName(), "Sucesso na obtenção do token de alteração do usuário de email " + email);
            return service.gerarTokenSenha(usuario);
        } catch (Exception e) {
            logService.erro(AutenticacaoService.class.getName(), "Ocorreu um erro na obtenção do token de alteração do usuário de email " + email, e);
            throw e;
        }
    }

    private void validarCriacaoUsuario(CriacaoUsuarioDto usuarioDto) throws InformacoesInvalidas, UsuarioExistente, SQLException {
        if (!usuarioDto.email.contains("@")) throw new InformacoesInvalidas();
        boolean existencia = dao.validarExistencia(usuarioDto.email, usuarioDto.username);
        if (existencia) throw new UsuarioExistente();
    }

    public void verificarCodigoUsuario(String email, String codigo, String tipoToken) throws SQLException, CodigoIncorreto, UsuarioNaoExistente, TokenDeValidacaoInvalido, IOException {
        String md5Email = HashService.obterMd5Email(email);
        try {
            if (!"user".equals(tipoToken)) throw new TokenDeValidacaoInvalido();
            logService.iniciar(AutenticacaoService.class.getName(), "Iniciando a verificação do código de email " + md5Email);
            usuarioService.validarIdentificadorUsuario(email);
            boolean codigoValido = dao.verificarCodigoSeguranca(email, codigo, false);
            if (!codigoValido) throw new CodigoIncorreto();
            dao.ativarUsuario(email);
            Usuario usuario = dao.obterUsuario(email);
            emailService.enviarEmailBoasVindas(email, usuario.getNome());
            logService.sucesso(AutenticacaoService.class.getName(), "Sucesso na verificação do código de email " + md5Email);
        } catch (Exception e) {
            logService.erro(AutenticacaoService.class.getName(), "Ocorreu um erro na verificação do código de email " + md5Email, e);
            throw e;
        }
    }

    public void verificarCodigoSenha(String email, String codigo, String tipoToken) throws SQLException, CodigoIncorreto, UsuarioNaoExistente, TokenDeValidacaoInvalido {
        String md5Email = HashService.obterMd5Email(email);
        try {
            if (!"password".equals(tipoToken)) throw new TokenDeValidacaoInvalido();
            logService.iniciar(AutenticacaoService.class.getName(), "Iniciando a verificação do código de senha do email " + md5Email);
            usuarioService.validarIdentificadorUsuario(email);
            boolean codigoValido = dao.verificarCodigoSeguranca(email, codigo, true);
            if (!codigoValido) throw new CodigoIncorreto();
            logService.sucesso(AutenticacaoService.class.getName(), "Sucesso na verificação do código de senha do email " + md5Email);
        } catch (Exception e) {
            logService.erro(AutenticacaoService.class.getName(), "Ocorreu um erro na verificação do código desenha do email " + md5Email, e);
            throw e;
        }
    }

    public void iniciarRecuperacaoDeSenha(String email) throws SQLException, UsuarioNaoExistente, IOException {
        try (Connection conexao = bd.obterConexao()) {
            logService.iniciar(AutenticacaoService.class.getName(), "Sucesso no processo de recuperação de senha do usuário de email " + email);
            usuarioService.validarIdentificadorUsuario(email);
            Usuario usuario = dao.obterUsuario(email);
            salvarCodigoAlteracaoUsuario(email, usuario.getNome(), conexao);
            logService.sucesso(AutenticacaoService.class.getName(), "Sucesso no processo de recuperação de senha do usuário de email " + email);
        } catch (Exception e) {
            logService.erro(AutenticacaoService.class.getName(), "Ocorreu um erro no processo de recuperação de senha do usuário de email " + email, e);
            throw e;
        }
    }


    public void atualizarSenhaUsuario(String email, String novaSenha, String tipoToken) throws SQLException, UsuarioNaoAtivo, UsuarioNaoExistente, TokenDeValidacaoInvalido {
        try {
            if (!"password".equals(tipoToken)) throw new TokenDeValidacaoInvalido();
            logService.iniciar(AutenticacaoService.class.getName(), "Iniciando a atualização da nova senha do usuário");
            usuarioService.validarIdentificadorUsuario(email);
            if (!usuarioService.verificarSeUsuarioAtivo(email)) throw new UsuarioNaoAtivo();
            String senhaCriptografada = obterSenhaCriptografada(novaSenha);
            usuarioService.atualizarSenhaUsuario(email, senhaCriptografada);
        } catch (Exception e) {
            logService.erro(AutenticacaoService.class.getName(), "Ocorreu um erro no processo de validação da senha do usuário", e);
            throw e;
        }
    }

    public void validarExistenciaIdentificadorUsuario(IdentificadorUsuarioDto login) throws SQLException, UsuarioExistente {
        try {
            logService.iniciar(AutenticacaoService.class.getName(), "Iniciando a validação do identificador do usuário");
            usuarioService.validarExistenciaIdentificadorUsuario(login);
            logService.sucesso(AutenticacaoService.class.getName(), "Sucesso na validação do identificador do usuário");
        } catch (Exception e) {
            logService.erro(AutenticacaoService.class.getName(), "Ocorreu um erro na validação do identificador do usuário", e);
            throw e;
        }
    }
}
