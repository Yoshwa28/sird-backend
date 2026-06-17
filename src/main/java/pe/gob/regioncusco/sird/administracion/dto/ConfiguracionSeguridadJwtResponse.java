package pe.gob.regioncusco.sird.administracion.dto;

import java.time.OffsetDateTime;

public record ConfiguracionSeguridadJwtResponse(
        Long id,
        Integer accessTokenHoras,
        Integer refreshTokenDias,
        Integer intentosFallidosMax,
        Integer bloqueoMinutos,
        Long versionSeguridad,
        Boolean activo,
        OffsetDateTime creadoEn,
        OffsetDateTime actualizadoEn
) {
}
