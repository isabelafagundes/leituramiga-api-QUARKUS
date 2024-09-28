package br.com.leituramiga.dto.solicitacao;

import br.com.leituramiga.dto.endereco.EnderecoDto;
import br.com.leituramiga.dto.livro.LivroSolicitacaoDto;
import br.com.leituramiga.model.solicitacao.Solicitacao;
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
        dto.endereco = EnderecoDto.deModel(solicitacao.getEndereco());
        dto.livrosTroca = solicitacao.getLivrosTroca().stream().map(LivroSolicitacaoDto::deModel).toList();
        dto.livrosUsuarioSolicitante = solicitacao.getLivrosUsuarioCriador().stream().map(LivroSolicitacaoDto::deModel).toList();
        return dto;
    }

    public Integer getCodigoSolicitacao() {
        return codigoSolicitacao;
    }

    public String getDataEntrega() {
        return dataEntrega;
    }

    public String getHoraEntrega() {
        return horaEntrega;
    }

    public String getDataDevolucao() {
        return dataDevolucao;
    }

    public String getHoraDevolucao() {
        return horaDevolucao;
    }

    public String getMotivoRecusa() {
        return motivoRecusa;
    }

    public String getInformacoesAdicionais() {
        return informacoesAdicionais;
    }

    public Integer getCodigoTipoSolicitacao() {
        return codigoTipoSolicitacao;
    }

    public Integer getCodigoStatusSolicitacao() {
        return codigoStatusSolicitacao;
    }

    public String getEmailUsuarioSolicitante() {
        return emailUsuarioSolicitante;
    }

    public String getEmailUsuarioReceptor() {
        return emailUsuarioReceptor;
    }

    public Integer getCodigoFormaEntrega() {
        return codigoFormaEntrega;
    }

    public String getCodigoRastreioCorreio() {
        return codigoRastreioCorreio;
    }

    public EnderecoDto getEndereco() {
        return endereco;
    }

    public List<LivroSolicitacaoDto> getLivrosUsuarioSolicitante() {
        return livrosUsuarioSolicitante;
    }

    public List<LivroSolicitacaoDto> getLivrosTroca() {
        return livrosTroca;
    }
}
