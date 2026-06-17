package pe.gob.regioncusco.sird.administracion.service;

import pe.gob.regioncusco.sird.administracion.dto.ActualizarLimiteEspacioColaborativoRequest;
import pe.gob.regioncusco.sird.administracion.dto.LimiteEspacioColaborativoResponse;
import pe.gob.regioncusco.sird.administracion.entity.TipoEspacioColaborativo;

import java.util.List;

public interface ConfiguracionLimiteEspacioColaborativoService {

    List<LimiteEspacioColaborativoResponse> listar();

    LimiteEspacioColaborativoResponse actualizar(
            TipoEspacioColaborativo tipoEspacio,
            ActualizarLimiteEspacioColaborativoRequest request
    );
}
