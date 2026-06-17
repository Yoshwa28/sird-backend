package pe.gob.regioncusco.sird.administracion.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import pe.gob.regioncusco.sird.administracion.dto.*;
import pe.gob.regioncusco.sird.administracion.entity.PerfilUsuario;
import pe.gob.regioncusco.sird.administracion.service.UsuarioSirdService;
import pe.gob.regioncusco.sird.shared.api.RespuestaApi;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@Tag(name = "M09 - Administración del Sistema", description = "Gestión de usuarios y perfiles RBAC")
public class UsuarioSirdController {

    private final UsuarioSirdService service;

    public UsuarioSirdController(UsuarioSirdService service) {
        this.service = service;
    }

    @GetMapping("/usuarios")
    @Operation(summary = "Listar usuarios SIRD")
    public RespuestaApi<List<UsuarioSirdResponse>> listar(
            @RequestParam(name = "soloActivos", required = false) Boolean soloActivos
    ) {
        return RespuestaApi.ok(
                "Listado de usuarios obtenido correctamente.",
                service.listar(soloActivos)
        );
    }

    @GetMapping("/usuarios/{usuarioId}")
    @Operation(summary = "Obtener usuario SIRD por ID")
    public RespuestaApi<UsuarioSirdResponse> obtenerPorId(@PathVariable Long usuarioId) {
        return RespuestaApi.ok(
                "Usuario obtenido correctamente.",
                service.obtenerPorId(usuarioId)
        );
    }

    @PostMapping("/usuarios")
    @Operation(summary = "Crear usuario SIRD")
    public RespuestaApi<UsuarioSirdResponse> crear(
            @Valid @RequestBody CrearUsuarioSirdRequest request
    ) {
        return RespuestaApi.ok(
                "Usuario SIRD creado correctamente.",
                service.crear(request)
        );
    }

    @PutMapping("/usuarios/{usuarioId}")
    @Operation(summary = "Actualizar usuario SIRD")
    public RespuestaApi<UsuarioSirdResponse> actualizar(
            @PathVariable Long usuarioId,
            @Valid @RequestBody ActualizarUsuarioSirdRequest request
    ) {
        return RespuestaApi.ok(
                "Usuario SIRD actualizado correctamente.",
                service.actualizar(usuarioId, request)
        );
    }

    @PatchMapping("/usuarios/{usuarioId}/estado")
    @Operation(summary = "Activar o desactivar usuario SIRD")
    public RespuestaApi<UsuarioSirdResponse> cambiarEstado(
            @PathVariable Long usuarioId,
            @Valid @RequestBody CambiarEstadoUsuarioRequest request
    ) {
        return RespuestaApi.ok(
                "Estado del usuario SIRD actualizado correctamente.",
                service.cambiarEstado(usuarioId, request)
        );
    }

    @GetMapping("/perfiles")
    @Operation(summary = "Listar perfiles RBAC disponibles")
    public RespuestaApi<List<PerfilUsuario>> listarPerfiles() {
        return RespuestaApi.ok(
                "Listado de perfiles RBAC obtenido correctamente.",
                service.listarPerfiles()
        );
    }
}
