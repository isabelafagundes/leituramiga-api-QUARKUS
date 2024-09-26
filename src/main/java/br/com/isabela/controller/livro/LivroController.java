package br.com.isabela.controller.livro;

import br.com.isabela.dto.livro.FiltrosDto;
import br.com.isabela.dto.livro.LivroDto;
import br.com.isabela.model.exception.livro.LivroJaDesativado;
import br.com.isabela.model.exception.livro.LivroNaoDisponivel;
import br.com.isabela.model.exception.livro.LivroNaoExistente;
import br.com.isabela.service.livro.LivroService;
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.Claim;

import java.sql.SQLException;
import java.util.List;

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
    public Response obterLivro(@PathParam("id") Integer numero) {
        try {
            LivroDto dto = livroService.obterLivro(numero);
            return Response.ok(dto).build();
        } catch (LivroNaoExistente e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @PermitAll
    @Path("/livros")
    public Response obterLivros(FiltrosDto filtrosDto) {
        try {
            List<LivroDto> livros = livroService.obterLivrosPaginados(
                    filtrosDto.numeroPagina,
                    filtrosDto.tamanhoPagina,
                    filtrosDto.pesquisa,
                    filtrosDto.numeroCidade,
                    filtrosDto.numeroCategoria,
                    filtrosDto.numeroInstituicao
            );
            return Response.ok(livros).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Authenticated
    @Path("/livros/usuario")
    public Response obterLivrosUsuario() {
        try {
            List<LivroDto> livros = livroService.obterLivrosUsuario(email);
            return Response.ok(livros).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @POST
    @Authenticated
    @Path("/livro/criar")
    public Response criarLivro(LivroDto livro) {
        try {
            livroService.salvarLivro(livro, email);
            return Response.ok("Livro salvo com sucesso!").build();
        } catch (LivroJaDesativado e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (LivroNaoExistente e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (LivroNaoDisponivel e) {
            return Response.status(Response.Status.EXPECTATION_FAILED).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Authenticated
    @Path("/livro/{id}")
    public Response atualizarLivro(@PathParam("id") Integer numeroLivro, LivroDto livro) {
        try {
            livroService.salvarLivro(livro, email);
            return Response.ok("Livro salvo com sucesso!").build();
        } catch (LivroJaDesativado e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (LivroNaoExistente e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (LivroNaoDisponivel e) {
            return Response.status(Response.Status.EXPECTATION_FAILED).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }


    @DELETE
    @Authenticated
    @Path("/livro/{id}")
    public Response deletarLivro(@PathParam("id") Integer numeroLivro) {
        try {
            livroService.deletarLivro(numeroLivro, email);
            return Response.ok("Livro deletado com sucesso!").build();
        } catch (LivroNaoExistente e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}
