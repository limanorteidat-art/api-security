package pe.edu.idat.api_security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.idat.api_security.model.Rol;

public interface RolRepository extends JpaRepository<Rol, Integer> {
}
