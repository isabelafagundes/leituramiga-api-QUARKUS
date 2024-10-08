package br.com.leituramiga.dto.endereco;

import br.com.leituramiga.model.categoria.Categoria;
import br.com.leituramiga.model.cidade.Cidade;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class CidadeDto {

    public Integer codigoCidade;
    public String nomeCidade;

    public String estado;

    public static CidadeDto deModel(Cidade categoria) {
        CidadeDto dto = new CidadeDto();
        dto.codigoCidade = categoria.getCodigoCidade();
        dto.nomeCidade = categoria.getNomeCidade();
        dto.estado = categoria.getEstado();

        return dto;
    }

}
