package br.com.leituramiga.model.categoria;

public class Categoria {
    private Integer id;
    private String descricao;

    public Categoria() {
    }

    public static Categoria carregar(
            Integer id,
            String descricao) {
        Categoria categoria = new Categoria();
        categoria.id = id;
        categoria.descricao = descricao;

        return categoria;
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
