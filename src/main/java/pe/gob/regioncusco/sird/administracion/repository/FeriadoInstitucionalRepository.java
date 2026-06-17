package pe.gob.regioncusco.sird.administracion.repository;

import pe.gob.regioncusco.sird.administracion.entity.FeriadoInstitucional;
import pe.gob.regioncusco.sird.administracion.entity.TipoFeriado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface FeriadoInstitucionalRepository extends JpaRepository<FeriadoInstitucional, Long> {

    List<FeriadoInstitucional> findByActivoTrueOrderByFechaAsc();

    List<FeriadoInstitucional> findAllByOrderByFechaAsc();

    Optional<FeriadoInstitucional> findByFechaAndTipo(LocalDate fecha, TipoFeriado tipo);

    boolean existsByFechaAndTipo(LocalDate fecha, TipoFeriado tipo);
}
