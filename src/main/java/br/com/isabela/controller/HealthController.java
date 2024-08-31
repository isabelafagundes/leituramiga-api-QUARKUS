package br.com.isabela.controller;

import io.quarkus.security.Authenticated;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.Claim;

@RequestScoped
@Path("")
@Authenticated
public class HealthController {


    @Inject
    @Claim("email")
    String email;

    @GET
    @Path("/health")
    public Response ok() {
        try {
            return Response.ok().build();
        } catch (Exception erro) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

}


