package br.com.leituramiga.dao.usuario;

import br.com.leituramiga.dao.FabricaDeConexoes;
import br.com.leituramiga.dto.usuario.CriacaoUsuarioDto;
import br.com.leituramiga.dto.usuario.UsuarioDto;
import br.com.leituramiga.model.exception.UsuarioNaoExistente;
import br.com.leituramiga.model.usuario.Usuario;
import br.com.leituramiga.service.autenticacao.CodigoUtil;
import br.com.leituramiga.service.autenticacao.HashService;
import br.com.leituramiga.service.autenticacao.LogService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class UsuarioDao {
    @Inject
    FabricaDeConexoes bd;

    @ConfigProperty(name = "config.tentativas")
    Integer tentativas;


    @Inject
    LogService logService;

    public Usuario obterUsuario(String login) throws SQLException {
        String md5Identificador = HashService.obterMd5Email(login);
        try (Connection conexao = bd.obterConexao()) {
            logService.iniciar(UsuarioDao.class.getName(), "Iniciando a obtenção do usuário de login " + md5Identificador);
            Usuario usuario = obterUsuarioPorIdentificador(login, conexao, true);
            logService.sucesso(UsuarioDao.class.getName(), "Sucesso na obtenção do usuário de login " + md5Identificador);
            return usuario;
        }
    }

    public Usuario obterUsuarioPorEmail(String email) throws SQLException {
        String md5Email = HashService.obterMd5Email(email);
        try (Connection conexao = bd.obterConexao()) {
            logService.iniciar(UsuarioDao.class.getName(), "Iniciando a obtenção do usuário de email " + md5Email);
            PreparedStatement pstmt = conexao.prepareStatement(UsuarioQueries.OBTER_USUARIO_POR_EMAIL);
            pstmt.setString(1, email);
            ResultSet resultado = pstmt.executeQuery();
            logService.sucesso(UsuarioDao.class.getName(), "Sucesso na obtenção do usuário de email " + md5Email);
            return resultado.next() ? obterUsuarioDeResultSet(resultado) : null;
        }
    }


    public void salvarUsuario(CriacaoUsuarioDto usuario, Connection conexao) throws SQLException {
        PreparedStatement pstmt = conexao.prepareStatement(UsuarioQueries.SALVAR_USUARIO);
        definirParametros(pstmt, usuario);
        pstmt.executeUpdate();
    }

    public boolean verificarUsuarioAtivo(String email) throws SQLException {
        try (Connection conexao = bd.obterConexao()) {
            PreparedStatement pstmt = conexao.prepareStatement(UsuarioQueries.USUARIO_ATIVO);
            pstmt.setString(1, email);
            ResultSet resultado = pstmt.executeQuery();
            return resultado.next() && resultado.getBoolean(1);
        }
    }

    public void atualizarUsuario(UsuarioDto usuario) throws SQLException {
        try (Connection conexao = bd.obterConexao()) {
            PreparedStatement pstmt = conexao.prepareStatement(UsuarioQueries.ATUALIZAR_USUARIO);
            pstmt.setString(1, usuario.nome);
            if (usuario.celular == null) pstmt.setNull(2, java.sql.Types.VARCHAR);
            else pstmt.setString(2, usuario.celular);
            pstmt.setString(3, usuario.descricao);
            pstmt.setString(4, usuario.imagem);
            if (usuario.codigoInstituicao == null) pstmt.setNull(5, java.sql.Types.INTEGER);
            else pstmt.setInt(5, usuario.codigoInstituicao);
            pstmt.setString(6, usuario.email);
            pstmt.executeUpdate();
        }
    }

    public void definirParametros(PreparedStatement pstmt, CriacaoUsuarioDto usuario) throws SQLException {
        pstmt.setString(1, usuario.nome);
        pstmt.setString(2, usuario.username);
        pstmt.setString(3, usuario.email);
        pstmt.setInt(4, usuario.tipoUsuario);
        pstmt.setString(5, usuario.senha);
        if (usuario.celular == null) pstmt.setNull(6, java.sql.Types.VARCHAR);
        else pstmt.setString(6, usuario.celular);
        pstmt.setString(7, usuario.descricao != null ? usuario.descricao : "");
        pstmt.setString(8, usuario.imagem != null ? usuario.imagem : "");
        if (usuario.codigoInstituicao == null) pstmt.setNull(9, java.sql.Types.INTEGER);
        else pstmt.setInt(9, usuario.codigoInstituicao);
    }

    public void desativarUsuario(String email) throws SQLException {
        String md5Login = HashService.obterMd5Email(email);
        try (Connection conexao = bd.obterConexao()) {
            logService.iniciar(UsuarioDao.class.getName(), "Iniciando a desativação do usuário de email " + md5Login);
            PreparedStatement pstmt = conexao.prepareStatement(UsuarioQueries.EXCLUIR_USUARIO);
            pstmt.setInt(1, 0);
            pstmt.setString(2, email);
            pstmt.executeUpdate();
            logService.sucesso(UsuarioDao.class.getName(), "Sucesso na desativação do usuário de email " + md5Login);
        }
    }

    public Boolean validarExistencia(String email, String username) throws SQLException {
        boolean existenciaEmail = validarExistenciaEmail(email);
        if (existenciaEmail) return existenciaEmail;
        if (username == null) return existenciaEmail;
        Boolean existenciaUsername = validarExistenciaUsername(username);
        return existenciaUsername;
    }

    private boolean validarExistenciaEmail(String email) throws SQLException {
        try (Connection conexao = bd.obterConexao()) {
            String md5Email = HashService.obterMd5Email(email);
            logService.iniciar(UsuarioDao.class.getName(), "Iniciando a validação da existência do email " + md5Email);
            PreparedStatement pstmt = conexao.prepareStatement(UsuarioQueries.VERIFICAR_EXISTENCIA);
            pstmt.setString(1, email);
            pstmt.setString(2, email);
            ResultSet resultado = pstmt.executeQuery();
            if (resultado.next()) {
                boolean existencia = resultado.getInt(1) > 0;
                logService.sucesso(UsuarioDao.class.getName(), "Sucesso na validação da existência do email " + md5Email);
                return existencia;
            }
            return false;
        }
    }

    private boolean validarExistenciaUsername(String username) throws SQLException {
        String md5Username = HashService.obterMd5Email(username);
        logService.iniciar(UsuarioDao.class.getName(), "Iniciando a validação da existência do username " + md5Username);
        try (Connection conexao = bd.obterConexao()) {
            PreparedStatement pstmt = conexao.prepareStatement(UsuarioQueries.VERIFICAR_USERNAME);
            pstmt.setString(1, username);
            ResultSet resultado = pstmt.executeQuery();
            boolean existencia = resultado.next() && resultado.getBoolean(1);
            logService.sucesso(UsuarioDao.class.getName(), "Sucesso na validação da existência do username " + md5Username);
            return existencia;
        }
    }


    public void atualizarSenha(String senha, String email) throws SQLException {
        String md5Login = HashService.obterMd5Email(email);
        try (Connection conexao = bd.obterConexao()) {
            logService.iniciar(UsuarioDao.class.getName(), "Iniciando a atualização da senha do usuário de email " + md5Login);
            PreparedStatement pstmt = conexao.prepareStatement(UsuarioQueries.ALTERAR_SENHA);
            pstmt.setString(1, senha);
            pstmt.setString(2, email);
            pstmt.executeUpdate();
            logService.sucesso(UsuarioDao.class.getName(), "Sucesso na atualização da senha do usuário de email " + md5Login);
        }
    }

    public String salvarCodigoAlteracao(String email, Connection conexao) throws SQLException {
        String md5Login = HashService.obterMd5Email(email);
        logService.iniciar(UsuarioDao.class.getName(), "Iniciando a geração do código de alteração da senha do usuário de email " + md5Login);
        String codigo = CodigoUtil.obterCodigo();
        PreparedStatement pstmt = conexao.prepareStatement(UsuarioQueries.SALVAR_CODIGO_ALTERACAO);
        pstmt.setString(1, CodigoUtil.obterCodigoCriptografado(codigo));
        pstmt.setString(2, email);
        pstmt.executeUpdate();
        logService.sucesso(UsuarioDao.class.getName(), "Sucesso na geração do código da alteração da senha do usuário de email " + md5Login);
        return codigo;
    }

    public Boolean verificarCodigoSeguranca(String identificador, String codigo, boolean ativo) throws SQLException, UsuarioNaoExistente {
        String md5Login = HashService.obterMd5Email(identificador);
        logService.iniciar(UsuarioDao.class.getName(), "Iniciando a verificação do código de segurança do usuário de email " + md5Login);

        try (Connection conexao = bd.obterConexao()) {
            Usuario usuario = obterUsuarioPorIdentificador(identificador, conexao, ativo);
            if (usuario == null) throw new UsuarioNaoExistente();
            Boolean codigoCorreto = CodigoUtil.verificarCodigo(usuario.getCodigoAlteracao(), codigo);
            logService.sucesso(UsuarioDao.class.getName(), "Sucesso na verificação do código de segurança do usuário de email " + md5Login);
            return codigoCorreto;
        }
    }

    private Usuario obterUsuarioPorIdentificador(String identificador, Connection conexao, boolean ativo) throws SQLException {
        PreparedStatement pstmt = conexao.prepareStatement(UsuarioQueries.OBTER_USUARIO_POR_EMAIL_E_USUARIO);
        pstmt.setBoolean(1, ativo);
        pstmt.setString(2, identificador);
        pstmt.setString(3, identificador);
        ResultSet resultado = pstmt.executeQuery();
        Usuario usuario = null;
        if (resultado.next()) usuario = obterUsuarioDeResultSet(resultado);
        return usuario;
    }

    public void ativarUsuario(String email) throws SQLException {
        String md5Login = HashService.obterMd5Email(email);
        try (Connection conexao = bd.obterConexao()) {
            logService.iniciar(UsuarioDao.class.getName(), "Iniciando a ativação do usuário de email " + md5Login);
            PreparedStatement pstmt = conexao.prepareStatement(UsuarioQueries.ATIVAR_USUARIO);
            pstmt.setBoolean(1, true);
            pstmt.setString(2, email);
            pstmt.executeUpdate();
            logService.sucesso(UsuarioDao.class.getName(), "Sucesso na ativação do usuário de email " + md5Login);
        }
    }

    public void atualizarTentativas(String login, boolean resetarTentativas) throws SQLException, ClassNotFoundException {
        String md5Login = HashService.obterMd5Email(login);
        try {
//            logService.iniciar(UsuarioDao.class.getName(), "Iniciando a atualização nas tentativas de login do usuário de email " + md5Login);
//
//            Usuario usuario = obterUsuario(login);
//            if (usuario == null) {
//                logService.sucesso(UsuarioDao.class.getName(), "Usuário não encontrado para o email " + md5Login);
//                return;
//            }
//
//            Integer tentativasAtuais = usuario.getTentativas();
//            if (tentativasAtuais > 0) {
//                Integer numeroDeTentativas = resetarTentativas ? tentativas : --tentativasAtuais;
//                atualizarTentativasUsuario(numeroDeTentativas, login);
//                logService.sucesso(UsuarioDao.class.getName(), "Sucesso na atualização das tentativas de login do usuário de email " + md5Login);
//            } else {
//                bloquearUsuario(login);
//                logService.sucesso(UsuarioDao.class.getName(), "Sucesso ao bloquear o usuário de email " + md5Login + " ao exceder o limite de tentativas de login");
//            }
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

    public List<Usuario> obterUsuariosPaginados(Integer numeroCidade, Integer numeroInstituicao, String pesquisa, Integer numeroPagina, Integer tamanhoPagina) throws SQLException {
        try (Connection conexao = bd.obterConexao()) {
            String pesquisaQuery = pesquisa == null ? "" : pesquisa;
            String query = UsuarioQueries.OBTER_USUARIOS_PAGINADOS.replace("PESQUISA", "'%" + pesquisaQuery + "%'");
            PreparedStatement pstmt = conexao.prepareStatement(query);
            pstmt.setObject(1, numeroInstituicao);
            pstmt.setObject(2, numeroInstituicao);

            pstmt.setObject(3, numeroCidade);
            pstmt.setObject(4, numeroCidade);

            pstmt.setInt(5, tamanhoPagina);
            pstmt.setInt(6, numeroPagina * tamanhoPagina);
            ResultSet resultado = pstmt.executeQuery();
            List<Usuario> usuarios = new ArrayList<>();
            while (resultado.next()) usuarios.add(obterUsuarioResumidoDeResultSet(resultado));
            return usuarios;
        }
    }


    private Usuario obterUsuarioResumidoDeResultSet(ResultSet resultado) throws SQLException {
        return Usuario.carregarResumo(
                resultado.getString("nome"),
                resultado.getString("username"),
                resultado.getString("email_usuario"),
                resultado.getString("descricao"),
                resultado.getString("imagem"),
                resultado.getInt("codigo_instituicao"),
                resultado.getString("nome_cidade"),
                resultado.getString("nome_instituicao"),
                resultado.getInt("quantidade_livros")
        );
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
                null,
                resultado.getString("nome_cidade"),
                resultado.getString("nome_instituicao"),
                resultado.getInt("quantidade_livros")
        );
    }
}
