package br.com.isabela.dto.comentario;

import br.com.isabela.model.comentario.Comentario;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class ComentarioDto {
    public int id;
    public String descricao;
    public String data_criacao;
    public String hora_criacao;

    public static ComentarioDto deModel(
            Comentario comentario
    ) {
        ComentarioDto dto = new ComentarioDto();

        dto.id = comentario.getId();
        dto.descricao = comentario.getDescricao();
        dto.data_criacao = comentario.getData_criacao();
        dto.hora_criacao = comentario.getHora_criacao();

        return dto;
    }

    public ComentarioDto() {
    }


        public int getId () {
            return id;
        }

        public void setId ( int id){
            this.id = id;
        }

        public String getDescricao () {
            return descricao;
        }

        public void setDescricao (String descricao){
            this.descricao = descricao;
        }

        public String getData_criacao () {
            return data_criacao;
        }

        public void setData_criacao (String data_criacao){
            this.data_criacao = data_criacao;
        }

        public String getHora_criacao () {
            return hora_criacao;
        }

        public void setHora_criacao (String hora_criacao){
            this.hora_criacao = hora_criacao;
        }


}
