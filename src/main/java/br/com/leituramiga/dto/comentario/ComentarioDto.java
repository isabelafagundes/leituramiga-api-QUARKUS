package br.com.leituramiga.dto.comentario;

import br.com.leituramiga.model.comentario.Comentario;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class ComentarioDto {
    public Integer codigoComentario;
    public String descricao;
    public String dataCriacao;
    public String horaCriacao;
    public String emailUsuarioCriador;
    public String emailUsuarioPerfil;
    public String imagem;
    public String nomeUsuarioCriador;

    public static ComentarioDto deModel(
            Comentario comentario
    ) {
        ComentarioDto dto = new ComentarioDto();
        dto.emailUsuarioCriador = comentario.getEmailUsuarioCriador();
        dto.emailUsuarioPerfil = comentario.getEmailUsuarioPerfil();
        dto.nomeUsuarioCriador = comentario.getNomeUsuarioCriador();
        dto.codigoComentario = comentario.getCodigoComentario();
        dto.descricao = comentario.getDescricao();
        dto.dataCriacao = comentario.getdataCriacao();
        dto.horaCriacao = comentario.gethoraCriacao();
        dto.imagem = comentario.getImagem();

        return dto;
    }

    public ComentarioDto() {
    }

    public String getImagem() {
        return imagem;
    }

    public String getNomeUsuarioCriador() {
        return nomeUsuarioCriador;
    }

    public Integer getCodigoComentario() {
        return codigoComentario;
    }

    public void setCodigoComentario(Integer codigoComentario) {
        this.codigoComentario = codigoComentario;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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

    public String getEmailUsuarioCriador() {
        return emailUsuarioCriador;
    }

    public void setEmailUsuarioCriador(String emailUsuarioCriador) {
        this.emailUsuarioCriador = emailUsuarioCriador;
    }

    public String getEmailUsuarioPerfil() {
        return emailUsuarioPerfil;
    }

    public void setEmailUsuarioPerfil(String emailUsuarioPerfil) {
        this.emailUsuarioPerfil = emailUsuarioPerfil;
    }


}
