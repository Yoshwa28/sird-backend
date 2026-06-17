package pe.gob.regioncusco.sird.administracion.dto;

import pe.gob.regioncusco.sird.administracion.entity.TipoEspacioColaborativo;

import java.time.OffsetDateTime;

public class LimiteEspacioColaborativoResponse {

    private Long id;
    private TipoEspacioColaborativo tipoEspacio;
    private Integer limiteGb;
    private Boolean activo;
    private OffsetDateTime creadoEn;
    private OffsetDateTime actualizadoEn;

    public LimiteEspacioColaborativoResponse(
            Long id,
            TipoEspacioColaborativo tipoEspacio,
            Integer limiteGb,
            Boolean activo,
            OffsetDateTime creadoEn,
            OffsetDateTime actualizadoEn
    ) {
        this.id = id;
        this.tipoEspacio = tipoEspacio;
        this.limiteGb = limiteGb;
        this.activo = activo;
        this.creadoEn = creadoEn;
        this.actualizadoEn = actualizadoEn;
    }

    public Long getId() {
        return id;
    }

    public TipoEspacioColaborativo getTipoEspacio() {
        return tipoEspacio;
    }

    public Integer getLimiteGb() {
        return limiteGb;
    }

    public Boolean getActivo() {
        return activo;
    }

    public OffsetDateTime getCreadoEn() {
        return creadoEn;
    }

    public OffsetDateTime getActualizadoEn() {
        return actualizadoEn;
    }
}
