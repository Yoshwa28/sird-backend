package pe.gob.regioncusco.sird.administracion.repository;

import pe.gob.regioncusco.sird.administracion.entity.ConfiguracionDocumental;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConfiguracionDocumentalRepository extends JpaRepository<ConfiguracionDocumental, Long> {

    Optional<ConfiguracionDocumental> findByActivoTrue();
}
