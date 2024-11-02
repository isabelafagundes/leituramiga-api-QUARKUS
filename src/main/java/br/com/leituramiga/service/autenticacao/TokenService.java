package br.com.leituramiga.service.autenticacao;

import br.com.leituramiga.dto.usuario.UsuarioAutenticadoDto;
import br.com.leituramiga.model.usuario.Usuario;
import br.com.leituramiga.model.exception.RefreshTokenInvalido;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.Duration;
import java.time.Instant;

@ApplicationScoped
public class TokenService {

    @ConfigProperty(name = "mp.jwt.verify.issuer")
    String issuer;

    @Inject
    LogService logService;

    public UsuarioAutenticadoDto obterUsuarioAutenticado(Usuario usuario) {
        String md5Email = HashService.obterMd5Email(usuario.getEmail());
        try {
            logService.iniciar("TokenSmallryeService", "Iniciando a geração do token de acesso do usuário de email " + md5Email);
            String token = gerarToken(usuario);
            String refreshToken = gerarRefreshToken(usuario);
            logService.sucesso("TokenSmallryeService", "Sucesso ao gerar o token de acesso do usuário de email " + md5Email);
            return UsuarioAutenticadoDto.deDomain(usuario, token, refreshToken);
        } catch (Exception erro) {
            logService.erro("TokenSmallryeService", "Ocorreu um erro na geração do token de acesso do usuário de email " + md5Email, erro);
            throw erro;
        }
    }

    public UsuarioAutenticadoDto atualizarToken(Usuario usuario, String tipoToken) throws RefreshTokenInvalido {
        String md5Email = HashService.obterMd5Email(usuario.getEmail());
        try {
            if (!"refresh".equals(tipoToken)) throw new RefreshTokenInvalido();
            logService.iniciar("TokenSmallryeService", "Iniciando a geração do token de acesso do usuário de email " + md5Email);
            String accessToken = gerarToken(usuario);
            String refreshToken = gerarRefreshToken(usuario);
            logService.sucesso("TokenSmallryeService", "Sucesso ao gerar o token de acesso do usuário de email " + md5Email);
            return UsuarioAutenticadoDto.deDomain(usuario, accessToken, refreshToken);
        } catch (RefreshTokenInvalido e) {
            logService.erro("TokenSmallryeService", "Token de atualização inválido para o usuário de email " + md5Email, e);
            throw e;
        } catch (Exception erro) {
            logService.erro("TokenSmallryeService", "Ocorreu um erro na atualização do token de acesso do usuário de email " + md5Email, erro);
            throw erro;
        }
    }

    public String gerarToken(Usuario usuario) {
        return obterToken(Duration.ofDays(1), "access", usuario);
    }

    public String gerarRefreshToken(Usuario usuario) {
        return obterToken(Duration.ofDays(5), "refresh", usuario);
    }

    public String gerarTokenUsuario(Usuario usuario) {
        return obterToken(Duration.ofMinutes(30), "user", usuario);
    }

    public String gerarTokenSenha(Usuario usuario) {
        return obterToken(Duration.ofMinutes(30), "password", usuario);
    }

    private String obterToken(Duration duration, String type, Usuario usuario) {
        String md5Login = HashService.obterMd5Email(usuario.getEmail());
        logService.iniciar("TokenSmallryeService", "Iniciando a geração do token do tipo " + type + " para o usuário de email " + md5Login);
        String token = Jwt.issuer(issuer)
                .subject("auth")
                .claim("type", type)
                .claim("email", usuario.getEmail())
                .claim("username", usuario.getUsername())
                .groups(usuario.getTipoUsuario().toString())
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plus(duration))
                .sign();
        logService.sucesso("TokenSmallryeService", "Sucesso na geração do token do tipo " + type + " para o usuário de email " + md5Login);
        return token;
    }
}
