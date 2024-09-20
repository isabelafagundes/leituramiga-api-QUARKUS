package br.com.isabela.dto.solicitacao;

import br.com.isabela.dto.endereco.EnderecoDto;
import br.com.isabela.dto.livro.LivroSolicitacaoDto;
import br.com.isabela.model.endereco.Endereco;
import br.com.isabela.model.solicitacao.Solicitacao;
import io.quarkus.runtime.annotations.RegisterForReflection;

import java.util.List;

@RegisterForReflection
public class SolicitacaoDto {

    public Integer codigoSolicitacao;
    public String dataEntrega;
    public String horaEntrega;
    public String dataDevolucao;
    public String horaDevolucao;
    public String motivoRecusa;
    public String informacoesAdicionais;
    public Integer codigoTipoSolicitacao;
    public Integer codigoStatusSolicitacao;
    public String emailUsuarioSolicitante;
    public String emailUsuarioReceptor;
    public Integer codigoFormaEntrega;
    public String codigoRastreioCorreio;
    public EnderecoDto endereco;
    public List<LivroSolicitacaoDto> livrosUsuarioSolicitante;
    public List<LivroSolicitacaoDto> livrosTroca;


    public static SolicitacaoDto deModel(
            Solicitacao solicitacao
    ) {
        SolicitacaoDto dto = new SolicitacaoDto();
        dto.codigoFormaEntrega = solicitacao.getCodigoFormaEntrega();
        dto.codigoRastreioCorreio = solicitacao.getCodigoRastreioCorreio();
        dto.codigoSolicitacao = solicitacao.getCodigoSolicitacao();
        dto.codigoStatusSolicitacao = solicitacao.getCodigoStatusSolicitacao();
        dto.codigoTipoSolicitacao = solicitacao.getCodigoTipoSolicitacao();
        dto.dataDevolucao = solicitacao.getDataDevolucao();
        dto.dataEntrega = solicitacao.getDataEntrega();
        dto.emailUsuarioReceptor = solicitacao.getEmailUsuarioReceptor();
        dto.emailUsuarioSolicitante = solicitacao.getEmailUsuarioSolicitante();
        dto.horaDevolucao = solicitacao.getHoraDevolucao();
        dto.horaEntrega = solicitacao.getHoraEntrega();
        dto.informacoesAdicionais = solicitacao.getInformacoesAdicionais();
        return dto;
    }

}
