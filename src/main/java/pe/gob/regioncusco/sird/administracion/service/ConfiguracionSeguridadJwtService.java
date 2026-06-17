package pe.gob.regioncusco.sird.administracion.service;

import pe.gob.regioncusco.sird.administracion.dto.ActualizarConfiguracionSeguridadJwtRequest;
import pe.gob.regioncusco.sird.administracion.dto.ConfiguracionSeguridadJwtResponse;

public interface ConfiguracionSeguridadJwtService {

    ConfiguracionSeguridadJwtResponse obtenerConfiguracionActiva();

    ConfiguracionSeguridadJwtResponse actualizarConfiguracion(
            ActualizarConfiguracionSeguridadJwtRequest request
    );
}
