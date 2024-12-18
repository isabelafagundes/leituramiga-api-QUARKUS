package br.com.leituramiga.controller.autenticacao;

import br.com.leituramiga.dto.usuario.*;
import br.com.leituramiga.model.exception.*;
import br.com.leituramiga.service.autenticacao.AutenticacaoService;
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@RequestScoped
@Path("/api")
@Tag(name = "Autenticação", description = "Controller responsável por gerenciar a autenticação")
public class AutenticacaoController {
    @Inject
    AutenticacaoService autenticacaoService;

    @Inject
    @Claim("email")
    String email;

    @Inject
    @Claim("type")
    String tipoToken;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    @Path("/login")
    @Operation(summary = "Realiza o login", description = "Realiza o login a partir do email e senha")
    public Response login(LoginDto dto) {
        try {
            UsuarioAutenticadoDto usuarioDto = autenticacaoService.autenticarUsuario(dto.email, dto.senha);
            return Response.ok(usuarioDto).build();
        } catch (UsuarioBloqueado erro) {
            throw new WebApplicationException(Response.Status.TOO_MANY_REQUESTS);
        } catch (UsuarioNaoAtivo erro) {
            throw new WebApplicationException(Response.Status.PRECONDITION_FAILED);
        } catch (UsuarioNaoExistente erro) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        } catch (UsuarioNaoAutorizado erro) {
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        } catch (Exception erro) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Authenticated
    @Path("/refresh")
    @Operation(summary = "Atualiza os tokens", description = "Atualiza os tokens a partir do token de autenticação")
    public Response atualizarTokens() {
        try {
            UsuarioAutenticadoDto dto = autenticacaoService.atualizarTokens(email, tipoToken);
            return Response.ok(dto).build();
        } catch (UsuarioNaoExistente erro) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        } catch (UsuarioNaoAtivo erro) {
            throw new WebApplicationException(Response.Status.PRECONDITION_FAILED);
        } catch (RefreshTokenInvalido erro) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        } catch (Exception erro) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @PermitAll
    @Operation(summary = "Cria um usuário", description = "Cria um usuário a partir do email e senha")
    @Path("/criar-usuario")
    public Response criarUsuario(CriacaoUsuarioDto dto) {
        try {
            autenticacaoService.criarUsuario(dto);
            String token = autenticacaoService.obterTokenUsuario(dto.email);
            TokenDto tokenDto = new TokenDto(token);
            return Response.ok(tokenDto).build();
        } catch (InformacoesInvalidas erro) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        } catch (UsuarioExistente erro) {
            throw new WebApplicationException(Response.Status.CONFLICT);
        } catch (Exception erro) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Verifica o código de segurança", description = "Verifica o código de segurança a partir do email e código")
    @Authenticated
    @Path("/verificar-codigo-seguranca")
    public Response verificarCodigoSeguranca(CodigoDto dto) {
        try {
            autenticacaoService.verificarCodigoUsuario(dto.email, dto.codigo, tipoToken);
            return Response.ok().build();
        } catch (CodigoIncorreto erro) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        } catch (TokenDeValidacaoInvalido erro) {
            throw new WebApplicationException(Response.Status.PRECONDITION_FAILED);
        } catch (Exception erro) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Verifica o código de recuperação", description = "Verifica o código de recuperação a partir do email e código")
    @Authenticated
    @Path("/verificar-codigo-recuperacao")
    public Response verificarCodigoRecuperacao(CodigoDto dto) {
        try {
            autenticacaoService.verificarCodigoSenha(dto.email, dto.codigo, tipoToken);
            return Response.ok().build();
        } catch (CodigoIncorreto erro) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        } catch (TokenDeValidacaoInvalido erro) {
            throw new WebApplicationException(Response.Status.PRECONDITION_FAILED);
        } catch (Exception erro) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Solicita a recuperação de senha", description = "Solicita a recuperação de senha a partir do email")
    @PermitAll
    @Path("/recuperar-senha")
    public Response iniciarRecuperacaoSenha(IdentificadorUsuarioDto dto) {
        try {
            autenticacaoService.iniciarRecuperacaoDeSenha(dto.email);
            String token = autenticacaoService.obterTokenSenha(dto.email);
            TokenDto tokenDto = new TokenDto(token);
            return Response.ok(tokenDto).build();
        } catch (UsuarioNaoExistente erro) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        } catch (Exception erro) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Atualiza a senha", description = "Atualiza a senha a partir do email e senha")
    @Authenticated
    @Path("/atualizar-senha")
    public Response atualizarSenha(AtualizacaoSenhaDto dto) {
        try {
            autenticacaoService.atualizarSenhaUsuario(dto.email, dto.senha, tipoToken);
            return Response.ok().build();
        } catch (TokenDeValidacaoInvalido erro) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        } catch (Exception erro) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Enviar email com código", description = "Enviar email com código de segurança")
    @PermitAll
    @Path("/enviar-codigo-recuperacao")
    public Response enviarCodigoRecuperacaoSenha(IdentificadorUsuarioDto dto) {
        try {
            autenticacaoService.enviarCodigoUsuario(dto.email);
            String token = autenticacaoService.obterTokenSenha(dto.email);
            TokenDto tokenDto = new TokenDto(token);
            return Response.ok(tokenDto).build();
        } catch (UsuarioNaoExistente erro) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        } catch (Exception erro) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Enviar email com código", description = "Enviar email com código de segurança")
    @PermitAll
    @Path("/enviar-codigo-verificacao")
    public Response enviarCodigoCriacaoUsuario(IdentificadorUsuarioDto dto) {
        try {
            autenticacaoService.enviarCodigoUsuario(dto.email);
            String token = autenticacaoService.obterTokenUsuario(dto.email);
            TokenDto tokenDto = new TokenDto(token);
            return Response.ok(tokenDto).build();
        } catch (UsuarioNaoExistente erro) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        } catch (Exception erro) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    @Path("/validar-identificador")
    @Operation(summary = "Valida o identificador do usuário", description = "Valida o identificador do usuário a partir do email")
    public Response validarIdentificador(IdentificadorUsuarioDto dto) {
        try {
            autenticacaoService.validarExistenciaIdentificadorUsuario(dto);
            return Response.ok().build();
        } catch (UsuarioExistente erro) {
            throw new WebApplicationException(Response.Status.CONFLICT);
        } catch (Exception erro) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

}
