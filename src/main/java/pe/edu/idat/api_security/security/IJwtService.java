package pe.edu.idat.api_security.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.GrantedAuthority;
import pe.edu.idat.api_security.model.Usuario;

import java.util.List;

public interface IJwtService {

    String generarToken(Usuario usuario,
                        List<GrantedAuthority> authorities);
    Claims obtenerClaims(String token);
    boolean tokenValido(String token);
    String extraerToken(HttpServletRequest request);
    void generarAutenticacion(Claims claims);
}
