package pe.edu.idat.api_security.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pe.edu.idat.api_security.model.Usuario;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtService implements  IJwtService {

    private static final String SECRET = "mi-clave-super-secreta-123456789";
    private static final Key KEY = Keys.hmacShaKeyFor(
            SECRET.getBytes());

    @Override
    public String generarToken(Usuario usuario,
                               List<GrantedAuthority> authorities) {
        return Jwts.builder()
                .id(usuario.getIdusuario().toString())
                .subject(usuario.getNomusuario())
                .claim("authorities",
                        authorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList())
                ).issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+3000000))
                .signWith(KEY)
                .compact();
    }

    @Override
    public Claims obtenerClaims(String token) {
        return Jwts.parser().verifyWith((SecretKey)KEY).build()
                .parseSignedClaims(token).getPayload();
    }
    @Override
    public boolean tokenValido(String token) {
        try{
            obtenerClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String extraerToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        //Bearer aksjdaidjjiweiqw.sdjsaiodqwijeiqu8437843.sdhaidi
        if(header != null && header.startsWith("Bearer ")){
            return header.substring(7);
        }
        return null;
    }

    @Override
    public void generarAutenticacion(Claims claims) {
        List<String> authorities = claims.get("authorities",
                List.class);
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(
                        claims.getSubject(),
                        null,
                        authorities.stream()
                        .map(SimpleGrantedAuthority::new).toList()
                );
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}
