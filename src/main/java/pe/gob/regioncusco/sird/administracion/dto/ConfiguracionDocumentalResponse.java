package pe.gob.regioncusco.sird.administracion.dto;

import java.time.OffsetDateTime;

public record ConfiguracionDocumentalResponse(
        Long id,
        Integer periodoRetencionAnios,
        String clasificacionPorDefecto,
        Boolean activo,
        OffsetDateTime creadoEn,
        OffsetDateTime actualizadoEn
) {
}
