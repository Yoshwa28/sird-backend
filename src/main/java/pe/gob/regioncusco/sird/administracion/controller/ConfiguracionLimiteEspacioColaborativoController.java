package pe.gob.regioncusco.sird.administracion.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import pe.gob.regioncusco.sird.administracion.dto.ActualizarLimiteEspacioColaborativoRequest;
import pe.gob.regioncusco.sird.administracion.dto.LimiteEspacioColaborativoResponse;
import pe.gob.regioncusco.sird.administracion.entity.TipoEspacioColaborativo;
import pe.gob.regioncusco.sird.administracion.service.ConfiguracionLimiteEspacioColaborativoService;
import pe.gob.regioncusco.sird.shared.api.RespuestaApi;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/configuracion/limites-ec")
@Tag(name = "M09 - Administración del Sistema", description = "Configuración de límites del Espacio Colaborativo")
public class ConfiguracionLimiteEspacioColaborativoController {

    private final ConfiguracionLimiteEspacioColaborativoService service;

    public ConfiguracionLimiteEspacioColaborativoController(
            ConfiguracionLimiteEspacioColaborativoService service
    ) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar límites del Espacio Colaborativo")
    public RespuestaApi<List<LimiteEspacioColaborativoResponse>> listar() {
        return RespuestaApi.ok(
                "Límites del Espacio Colaborativo obtenidos correctamente.",
                service.listar()
        );
    }

    @PutMapping("/{tipoEspacio}")
    @Operation(summary = "Actualizar límite por tipo de Espacio Colaborativo")
    public RespuestaApi<LimiteEspacioColaborativoResponse> actualizar(
            @PathVariable TipoEspacioColaborativo tipoEspacio,
            @Valid @RequestBody ActualizarLimiteEspacioColaborativoRequest request
    ) {
        return RespuestaApi.ok(
                "Límite del Espacio Colaborativo actualizado correctamente.",
                service.actualizar(tipoEspacio, request)
        );
    }
}
