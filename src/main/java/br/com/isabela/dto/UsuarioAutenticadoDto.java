package br.com.isabela.dto;

import br.com.isabela.model.Usuario;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class UsuarioAutenticadoDto {
    public String username;

    public String email;
    public Integer tipoUsuario;
    public String accessToken;
    public String refreshToken;

    public static UsuarioAutenticadoDto deDomain(Usuario usuario, String accessToken, String refreshToken) {
        UsuarioAutenticadoDto dto = new UsuarioAutenticadoDto();
        dto.username = usuario.getUsername();
        dto.email = usuario.getEmail();
        dto.tipoUsuario = usuario.getTipoUsuario().getId();
        dto.accessToken = accessToken;
        dto.refreshToken = refreshToken;
        return dto;
    }
}
