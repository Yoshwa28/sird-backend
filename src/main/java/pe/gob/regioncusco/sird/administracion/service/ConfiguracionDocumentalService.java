package pe.gob.regioncusco.sird.administracion.service;

import pe.gob.regioncusco.sird.administracion.dto.ActualizarConfiguracionDocumentalRequest;
import pe.gob.regioncusco.sird.administracion.dto.ConfiguracionDocumentalResponse;

public interface ConfiguracionDocumentalService {

    ConfiguracionDocumentalResponse obtenerConfiguracionActiva();

    ConfiguracionDocumentalResponse actualizarConfiguracion(
            ActualizarConfiguracionDocumentalRequest request
    );
}
