package br.com.leituramiga.dto.livro;

import br.com.leituramiga.model.livro.LivroSolicitacao;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class LivroSolicitacaoDto {
    public Integer codigoLivro;
    public String titulo;
    public String autor;
    public String emailUsuario;

    public LivroSolicitacaoDto() {
    }

    public static LivroSolicitacaoDto deModel(
            LivroSolicitacao livro
    ) {
        LivroSolicitacaoDto dto = new LivroSolicitacaoDto();
        dto.autor = livro.getAutor();
        dto.codigoLivro = livro.getCodigoLivro();
        dto.titulo = livro.getTitulo();
        dto.emailUsuario = livro.getEmailUsuario();;
        return dto;
    }

}
