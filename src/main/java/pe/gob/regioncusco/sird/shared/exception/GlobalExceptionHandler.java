package pe.gob.regioncusco.sird.shared.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import pe.gob.regioncusco.sird.shared.api.ErrorDetalle;
import pe.gob.regioncusco.sird.shared.api.RespuestaErrorApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RespuestaErrorApi> manejarValidacionBody(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        List<ErrorDetalle> detalles = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new ErrorDetalle(error.getField(), error.getDefaultMessage()))
                .toList();

        return ResponseEntity.badRequest().body(
                RespuestaErrorApi.crear(
                        "Error de validación.",
                        "ERROR_VALIDACION",
                        400,
                        detalles,
                        obtenerTrazaId()
                )
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<RespuestaErrorApi> manejarConstraintViolation(
            ConstraintViolationException ex
    ) {
        List<ErrorDetalle> detalles = ex.getConstraintViolations()
                .stream()
                .map(error -> new ErrorDetalle(
                        error.getPropertyPath().toString(),
                        error.getMessage()
                ))
                .toList();

        return ResponseEntity.badRequest().body(
                RespuestaErrorApi.crear(
                        "Error de validación.",
                        "ERROR_VALIDACION",
                        400,
                        detalles,
                        obtenerTrazaId()
                )
        );
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<RespuestaErrorApi> manejarResponseStatusException(
            ResponseStatusException ex
    ) {
        int estado = ex.getStatusCode().value();

        return ResponseEntity.status(ex.getStatusCode()).body(
                RespuestaErrorApi.crear(
                        ex.getReason() != null ? ex.getReason() : "No se pudo completar la operación.",
                        codigoPorEstado(estado),
                        estado,
                        List.of(),
                        obtenerTrazaId()
                )
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RespuestaErrorApi> manejarErrorGeneral(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                RespuestaErrorApi.crear(
                        "Error interno del servidor.",
                        "ERROR_INTERNO",
                        500,
                        List.of(new ErrorDetalle(null, ex.getMessage())),
                        obtenerTrazaId()
                )
        );
    }

    private String obtenerTrazaId() {
        return UUID.randomUUID().toString();
    }

    private String codigoPorEstado(int estado) {
        return switch (estado) {
            case 400 -> "ERROR_VALIDACION";
            case 401 -> "NO_AUTENTICADO";
            case 403 -> "ACCESO_DENEGADO";
            case 404 -> "RECURSO_NO_ENCONTRADO";
            case 409 -> "ERROR_REGLA_NEGOCIO";
            default -> "ERROR_INTERNO";
        };
    }
}
