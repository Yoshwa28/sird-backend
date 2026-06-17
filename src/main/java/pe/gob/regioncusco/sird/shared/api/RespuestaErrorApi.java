package pe.gob.regioncusco.sird.shared.api;

import java.time.OffsetDateTime;
import java.util.List;

public record RespuestaErrorApi(
        boolean resultado,
        String mensaje,
        Object datos,
        ErrorApi error,
        OffsetDateTime fechaHora
) {
    public static RespuestaErrorApi crear(
            String mensaje,
            String codigo,
            int estadoHttp,
            List<ErrorDetalle> detalles,
            String trazaId
    ) {
        return new RespuestaErrorApi(
                false,
                mensaje,
                null,
                new ErrorApi(codigo, estadoHttp, detalles, trazaId),
                OffsetDateTime.now()
        );
    }

    public record ErrorApi(
            String codigo,
            int estadoHttp,
            List<ErrorDetalle> detalles,
            String trazaId
    ) {
    }
}
