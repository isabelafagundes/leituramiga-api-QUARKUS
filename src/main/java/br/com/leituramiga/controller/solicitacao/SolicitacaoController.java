package br.com.leituramiga.controller.solicitacao;

import br.com.leituramiga.dto.livro.FiltrosDto;
import br.com.leituramiga.dto.solicitacao.NotificacaoSolicitacaoDto;
import br.com.leituramiga.dto.solicitacao.SolicitacaoDto;
import br.com.leituramiga.model.exception.UsuarioNaoPertenceASolicitacao;
import br.com.leituramiga.model.exception.solicitacao.SolicitacaoExcedeuPrazoEntrega;
import br.com.leituramiga.model.exception.solicitacao.SolicitacaoNaoAberta;
import br.com.leituramiga.model.exception.solicitacao.SolicitacaoNaoExistente;
import br.com.leituramiga.model.exception.solicitacao.SolicitacaoNaoPendente;
import br.com.leituramiga.service.solicitacao.SolicitacaoService;
import io.quarkus.security.Authenticated;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@RequestScoped
@Path("/api")
@Tag(name = "Solicitações", description = "Controller responsável por gerenciar solicitações")
public class SolicitacaoController {

    @Inject
    SolicitacaoService service;

    @Inject
    @Claim("email")
    String email;


    //todo: adicionar mais informações na solicitação
    @GET
    @Authenticated
    @Path("/solicitacao/{id}")
    @Operation(summary = "Retorna uma solicitação", description = "Retorna uma solicitação a partir do seu código de identificação")
    public Response obterSolicitacao(@PathParam("id") Integer numero) {
        try {
            SolicitacaoDto dto = service.obterSolicitacao(numero);
            return Response.ok(dto).build();
        } catch (SolicitacaoNaoExistente erro) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        } catch (Exception erro) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @POST
    @Authenticated
    @Path("/solicitacao/{id}/aceitar")
    @Operation(summary = "Aceita uma solicitação", description = "Aceita uma solicitação a partir do seu código de identificação")
    public Response aceitarSolicitacao(@PathParam("id") Integer numero) {
        try {
            service.aceitarSolicitacao(numero, email);
            return Response.ok().build();
        } catch (SolicitacaoExcedeuPrazoEntrega erro) {
            throw new WebApplicationException(Response.Status.REQUEST_TIMEOUT);
        } catch (SolicitacaoNaoExistente erro) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        } catch (UsuarioNaoPertenceASolicitacao erro) {
            throw new WebApplicationException(Response.Status.FORBIDDEN);
        } catch (SolicitacaoNaoPendente erro) {
            throw new WebApplicationException(Response.Status.PRECONDITION_FAILED);
        } catch (Exception erro) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @POST
    @Authenticated
    @Path("/solicitacao/{id}/recusar")
    @Operation(summary = "Recusa uma solicitação", description = "Recusa uma solicitação a partir do seu código de identificação")
    public Response recusarSolicitacao(@PathParam("id") Integer numero, String motivoRecusa) {
        try {
            service.recusarSolicitacao(numero, motivoRecusa, email);
            return Response.ok().build();
        } catch (SolicitacaoExcedeuPrazoEntrega erro) {
            throw new WebApplicationException(Response.Status.REQUEST_TIMEOUT);
        } catch (SolicitacaoNaoExistente erro) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        } catch (UsuarioNaoPertenceASolicitacao erro) {
            throw new WebApplicationException(Response.Status.FORBIDDEN);
        } catch (SolicitacaoNaoPendente erro) {
            throw new WebApplicationException(Response.Status.PRECONDITION_FAILED);
        } catch (Exception erro) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @POST
    @Authenticated
    @Path("/solicitacao/{id}/finalizar")
    @Operation(summary = "Finaliza uma solicitação", description = "Finaliza uma solicitação a partir do seu código de identificação")
    public Response finalizarSolicitacao(@PathParam("id") Integer numero) {
        try {
            service.finalizarSolicitacao(numero, email);
            return Response.ok().build();
        } catch (SolicitacaoExcedeuPrazoEntrega erro) {
            throw new WebApplicationException(Response.Status.REQUEST_TIMEOUT);
        } catch (SolicitacaoNaoExistente erro) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        } catch (UsuarioNaoPertenceASolicitacao erro) {
            throw new WebApplicationException(Response.Status.FORBIDDEN);
        } catch (SolicitacaoNaoPendente erro) {
            throw new WebApplicationException(Response.Status.PRECONDITION_FAILED);
        } catch (Exception erro) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @POST
    @Authenticated
    @Path("/solicitacao/{id}/cancelar")
    @Operation(summary = "Cancela uma solicitação", description = "Cancela uma solicitação a partir do seu código de identificação")
    public Response cancelarSolicitacao(@PathParam("id") Integer numero, String motivoRecusa) {
        try {
            service.cancelarSolicitacao(numero, motivoRecusa, email);
            return Response.ok().build();
        } catch (SolicitacaoNaoExistente erro) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        } catch (SolicitacaoNaoAberta erro) {
            throw new WebApplicationException(Response.Status.PRECONDITION_FAILED);
        } catch (UsuarioNaoPertenceASolicitacao erro) {
            throw new WebApplicationException(Response.Status.FORBIDDEN);
        } catch (Exception erro) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }


    @POST
    @Authenticated
    @Path("/solicitacoes")
    @Operation(summary = "Retorna as solicitações do usuário", description = "Retorna as solicitações do usuário do token de autenticação")
    public Response obterSolicitacoesPaginadas(FiltrosDto dto) {
        try {
            List<SolicitacaoDto> solicitacoes = service.obterSolicitacoesPaginadas(dto.numeroPagina, dto.tamanhoPagina, email);
            return Response.ok(solicitacoes).build();
        } catch (Exception erro) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @POST
    @Authenticated
    @Path("/solicitacoes/historico")
    @Operation(summary = "Retorna as solicitações do usuário", description = "Retorna as solicitações do usuário do token de autenticação")
    public Response obterHistoricoSolicitacoesPaginadas(FiltrosDto dto) {
        try {
            List<SolicitacaoDto> solicitacoes = service.obterHistoricoSolicitacoesPaginadas(dto.numeroPagina, dto.tamanhoPagina, email);
            return Response.ok(solicitacoes).build();
        } catch (Exception erro) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }


    @GET
    @Authenticated
    @Path("/notificacoes")
    @Operation(summary = "Retorna as notificações do usuário", description = "Retorna as notificações do usuário do token de autenticação")
    public Response obterNotificacoes() {
        try {
            List<NotificacaoSolicitacaoDto> solicitacoes = service.obterNotificacoesSolicitacao(email);
            return Response.ok(solicitacoes).build();
        } catch (Exception erro) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }


    @POST
    @Authenticated
    @Path("/solicitacao")
    @Operation(summary = "Cadastra uma solicitação", description = "Cadastra uma solicitação do usuário do token de autenticação")
    public Response cadastrarSolicitacao(SolicitacaoDto dto) {
        try {
            service.cadastrarSolicitacao(dto);
            return Response.ok().build();
        } catch (Exception erro) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }


    @POST
    @Authenticated
    @Path("/solicitacao/{id}/atualizar")
    @Operation(summary = "Atualiza uma solicitação", description = "Atualiza uma solicitação a partir do seu código de identificação")
    public Response atualizarSolicitacao(@PathParam("id") Integer numero, SolicitacaoDto dto) {
        try {
            service.atualizarSoliciacao(dto);
            return Response.ok().build();
        } catch (SolicitacaoNaoExistente erro) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        } catch (Exception erro) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }


}
