# SIRD - Contrato API M09 Configuración Documental

**Versión:** v1.0  
**Fecha:** 17/06/2026  
**Módulo:** M09 - Administración del Sistema  
**Historias relacionadas:** HU-001, HU-002  
**Base URL:** `http://localhost:8080`  
**Prefijo API:** `/api/v1`

---

## 1. Resumen

Este documento define los contratos API iniciales del módulo M09 para la configuración documental del SIRD.

Incluye:

- Consulta de configuración documental activa.
- Actualización del período de retención TRD.
- Actualización de la clasificación por defecto.

---

## 2. Convención de respuesta

Todas las respuestas usan el envoltorio estándar:

```json
{
  "resultado": true,
  "mensaje": "Operación realizada correctamente.",
  "datos": {},
  "fechaHora": "2026-06-17T10:30:00-05:00"
}
```

---

## 3. Endpoint: Consultar configuración documental

### Método

```http
GET /api/v1/admin/configuracion/documental
```

### Descripción

Permite consultar la configuración documental activa del sistema.

### Respuesta exitosa HTTP 200

```json
{
  "resultado": true,
  "mensaje": "Configuración documental obtenida correctamente.",
  "datos": {
    "id": 1,
    "periodoRetencionAnios": 5,
    "clasificacionPorDefecto": "INTERNO",
    "activo": true,
    "creadoEn": "2026-06-17T10:30:00-05:00",
    "actualizadoEn": null
  },
  "fechaHora": "2026-06-17T10:30:00-05:00"
}
```

---

## 4. Endpoint: Actualizar configuración documental

### Método

```http
PUT /api/v1/admin/configuracion/documental
```

### Descripción

Permite actualizar el período de retención TRD y la clasificación por defecto.

### Request body

```json
{
  "periodoRetencionAnios": 7,
  "clasificacionPorDefecto": "PUBLICO"
}
```

### Validaciones

| Campo | Regla |
|---|---|
| `periodoRetencionAnios` | Obligatorio, mínimo 1, máximo 50 |
| `clasificacionPorDefecto` | Obligatorio, solo `PUBLICO` o `INTERNO` |

### Respuesta exitosa HTTP 200

```json
{
  "resultado": true,
  "mensaje": "Configuración documental actualizada correctamente.",
  "datos": {
    "id": 1,
    "periodoRetencionAnios": 7,
    "clasificacionPorDefecto": "PUBLICO",
    "activo": true,
    "creadoEn": "2026-06-17T10:30:00-05:00",
    "actualizadoEn": "2026-06-17T10:35:00-05:00"
  },
  "fechaHora": "2026-06-17T10:35:00-05:00"
}
```

---

## 5. Errores esperados

### Error por período inválido

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
        "campo": "periodoRetencionAnios",
        "mensaje": "El período de retención debe ser como mínimo 1 año."
      }
    ],
    "trazaId": "..."
  },
  "fechaHora": "..."
}
```

### Error por clasificación no permitida

```json
{
  "resultado": false,
  "mensaje": "Solo PUBLICO o INTERNO pueden configurarse como clasificación por defecto.",
  "datos": null,
  "error": {
    "codigo": "ERROR_VALIDACION",
    "estadoHttp": 400,
    "detalles": [],
    "trazaId": "..."
  },
  "fechaHora": "..."
}
```

---

## 6. Seguridad

Estado actual:

- Seguridad temporal permitida para pruebas locales.
- En siguientes historias se restringirá a perfil `ADMIN_SIRD`.

Estado objetivo:

```text
Solo ADMIN_SIRD puede modificar configuración documental.
```

---

## 7. Swagger UI

Disponible localmente en:

```text
http://localhost:8080/swagger-ui.html
```

OpenAPI JSON:

```text
http://localhost:8080/v3/api-docs
```
