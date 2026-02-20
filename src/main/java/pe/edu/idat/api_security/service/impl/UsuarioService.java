package pe.edu.idat.api_security.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.idat.api_security.model.Usuario;
import pe.edu.idat.api_security.repository.UsuarioRepository;
import pe.edu.idat.api_security.service.IUsuarioService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UsuarioService implements IUsuarioService {
    private final UsuarioRepository usuarioRepository;
    @Override
    public Usuario getUsuarioByNomusuario(String nomusuario) {
        return usuarioRepository.findByNomusuario(nomusuario);
    }
    @Override
    public List<String> getRolesByNomusuario(String nomusuario) {
        return usuarioRepository.getRoleByNomUsuario(nomusuario);
    }
}
