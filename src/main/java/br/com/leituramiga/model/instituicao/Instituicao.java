package br.com.leituramiga.model.instituicao;

public class Instituicao {
    private Integer id;
    private String nome;
    private String sigla;

    public Instituicao() {
    }

    public static Instituicao carregar(
            Integer id,
            String nome,
            String sigla) {
        Instituicao instituicao = new Instituicao();
        instituicao.id = id;
        instituicao.nome = nome;
        instituicao.sigla = sigla;

        return instituicao;

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
