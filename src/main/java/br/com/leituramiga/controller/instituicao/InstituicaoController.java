package br.com.leituramiga.controller.instituicao;

import br.com.leituramiga.dto.Instituicao.ArquivoInstituicaoDto;
import br.com.leituramiga.service.instituicao.InstituicaoService;
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@RequestScoped
@Path("/api")
@Tag(name = "Instituição", description = "Controller responsável por gerenciar instituições")
public class InstituicaoController {

    @Inject
    InstituicaoService instituicaoService;

    @Inject
    @Claim("email")
    String email;

    @POST
    @RolesAllowed("ADMINISTRADOR")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
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
