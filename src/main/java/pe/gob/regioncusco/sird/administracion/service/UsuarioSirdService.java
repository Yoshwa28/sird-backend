package pe.gob.regioncusco.sird.administracion.service;

import pe.gob.regioncusco.sird.administracion.dto.*;
import pe.gob.regioncusco.sird.administracion.entity.PerfilUsuario;

import java.util.List;

public interface UsuarioSirdService {

    List<UsuarioSirdResponse> listar(Boolean soloActivos);

    UsuarioSirdResponse obtenerPorId(Long usuarioId);

    UsuarioSirdResponse crear(CrearUsuarioSirdRequest request);

    UsuarioSirdResponse actualizar(Long usuarioId, ActualizarUsuarioSirdRequest request);

    UsuarioSirdResponse cambiarEstado(Long usuarioId, CambiarEstadoUsuarioRequest request);

    List<PerfilUsuario> listarPerfiles();
}
