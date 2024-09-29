package br.com.leituramiga.controller;

import io.quarkus.security.Authenticated;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@RequestScoped
@Path("")
@Authenticated
@Tag(name = "Health", description = "Controller responsável por gerenciar a saúde da aplicação")
public class HealthController {


    @Inject
    @Claim("email")
    String email;

    @GET
    @Path("/health")
    @Operation(summary = "Retorna o status da aplicação", description = "Retorna o status da aplicação")
    public Response ok() {
        try {
            return Response.ok().build();
        } catch (Exception erro) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

}


