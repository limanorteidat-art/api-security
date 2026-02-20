package pe.edu.idat.api_security.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pe.edu.idat.api_security.model.Usuario;
import pe.edu.idat.api_security.service.IUsuarioService;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DetalleUsuarioService implements UserDetailsService {
    private final IUsuarioService usuarioService;
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        Usuario usuario = usuarioService
                .getUsuarioByNomusuario(username);
        List<String> roles = usuarioService
                .getRolesByNomusuario(username);
        return getUserSecurity(usuario, getAuthorities(roles));
    }

    public List<GrantedAuthority> getAuthorities(
            List<String> roles){
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String rol : roles){
            authorities.add(new
                    SimpleGrantedAuthority("ROLE_"+rol));
        }
        return authorities;
    }

    public UserDetails getUserSecurity(Usuario usuario,
               List<GrantedAuthority> authorities){
        return new User(usuario.getNomusuario(),
                usuario.getPassword(),
                usuario.getActivo(),
                true,
                true,
                true, authorities);
    }
}
