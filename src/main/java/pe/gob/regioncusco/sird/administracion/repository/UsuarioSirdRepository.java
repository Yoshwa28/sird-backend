package pe.gob.regioncusco.sird.administracion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.gob.regioncusco.sird.administracion.entity.UsuarioSird;

import java.util.List;
import java.util.Optional;

public interface UsuarioSirdRepository extends JpaRepository<UsuarioSird, Long> {

    boolean existsByCorreo(String correo);

    Optional<UsuarioSird> findByCorreo(String correo);

    List<UsuarioSird> findByActivoTrue();

    List<UsuarioSird> findByActivoFalse();
}
