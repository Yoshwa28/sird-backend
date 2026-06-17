# SIRD - Contrato API M09 Configuración JWT y Feriados

**Versión:** v1.0  
**Fecha:** 17/06/2026  
**Módulo:** M09 - Administración del Sistema  
**Historias relacionadas:** HU-003, HU-004  
**Base URL:** `http://localhost:8080`  
**Prefijo API:** `/api/v1`

---

## 1. Resumen

Este documento define los contratos API del módulo M09 para configuración de seguridad JWT y gestión de feriados institucionales.

Incluye:

- Consulta de configuración JWT activa.
- Actualización de parámetros de seguridad JWT.
- Listado de feriados institucionales.
- Creación de feriados institucionales.
- Actualización de feriados institucionales.
- Activación y desactivación de feriados institucionales.

---

## 2. Convención de respuesta

Todas las respuestas usan el envoltorio estándar:

```json
{
  "resultado": true,
  "mensaje": "Operación realizada correctamente.",
  "datos": {},
  "fechaHora": "2026-06-17T12:00:00-05:00"
}
```

---

## 3. HU-003 - Configuración JWT

### 3.1 Endpoint: Consultar configuración JWT

#### Método

```http
GET /api/v1/admin/configuracion/seguridad-jwt
```

#### Descripción

Permite consultar la configuración JWT activa del sistema.

#### Respuesta exitosa HTTP 200

```json
{
  "resultado": true,
  "mensaje": "Configuración JWT obtenida correctamente.",
  "datos": {
    "id": 1,
    "accessTokenHoras": 8,
    "refreshTokenDias": 7,
    "intentosFallidosMax": 5,
    "bloqueoMinutos": 15,
    "versionSeguridad": 1,
    "activo": true,
    "creadoEn": "2026-06-17T12:00:00-05:00",
    "actualizadoEn": null
  },
  "fechaHora": "2026-06-17T12:00:00-05:00"
}
```

---

### 3.2 Endpoint: Actualizar configuración JWT

#### Método

```http
PUT /api/v1/admin/configuracion/seguridad-jwt
```

#### Descripción

Permite actualizar los parámetros de seguridad JWT del sistema.

Al actualizar correctamente, el sistema incrementa `versionSeguridad` para preparar la invalidación futura de sesiones activas.

#### Request body

```json
{
  "accessTokenHoras": 12,
  "refreshTokenDias": 30,
  "intentosFallidosMax": 5,
  "bloqueoMinutos": 20
}
```

#### Validaciones

| Campo | Regla |
|---|---|
| `accessTokenHoras` | Obligatorio, mínimo 1, máximo 24 |
| `refreshTokenDias` | Obligatorio, mínimo 1, máximo 90 |
| `intentosFallidosMax` | Obligatorio, mínimo 3, máximo 10 |
| `bloqueoMinutos` | Obligatorio, mínimo 5, máximo 60 |

#### Respuesta exitosa HTTP 200

```json
{
  "resultado": true,
  "mensaje": "Configuración JWT actualizada correctamente. Las sesiones activas serán invalidadas según la versión de seguridad.",
  "datos": {
    "id": 1,
    "accessTokenHoras": 12,
    "refreshTokenDias": 30,
    "intentosFallidosMax": 5,
    "bloqueoMinutos": 20,
    "versionSeguridad": 2,
    "activo": true,
    "creadoEn": "2026-06-17T12:00:00-05:00",
    "actualizadoEn": "2026-06-17T12:10:00-05:00"
  },
  "fechaHora": "2026-06-17T12:10:00-05:00"
}
```

---

## 4. Errores esperados HU-003

### 4.1 Error por access token mayor a 24 horas

```json
{
  "resultado": false,
  "mensaje": "Error de validación.",
  "datos": null,
  "error": {
    "codigo": "ERROR_VALIDACION",
    "estadoHttp": 400,
    "detalles": [
      {
        "campo": "accessTokenHoras",
        "mensaje": "El access token no debe superar 24 horas."
      }
    ],
    "trazaId": "..."
  },
  "fechaHora": "..."
}
```

### 4.2 Error por refresh token mayor a 90 días

