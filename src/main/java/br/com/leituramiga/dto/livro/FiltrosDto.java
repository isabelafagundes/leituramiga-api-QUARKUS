package br.com.leituramiga.dto.livro;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class FiltrosDto {
    public Integer numeroCategoria;
    public Integer numeroPagina;
    public Integer tamanhoPagina;
    public Integer numeroCidade;
    public Integer numeroInstituicao;
    public String pesquisa;
    public Integer tipoSolicitacao;
    public String emailUsuario;

    public FiltrosDto() {
    }

    public FiltrosDto(Integer numeroCategoria, Integer numeroPagina, Integer tamanhoPagina, Integer numeroCidade, Integer numeroInstituicao, String pesquisa, Integer tipoSolicitacao, String emailUsuario) {
        this.numeroCategoria = numeroCategoria;
        this.numeroPagina = numeroPagina;
        this.tamanhoPagina = tamanhoPagina;
        this.numeroCidade = numeroCidade;
        this.numeroInstituicao = numeroInstituicao;
        this.pesquisa = pesquisa;
        this.tipoSolicitacao = tipoSolicitacao;
        this.emailUsuario = emailUsuario;
    }
}
