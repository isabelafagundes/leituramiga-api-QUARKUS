package br.com.isabela.dto.comentario;

import br.com.isabela.model.comentario.Comentario;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class ComentarioDto {
    public Integer id;
    public String descricao;
    public String dataCriacao;
    public String horaCriacao;
    public String emailUsuarioCriador;
    public String emailUsuarioPerfil;

    public static ComentarioDto deModel(
            Comentario comentario
    ) {
        ComentarioDto dto = new ComentarioDto();

        dto.id = comentario.getId();
        dto.descricao = comentario.getDescricao();
        dto.dataCriacao = comentario.getdataCriacao();
        dto.horaCriacao = comentario.gethoraCriacao();

        return dto;
    }

    public ComentarioDto() {
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
