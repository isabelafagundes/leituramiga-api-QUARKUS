package br.com.leituramiga.controller.email;

import br.com.leituramiga.service.email.EmailService;
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.io.IOException;

@RequestScoped
@Path("/api")
@Tag(name = "Email", description = "Controller responsável por enviar emails")
public class EmailController {

    @Inject
    EmailService emailService;

    @POST
    @PermitAll
    @Path("/email")
    public Response enviarEmailSimples() throws IOException {
        emailService.enviarEmailBoasVindas("isabelahidalgo.2004@gmail.com", "Isabela");
        return Response.ok("Email enviado com sucesso").build();
    }

}
