package br.com.leituramiga.dao.usuario;

import br.com.leituramiga.dao.FabricaDeConexoes;
import br.com.leituramiga.model.usuario.Usuario;
import br.com.leituramiga.dto.usuario.CriacaoUsuarioDto;
import br.com.leituramiga.service.autenticacao.HashService;
import br.com.leituramiga.service.autenticacao.LogService;
import br.com.leituramiga.service.autenticacao.CodigoUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@ApplicationScoped
public class UsuarioDao {
    @Inject
    FabricaDeConexoes bd;

    @ConfigProperty(name = "config.tentativas")
    Integer tentativas;


    @Inject
    LogService logService;

    public Usuario obterUsuario(String login) throws SQLException {
        String md5Login = HashService.obterMd5Email(login);
        try (Connection conexao = bd.obterConexao()) {
            logService.iniciar(UsuarioDao.class.getName(), "Iniciando a obtenção do usuário de login " + md5Login);
            PreparedStatement pstmt = conexao.prepareStatement(UsuarioQueries.OBTER_USUARIO_POR_EMAIL_E_USUARIO);
            pstmt.setString(1, login);
            pstmt.setString(2, login);
            ResultSet resultado = pstmt.executeQuery();
            Usuario usuario = null;
            if (resultado.next()) usuario = obterUsuarioDeResultSet(resultado);
            logService.sucesso(UsuarioDao.class.getName(), "Sucesso na obtenção do usuário de login " + md5Login);
            return usuario;
        }
    }


    public void salvarUsuario(CriacaoUsuarioDto usuario, Connection conexao) throws SQLException {
        PreparedStatement pstmt = conexao.prepareStatement(UsuarioQueries.SALVAR_USUARIO);
        definirParametros(pstmt, usuario);
        pstmt.executeUpdate();
    }

    public void definirParametros(PreparedStatement pstmt, CriacaoUsuarioDto usuario) throws SQLException {
        pstmt.setString(1, usuario.nome);
        pstmt.setString(2, usuario.username);
        pstmt.setString(3, usuario.email);
        pstmt.setInt(4, usuario.tipoUsuario);
        pstmt.setString(5, usuario.senha);
        pstmt.setString(6, usuario.celular);
        pstmt.setString(7, usuario.descricao);
        pstmt.setString(8, usuario.imagem);
        pstmt.setInt(9, usuario.codigoInstituicao);
    }

    public void desativarUsuario(String email) throws SQLException {
        String md5Login = HashService.obterMd5Email(email);
        logService.iniciar(UsuarioDao.class.getName(), "Iniciando a desativação do usuário de email " + md5Login);
        Connection conexao = bd.obterConexao();
        PreparedStatement pstmt = conexao.prepareStatement(UsuarioQueries.EXCLUIR_USUARIO);
        pstmt.setInt(1, 0);
        pstmt.setString(2, email);
        pstmt.executeUpdate();
        logService.sucesso(UsuarioDao.class.getName(), "Sucesso na desativação do usuário de email " + md5Login);
    }

    public Boolean validarExistencia(String email, String username) throws SQLException {
        String md5Login = HashService.obterMd5Email(email);
        boolean existenciaEmail = validarExistenciaEmail(email);
        if (existenciaEmail) return existenciaEmail;
        if (username == null) return existenciaEmail;
        Boolean existenciaUsername = validarExistenciaUsername(username);
        return existenciaUsername;
    }


    private boolean validarExistenciaEmail(String email) throws SQLException {
        String md5Email = HashService.obterMd5Email(email);
        logService.iniciar(UsuarioDao.class.getName(), "Iniciando a validação da existência do email " + md5Email);
        Connection conexao = bd.obterConexao();
        PreparedStatement pstmt = conexao.prepareStatement(UsuarioQueries.VERIFICAR_EXISTENCIA);
        pstmt.setString(1, email);
        pstmt.setString(2, email);
        ResultSet resultado = pstmt.executeQuery();
        boolean existencia = resultado.next() && resultado.getBoolean(1);
        logService.sucesso(UsuarioDao.class.getName(), "Sucesso na validação da existência do email " + md5Email);
        return existencia;
    }

    private boolean validarExistenciaUsername(String username) throws SQLException {
        String md5Username = HashService.obterMd5Email(username);
        logService.iniciar(UsuarioDao.class.getName(), "Iniciando a validação da existência do username " + md5Username);
        Connection conexao = bd.obterConexao();
        PreparedStatement pstmt = conexao.prepareStatement(UsuarioQueries.VERIFICAR_USERNAME);
        pstmt.setString(1, username);
        ResultSet resultado = pstmt.executeQuery();
        boolean existencia = resultado.next() && resultado.getBoolean(1);
        logService.sucesso(UsuarioDao.class.getName(), "Sucesso na validação da existência do username " + md5Username);
        return existencia;
    }


    public void atualizarSenha(String senha, String email) throws SQLException {
        String md5Login = HashService.obterMd5Email(email);
        logService.iniciar(UsuarioDao.class.getName(), "Iniciando a atualização da senha do usuário de email " + md5Login);
        Connection conexao = bd.obterConexao();
        PreparedStatement pstmt = conexao.prepareStatement(UsuarioQueries.ALTERAR_SENHA);
        pstmt.setString(1, senha);
        pstmt.setString(2, email);
        pstmt.executeUpdate();
        logService.sucesso(UsuarioDao.class.getName(), "Sucesso na atualização da senha do usuário de email " + md5Login);
    }

