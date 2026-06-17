package pe.gob.regioncusco.sird.administracion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.gob.regioncusco.sird.administracion.entity.ConfiguracionLimiteEspacioColaborativo;
import pe.gob.regioncusco.sird.administracion.entity.TipoEspacioColaborativo;

import java.util.Optional;

public interface ConfiguracionLimiteEspacioColaborativoRepository
        extends JpaRepository<ConfiguracionLimiteEspacioColaborativo, Long> {

    Optional<ConfiguracionLimiteEspacioColaborativo> findByTipoEspacio(TipoEspacioColaborativo tipoEspacio);
}
