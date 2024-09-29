package br.com.leituramiga.controller.endereco;

import br.com.leituramiga.dto.endereco.EnderecoDto;
import br.com.leituramiga.model.exception.EnderecoNaoExistente;
import br.com.leituramiga.service.endereco.EnderecoService;
import io.quarkus.security.Authenticated;
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
@Tag(name = "Endereço", description = "Controller responsável por gerenciar endereços")
public class EnderecoController {

    @Inject
    EnderecoService enderecoService;

    @Inject
    @Claim("email")
    String email;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Authenticated
    @Operation(summary = "Retorna o endereço do usuário", description = "Retorna o endereço do usuário a partir do token de autenticação")
    @Path("/endereco")
    public Response obterEnderecoUsuario() {
        try {
            EnderecoDto enderecoDto = enderecoService.obterEnderecoUsuario(email);
            return Response.ok(enderecoDto).build();
        } catch (EnderecoNaoExistente erro) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        } catch (Exception erro) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Authenticated
    @Operation(summary = "Altera o endereço do usuário", description = "Altera o endereço do usuário a partir do token de autenticação")
    @Path("/endereco")
    public Response salvarEndereco(EnderecoDto endereco) {
        try {
            enderecoService.salvarEndereco(endereco, email);
            return Response.ok().build();
        } catch (Exception erro) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }


}
