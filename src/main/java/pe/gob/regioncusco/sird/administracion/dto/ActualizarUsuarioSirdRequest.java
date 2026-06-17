package pe.gob.regioncusco.sird.administracion.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import pe.gob.regioncusco.sird.administracion.entity.PerfilUsuario;

public class ActualizarUsuarioSirdRequest {

    @NotBlank(message = "Los nombres son obligatorios.")
    @Size(min = 2, max = 100, message = "Los nombres deben tener entre 2 y 100 caracteres.")
    private String nombres;

    @NotBlank(message = "Los apellidos son obligatorios.")
    @Size(min = 2, max = 100, message = "Los apellidos deben tener entre 2 y 100 caracteres.")
    private String apellidos;

    @NotBlank(message = "El correo es obligatorio.")
    @Email(message = "El correo debe tener un formato válido.")
    @Size(max = 150, message = "El correo no debe superar 150 caracteres.")
    private String correo;

    @NotBlank(message = "El cargo es obligatorio.")
    @Size(min = 2, max = 120, message = "El cargo debe tener entre 2 y 120 caracteres.")
    private String cargo;

    @NotBlank(message = "La unidad es obligatoria.")
    @Size(min = 2, max = 150, message = "La unidad debe tener entre 2 y 150 caracteres.")
    private String unidad;

    @NotNull(message = "El perfil es obligatorio.")
    private PerfilUsuario perfil;

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

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public void setPerfil(PerfilUsuario perfil) {
        this.perfil = perfil;
    }
}
