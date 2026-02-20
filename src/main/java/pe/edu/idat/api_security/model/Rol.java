package pe.edu.idat.api_security.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "rol")
public class Rol {
    private Integer idrol;
    private String nomrol;
}
