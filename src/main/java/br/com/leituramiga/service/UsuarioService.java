package br.com.leituramiga.service;

import br.com.leituramiga.dao.FabricaDeConexoes;
import br.com.leituramiga.dao.usuario.UsuarioDao;
import br.com.leituramiga.dto.usuario.IdentificadorUsuarioDto;
import br.com.leituramiga.dto.usuario.UsuarioDto;
import br.com.leituramiga.model.exception.UsuarioBloqueado;
import br.com.leituramiga.model.exception.UsuarioExistente;
import br.com.leituramiga.model.exception.UsuarioNaoAtivo;
import br.com.leituramiga.model.exception.UsuarioNaoExistente;
import br.com.leituramiga.model.usuario.Usuario;
import br.com.leituramiga.service.autenticacao.HashService;
import br.com.leituramiga.service.autenticacao.LogService;
import br.com.leituramiga.service.imagem.ImagemService;
import br.com.leituramiga.service.livro.LivroService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@ApplicationScoped
public class UsuarioService {

    @Inject
    LogService logService;

    @Inject
    ImagemService imagemService;

    @Inject
    UsuarioDao dao;

    @Inject
    FabricaDeConexoes bd;

    public UsuarioDto obterUsuarioPorIdentificador(String identificador) throws UsuarioNaoAtivo, UsuarioNaoExistente, SQLException, IOException {
        String md5Email = HashService.obterMd5Email(identificador);
        try {
            logService.iniciar(UsuarioService.class.getName(), "Iniciando a obtenção do usuário de identificador " + md5Email);
            validarIdentificadorUsuario(identificador);
            Usuario usuario = obterUsuario(identificador);
            if (usuario == null) throw new UsuarioNaoExistente();
            validarUsuarioAtivo(usuario);
            String imagem = imagemService.obterImagemUsuario(usuario.getCaminhoImagem());
            usuario.setImagem(imagem);
            logService.sucesso(UsuarioService.class.getName(), "Sucesso na obtenção do usuário de identificador " + md5Email);
            return UsuarioDto.deModel(usuario);
        } catch (Exception e) {
            logService.erro(UsuarioService.class.getName(), "Ocorreu um erro na obtenção do usuário de identificador " + md5Email, e);
            throw e;
        }
    }

    public List<UsuarioDto> obterUsuariosPaginados(Integer numeroCidade, Integer numeroInstituicao, String pesquisa, Integer pagina, Integer tamanhoPagina) throws SQLException, IOException {
        try {
            logService.iniciar(UsuarioService.class.getName(), "Iniciando a obtenção dos usuários paginados");
            List<Usuario> usuarios = dao.obterUsuariosPaginados(numeroCidade, numeroInstituicao, pesquisa, pagina, tamanhoPagina);
            for (Usuario usuario : usuarios) {
                String imagem = imagemService.obterImagemUsuario(usuario.getCaminhoImagem());
                usuario.setImagem(imagem);
            }
            logService.sucesso(UsuarioService.class.getName(), "Sucesso na obtenção dos usuários paginados");
            return usuarios.stream().map(UsuarioDto::deModel).toList();
        } catch (Exception e) {
            logService.erro(UsuarioService.class.getName(), "Ocorreu um erro na obtenção dos usuários paginados", e);
            throw e;
        }
    }

    public Usuario obterUsuario(String identificador) throws UsuarioNaoAtivo, UsuarioNaoExistente, SQLException, IOException {
        String md5Identificador = HashService.obterMd5Email(identificador);
        try {
            logService.iniciar(UsuarioService.class.getName(), "Iniciando a obtenção do usuário de identificador " + md5Identificador);
            validarIdentificadorUsuario(identificador);
            Usuario usuario = dao.obterUsuario(identificador);
            validarUsuarioAtivo(usuario);
            logService.sucesso(UsuarioService.class.getName(), "Sucesso na obtenção do usuário de identificador " + md5Identificador);
            return usuario;
        } catch (Exception e) {
            logService.erro(UsuarioService.class.getName(), "Ocorreu um erro na obtenção do usuário de identificador " + md5Identificador, e);
            throw e;
        }
    }

    public boolean verificarSeUsuarioAtivo(String email) throws SQLException {
        try {
            logService.iniciar(UsuarioService.class.getName(), "Verificando se o usuário de email " + email + " está ativo");
            boolean ativo = dao.verificarUsuarioAtivo(email);
            logService.sucesso(UsuarioService.class.getName(), "Sucesso na verificação do usuário de email " + email + " ativo");
            return ativo;
        } catch (Exception e) {
            logService.erro(UsuarioService.class.getName(), "Ocorreu um erro na verificação do usuário de email " + email + " ativo", e);
            throw e;
        }
    }

