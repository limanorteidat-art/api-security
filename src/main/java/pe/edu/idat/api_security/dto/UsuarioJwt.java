package pe.edu.idat.api_security.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UsuarioJwt {
    private Integer idusuario;
    private String nomusuario;
    private String token;
}
