package pe.gob.regioncusco.sird.administracion.dto;

import pe.gob.regioncusco.sird.administracion.entity.PerfilUsuario;

import java.time.OffsetDateTime;

public class UsuarioSirdResponse {

    private Long id;
    private String nombres;
    private String apellidos;
    private String correo;
    private String cargo;
    private String unidad;
    private PerfilUsuario perfil;
    private Boolean activo;
    private OffsetDateTime creadoEn;
    private OffsetDateTime actualizadoEn;

    public UsuarioSirdResponse(
            Long id,
            String nombres,
            String apellidos,
            String correo,
            String cargo,
            String unidad,
            PerfilUsuario perfil,
            Boolean activo,
            OffsetDateTime creadoEn,
            OffsetDateTime actualizadoEn
    ) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.cargo = cargo;
        this.unidad = unidad;
        this.perfil = perfil;
        this.activo = activo;
        this.creadoEn = creadoEn;
        this.actualizadoEn = actualizadoEn;
    }

    public Long getId() {
        return id;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public String getCargo() {
        return cargo;
    }

    public String getUnidad() {
        return unidad;
    }

    public PerfilUsuario getPerfil() {
        return perfil;
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
