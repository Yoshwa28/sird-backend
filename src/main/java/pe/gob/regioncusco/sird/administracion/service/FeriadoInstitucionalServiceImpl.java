package pe.gob.regioncusco.sird.administracion.service;

import pe.gob.regioncusco.sird.administracion.dto.ActualizarFeriadoInstitucionalRequest;
import pe.gob.regioncusco.sird.administracion.dto.CambiarEstadoFeriadoRequest;
import pe.gob.regioncusco.sird.administracion.dto.CrearFeriadoInstitucionalRequest;
import pe.gob.regioncusco.sird.administracion.dto.FeriadoInstitucionalResponse;
import pe.gob.regioncusco.sird.administracion.entity.FeriadoInstitucional;
import pe.gob.regioncusco.sird.administracion.repository.FeriadoInstitucionalRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class FeriadoInstitucionalServiceImpl implements FeriadoInstitucionalService {

    private final FeriadoInstitucionalRepository repository;

    public FeriadoInstitucionalServiceImpl(FeriadoInstitucionalRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<FeriadoInstitucionalResponse> listar(Boolean soloActivos) {
        List<FeriadoInstitucional> feriados = Boolean.TRUE.equals(soloActivos)
                ? repository.findByActivoTrueOrderByFechaAsc()
                : repository.findAllByOrderByFechaAsc();

        return feriados.stream()
                .map(this::convertirAResponse)
                .toList();
    }

    @Override
    @Transactional
    public FeriadoInstitucionalResponse crear(CrearFeriadoInstitucionalRequest request) {
        validarDuplicado(request.fecha(), request.tipo());

        FeriadoInstitucional feriado = new FeriadoInstitucional();
        feriado.setNombre(request.nombre().trim());
        feriado.setFecha(request.fecha());
        feriado.setTipo(request.tipo());
        feriado.setRecurrenteAnual(Boolean.TRUE.equals(request.recurrenteAnual()));

        FeriadoInstitucional guardado = repository.save(feriado);
        return convertirAResponse(guardado);
    }

    @Override
    @Transactional
    public FeriadoInstitucionalResponse actualizar(Long feriadoId, ActualizarFeriadoInstitucionalRequest request) {
        FeriadoInstitucional feriado = obtenerPorId(feriadoId);

        repository.findByFechaAndTipo(request.fecha(), request.tipo())
                .ifPresent(existente -> {
                    if (!existente.getId().equals(feriadoId)) {
                        throw new ResponseStatusException(
                                HttpStatus.CONFLICT,
                                "Ya existe un feriado registrado para la fecha y tipo indicados."
                        );
                    }
                });

        feriado.setNombre(request.nombre().trim());
        feriado.setFecha(request.fecha());
        feriado.setTipo(request.tipo());
        feriado.setRecurrenteAnual(Boolean.TRUE.equals(request.recurrenteAnual()));

        return convertirAResponse(repository.save(feriado));
    }

    @Override
    @Transactional
    public FeriadoInstitucionalResponse cambiarEstado(Long feriadoId, CambiarEstadoFeriadoRequest request) {
        FeriadoInstitucional feriado = obtenerPorId(feriadoId);
        feriado.setActivo(request.activo());
        return convertirAResponse(repository.save(feriado));
    }

    private FeriadoInstitucional obtenerPorId(Long feriadoId) {
        return repository.findById(feriadoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No existe el feriado institucional solicitado."
                ));
    }

    private void validarDuplicado(java.time.LocalDate fecha, pe.gob.regioncusco.sird.administracion.entity.TipoFeriado tipo) {
        if (repository.existsByFechaAndTipo(fecha, tipo)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Ya existe un feriado registrado para la fecha y tipo indicados."
            );
        }
    }

    private FeriadoInstitucionalResponse convertirAResponse(FeriadoInstitucional feriado) {
        return new FeriadoInstitucionalResponse(
                feriado.getId(),
                feriado.getNombre(),
                feriado.getFecha(),
                feriado.getTipo().name(),
                feriado.getRecurrenteAnual(),
                feriado.getActivo(),
                feriado.getCreadoEn(),
                feriado.getActualizadoEn()
        );
    }
}
