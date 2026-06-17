package pe.gob.regioncusco.sird.administracion.dto;

import jakarta.validation.constraints.NotNull;

public record CambiarEstadoFeriadoRequest(

        @NotNull(message = "El estado activo es obligatorio.")
        Boolean activo
) {
}
