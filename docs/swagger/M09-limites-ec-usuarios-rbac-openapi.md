# SIRD - Contrato API M09 Límites EC y Usuarios RBAC

**Versión:** v1.0  
**Fecha:** 17/06/2026  
**Módulo:** M09 - Administración del Sistema  
**Historias relacionadas:** HU-005, HU-006  
**Base URL:** `http://localhost:8080`  
**Prefijo API:** `/api/v1`

---

## 1. Resumen

Este documento define los contratos API del módulo M09 para las historias HU-005 y HU-006.

Incluye:

- Configuración de límites del Espacio Colaborativo.
- Gestión de usuarios SIRD.
- Listado de perfiles RBAC disponibles.

---

## 2. Convención de respuesta

Todas las respuestas exitosas usan el envoltorio estándar del sistema:

```json
{
  "resultado": true,
  "mensaje": "Operación realizada correctamente.",
  "datos": {},
  "fechaHora": "2026-06-17T14:00:00-05:00"
}
```

Las respuestas de error usan el envoltorio estándar:

```json
{
  "resultado": false,
  "mensaje": "Error de validación.",
  "datos": null,
  "error": {
    "codigo": "ERROR_VALIDACION",
    "estadoHttp": 400,
    "detalles": [],
    "trazaId": "..."
  },
  "fechaHora": "2026-06-17T14:00:00-05:00"
}
```

---

## 3. HU-005 - Configuración de límites del Espacio Colaborativo

### 3.1 Tipos permitidos

```text
EXPEDIENTE
PLAN
PERFIL
```

### 3.2 Endpoint: Listar límites EC

```http
GET /api/v1/admin/configuracion/limites-ec
```

#### Respuesta esperada

```text
HTTP 200
Límites del Espacio Colaborativo obtenidos correctamente.
```

#### Ejemplo de respuesta

```json
{
  "resultado": true,
  "mensaje": "Límites del Espacio Colaborativo obtenidos correctamente.",
  "datos": [
    {
      "id": 1,
      "tipoEspacio": "EXPEDIENTE",
      "limiteGb": 10,
      "activo": true,
      "creadoEn": "2026-06-17T14:00:00-05:00",
      "actualizadoEn": null
    },
    {
      "id": 2,
      "tipoEspacio": "PLAN",
      "limiteGb": 20,
      "activo": true,
      "creadoEn": "2026-06-17T14:00:00-05:00",
      "actualizadoEn": null
    },
    {
      "id": 3,
      "tipoEspacio": "PERFIL",
      "limiteGb": 5,
      "activo": true,
      "creadoEn": "2026-06-17T14:00:00-05:00",
      "actualizadoEn": null
    }
  ],
  "fechaHora": "2026-06-17T14:00:00-05:00"
}
```

---

### 3.3 Endpoint: Actualizar límite EC por tipo

```http
PUT /api/v1/admin/configuracion/limites-ec/{tipoEspacio}
```

#### Path params

| Parámetro | Tipo | Obligatorio | Valores |
|---|---|---|---|
| `tipoEspacio` | string | Sí | `EXPEDIENTE`, `PLAN`, `PERFIL` |

#### Request body

```json
{
  "limiteGb": 25
}
```

#### Validaciones

| Campo | Regla |
|---|---|
| `limiteGb` | Obligatorio |
| `limiteGb` | Mínimo 1 |
| `limiteGb` | Máximo 100 |

#### Respuesta esperada

```text
HTTP 200
Límite del Espacio Colaborativo actualizado correctamente.
```

---

## 4. HU-006 - Gestión de usuarios y perfiles RBAC

### 4.1 Perfiles permitidos

```text
ADMIN_SIRD
GERENTE_SUBGERENTE
JEFE_DEPENDENCIA_PROYECTO
ESPECIALISTA_RESPONSABLE
TECNICO_ADMINISTRATIVO
```

---

### 4.2 Endpoint: Listar perfiles

```http
GET /api/v1/admin/perfiles
```

