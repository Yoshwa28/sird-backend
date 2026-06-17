package pe.gob.regioncusco.sird.administracion.entity;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "configuracion_documental", schema = "sird")
public class ConfiguracionDocumental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "periodo_retencion_anios", nullable = false)
    private Integer periodoRetencionAnios;

    @Enumerated(EnumType.STRING)
    @Column(name = "clasificacion_por_defecto", nullable = false, length = 20)
    private ClasificacionDocumento clasificacionPorDefecto;

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

    public Integer getPeriodoRetencionAnios() {
        return periodoRetencionAnios;
    }

    public void setPeriodoRetencionAnios(Integer periodoRetencionAnios) {
        this.periodoRetencionAnios = periodoRetencionAnios;
    }

    public ClasificacionDocumento getClasificacionPorDefecto() {
        return clasificacionPorDefecto;
    }

    public void setClasificacionPorDefecto(ClasificacionDocumento clasificacionPorDefecto) {
        this.clasificacionPorDefecto = clasificacionPorDefecto;
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
