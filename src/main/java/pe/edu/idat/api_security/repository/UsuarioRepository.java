package pe.edu.idat.api_security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.edu.idat.api_security.model.Rol;
import pe.edu.idat.api_security.model.Usuario;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    /*@Query(value = """
        SELECT * FROM usuario
        WHERE nomusuario = :nomusuario
        """, nativeQuery = true)
    Usuario getUsuarioByNomUsuario(
            @Param("nomusuario") String nomusuario);*/

    Usuario findByNomusuario(String nomusuario);


    @Query(value = """
            SELECT r.nomrol FROM USUARIO u
            INNER JOIN usuario_rol ur
            ON u.idusuario = ur.idusuario
            INNER JOIN rol r
            ON r.idrol = ur.idrol
            WHERE NOMUSUARIO = :nomusuario
        """, nativeQuery = true)
    List<String> getRoleByNomUsuario(
            @Param("nomusuario") String nomusuario);

}
