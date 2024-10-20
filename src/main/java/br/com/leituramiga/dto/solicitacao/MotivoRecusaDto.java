package br.com.leituramiga.dto.solicitacao;


import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class MotivoRecusaDto {

    public Long codigoSolicitacao;
    public String motivo;

    public MotivoRecusaDto(Long codigoSolicitacao, String motivo) {
        this.codigoSolicitacao = codigoSolicitacao;
        this.motivo = motivo;
    }
}
