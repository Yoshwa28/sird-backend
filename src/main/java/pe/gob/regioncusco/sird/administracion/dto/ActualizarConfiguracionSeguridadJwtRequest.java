package pe.gob.regioncusco.sird.administracion.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ActualizarConfiguracionSeguridadJwtRequest(

        @NotNull(message = "El tiempo de vida del access token es obligatorio.")
        @Min(value = 1, message = "El access token debe durar como mínimo 1 hora.")
        @Max(value = 24, message = "El access token no debe superar 24 horas.")
        Integer accessTokenHoras,

        @NotNull(message = "La duración del refresh token es obligatoria.")
        @Min(value = 1, message = "El refresh token debe durar como mínimo 1 día.")
        @Max(value = 90, message = "El refresh token no debe superar 90 días.")
        Integer refreshTokenDias,

        @NotNull(message = "El máximo de intentos fallidos es obligatorio.")
        @Min(value = 3, message = "Los intentos fallidos deben ser como mínimo 3.")
        @Max(value = 10, message = "Los intentos fallidos no deben superar 10.")
        Integer intentosFallidosMax,

        @NotNull(message = "La duración del bloqueo es obligatoria.")
        @Min(value = 5, message = "El bloqueo debe durar como mínimo 5 minutos.")
        @Max(value = 60, message = "El bloqueo no debe superar 60 minutos.")
        Integer bloqueoMinutos
) {
}
