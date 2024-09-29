package br.com.leituramiga.controller.usuario;

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
import org.eclipse.microprofile.jwt.JsonWebToken;

@RequestScoped
@Path("/api")
public class UsuarioController {

    @Inject
    AutenticacaoService autenticacaoService;

    @Inject
    @Claim("email")
    String email;


    @Inject
    @Claim("type")
    String tipoToken;

    @Inject
    JsonWebToken token;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    @Path("/login")
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
    @Path("/usuario")
    public Response obterUsuario() {
        try {
            UsuarioDto usuarioDto = autenticacaoService.obterUsuarioDto(email);
            return Response.ok(usuarioDto).build();
        } catch (UsuarioNaoExistente erro) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        } catch (UsuarioNaoAtivo erro) {
            throw new WebApplicationException(Response.Status.PRECONDITION_FAILED);
        } catch (Exception erro) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Authenticated
    @Path("/refresh")
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
    @Path("/criar-usuario")
    public Response criarUsuario(CriacaoUsuarioDto dto) {
        try {
            autenticacaoService.criarUsuario(dto);
            return Response.ok().build();
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
    @Authenticated
    @Path("/salvar-usuario")
    public Response salvarUsuario(CriacaoUsuarioDto dto) {
        try {
            autenticacaoService.salvarUsuario(dto);
            return Response.ok().build();
        } catch (Exception erro) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Authenticated
    @Path("/desativar")
    public Response desativar() {
        try {
            autenticacaoService.desativarUsuario(email);
            return Response.ok().build();
        } catch (UsuarioNaoExistente erro) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        } catch (Exception erro) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @PermitAll
    @Path("/verificar-codigo_seguranca")
    public Response verificarCodigoSeguranca(CodigoDto dto) {
        try {
            autenticacaoService.verificarCodigo(dto.email, dto.codigo);
            return Response.ok().build();
        } catch (CodigoIncorreto erro) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        } catch (Exception erro) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }


}
