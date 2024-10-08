package br.com.leituramiga.model.cidade;

public class Cidade {
    private Integer codigoCidade;
    private String nomeCidade;
    private String estado;

    public Cidade() {
    }

    public static Cidade carregar(
            Integer codigoCidade,
            String nomeCidade,
            String estado
    ) {
        Cidade cidade = new Cidade();
        cidade.setCodigoCidade(codigoCidade);
        cidade.setNomeCidade(nomeCidade);
        cidade.setEstado(estado);
        return cidade;
    }


    public Integer getCodigoCidade() {
        return codigoCidade;
    }

    public void setCodigoCidade(Integer codigoCidade) {
        this.codigoCidade = codigoCidade;
    }

    public String getNomeCidade() {
        return nomeCidade;
    }

    public void setNomeCidade(String nomeCidade) {
        this.nomeCidade = nomeCidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
