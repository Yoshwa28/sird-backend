CREATE TABLE IF NOT EXISTS sird.configuracion_seguridad_jwt (
    id BIGSERIAL PRIMARY KEY,
    access_token_horas INTEGER NOT NULL,
    refresh_token_dias INTEGER NOT NULL,
    intentos_fallidos_max INTEGER NOT NULL,
    bloqueo_minutos INTEGER NOT NULL,
    version_seguridad BIGINT NOT NULL DEFAULT 1,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    creado_en TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    actualizado_en TIMESTAMP WITH TIME ZONE
);

ALTER TABLE sird.configuracion_seguridad_jwt
ADD CONSTRAINT chk_jwt_access_token_horas
CHECK (access_token_horas BETWEEN 1 AND 24);

ALTER TABLE sird.configuracion_seguridad_jwt
ADD CONSTRAINT chk_jwt_refresh_token_dias
CHECK (refresh_token_dias BETWEEN 1 AND 90);

ALTER TABLE sird.configuracion_seguridad_jwt
ADD CONSTRAINT chk_jwt_intentos_fallidos
CHECK (intentos_fallidos_max BETWEEN 3 AND 10);

ALTER TABLE sird.configuracion_seguridad_jwt
ADD CONSTRAINT chk_jwt_bloqueo_minutos
CHECK (bloqueo_minutos BETWEEN 5 AND 60);

CREATE UNIQUE INDEX IF NOT EXISTS uq_configuracion_seguridad_jwt_activa
ON sird.configuracion_seguridad_jwt (activo)
WHERE activo = TRUE;

INSERT INTO sird.configuracion_seguridad_jwt (
    access_token_horas,
    refresh_token_dias,
    intentos_fallidos_max,
    bloqueo_minutos,
    version_seguridad,
    activo,
    creado_en
)
SELECT
    8,
    7,
    5,
    15,
    1,
    TRUE,
    NOW()
WHERE NOT EXISTS (
    SELECT 1
    FROM sird.configuracion_seguridad_jwt
    WHERE activo = TRUE
);

CREATE TABLE IF NOT EXISTS sird.feriado_institucional (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    fecha DATE NOT NULL,
    tipo VARCHAR(30) NOT NULL,
    recurrente_anual BOOLEAN NOT NULL DEFAULT FALSE,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    creado_en TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    actualizado_en TIMESTAMP WITH TIME ZONE
);

ALTER TABLE sird.feriado_institucional
ADD CONSTRAINT chk_feriado_tipo
CHECK (tipo IN ('NACIONAL', 'REGIONAL_CUSCO'));

CREATE UNIQUE INDEX IF NOT EXISTS uq_feriado_fecha_tipo
ON sird.feriado_institucional (fecha, tipo);

INSERT INTO sird.feriado_institucional (
    nombre,
    fecha,
    tipo,
    recurrente_anual,
    activo,
    creado_en
)
SELECT
    'Inti Raymi',
    DATE '2026-06-24',
    'REGIONAL_CUSCO',
    TRUE,
    TRUE,
    NOW()
WHERE NOT EXISTS (
    SELECT 1
    FROM sird.feriado_institucional
    WHERE nombre = 'Inti Raymi'
      AND EXTRACT(MONTH FROM fecha) = 6
      AND EXTRACT(DAY FROM fecha) = 24
      AND tipo = 'REGIONAL_CUSCO'
);
