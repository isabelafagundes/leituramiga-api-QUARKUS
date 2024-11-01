package br.com.leituramiga.dto.solicitacao;

import br.com.leituramiga.dto.endereco.EnderecoDto;
import br.com.leituramiga.dto.livro.LivroSolicitacaoDto;
import io.quarkus.runtime.annotations.RegisterForReflection;

import java.util.List;

@RegisterForReflection
public class AceiteSolicitacaoDto {

    public List<LivroSolicitacaoDto> livros;

    public EnderecoDto endereco;
    public Integer codigoSolitacao;

    public AceiteSolicitacaoDto(List<LivroSolicitacaoDto> livros, Integer codigoSolitacao) {
        this.livros = livros;
        this.codigoSolitacao = codigoSolitacao;
    }
}
