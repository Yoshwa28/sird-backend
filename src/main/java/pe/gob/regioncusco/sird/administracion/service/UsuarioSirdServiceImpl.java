package pe.gob.regioncusco.sird.administracion.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import pe.gob.regioncusco.sird.administracion.dto.*;
import pe.gob.regioncusco.sird.administracion.entity.PerfilUsuario;
import pe.gob.regioncusco.sird.administracion.entity.UsuarioSird;
import pe.gob.regioncusco.sird.administracion.repository.UsuarioSirdRepository;

import java.util.Arrays;
import java.util.List;

@Service
public class UsuarioSirdServiceImpl implements UsuarioSirdService {

    private final UsuarioSirdRepository repository;

    public UsuarioSirdServiceImpl(UsuarioSirdRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioSirdResponse> listar(Boolean soloActivos) {
        List<UsuarioSird> usuarios;

        if (Boolean.TRUE.equals(soloActivos)) {
            usuarios = repository.findByActivoTrue();
        } else {
            usuarios = repository.findAll();
        }

        return usuarios.stream().map(this::mapear).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioSirdResponse obtenerPorId(Long usuarioId) {
        return mapear(buscarUsuario(usuarioId));
    }

    @Override
    @Transactional
    public UsuarioSirdResponse crear(CrearUsuarioSirdRequest request) {
        String correoNormalizado = normalizarCorreo(request.getCorreo());

        if (repository.existsByCorreo(correoNormalizado)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Ya existe un usuario registrado con el correo indicado."
            );
        }

        UsuarioSird usuario = new UsuarioSird();
        usuario.setNombres(request.getNombres().trim());
        usuario.setApellidos(request.getApellidos().trim());
        usuario.setCorreo(correoNormalizado);
        usuario.setCargo(request.getCargo().trim());
        usuario.setUnidad(request.getUnidad().trim());
        usuario.setPerfil(request.getPerfil());
        usuario.setActivo(true);

        return mapear(repository.save(usuario));
    }

    @Override
    @Transactional
    public UsuarioSirdResponse actualizar(Long usuarioId, ActualizarUsuarioSirdRequest request) {
        UsuarioSird usuario = buscarUsuario(usuarioId);
        String correoNormalizado = normalizarCorreo(request.getCorreo());

        repository.findByCorreo(correoNormalizado)
                .filter(existente -> !existente.getId().equals(usuarioId))
                .ifPresent(existente -> {
                    throw new ResponseStatusException(
                            HttpStatus.CONFLICT,
                            "Ya existe otro usuario registrado con el correo indicado."
                    );
                });

        usuario.setNombres(request.getNombres().trim());
        usuario.setApellidos(request.getApellidos().trim());
        usuario.setCorreo(correoNormalizado);
        usuario.setCargo(request.getCargo().trim());
        usuario.setUnidad(request.getUnidad().trim());
        usuario.setPerfil(request.getPerfil());

        return mapear(repository.save(usuario));
    }

    @Override
    @Transactional
    public UsuarioSirdResponse cambiarEstado(Long usuarioId, CambiarEstadoUsuarioRequest request) {
        UsuarioSird usuario = buscarUsuario(usuarioId);
        usuario.setActivo(request.getActivo());
        return mapear(repository.save(usuario));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PerfilUsuario> listarPerfiles() {
        return Arrays.asList(PerfilUsuario.values());
    }

    private UsuarioSird buscarUsuario(Long usuarioId) {
        return repository.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No existe el usuario solicitado."
                ));
    }

    private String normalizarCorreo(String correo) {
        return correo == null ? null : correo.trim().toLowerCase();
    }

    private UsuarioSirdResponse mapear(UsuarioSird usuario) {
        return new UsuarioSirdResponse(
                usuario.getId(),
                usuario.getNombres(),
                usuario.getApellidos(),
                usuario.getCorreo(),
                usuario.getCargo(),
                usuario.getUnidad(),
                usuario.getPerfil(),
                usuario.getActivo(),
                usuario.getCreadoEn(),
                usuario.getActualizadoEn()
        );
    }
}
