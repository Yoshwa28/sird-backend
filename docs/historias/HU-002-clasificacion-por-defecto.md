# HU-002 - Admin configura clasificación por defecto

**Proyecto:** SIRD  
**Versión:** v1.0  
**Fecha:** 17/06/2026  
**Épica:** EP-08 - Administración del Sistema  
**Módulo:** M09  
**Sprint:** S1  
**Prioridad:** Alta  
**Story Points:** 5  

---

## 1. Descripción funcional

Como Administrador SIRD, necesito configurar la clasificación documental por defecto para que el sistema pueda asignarla cuando un sistema externo no envíe clasificación al momento de registrar un documento.

---

## 2. Regla principal

Si el SGD o sistema externo envía clasificación, el SIRD debe usar esa clasificación.

Si no la envía, el SIRD debe usar la clasificación configurada por defecto.

Clasificaciones permitidas como default:

```text
PUBLICO
INTERNO
```

Clasificaciones no permitidas como default:

```text
CONFIDENCIAL
RESTRINGIDO
```

---

## 3. Alcance implementado

| Área | Estado |
|---|---|
| Backend | Implementado |
| Base de datos | Implementado |
| Swagger | Implementado |
| Validaciones | Implementado |
| Aplicación durante ingesta A-1 | Pendiente para HU-008/HU-009 |
| Seguridad real con ADMIN_SIRD | Pendiente |
| Auditoría M06 | Pendiente |

---

## 4. Endpoint relacionado

```http
GET /api/v1/admin/configuracion/documental
PUT /api/v1/admin/configuracion/documental
```

---

## 5. Validaciones

| Campo | Regla |
|---|---|
| `clasificacionPorDefecto` | Obligatoria |
| `clasificacionPorDefecto` | Solo puede ser `PUBLICO` o `INTERNO` |
| `clasificacionPorDefecto` | No puede ser `CONFIDENCIAL` |
| `clasificacionPorDefecto` | No puede ser `RESTRINGIDO` |

---

## 6. Casos de prueba

### Caso exitoso

Request:

```json
{
  "periodoRetencionAnios": 5,
  "clasificacionPorDefecto": "INTERNO"
}
```

Resultado esperado:

```text
HTTP 200
Configuración documental actualizada correctamente.
```

### Caso inválido

Request:

```json
{
  "periodoRetencionAnios": 5,
  "clasificacionPorDefecto": "RESTRINGIDO"
}
```

Resultado esperado:

```text
HTTP 400
ERROR_VALIDACION
```

---

## 7. Archivos implementados

| Archivo | Propósito |
|---|---|
| `ClasificacionDocumento.java` | Enum de clasificación documental |
| `ConfiguracionDocumental.java` | Entidad JPA |
| `ActualizarConfiguracionDocumentalRequest.java` | DTO de actualización |
| `ConfiguracionDocumentalServiceImpl.java` | Validación de regla de negocio |
| `ConfiguracionDocumentalController.java` | Endpoint REST |

---

## 8. Definition of Done

| Criterio | Estado |
|---|---|
| Se puede configurar clasificación default | Cumplido |
| `PUBLICO` permitido como default | Cumplido |
| `INTERNO` permitido como default | Cumplido |
| `CONFIDENCIAL` rechazado como default | Cumplido |
| `RESTRINGIDO` rechazado como default | Cumplido |
| Persistencia en PostgreSQL | Cumplido |
| Documentación Swagger | Cumplido |
| Aplicación en contrato A-1 | Pendiente |
| Seguridad ADMIN_SIRD | Pendiente |
| Auditoría de cambio de configuración | Pendiente |
