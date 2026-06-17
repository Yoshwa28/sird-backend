package pe.gob.regioncusco.sird.administracion.entity;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "configuracion_seguridad_jwt", schema = "sird")
public class ConfiguracionSeguridadJwt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "access_token_horas", nullable = false)
    private Integer accessTokenHoras;

    @Column(name = "refresh_token_dias", nullable = false)
    private Integer refreshTokenDias;

    @Column(name = "intentos_fallidos_max", nullable = false)
    private Integer intentosFallidosMax;

    @Column(name = "bloqueo_minutos", nullable = false)
    private Integer bloqueoMinutos;

    @Column(name = "version_seguridad", nullable = false)
    private Long versionSeguridad = 1L;

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
        if (versionSeguridad == null) {
            versionSeguridad = 1L;
        }
    }

    @PreUpdate
    public void preUpdate() {
        actualizadoEn = OffsetDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Integer getAccessTokenHoras() {
        return accessTokenHoras;
    }

    public void setAccessTokenHoras(Integer accessTokenHoras) {
        this.accessTokenHoras = accessTokenHoras;
    }

    public Integer getRefreshTokenDias() {
        return refreshTokenDias;
    }

    public void setRefreshTokenDias(Integer refreshTokenDias) {
        this.refreshTokenDias = refreshTokenDias;
    }

    public Integer getIntentosFallidosMax() {
        return intentosFallidosMax;
    }

    public void setIntentosFallidosMax(Integer intentosFallidosMax) {
        this.intentosFallidosMax = intentosFallidosMax;
    }

    public Integer getBloqueoMinutos() {
        return bloqueoMinutos;
    }

    public void setBloqueoMinutos(Integer bloqueoMinutos) {
        this.bloqueoMinutos = bloqueoMinutos;
    }

    public Long getVersionSeguridad() {
        return versionSeguridad;
    }

    public void incrementarVersionSeguridad() {
        this.versionSeguridad = this.versionSeguridad == null ? 1L : this.versionSeguridad + 1;
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
