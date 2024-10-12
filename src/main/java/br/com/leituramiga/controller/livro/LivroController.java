package br.com.leituramiga.controller.livro;

import br.com.leituramiga.dto.livro.FiltrosDto;
import br.com.leituramiga.dto.livro.LivroDto;
import br.com.leituramiga.model.exception.livro.LivroJaDesativado;
import br.com.leituramiga.model.exception.livro.LivroNaoDisponivel;
import br.com.leituramiga.model.exception.livro.LivroNaoExistente;
import br.com.leituramiga.service.livro.LivroService;
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.sql.SQLException;
import java.util.List;

@RequestScoped
@Path("/api")
@Tag(name = "Livro", description = "Controller responsável por gerenciar livros")
public class LivroController {


    @Inject
    LivroService livroService;

    @Inject
    @Claim("email")
    String email;


    @GET
    @Authenticated
    @Path("/livro/{id}")
    @Operation(summary = "Retorna um livro", description = "Retorna um livro a partir do seu código de identificação")
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

    @POST
    @PermitAll
    @Operation(summary = "Retorna os livros", description = "Retorna os livros a partir de filtros")
    @Path("/livros")
    public Response obterLivros(FiltrosDto filtrosDto) {
        try {
            List<LivroDto> livros = livroService.obterLivrosPaginados(
                    filtrosDto.numeroPagina,
                    filtrosDto.tamanhoPagina,
                    filtrosDto.pesquisa,
                    filtrosDto.numeroCidade,
                    filtrosDto.numeroCategoria,
                    filtrosDto.numeroInstituicao,
                    filtrosDto.tipoSolicitacao,
                    filtrosDto.emailUsuario
            );
            return Response.ok(livros).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Authenticated
    @Path("/livros/usuario")
    @Operation(summary = "Retorna os livros do usuário", description = "Retorna os livros do usuário do token de autenticação")
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
    @Operation(summary = "Cria um livro", description = "Cria um livro do usuário do token de autenticação")
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
    @Operation(summary = "Atualiza um livro", description = "Atualiza um livro do usuário do token de autenticação")
    public Response atualizarLivro(@PathParam("id") Integer numeroLivro, LivroDto livro) {
        try {
            livroService.atualizarLivro(livro, email);
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
    @Operation(summary = "Deleta um livro", description = "Deleta um livro do usuário do token de autenticação")
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
