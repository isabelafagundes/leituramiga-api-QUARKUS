package br.com.isabela.model.categoria;

public class Categoria {
    private int id;
    private String nome_categoria;
    private String descricao_categoria;

    public Categoria() {
    }

    public static Categoria carregar(
            int id,
            String nome_categoria,
            String descricao_categoria) {
        Categoria categoria = new Categoria();
        categoria.id = id;
        categoria.nome_categoria = nome_categoria;
        categoria.descricao_categoria = descricao_categoria;
        return categoria;
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
