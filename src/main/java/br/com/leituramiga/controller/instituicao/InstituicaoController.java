package br.com.leituramiga.controller.instituicao;

import br.com.leituramiga.dto.Instituicao.ArquivoInstituicaoDto;
import br.com.leituramiga.dto.Instituicao.InstituicaoDto;
import br.com.leituramiga.service.instituicao.InstituicaoService;
import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@RequestScoped
@Path("/api")
@Tag(name = "Instituição", description = "Controller responsável por gerenciar instituições")
public class InstituicaoController {

    @Inject
    InstituicaoService instituicaoService;

    @GET
    @PermitAll
    @Path("/instituicao")
    public Response obterInstituicoesPorEstado(@QueryParam("pesquisa") String pesquisa) {
        try {
            List<InstituicaoDto> instituicoes = instituicaoService.obterInstituicoes(pesquisa);
            return Response.ok(instituicoes).build();
        } catch (Exception e) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @PermitAll
    @Path("/instituicao/xlsx")
    public Response salvarInstituicoesDeXlsx(ArquivoInstituicaoDto arquivo) {
        try {
            instituicaoService.salvarInstituicoesDeXlsx(arquivo.getArquivo());
            return Response.ok().build();
        } catch (Exception e) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

}
