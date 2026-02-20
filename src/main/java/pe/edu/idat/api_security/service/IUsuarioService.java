package pe.edu.idat.api_security.service;

import pe.edu.idat.api_security.model.Usuario;

import java.util.List;

public interface IUsuarioService {

    Usuario getUsuarioByNomusuario(String nomusuario);

    List<String> getRolesByNomusuario(String nomusuario);
}
