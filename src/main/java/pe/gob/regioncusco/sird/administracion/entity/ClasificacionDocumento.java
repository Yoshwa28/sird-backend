package pe.gob.regioncusco.sird.administracion.entity;

public enum ClasificacionDocumento {
    PUBLICO,
    INTERNO,
    CONFIDENCIAL,
    RESTRINGIDO;

    public boolean puedeSerDefault() {
        return this == PUBLICO || this == INTERNO;
    }
}
