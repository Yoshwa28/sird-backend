package pe.gob.regioncusco.sird.administracion.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import pe.gob.regioncusco.sird.administracion.entity.TipoFeriado;

import java.time.LocalDate;

public record ActualizarFeriadoInstitucionalRequest(

        @NotBlank(message = "El nombre del feriado es obligatorio.")
        @Size(min = 3, max = 150, message = "El nombre del feriado debe tener entre 3 y 150 caracteres.")
        String nombre,

        @NotNull(message = "La fecha del feriado es obligatoria.")
        LocalDate fecha,

        @NotNull(message = "El tipo de feriado es obligatorio.")
        TipoFeriado tipo,

        Boolean recurrenteAnual
) {
}
