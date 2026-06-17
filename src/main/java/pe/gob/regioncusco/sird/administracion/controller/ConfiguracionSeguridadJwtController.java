package pe.gob.regioncusco.sird.administracion.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import pe.gob.regioncusco.sird.administracion.dto.ActualizarConfiguracionSeguridadJwtRequest;
import pe.gob.regioncusco.sird.administracion.dto.ConfiguracionSeguridadJwtResponse;
import pe.gob.regioncusco.sird.administracion.service.ConfiguracionSeguridadJwtService;
import pe.gob.regioncusco.sird.shared.api.RespuestaApi;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/configuracion/seguridad-jwt")
@Tag(name = "M09 - Administración del Sistema", description = "Configuración de seguridad JWT del SIRD")
public class ConfiguracionSeguridadJwtController {

    private final ConfiguracionSeguridadJwtService service;

    public ConfiguracionSeguridadJwtController(ConfiguracionSeguridadJwtService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(
            summary = "Obtener configuración JWT activa",
            description = "Consulta los parámetros de seguridad JWT configurados en el SIRD."
    )
    public RespuestaApi<ConfiguracionSeguridadJwtResponse> obtenerConfiguracion() {
        return RespuestaApi.ok(
                "Configuración JWT obtenida correctamente.",
                service.obtenerConfiguracionActiva()
        );
    }

    @PutMapping
    @Operation(
            summary = "Actualizar configuración JWT",
            description = "Actualiza parámetros JWT e incrementa la versión de seguridad para invalidación futura de sesiones."
    )
    public RespuestaApi<ConfiguracionSeguridadJwtResponse> actualizarConfiguracion(
            @Valid @RequestBody ActualizarConfiguracionSeguridadJwtRequest request
    ) {
        return RespuestaApi.ok(
                "Configuración JWT actualizada correctamente. Las sesiones activas serán invalidadas según la versión de seguridad.",
                service.actualizarConfiguracion(request)
        );
    }
}
