package pe.gob.regioncusco.sird.administracion.service;

import pe.gob.regioncusco.sird.administracion.dto.ActualizarConfiguracionDocumentalRequest;
import pe.gob.regioncusco.sird.administracion.dto.ConfiguracionDocumentalResponse;
import pe.gob.regioncusco.sird.administracion.entity.ClasificacionDocumento;
import pe.gob.regioncusco.sird.administracion.entity.ConfiguracionDocumental;
import pe.gob.regioncusco.sird.administracion.repository.ConfiguracionDocumentalRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ConfiguracionDocumentalServiceImpl implements ConfiguracionDocumentalService {

    private final ConfiguracionDocumentalRepository repository;

    public ConfiguracionDocumentalServiceImpl(ConfiguracionDocumentalRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public ConfiguracionDocumentalResponse obtenerConfiguracionActiva() {
        ConfiguracionDocumental configuracion = obtenerEntidadActiva();
        return convertirAResponse(configuracion);
    }

    @Override
    @Transactional
    public ConfiguracionDocumentalResponse actualizarConfiguracion(
            ActualizarConfiguracionDocumentalRequest request
    ) {
        validarClasificacionPorDefecto(request.clasificacionPorDefecto());

        ConfiguracionDocumental configuracion = obtenerEntidadActiva();
        configuracion.setPeriodoRetencionAnios(request.periodoRetencionAnios());
        configuracion.setClasificacionPorDefecto(request.clasificacionPorDefecto());

        ConfiguracionDocumental guardada = repository.save(configuracion);

        return convertirAResponse(guardada);
    }

    private ConfiguracionDocumental obtenerEntidadActiva() {
        return repository.findByActivoTrue()
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No existe configuración documental activa."
                ));
    }

    private void validarClasificacionPorDefecto(ClasificacionDocumento clasificacion) {
        if (clasificacion == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La clasificación por defecto es obligatoria."
            );
        }

        if (!clasificacion.puedeSerDefault()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Solo PUBLICO o INTERNO pueden configurarse como clasificación por defecto."
            );
        }
    }

    private ConfiguracionDocumentalResponse convertirAResponse(ConfiguracionDocumental configuracion) {
        return new ConfiguracionDocumentalResponse(
                configuracion.getId(),
                configuracion.getPeriodoRetencionAnios(),
                configuracion.getClasificacionPorDefecto().name(),
                configuracion.getActivo(),
                configuracion.getCreadoEn(),
                configuracion.getActualizadoEn()
        );
    }
}
