package pe.edu.idat.api_security.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.idat.api_security.dto.ErrorMessage;
import pe.edu.idat.api_security.dto.GenericResponse;
import pe.edu.idat.api_security.dto.Login;
import pe.edu.idat.api_security.dto.UsuarioJwt;
import pe.edu.idat.api_security.model.Rol;
import pe.edu.idat.api_security.model.Usuario;
import pe.edu.idat.api_security.security.IJwtService;
import pe.edu.idat.api_security.service.impl.DetalleUsuarioService;
import pe.edu.idat.api_security.service.impl.UsuarioService;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@RestController
public class AuthController {

    private final UsuarioService usuarioService;
    private final DetalleUsuarioService detalleUsuarioService;
    private final IJwtService jwtService;
    private final AuthenticationManager authManager;

    //localhost:8080/api/v1/auth/login
    @PostMapping("/login")
    public ResponseEntity<GenericResponse<UsuarioJwt>> login(
            @RequestBody Login login){
        GenericResponse<UsuarioJwt> response;
        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            login.getUsername(),
                            login.getPassword()));
            Usuario usuario = usuarioService
                    .getUsuarioByNomusuario(login.getUsername());
            List<String> roles = usuarioService
                    .getRolesByNomusuario(login.getUsername());
            String token = jwtService.generarToken(usuario,
                    detalleUsuarioService.getAuthorities(roles));
            UsuarioJwt usuarioJwt = UsuarioJwt.builder()
                    .idusuario(usuario.getIdusuario())
                    .nomusuario(usuario.getNomusuario())
                    .token(token).build();
            response = GenericResponse.<UsuarioJwt>builder()
                    .response(usuarioJwt).build();
            return ResponseEntity.ok(response);
        }catch (AuthenticationException e){
            response = GenericResponse.<UsuarioJwt>builder()
                    .error(ErrorMessage.builder()
                            .message("Usuario y/o password incorrecto")
                            .build()).build();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(response);
        }
    }

}
