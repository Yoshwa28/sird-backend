# HU-001 - Admin configura período de retención TRD

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

Como Administrador SIRD, necesito configurar el período de retención TRD para que el sistema pueda calcular la fecha de vencimiento documental de acuerdo con las reglas institucionales.

---

## 2. Regla principal

El sistema debe permitir configurar un período de retención en años.

Valor por defecto:

```text
5 años
```

Fórmula funcional:

```text
fecha_vencimiento_trd = fecha_ingreso + periodo_retencion_anios
```

---

## 3. Alcance implementado

| Área | Estado |
|---|---|
| Backend | Implementado |
| Base de datos | Implementado |
| Swagger | Implementado |
| Validaciones | Implementado |
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
| `periodoRetencionAnios` | Obligatorio |
| `periodoRetencionAnios` | Mínimo 1 |
| `periodoRetencionAnios` | Máximo 50 |

---

## 6. Casos de prueba

### Caso exitoso

Request:

```json
{
  "periodoRetencionAnios": 7,
  "clasificacionPorDefecto": "PUBLICO"
}
```

Resultado esperado:

```text
HTTP 200
Configuración actualizada correctamente.
```

### Caso inválido

Request:

```json
{
  "periodoRetencionAnios": 0,
  "clasificacionPorDefecto": "PUBLICO"
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
| `V1__crear_esquema_y_configuracion_documental.sql` | Migración inicial |
| `ConfiguracionDocumental.java` | Entidad JPA |
| `ConfiguracionDocumentalRepository.java` | Acceso a datos |
| `ConfiguracionDocumentalService.java` | Contrato de servicio |
| `ConfiguracionDocumentalServiceImpl.java` | Lógica de negocio |
| `ConfiguracionDocumentalController.java` | Endpoint REST |
| `ActualizarConfiguracionDocumentalRequest.java` | Request DTO |
| `ConfiguracionDocumentalResponse.java` | Response DTO |

---

## 8. Definition of Done

| Criterio | Estado |
|---|---|
| Se puede configurar período TRD | Cumplido |
| Default inicial 5 años | Cumplido |
| Persistencia en PostgreSQL | Cumplido |
| Validación backend | Cumplido |
| Documentación Swagger | Cumplido |
| Seguridad ADMIN_SIRD | Pendiente |
| Auditoría de cambio de configuración | Pendiente |