    public void validarUsuarioAtivo(Usuario usuario) throws UsuarioNaoAtivo {
        logService.iniciar(UsuarioService.class.getName(), "Verificando se o usuário está ativo na base de dados");
        if (usuario == null || !usuario.isAtivo()) throw new UsuarioNaoAtivo();
    }

    public void validarUsuarioBloqueado(Usuario usuario) throws UsuarioBloqueado {
        logService.iniciar(UsuarioService.class.getName(), "Verificando se o usuário está bloqueado na base de dados");
        if (usuario.isBloqueado()) throw new UsuarioBloqueado();
    }

    public void validarIdentificadorUsuario(String login) throws UsuarioNaoExistente, SQLException {
        logService.iniciar(UsuarioService.class.getName(), "Verificando a existência do usuário de login " + login);
        boolean existenciaUsuario = dao.validarExistencia(login, null);
        if (!existenciaUsuario) throw new UsuarioNaoExistente();
    }

    public void validarExistenciaIdentificadorUsuario(IdentificadorUsuarioDto login) throws UsuarioExistente, SQLException {
        logService.iniciar(UsuarioService.class.getName(), "Verificando a existência do usuário de login " + login);
        boolean existenciaUsuario = dao.validarExistencia(login.email, login.username);
        if (existenciaUsuario) throw new UsuarioExistente();
    }

    public IdentificadorUsuarioDto obterIdentificadorUsuario(String email, String username) throws SQLException, UsuarioNaoExistente {
        try {
            logService.iniciar(UsuarioService.class.getName(), "Obtendo o identificador do usuário de email " + email);
            validarIdentificadorUsuario(username == null ? email : username);
            IdentificadorUsuarioDto identificadorUsuario = dao.obterIdentificadorUsuario(email);
            logService.sucesso(UsuarioService.class.getName(), "Sucesso na obtenção do identificador do usuário de email " + email);
            return identificadorUsuario;
        } catch (Exception e) {
            logService.erro(UsuarioService.class.getName(), "Ocorreu um erro na obtenção do identificador do usuário de email " + email, e);
            throw e;
        }
    }

    public void salvarImagemUsuario(UsuarioDto usuario, String email, Connection conexao) throws IOException, SQLException {
        logService.iniciar(ImagemService.class.getName(), "Iniciando o salvamento da imagem do usuário");
        String caminhoImagem;
        if (usuario.imagem != null) {
            caminhoImagem = imagemService.salvarImagemUsuario(usuario.imagem, email);
            if (caminhoImagem == null) return;
            dao.atualizarImagemUsuario(email, caminhoImagem, conexao);
            logService.iniciar(LivroService.class.getName(), "Sucesso em atualizar a imagem do usuário de email " + email);
        }
    }

    public void atualizarUsuario(UsuarioDto usuarioDto) throws SQLException, UsuarioNaoExistente, IOException {
        validarIdentificadorUsuario(usuarioDto.email);
        Connection conexao = bd.obterConexao();
        try {
            conexao.setAutoCommit(false);
            logService.iniciar(UsuarioService.class.getName(), "Iniciando a atualização do usuário de email " + usuarioDto.email);
            dao.atualizarUsuario(usuarioDto, conexao);
            salvarImagemUsuario(usuarioDto, usuarioDto.email, conexao);
            logService.sucesso(UsuarioService.class.getName(), "Sucesso na atualização do usuário de email " + usuarioDto.email);
            conexao.commit();
        } catch (Exception e) {
            conexao.rollback();
            logService.erro(UsuarioService.class.getName(), "Ocorreu um erro na atualização do usuário de email  " + usuarioDto.email, e);
            throw e;
        } finally {
            conexao.close();
        }
    }

    public void atualizarSenhaUsuario(String email, String senha) throws SQLException {
        try {
            logService.iniciar(UsuarioService.class.getName(), "Iniciando a atualização da senha do usuário de email " + email);
            dao.atualizarSenha(senha, email);
            logService.iniciar(UsuarioService.class.getName(), "Sucesso na atualização do usuário de email " + email);
        } catch (Exception e) {
            logService.erro(UsuarioService.class.getName(), "Ocorreu um erro na atualização da senha do usuário de email  " + email, e);
            throw e;
        }
    }

}
