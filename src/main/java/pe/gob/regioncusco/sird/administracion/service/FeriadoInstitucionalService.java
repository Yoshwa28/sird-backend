package pe.gob.regioncusco.sird.administracion.service;

import pe.gob.regioncusco.sird.administracion.dto.ActualizarFeriadoInstitucionalRequest;
import pe.gob.regioncusco.sird.administracion.dto.CambiarEstadoFeriadoRequest;
import pe.gob.regioncusco.sird.administracion.dto.CrearFeriadoInstitucionalRequest;
import pe.gob.regioncusco.sird.administracion.dto.FeriadoInstitucionalResponse;

import java.util.List;

public interface FeriadoInstitucionalService {

    List<FeriadoInstitucionalResponse> listar(Boolean soloActivos);

    FeriadoInstitucionalResponse crear(CrearFeriadoInstitucionalRequest request);

    FeriadoInstitucionalResponse actualizar(Long feriadoId, ActualizarFeriadoInstitucionalRequest request);

    FeriadoInstitucionalResponse cambiarEstado(Long feriadoId, CambiarEstadoFeriadoRequest request);
}
