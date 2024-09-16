package br.com.isabela.dto.categoria;

import br.com.isabela.model.categoria.Categoria;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class CategoriaDto {
    public int id;
    public String nome_categoria;
    public String descricao_categoria;

    public static CategoriaDto deModel(
            Categoria categoria
    ) {
        CategoriaDto dto = new CategoriaDto();

        dto.id = categoria.getId();
        dto.nome_categoria = categoria.getNome_categoria();
        dto.descricao_categoria = categoria.getDescricao_categoria();

        return dto;
    }

    public CategoriaDto() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome_categoria() {
        return nome_categoria;
    }

    public void setNome_categoria(String nome_categoria) {
        this.nome_categoria = nome_categoria;
    }

    public String getDescricao_categoria() {
        return descricao_categoria;
    }

    public void setDescricao_categoria(String descricao_categoria) {
        this.descricao_categoria = descricao_categoria;
    }

}