```json
{
  "resultado": false,
  "mensaje": "Error de validación.",
  "datos": null,
  "error": {
    "codigo": "ERROR_VALIDACION",
    "estadoHttp": 400,
    "detalles": [
      {
        "campo": "refreshTokenDias",
        "mensaje": "El refresh token no debe superar 90 días."
      }
    ],
    "trazaId": "..."
  },
  "fechaHora": "..."
}
```

### 4.3 Error por intentos fallidos fuera de rango

```json
{
  "resultado": false,
  "mensaje": "Error de validación.",
  "datos": null,
  "error": {
    "codigo": "ERROR_VALIDACION",
    "estadoHttp": 400,
    "detalles": [
      {
        "campo": "intentosFallidosMax",
        "mensaje": "Los intentos fallidos deben ser como mínimo 3."
      }
    ],
    "trazaId": "..."
  },
  "fechaHora": "..."
}
```

### 4.4 Error por bloqueo fuera de rango

```json
{
  "resultado": false,
  "mensaje": "Error de validación.",
  "datos": null,
  "error": {
    "codigo": "ERROR_VALIDACION",
    "estadoHttp": 400,
    "detalles": [
      {
        "campo": "bloqueoMinutos",
        "mensaje": "El bloqueo debe durar como mínimo 5 minutos."
      }
    ],
    "trazaId": "..."
  },
  "fechaHora": "..."
}
```

---

## 5. HU-004 - Gestión de feriados institucionales

### 5.1 Tipos de feriado permitidos

```text
NACIONAL
REGIONAL_CUSCO
```

### 5.2 Feriado obligatorio

El sistema registra por defecto:

| Nombre | Fecha | Tipo | Recurrente anual |
|---|---|---|---|
| Inti Raymi | 2026-06-24 | REGIONAL_CUSCO | true |

---

### 5.3 Endpoint: Listar feriados institucionales

#### Método

```http
GET /api/v1/admin/feriados
```

#### Descripción

Permite listar los feriados institucionales registrados en el sistema.

#### Query params

| Parámetro | Tipo | Obligatorio | Descripción |
|---|---|---|---|
| `soloActivos` | boolean | No | Si es `true`, lista solo feriados activos |

#### Ejemplo

```http
GET /api/v1/admin/feriados?soloActivos=true
```

#### Respuesta exitosa HTTP 200

```json
{
  "resultado": true,
  "mensaje": "Listado de feriados obtenido correctamente.",
  "datos": [
    {
      "id": 1,
      "nombre": "Inti Raymi",
      "fecha": "2026-06-24",
      "tipo": "REGIONAL_CUSCO",
      "recurrenteAnual": true,
      "activo": true,
      "creadoEn": "2026-06-17T12:00:00-05:00",
      "actualizadoEn": null
    }
  ],
  "fechaHora": "2026-06-17T12:00:00-05:00"
}
```

---

### 5.4 Endpoint: Crear feriado institucional

#### Método

```http
POST /api/v1/admin/feriados
```

#### Descripción

Permite registrar un feriado institucional nacional o regional del Cusco.

#### Request body

```json
{
  "nombre": "Día de prueba institucional",
  "fecha": "2026-07-15",
  "tipo": "NACIONAL",
  "recurrenteAnual": false
}
```

#### Validaciones

| Campo | Regla |
|---|---|
| `nombre` | Obligatorio, entre 3 y 150 caracteres |
| `fecha` | Obligatorio, formato `yyyy-MM-dd` |
| `tipo` | Obligatorio, solo `NACIONAL` o `REGIONAL_CUSCO` |
| `recurrenteAnual` | Opcional, por defecto `false` |

#### Respuesta exitosa HTTP 200

```json
{
  "resultado": true,
  "mensaje": "Feriado institucional creado correctamente.",
  "datos": {
    "id": 2,
    "nombre": "Día de prueba institucional",
    "fecha": "2026-07-15",
    "tipo": "NACIONAL",
    "recurrenteAnual": false,
    "activo": true,
    "creadoEn": "2026-06-17T12:15:00-05:00",
    "actualizadoEn": null
  },
  "fechaHora": "2026-06-17T12:15:00-05:00"
}
```

---

### 5.5 Endpoint: Actualizar feriado institucional

#### Método

