package br.com.leituramiga.controller.comentario;

import br.com.leituramiga.dto.comentario.ComentarioDto;
import br.com.leituramiga.dto.usuario.IdentificadorUsuarioDto;
import br.com.leituramiga.model.exception.ComentarioNaoExistente;
import br.com.leituramiga.service.comentario.ComentarioService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@RequestScoped
@Path("/api")
@Tag(name = "Comentario", description = "Controller responsável por gerenciar os comentários")
public class ComentarioController {

    @Inject
    ComentarioService comentarioService;

    @GET
    @Path("/comentarios")
    public Response obterComentarios(IdentificadorUsuarioDto identificadorUsuarioDto) {
        try {
            List<ComentarioDto> comentarios = comentarioService.obterComentarioPorUsuario(identificadorUsuarioDto.email);
            return Response.ok(comentarios).build();
        } catch (ComentarioNaoExistente e) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        } catch (Exception e) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @DELETE
    @Path("/comentario/{id}")
    public Response deletarComentario(@PathParam("id") Integer numeroComentario) {
        try {
            comentarioService.deletarComentario(numeroComentario);
            return Response.ok().build();
        } catch (Exception e) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @POST
    @Path("/comentario")
    public Response salvarComentario(ComentarioDto comentario) {
        try {
            comentarioService.salvarComentario(comentario);
            return Response.ok().build();
        } catch (Exception e) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

}
