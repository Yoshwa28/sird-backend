package pe.gob.regioncusco.sird.administracion.service;

import pe.gob.regioncusco.sird.administracion.dto.ActualizarConfiguracionSeguridadJwtRequest;
import pe.gob.regioncusco.sird.administracion.dto.ConfiguracionSeguridadJwtResponse;
import pe.gob.regioncusco.sird.administracion.entity.ConfiguracionSeguridadJwt;
import pe.gob.regioncusco.sird.administracion.repository.ConfiguracionSeguridadJwtRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ConfiguracionSeguridadJwtServiceImpl implements ConfiguracionSeguridadJwtService {

    private final ConfiguracionSeguridadJwtRepository repository;

    public ConfiguracionSeguridadJwtServiceImpl(ConfiguracionSeguridadJwtRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public ConfiguracionSeguridadJwtResponse obtenerConfiguracionActiva() {
        return convertirAResponse(obtenerEntidadActiva());
    }

    @Override
    @Transactional
    public ConfiguracionSeguridadJwtResponse actualizarConfiguracion(
            ActualizarConfiguracionSeguridadJwtRequest request
    ) {
        ConfiguracionSeguridadJwt configuracion = obtenerEntidadActiva();

        configuracion.setAccessTokenHoras(request.accessTokenHoras());
        configuracion.setRefreshTokenDias(request.refreshTokenDias());
        configuracion.setIntentosFallidosMax(request.intentosFallidosMax());
        configuracion.setBloqueoMinutos(request.bloqueoMinutos());

        // Base para invalidación futura de sesiones:
        // cada cambio aumenta la versión de seguridad.
        configuracion.incrementarVersionSeguridad();

        ConfiguracionSeguridadJwt guardada = repository.save(configuracion);
        return convertirAResponse(guardada);
    }

    private ConfiguracionSeguridadJwt obtenerEntidadActiva() {
        return repository.findByActivoTrue()
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No existe configuración JWT activa."
                ));
    }

    private ConfiguracionSeguridadJwtResponse convertirAResponse(ConfiguracionSeguridadJwt configuracion) {
        return new ConfiguracionSeguridadJwtResponse(
                configuracion.getId(),
                configuracion.getAccessTokenHoras(),
                configuracion.getRefreshTokenDias(),
                configuracion.getIntentosFallidosMax(),
                configuracion.getBloqueoMinutos(),
                configuracion.getVersionSeguridad(),
                configuracion.getActivo(),
                configuracion.getCreadoEn(),
                configuracion.getActualizadoEn()
        );
    }
}
