CREATE SCHEMA IF NOT EXISTS sird;

CREATE TABLE IF NOT EXISTS sird.configuracion_documental (
    id BIGSERIAL PRIMARY KEY,
    periodo_retencion_anios INTEGER NOT NULL,
    clasificacion_por_defecto VARCHAR(20) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    creado_en TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    actualizado_en TIMESTAMP WITH TIME ZONE
);

ALTER TABLE sird.configuracion_documental
ADD CONSTRAINT chk_configuracion_documental_periodo_retencion
CHECK (periodo_retencion_anios BETWEEN 1 AND 50);

ALTER TABLE sird.configuracion_documental
ADD CONSTRAINT chk_configuracion_documental_clasificacion_default
CHECK (clasificacion_por_defecto IN ('PUBLICO', 'INTERNO'));

CREATE UNIQUE INDEX IF NOT EXISTS uq_configuracion_documental_activa
ON sird.configuracion_documental (activo)
WHERE activo = TRUE;

INSERT INTO sird.configuracion_documental (
    periodo_retencion_anios,
    clasificacion_por_defecto,
    activo,
    creado_en
)
SELECT
    5,
    'INTERNO',
    TRUE,
    NOW()
WHERE NOT EXISTS (
    SELECT 1
    FROM sird.configuracion_documental
    WHERE activo = TRUE
);
