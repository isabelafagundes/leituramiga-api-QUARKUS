package br.com.leituramiga.dto.Instituicao;

import br.com.leituramiga.model.instituicao.Instituicao;

public class InstituicaoDto {
    public Integer id;
    public String nome;
    public String sigla;

    public static InstituicaoDto deModel(
            Instituicao instituicao
    ) {
        InstituicaoDto dto = new InstituicaoDto();

        dto.id = instituicao.getId();
        dto.nome = instituicao.getNome();
        dto.sigla = instituicao.getSigla();

        return dto;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }


}
