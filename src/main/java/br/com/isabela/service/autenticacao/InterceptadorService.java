package br.com.isabela.service.autenticacao;

import jakarta.annotation.Priority;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Provider
@Priority(1)
public class InterceptadorService implements ContainerRequestFilter {

    final Response NAO_AUTORIZADO = Response.status(Response.Status.UNAUTHORIZED).build();
    final Response TOKEN_INVALIDO = Response.status(Response.Status.CONFLICT).build();

    @Context
    ResourceInfo resourceInfo;

    @Inject
    JsonWebToken token;

    @Override
    public void filter(ContainerRequestContext context) {
        if (verificarPermitAll()) {
            return;
        }

        verificarTipoToken(context);

        if (verificarToken(context) || verificarPrincipal(context)) {
            context.abortWith(NAO_AUTORIZADO);
        }

    }

    private boolean verificarPermitAll() {
        try {
            return resourceInfo.getResourceMethod().isAnnotationPresent(PermitAll.class);
        } catch (Exception erro) {
            throw erro;
        }
    }

    private boolean verificarToken(ContainerRequestContext context) {
        String authHeader = context.getHeaderString(HttpHeaders.AUTHORIZATION);
        return authHeader == null || !authHeader.startsWith("Bearer ") || token.getRawToken() == null;
    }

    private void verificarTipoToken(ContainerRequestContext context) {
        String path = context.getUriInfo().getPath();
        if (!path.contains("refresh") && token.getClaim("type").equals("refresh")) context.abortWith(TOKEN_INVALIDO);
    }

    private boolean verificarPrincipal(ContainerRequestContext context) {
        return context.getSecurityContext().getUserPrincipal() == null;
    }
}