```http
PUT /api/v1/admin/feriados/{feriadoId}
```

#### Descripción

Permite actualizar los datos de un feriado institucional existente.

#### Request body

```json
{
  "nombre": "Inti Raymi",
  "fecha": "2026-06-24",
  "tipo": "REGIONAL_CUSCO",
  "recurrenteAnual": true
}
```

#### Respuesta exitosa HTTP 200

```json
{
  "resultado": true,
  "mensaje": "Feriado institucional actualizado correctamente.",
  "datos": {
    "id": 1,
    "nombre": "Inti Raymi",
    "fecha": "2026-06-24",
    "tipo": "REGIONAL_CUSCO",
    "recurrenteAnual": true,
    "activo": true,
    "creadoEn": "2026-06-17T12:00:00-05:00",
    "actualizadoEn": "2026-06-17T12:20:00-05:00"
  },
  "fechaHora": "2026-06-17T12:20:00-05:00"
}
```

---

### 5.6 Endpoint: Activar o desactivar feriado institucional

#### Método

```http
PATCH /api/v1/admin/feriados/{feriadoId}/estado
```

#### Descripción

Permite cambiar el estado activo/inactivo de un feriado institucional.

#### Request body

```json
{
  "activo": false
}
```

#### Respuesta exitosa HTTP 200

```json
{
  "resultado": true,
  "mensaje": "Estado del feriado institucional actualizado correctamente.",
  "datos": {
    "id": 1,
    "nombre": "Inti Raymi",
    "fecha": "2026-06-24",
    "tipo": "REGIONAL_CUSCO",
    "recurrenteAnual": true,
    "activo": false,
    "creadoEn": "2026-06-17T12:00:00-05:00",
    "actualizadoEn": "2026-06-17T12:25:00-05:00"
  },
  "fechaHora": "2026-06-17T12:25:00-05:00"
}
```

---

## 6. Errores esperados HU-004

### 6.1 Error por feriado duplicado

```json
{
  "resultado": false,
  "mensaje": "Ya existe un feriado registrado para la fecha y tipo indicados.",
  "datos": null,
  "error": {
    "codigo": "ERROR_REGLA_NEGOCIO",
    "estadoHttp": 409,
    "detalles": [],
    "trazaId": "..."
  },
  "fechaHora": "..."
}
```

### 6.2 Error por estado no enviado

```json
{
  "resultado": false,
  "mensaje": "Error de validación.",
  "datos": null,
  "error": {
    "codigo": "ERROR_VALIDACION",
    "estadoHttp": 400,
    "detalles": [
      {
        "campo": "activo",
        "mensaje": "El estado activo es obligatorio."
      }
    ],
    "trazaId": "..."
  },
  "fechaHora": "..."
}
```

### 6.3 Error por feriado no encontrado

```json
{
  "resultado": false,
  "mensaje": "No existe el feriado institucional solicitado.",
  "datos": null,
  "error": {
    "codigo": "RECURSO_NO_ENCONTRADO",
    "estadoHttp": 404,
    "detalles": [],
    "trazaId": "..."
  },
  "fechaHora": "..."
}
```

---

## 7. Seguridad

Estado actual:

- Seguridad temporal permitida para pruebas locales.
- En siguientes historias se restringirá a perfil `ADMIN_SIRD`.

Estado objetivo:

```text
Solo ADMIN_SIRD puede modificar configuración JWT y feriados institucionales.
```

---

## 8. Auditoría

Estado actual:

- Registro real en M06 pendiente.

Evento futuro esperado:

```text
CAMBIO_CONFIGURACION
```

Aplica para:

- Actualización de parámetros JWT.
- Creación de feriados.
- Actualización de feriados.
- Activación/desactivación de feriados.

---

## 9. Relación con historias futuras

| Historia | Relación |
|---|---|
| HU-006 | Gestión de usuarios y perfiles RBAC |
| HU-035 | Autenticación JWT SIRD |
| HU-038 | Ejecución de timer de 5 días hábiles |
| HU-040 | Registro en Log de Auditoría M06 |

---

## 10. Swagger UI

Disponible localmente en:

```text
http://localhost:8080/swagger-ui.html
```

OpenAPI JSON:

```text
http://localhost:8080/v3/api-docs
```
