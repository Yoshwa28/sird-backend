package pe.gob.regioncusco.sird.administracion.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import pe.gob.regioncusco.sird.administracion.dto.ActualizarLimiteEspacioColaborativoRequest;
import pe.gob.regioncusco.sird.administracion.dto.LimiteEspacioColaborativoResponse;
import pe.gob.regioncusco.sird.administracion.entity.ConfiguracionLimiteEspacioColaborativo;
import pe.gob.regioncusco.sird.administracion.entity.TipoEspacioColaborativo;
import pe.gob.regioncusco.sird.administracion.repository.ConfiguracionLimiteEspacioColaborativoRepository;

import java.util.List;

@Service
public class ConfiguracionLimiteEspacioColaborativoServiceImpl
        implements ConfiguracionLimiteEspacioColaborativoService {

    private final ConfiguracionLimiteEspacioColaborativoRepository repository;

    public ConfiguracionLimiteEspacioColaborativoServiceImpl(
            ConfiguracionLimiteEspacioColaborativoRepository repository
    ) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<LimiteEspacioColaborativoResponse> listar() {
        return repository.findAll()
                .stream()
                .map(this::mapear)
                .toList();
    }

    @Override
    @Transactional
    public LimiteEspacioColaborativoResponse actualizar(
            TipoEspacioColaborativo tipoEspacio,
            ActualizarLimiteEspacioColaborativoRequest request
    ) {
        ConfiguracionLimiteEspacioColaborativo configuracion = repository.findByTipoEspacio(tipoEspacio)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No existe configuración para el tipo de Espacio Colaborativo solicitado."
                ));

        configuracion.setLimiteGb(request.getLimiteGb());

        return mapear(repository.save(configuracion));
    }

    private LimiteEspacioColaborativoResponse mapear(ConfiguracionLimiteEspacioColaborativo entity) {
        return new LimiteEspacioColaborativoResponse(
                entity.getId(),
                entity.getTipoEspacio(),
                entity.getLimiteGb(),
                entity.getActivo(),
                entity.getCreadoEn(),
                entity.getActualizadoEn()
        );
    }
}
