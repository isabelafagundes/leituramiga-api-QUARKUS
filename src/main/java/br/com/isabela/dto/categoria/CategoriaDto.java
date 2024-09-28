package br.com.isabela.dto.categoria;

import br.com.isabela.model.categoria.Categoria;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class CategoriaDto {
    public Integer id;
    public String descricao;

    public static CategoriaDto deModel(
            Categoria categoria
    ) {
        CategoriaDto dto = new CategoriaDto();

        dto.id = categoria.getId();
        dto.descricao = categoria.getDescricao();

        return dto;
    }

    public CategoriaDto() {

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


}
