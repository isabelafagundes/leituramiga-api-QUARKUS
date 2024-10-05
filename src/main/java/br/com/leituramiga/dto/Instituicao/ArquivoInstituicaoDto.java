package br.com.leituramiga.dto.Instituicao;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.PartType;

import java.io.InputStream;

@RegisterForReflection
public class ArquivoInstituicaoDto {


    @FormParam("file")  // 'file' é o nome do campo no formulário
    @PartType(MediaType.APPLICATION_OCTET_STREAM)  // ou outro tipo, dependendo do tipo do arquivo
    private InputStream arquivo;

    public InputStream getArquivo() {
        return arquivo;
    }

    public void setArquivo(InputStream arquivo) {
        this.arquivo = arquivo;
    }


}
