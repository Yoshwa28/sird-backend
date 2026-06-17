package pe.gob.regioncusco.sird.administracion.repository;

import pe.gob.regioncusco.sird.administracion.entity.ConfiguracionSeguridadJwt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConfiguracionSeguridadJwtRepository extends JpaRepository<ConfiguracionSeguridadJwt, Long> {

    Optional<ConfiguracionSeguridadJwt> findByActivoTrue();
}
