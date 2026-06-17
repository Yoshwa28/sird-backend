package pe.gob.regioncusco.sird.administracion.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import pe.gob.regioncusco.sird.administracion.dto.ActualizarConfiguracionDocumentalRequest;
import pe.gob.regioncusco.sird.administracion.dto.ConfiguracionDocumentalResponse;
import pe.gob.regioncusco.sird.administracion.service.ConfiguracionDocumentalService;
import pe.gob.regioncusco.sird.shared.api.RespuestaApi;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/configuracion/documental")
@Tag(name = "M09 - Administración del Sistema", description = "Configuración documental del SIRD")
public class ConfiguracionDocumentalController {

    private final ConfiguracionDocumentalService service;

    public ConfiguracionDocumentalController(ConfiguracionDocumentalService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(
            summary = "Obtener configuración documental activa",
            description = "Consulta el período de retención TRD y la clasificación documental por defecto."
    )
    public RespuestaApi<ConfiguracionDocumentalResponse> obtenerConfiguracion() {
        return RespuestaApi.ok(
                "Configuración documental obtenida correctamente.",
                service.obtenerConfiguracionActiva()
        );
    }

    @PutMapping
    @Operation(
            summary = "Actualizar configuración documental",
            description = "Permite al Admin SIRD actualizar el período de retención TRD y la clasificación por defecto."
    )
    public RespuestaApi<ConfiguracionDocumentalResponse> actualizarConfiguracion(
            @Valid @RequestBody ActualizarConfiguracionDocumentalRequest request
    ) {
        return RespuestaApi.ok(
                "Configuración documental actualizada correctamente.",
                service.actualizarConfiguracion(request)
        );
    }
}
