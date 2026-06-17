package pe.gob.regioncusco.sird.administracion.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ActualizarLimiteEspacioColaborativoRequest {

    @NotNull(message = "El límite en GB es obligatorio.")
    @Min(value = 1, message = "El límite del Espacio Colaborativo debe ser como mínimo 1 GB.")
    @Max(value = 100, message = "El límite del Espacio Colaborativo no debe superar 100 GB.")
    private Integer limiteGb;

    public Integer getLimiteGb() {
        return limiteGb;
    }

    public void setLimiteGb(Integer limiteGb) {
        this.limiteGb = limiteGb;
    }
}
