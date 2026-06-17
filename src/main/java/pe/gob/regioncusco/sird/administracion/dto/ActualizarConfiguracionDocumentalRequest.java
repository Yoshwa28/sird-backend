package pe.gob.regioncusco.sird.administracion.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import pe.gob.regioncusco.sird.administracion.entity.ClasificacionDocumento;

public record ActualizarConfiguracionDocumentalRequest(

        @NotNull(message = "El período de retención TRD es obligatorio.")
        @Min(value = 1, message = "El período de retención debe ser como mínimo 1 año.")
        @Max(value = 50, message = "El período de retención no debe superar 50 años.")
        Integer periodoRetencionAnios,

        @NotNull(message = "La clasificación por defecto es obligatoria.")
        ClasificacionDocumento clasificacionPorDefecto
) {
}
