CREATE TABLE IF NOT EXISTS sird.configuracion_limite_espacio_colaborativo (
    id BIGSERIAL PRIMARY KEY,
    tipo_espacio VARCHAR(30) NOT NULL UNIQUE,
    limite_gb INTEGER NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    creado_en TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    actualizado_en TIMESTAMP WITH TIME ZONE NULL,
    CONSTRAINT chk_limite_ec_tipo CHECK (tipo_espacio IN ('EXPEDIENTE', 'PLAN', 'PERFIL')),
    CONSTRAINT chk_limite_ec_rango CHECK (limite_gb BETWEEN 1 AND 100)
);

INSERT INTO sird.configuracion_limite_espacio_colaborativo
    (tipo_espacio, limite_gb, activo, creado_en)
VALUES
    ('EXPEDIENTE', 10, TRUE, NOW()),
    ('PLAN', 20, TRUE, NOW()),
    ('PERFIL', 5, TRUE, NOW())
ON CONFLICT (tipo_espacio) DO NOTHING;

CREATE TABLE IF NOT EXISTS sird.usuario_sird (
    id BIGSERIAL PRIMARY KEY,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    correo VARCHAR(150) NOT NULL UNIQUE,
    cargo VARCHAR(120) NOT NULL,
    unidad VARCHAR(150) NOT NULL,
    perfil VARCHAR(60) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    creado_en TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    actualizado_en TIMESTAMP WITH TIME ZONE NULL,
    CONSTRAINT chk_usuario_sird_perfil CHECK (
        perfil IN (
            'ADMIN_SIRD',
            'GERENTE_SUBGERENTE',
            'JEFE_DEPENDENCIA_PROYECTO',
            'ESPECIALISTA_RESPONSABLE',
            'TECNICO_ADMINISTRATIVO'
        )
    )
);

CREATE INDEX IF NOT EXISTS idx_usuario_sird_correo ON sird.usuario_sird(correo);
CREATE INDEX IF NOT EXISTS idx_usuario_sird_perfil ON sird.usuario_sird(perfil);
CREATE INDEX IF NOT EXISTS idx_usuario_sird_activo ON sird.usuario_sird(activo);

INSERT INTO sird.usuario_sird
    (nombres, apellidos, correo, cargo, unidad, perfil, activo, creado_en)
VALUES
    (
        'Administrador',
        'SIRD',
        'admin.sird@regioncusco.gob.pe',
        'Administrador del Sistema',
        'Oficina de Tecnologías de la Información',
        'ADMIN_SIRD',
        TRUE,
        NOW()
    )
ON CONFLICT (correo) DO NOTHING;
