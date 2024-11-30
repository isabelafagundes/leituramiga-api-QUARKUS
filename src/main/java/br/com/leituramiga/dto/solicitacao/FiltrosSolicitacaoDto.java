package br.com.leituramiga.dto.solicitacao;

public class FiltrosSolicitacaoDto {
    public Integer numeroPagina;
    public Integer tamanhoPagina;
    public String dataInicio;
    public String dataFim;

    public FiltrosSolicitacaoDto() {
    }

    public FiltrosSolicitacaoDto(Integer numeroPagina, Integer tamanhoPagina, String dataInicio, String dataFim) {
        this.numeroPagina = numeroPagina;
        this.tamanhoPagina = tamanhoPagina;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }
}