    public String salvarCodigoAlteracao(String email) throws SQLException {
        String md5Login = HashService.obterMd5Email(email);
        logService.iniciar(UsuarioDao.class.getName(), "Iniciando a geração do código de alteração da senha do usuário de email " + md5Login);
        String codigo = CodigoUtil.obterCodigo();
        try (Connection conexao = bd.obterConexao()) {
            PreparedStatement pstmt = conexao.prepareStatement(UsuarioQueries.SALVAR_CODIGO_ALTERACAO);
            pstmt.setString(1, codigo);
            pstmt.setString(2, email);
            pstmt.executeUpdate();
            logService.sucesso(UsuarioDao.class.getName(), "Sucesso na geração do código da alteração da senha do usuário de email " + md5Login);
        }
        return codigo;
    }

    public Boolean verificarCodigoAlteracao(String email, String codigo) throws SQLException {
        String md5Login = HashService.obterMd5Email(email);
        logService.iniciar(UsuarioDao.class.getName(), "Iniciando a verificação do código de alteração da senha do usuário de email " + md5Login);

        try (Connection conexao = bd.obterConexao()) {
            PreparedStatement pstmt = conexao.prepareStatement(UsuarioQueries.ATUALIZAR_EMAIL_USUARIO);
            pstmt.setString(1, email);
            pstmt.setString(2, email);
            ResultSet resultado = pstmt.executeQuery();
            Usuario usuario = null;
            if (resultado.next()) usuario = obterUsuarioDeResultSet(resultado);
            Boolean codigoCorreto = CodigoUtil.verificarCodigo(usuario.getCodigoAlteracao(), codigo);
            logService.sucesso(UsuarioDao.class.getName(), "Sucesso na verificação do código de alteração da senha do usuário de email " + md5Login);
            return codigoCorreto;
        }
    }

    public void ativarUsuario(String email) throws SQLException {
        String md5Login = HashService.obterMd5Email(email);
        logService.iniciar(UsuarioDao.class.getName(), "Iniciando a ativação do usuário de email " + md5Login);
        Connection conexao = bd.obterConexao();
        PreparedStatement pstmt = conexao.prepareStatement(UsuarioQueries.ATIVAR_USUARIO);
        pstmt.setBoolean(1, true);
        pstmt.setString(2, email);
        pstmt.executeUpdate();
        logService.sucesso(UsuarioDao.class.getName(), "Sucesso na ativação do usuário de email " + md5Login);
    }

    public void atualizarTentativas(String login, boolean resetarTentativas) throws SQLException, ClassNotFoundException {
        String md5Login = HashService.obterMd5Email(login);
        try {
            logService.iniciar(UsuarioDao.class.getName(), "Iniciando a atualização nas tentativas de login do usuário de email " + md5Login);

            Usuario usuario = obterUsuario(login);
            if (usuario == null) {
                logService.sucesso(UsuarioDao.class.getName(), "Usuário não encontrado para o email " + md5Login);
                return;
            }

            Integer tentativasAtuais = usuario.getTentativas();
            if (tentativasAtuais > 0) {
                Integer numeroDeTentativas = resetarTentativas ? tentativas : --tentativasAtuais;
                atualizarTentativasUsuario(numeroDeTentativas, login);
                logService.sucesso(UsuarioDao.class.getName(), "Sucesso na atualização das tentativas de login do usuário de email " + md5Login);
            } else {
                bloquearUsuario(login);
                logService.sucesso(UsuarioDao.class.getName(), "Sucesso ao bloquear o usuário de email " + md5Login + " ao exceder o limite de tentativas de login");
            }
        } catch (Exception e) {
            logService.erro(UsuarioDao.class.getName(), "Erro ao atualizar as tentativas de login do usuário de email " + md5Login, e);
            throw e;
        }
    }

    private void atualizarTentativasUsuario(Integer tentativas, String login) throws SQLException {
        try (Connection conexao = bd.obterConexao()) {
            PreparedStatement pstmt = conexao.prepareStatement(UsuarioQueries.ATUALIZAR_TENTATIVAS);
            pstmt.setInt(1, tentativas);
            pstmt.setString(2, login);
            pstmt.executeUpdate();
        }
    }

    private void bloquearUsuario(String login) throws SQLException {
        try (Connection conexao = bd.obterConexao()) {
            PreparedStatement pstmt = conexao.prepareStatement(UsuarioQueries.BLOQUEAR_USUARIO);
            pstmt.setBoolean(1, true);
            pstmt.setString(2, login);
            pstmt.executeUpdate();
        }
    }


    public Boolean validarUsername(String username) throws SQLException {
        try (Connection conexao = bd.obterConexao()) {
            String md5Username = HashService.obterMd5Email(username);
            logService.iniciar(UsuarioDao.class.getName(), "Iniciando a verificação da existencia do username " + md5Username);
            PreparedStatement pstmt = conexao.prepareStatement(UsuarioQueries.VERIFICAR_USERNAME);
            pstmt.setString(1, username);
            ResultSet resultado = pstmt.executeQuery();
            Boolean existencia = resultado.next() && resultado.getBoolean(1);
            logService.sucesso(UsuarioDao.class.getName(), "Sucesso na verificação da existência do username " + md5Username);
            return existencia;
        }
    }

    private Usuario obterUsuarioDeResultSet(ResultSet resultado) throws SQLException {
        return Usuario.carregar(
                resultado.getString("nome"),
                resultado.getString("username"),
                resultado.getString("senha"),
                resultado.getString("email_usuario"),
                resultado.getInt("tipo_usuario"),
                resultado.getInt("tentativas"),
                resultado.getBoolean("bloqueado"),
                resultado.getBoolean("ativo"),
                resultado.getString("codigo_alteracao"),
                resultado.getString("celular"),
                resultado.getString("descricao"),
                resultado.getString("imagem"),
                resultado.getInt("codigo_instituicao"),
                null
        );
    }
}
