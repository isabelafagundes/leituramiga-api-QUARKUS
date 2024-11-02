package br.com.leituramiga.controller.categoria;

import br.com.leituramiga.dto.categoria.CategoriaDto;
import br.com.leituramiga.service.categoria.CategoriaService;
import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import java.util.List;

@RequestScoped
@Path("/api")
public class CategoriaController {

    @Inject
    CategoriaService categoriaService;

    @GET
    @Path("/categorias")
    @PermitAll
    public Response obterCategorias() {
        try {
            List<CategoriaDto> categorias = categoriaService.obterCategorias();
            return Response.ok(categorias).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }


}
