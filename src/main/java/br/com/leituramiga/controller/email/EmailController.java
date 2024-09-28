package br.com.leituramiga.controller.email;

import br.com.leituramiga.service.email.EmailService;
import io.quarkus.security.Authenticated;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@RequestScoped
@Path("/api")
public class EmailController {

    @Inject
    EmailService emailService;

    @POST
    @Authenticated
    @Path("/email")
    public Response enviarEmailSimples() {
        emailService.enviarEmailBoasVindas("isabelahidalgo.2004@gmail.com", "Isabela");
        return Response.ok("Email enviado com sucesso").build();
    }

}
