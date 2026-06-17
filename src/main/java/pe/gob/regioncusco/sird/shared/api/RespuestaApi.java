package pe.gob.regioncusco.sird.shared.api;

import java.time.OffsetDateTime;

public record RespuestaApi<T>(
        boolean resultado,
        String mensaje,
        T datos,
        OffsetDateTime fechaHora
) {
    public static <T> RespuestaApi<T> ok(String mensaje, T datos) {
        return new RespuestaApi<>(true, mensaje, datos, OffsetDateTime.now());
    }
}