#### Respuesta esperada

```text
HTTP 200
Listado de perfiles RBAC obtenido correctamente.
```

---

### 4.3 Endpoint: Listar usuarios

```http
GET /api/v1/admin/usuarios
```

#### Query params

| Parámetro | Tipo | Obligatorio | Descripción |
|---|---|---|---|
| `soloActivos` | boolean | No | Si es `true`, lista solo usuarios activos |

#### Respuesta esperada

```text
HTTP 200
Listado de usuarios obtenido correctamente.
```

---

### 4.4 Endpoint: Obtener usuario por ID

```http
GET /api/v1/admin/usuarios/{usuarioId}
```

#### Respuesta esperada

```text
HTTP 200
Usuario obtenido correctamente.
```

#### Usuario no encontrado

```text
HTTP 404
RECURSO_NO_ENCONTRADO
```

---

### 4.5 Endpoint: Crear usuario

```http
POST /api/v1/admin/usuarios
```

#### Request body

```json
{
  "nombres": "Juan",
  "apellidos": "Puma Quispe",
  "correo": "juan.puma@regioncusco.gob.pe",
  "cargo": "Especialista Documental",
  "unidad": "Gerencia Regional de Planeamiento",
  "perfil": "ESPECIALISTA_RESPONSABLE"
}
```

#### Respuesta esperada

```text
HTTP 200
Usuario SIRD creado correctamente.
```

#### Correo duplicado

```text
HTTP 409
ERROR_REGLA_NEGOCIO
```

---

### 4.6 Endpoint: Actualizar usuario

```http
PUT /api/v1/admin/usuarios/{usuarioId}
```

#### Respuesta esperada

```text
HTTP 200
Usuario SIRD actualizado correctamente.
```

#### Correo duplicado en otro usuario

```text
HTTP 409
ERROR_REGLA_NEGOCIO
```

---

### 4.7 Endpoint: Activar o desactivar usuario

```http
PATCH /api/v1/admin/usuarios/{usuarioId}/estado
```

#### Request body

```json
{
  "activo": false
}
```

#### Respuesta esperada

```text
HTTP 200
Estado del usuario SIRD actualizado correctamente.
```

---

## 5. Errores esperados

### 5.1 Error de validación

```text
HTTP 400
ERROR_VALIDACION
```

Aplica para:

- Límite EC menor a 1.
- Límite EC mayor a 100.
- Campos obligatorios faltantes.
- Correo con formato inválido.
- Perfil no permitido.

---

### 5.2 Recurso no encontrado

```text
HTTP 404
RECURSO_NO_ENCONTRADO
```

Aplica para:

- Usuario inexistente.
- Tipo de configuración EC inexistente.

---

### 5.3 Regla de negocio

```text
HTTP 409
ERROR_REGLA_NEGOCIO
```

Aplica para:

- Correo duplicado al crear usuario.
- Correo duplicado al actualizar usuario.

---

## 6. Seguridad

Estado actual:

| Criterio | Estado |
|---|---|
| Endpoints disponibles para pruebas locales | Implementado |
| Restricción real por `ADMIN_SIRD` | Pendiente |
| Integración JWT real | Pendiente |

Estado objetivo:

```text
Solo usuarios con perfil ADMIN_SIRD podrán gestionar límites EC, usuarios y perfiles RBAC.
```

---

## 7. Auditoría

Estado actual:

| Criterio | Estado |
|---|---|
| Auditoría técnica con `creadoEn` y `actualizadoEn` | Implementado |
| Auditoría funcional M06 | Pendiente |

Eventos futuros esperados:

```text
CAMBIO_CONFIGURACION
CREACION_USUARIO
ACTUALIZACION_USUARIO
CAMBIO_ESTADO_USUARIO
```

---

## 8. Swagger UI

Disponible localmente en:

```text
http://localhost:8080/swagger-ui.html
```

OpenAPI JSON:

```text
http://localhost:8080/v3/api-docs
```
