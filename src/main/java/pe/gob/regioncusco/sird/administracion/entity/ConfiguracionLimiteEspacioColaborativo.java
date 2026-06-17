package pe.gob.regioncusco.sird.administracion.entity;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "configuracion_limite_espacio_colaborativo", schema = "sird")
public class ConfiguracionLimiteEspacioColaborativo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_espacio", nullable = false, unique = true, length = 30)
    private TipoEspacioColaborativo tipoEspacio;

    @Column(name = "limite_gb", nullable = false)
    private Integer limiteGb;

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;

    @Column(name = "creado_en", nullable = false)
    private OffsetDateTime creadoEn;

    @Column(name = "actualizado_en")
    private OffsetDateTime actualizadoEn;

    @PrePersist
    public void prePersist() {
        if (creadoEn == null) {
            creadoEn = OffsetDateTime.now();
        }
        if (activo == null) {
            activo = true;
        }
    }

    @PreUpdate
    public void preUpdate() {
        actualizadoEn = OffsetDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public TipoEspacioColaborativo getTipoEspacio() {
        return tipoEspacio;
    }

    public void setTipoEspacio(TipoEspacioColaborativo tipoEspacio) {
        this.tipoEspacio = tipoEspacio;
    }

    public Integer getLimiteGb() {
        return limiteGb;
    }

    public void setLimiteGb(Integer limiteGb) {
        this.limiteGb = limiteGb;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public OffsetDateTime getCreadoEn() {
        return creadoEn;
    }

    public OffsetDateTime getActualizadoEn() {
        return actualizadoEn;
    }
}
