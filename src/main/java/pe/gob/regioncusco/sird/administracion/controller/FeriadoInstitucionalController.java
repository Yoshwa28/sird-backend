package pe.gob.regioncusco.sird.administracion.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import pe.gob.regioncusco.sird.administracion.dto.ActualizarFeriadoInstitucionalRequest;
import pe.gob.regioncusco.sird.administracion.dto.CambiarEstadoFeriadoRequest;
import pe.gob.regioncusco.sird.administracion.dto.CrearFeriadoInstitucionalRequest;
import pe.gob.regioncusco.sird.administracion.dto.FeriadoInstitucionalResponse;
import pe.gob.regioncusco.sird.administracion.service.FeriadoInstitucionalService;
import pe.gob.regioncusco.sird.shared.api.RespuestaApi;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/feriados")
@Tag(name = "M09 - Administración del Sistema", description = "Gestión de feriados institucionales")
public class FeriadoInstitucionalController {

    private final FeriadoInstitucionalService service;

    public FeriadoInstitucionalController(FeriadoInstitucionalService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(
            summary = "Listar feriados institucionales",
            description = "Lista los feriados institucionales usados para el cálculo de días hábiles."
    )
    public RespuestaApi<List<FeriadoInstitucionalResponse>> listar(
            @RequestParam(defaultValue = "false") Boolean soloActivos
    ) {
        return RespuestaApi.ok(
                "Listado de feriados obtenido correctamente.",
                service.listar(soloActivos)
        );
    }

    @PostMapping
    @Operation(
            summary = "Crear feriado institucional",
            description = "Permite registrar un feriado nacional o regional del Cusco."
    )
    public RespuestaApi<FeriadoInstitucionalResponse> crear(
            @Valid @RequestBody CrearFeriadoInstitucionalRequest request
    ) {
        return RespuestaApi.ok(
                "Feriado institucional creado correctamente.",
                service.crear(request)
        );
    }

    @PutMapping("/{feriadoId}")
    @Operation(
            summary = "Actualizar feriado institucional",
            description = "Actualiza los datos de un feriado institucional existente."
    )
    public RespuestaApi<FeriadoInstitucionalResponse> actualizar(
            @PathVariable Long feriadoId,
            @Valid @RequestBody ActualizarFeriadoInstitucionalRequest request
    ) {
        return RespuestaApi.ok(
                "Feriado institucional actualizado correctamente.",
                service.actualizar(feriadoId, request)
        );
    }

    @PatchMapping("/{feriadoId}/estado")
    @Operation(
            summary = "Activar o desactivar feriado institucional",
            description = "Permite cambiar el estado activo/inactivo de un feriado."
    )
    public RespuestaApi<FeriadoInstitucionalResponse> cambiarEstado(
            @PathVariable Long feriadoId,
            @Valid @RequestBody CambiarEstadoFeriadoRequest request
    ) {
        return RespuestaApi.ok(
                "Estado del feriado institucional actualizado correctamente.",
                service.cambiarEstado(feriadoId, request)
        );
    }
}
