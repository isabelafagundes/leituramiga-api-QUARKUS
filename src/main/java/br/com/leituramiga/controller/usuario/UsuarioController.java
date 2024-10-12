package br.com.leituramiga.controller.usuario;

import br.com.leituramiga.dto.livro.FiltrosDto;
import br.com.leituramiga.dto.usuario.*;
import br.com.leituramiga.model.exception.*;
import br.com.leituramiga.model.usuario.Usuario;
import br.com.leituramiga.service.UsuarioService;
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

import java.util.List;

@RequestScoped
@Path("/api")
@Tag(name = "Usuários", description = "Controller responsável por gerenciar usuários")
public class UsuarioController {

    @Inject
    AutenticacaoService autenticacaoService;

    @Inject
    UsuarioService usuarioService;

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
    @Path("/perfil")
    @Operation(summary = "Retorna o usuário", description = "Retorna o usuário a partir do token de autenticação")
    public Response obterUsuarioToken() {
        try {
            UsuarioDto usuarioDto = usuarioService.obterUsuarioPorIdentificador(email);
            return Response.ok(usuarioDto).build();
        } catch (UsuarioNaoExistente erro) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        } catch (UsuarioNaoAtivo erro) {
            throw new WebApplicationException(Response.Status.PRECONDITION_FAILED);
        } catch (Exception erro) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Authenticated
    @Path("/usuarios")
    @Operation(summary = "Retorna os usuários paginados", description = "Retorna os usuários paginados a partir dos filtros")
    public Response obterUsuarios(FiltrosDto dto) {
        try {
            List<UsuarioDto> usuarios = usuarioService.obterUsuariosPaginados(
                    dto.numeroCidade,
                    dto.numeroInstituicao,
                    dto.pesquisa,
                    dto.numeroPagina,
                    dto.tamanhoPagina
            );
            return Response.ok(usuarios).build();
        } catch (Exception erro) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Authenticated
    @Path("/usuario")
    @Operation(summary = "Retorna o usuário", description = "Retorna o usuário a partir do identificador (email ou username)")
    public Response obterUsuario(IdentificadorUsuarioDto dto) {
        try {
            String identificador = dto.email != null ? dto.email : dto.username;
            UsuarioDto usuarioDto = usuarioService.obterUsuarioPorIdentificador(identificador);
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
    @Operation(summary = "Atualiza o usuário", description = "Atualiza o usuário a partir do token de autenticação")
    @Path("/salvar-usuario")
    public Response salvarUsuario(CriacaoUsuarioDto dto) {
        try {
            autenticacaoService.salvarUsuario(dto);
            return Response.ok().build();
        } catch (Exception erro) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Authenticated
    @Operation(summary = "Desativa o usuário", description = "Desativa o usuário a partir do token de autenticação")
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
    @Operation(summary = "Solicita a recuperação de senha", description = "Solicita a recuperação de senha a partir do email")
    @PermitAll
    @Path("/verificar-codigo-seguranca")
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
