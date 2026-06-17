package pe.gob.regioncusco.sird.administracion.dto;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public record FeriadoInstitucionalResponse(
        Long id,
        String nombre,
        LocalDate fecha,
        String tipo,
        Boolean recurrenteAnual,
        Boolean activo,
        OffsetDateTime creadoEn,
        OffsetDateTime actualizadoEn
) {
}
