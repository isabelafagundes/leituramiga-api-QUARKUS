package br.com.leituramiga.model.solicitacao;

import br.com.leituramiga.model.endereco.Endereco;
import br.com.leituramiga.model.livro.LivroSolicitacao;

import java.util.List;

public class Solicitacao {
    private Integer codigoSolicitacao;
    private String dataCriacao;
    private String horaCriacao;
    private String dataAtualizacao;
    private String horaAtualizacao;
    private String dataEntrega;
    private String horaEntrega;
    private String dataDevolucao;
    private String horaDevolucao;
    private String dataAceite;
    private String horaAceite;
    private String motivoRecusa;
    private String informacoesAdicionais;
    private Integer codigoTipoSolicitacao;
    private Integer codigoStatusSolicitacao;
    private String emailUsuarioSolicitante;
    private String emailUsuarioReceptor;
    private Integer codigoFormaEntrega;
    private String codigoRastreioCorreio;
    private Endereco endereco;
    private List<LivroSolicitacao> livrosUsuarioCriador;
    private List<LivroSolicitacao> livrosTroca;

    public Solicitacao() {
    }

    public static Solicitacao criar(
            Integer codigoSolicitacao,
            String dataCriacao,
            String horaCriacao,
            String dataAtualiacao,
            String horaAtualizacao,
            String dataEntrega,
            String horaEntrega,
            String dataDevolucao,
            String horaDevolucao,
            String dataAceite,
            String horaAceite,
            String motivoRecusa,
            String informacoesAdicionais,
            Integer codigoTipoSolicitacao,
            Integer codigoStatusSolicitacao,
            String emailUsuarioSolicitante,
            String emailUsuarioReceptor,
            Integer codigoFormaEntrega,
            String codigoRastreioCorreio,
            Endereco endereco,
            List<LivroSolicitacao> livrosUsuarioCriador,
            List<LivroSolicitacao> livrosTroca
    ) {
        Solicitacao solicitacao = new Solicitacao();
        solicitacao.setCodigoSolicitacao(codigoSolicitacao);
        solicitacao.setDataCriacao(dataCriacao);
        solicitacao.setHoraCriacao(horaCriacao);
        solicitacao.setDataAtualizacao(dataAtualiacao);
        solicitacao.setHoraAtualizacao(horaAtualizacao);
        solicitacao.setDataEntrega(dataEntrega);
        solicitacao.setHoraEntrega(horaEntrega);
        solicitacao.setDataDevolucao(dataDevolucao);
        solicitacao.setHoraDevolucao(horaDevolucao);
        solicitacao.setDataAceite(dataAceite);
        solicitacao.setHoraAceite(horaAceite);
        solicitacao.setMotivoRecusa(motivoRecusa);
        solicitacao.setInformacoesAdicionais(informacoesAdicionais);
        solicitacao.setCodigoTipoSolicitacao(codigoTipoSolicitacao);
        solicitacao.setCodigoStatusSolicitacao(codigoStatusSolicitacao);
        solicitacao.setEmailUsuarioSolicitante(emailUsuarioSolicitante);
        solicitacao.setCodigoFormaEntrega(codigoFormaEntrega);
        solicitacao.setCodigoRastreioCorreio(codigoRastreioCorreio);
        solicitacao.setEndereco(endereco);
        solicitacao.setLivrosUsuarioCriador(livrosUsuarioCriador);
        solicitacao.setLivrosTroca(livrosTroca);
        solicitacao.setEmailUsuarioReceptor(emailUsuarioReceptor);
        return solicitacao;
    }

    public void setEmailUsuarioReceptor(String emailUsuarioReceptor) {
        this.emailUsuarioReceptor = emailUsuarioReceptor;
    }

    public List<LivroSolicitacao> getLivrosUsuarioCriador() {
        return livrosUsuarioCriador;
    }

    public void setLivrosUsuarioCriador(List<LivroSolicitacao> livrosUsuarioCriador) {
        this.livrosUsuarioCriador = livrosUsuarioCriador;
    }

    public List<LivroSolicitacao> getLivrosTroca() {
        return livrosTroca;
    }

    public void setLivrosTroca(List<LivroSolicitacao> livrosTroca) {
        this.livrosTroca = livrosTroca;
    }

    public Integer getCodigoSolicitacao() {
        return codigoSolicitacao;
    }

    public void setCodigoSolicitacao(Integer codigoSolicitacao) {
        this.codigoSolicitacao = codigoSolicitacao;
    }

    public String getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(String dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getHoraCriacao() {
        return horaCriacao;
    }

    public void setHoraCriacao(String horaCriacao) {
        this.horaCriacao = horaCriacao;
    }

    public String getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(String dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public String getHoraAtualizacao() {
        return horaAtualizacao;
    }

    public void setHoraAtualizacao(String horaAtualizacao) {
        this.horaAtualizacao = horaAtualizacao;
    }

    public String getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(String dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public String getHoraEntrega() {
        return horaEntrega;
    }

    public void setHoraEntrega(String horaEntrega) {
        this.horaEntrega = horaEntrega;
    }

    public String getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(String dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public String getHoraDevolucao() {
        return horaDevolucao;
    }

    public void setHoraDevolucao(String horaDevolucao) {
        this.horaDevolucao = horaDevolucao;
    }

    public String getDataAceite() {
        return dataAceite;
    }

    public void setDataAceite(String dataAceite) {
        this.dataAceite = dataAceite;
    }

    public String getHoraAceite() {
        return horaAceite;
    }

    public void setHoraAceite(String horaAceite) {
        this.horaAceite = horaAceite;
    }

    public String getMotivoRecusa() {
        return motivoRecusa;
    }

    public void setMotivoRecusa(String motivoRecusa) {
        this.motivoRecusa = motivoRecusa;
    }

    public String getInformacoesAdicionais() {
        return informacoesAdicionais;
    }

    public void setInformacoesAdicionais(String informacoesAdicionais) {
        this.informacoesAdicionais = informacoesAdicionais;
    }

    public int getCodigoTipoSolicitacao() {
        return codigoTipoSolicitacao;
    }

    public void setCodigoTipoSolicitacao(int codigoTipoSolicitacao) {
        this.codigoTipoSolicitacao = codigoTipoSolicitacao;
    }

    public int getCodigoStatusSolicitacao() {
        return codigoStatusSolicitacao;
    }

    public void setCodigoStatusSolicitacao(int codigoStatusSolicitacao) {
        this.codigoStatusSolicitacao = codigoStatusSolicitacao;
    }

    public String getEmailUsuarioSolicitante() {
        return emailUsuarioSolicitante;
    }

    public void setEmailUsuarioSolicitante(String emailUsuarioSolicitante) {
        this.emailUsuarioSolicitante = emailUsuarioSolicitante;
    }

    public int getCodigoFormaEntrega() {
        return codigoFormaEntrega;
    }

    public void setCodigoFormaEntrega(int codigoFormaEntrega) {
        this.codigoFormaEntrega = codigoFormaEntrega;
    }

    public String getCodigoRastreioCorreio() {
        return codigoRastreioCorreio;
    }

    public void setCodigoRastreioCorreio(String codigoRastreioCorreio) {
        this.codigoRastreioCorreio = codigoRastreioCorreio;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public String getEmailUsuarioReceptor() {
        return emailUsuarioReceptor;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
}

