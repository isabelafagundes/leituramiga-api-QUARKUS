package br.com.isabela.controller.livro;

import br.com.isabela.service.livro.LivroService;
import io.quarkus.security.Authenticated;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.Claim;

@RequestScoped
@Path("/api")
public class LivroController {


    @Inject
    LivroService livroService;

    @Inject
    @Claim("email")
    String email;


    @GET
    @Authenticated
    @Path("/livro/{id}")
    public Response obterLivros(@PathParam("id") Integer numero) {
      return Response.ok().build();
    }


}
