package br.com.leituramiga.controller.solicitacao;

import br.com.leituramiga.dto.solicitacao.SolicitacaoDto;
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

import java.util.List;

@RequestScoped
@Path("/api")
public class SolicitacaoController {

    @Inject
    SolicitacaoService service;

    @Inject
    @Claim("email")
    String email;


    @GET
    @Authenticated
    @Path("/solicitacao/{id}")
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
    public Response aceitarSolicitacao(@PathParam("id") Integer numero) {
        try {
            service.aceitarSolicitacao(numero, email);
            return Response.ok().build();
        } catch (SolicitacaoExcedeuPrazoEntrega erro) {
            throw new WebApplicationException(Response.Status.REQUEST_TIMEOUT);
        } catch (SolicitacaoNaoExistente erro) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        } catch (SolicitacaoNaoPendente erro) {
            throw new WebApplicationException(Response.Status.PRECONDITION_FAILED);
        } catch (Exception erro) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @POST
    @Authenticated
    @Path("/solicitacao/{id}/recusar")
    public Response recusarSolicitacao(@PathParam("id") Integer numero, String motivoRecusa) {
        try {
            service.recusarSolicitacao(numero, motivoRecusa, email);
            return Response.ok().build();
        } catch (SolicitacaoExcedeuPrazoEntrega erro) {
            throw new WebApplicationException(Response.Status.REQUEST_TIMEOUT);
        } catch (SolicitacaoNaoExistente erro) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        } catch (SolicitacaoNaoPendente erro) {
            throw new WebApplicationException(Response.Status.PRECONDITION_FAILED);
        } catch (Exception erro) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @POST
    @Authenticated
    @Path("/solicitacao/{id}/cancelar")
    public Response cancelarSolicitacao(@PathParam("id") Integer numero, String motivoRecusa) {
        try {
            service.cancelarSolicitacao(numero, motivoRecusa, email);
            return Response.ok().build();
        } catch (SolicitacaoNaoExistente erro) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        } catch (SolicitacaoNaoAberta erro) {
            throw new WebApplicationException(Response.Status.PRECONDITION_FAILED);
        } catch (Exception erro) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }


    @GET
    @Authenticated
    @Path("/solicitaoes")
    public Response obterSolicitacoesPaginadas(@QueryParam("pagina") Integer pagina, @QueryParam("tamanhoPagina") Integer tamanhoPagina) {
        try {
            List<SolicitacaoDto> solicitacoes = service.obterSolicitacoesPaginadas(pagina, tamanhoPagina, email);
            return Response.ok(solicitacoes).build();
        } catch (Exception erro) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }


    @POST
    @Authenticated
    @Path("/solicitacao")
    public Response cadastrarSolicitacao(SolicitacaoDto dto) {
        try {
            service.cadastrarSolicitacao(dto, email);
            return Response.ok().build();
        } catch (Exception erro) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }


    @POST
    @Authenticated
    @Path("/solicitacao/{id}/atualizar")
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
