# HU-004 - Admin gestiona feriados institucionales para el timer

**Proyecto:** SIRD  
**Versión:** v1.0  
**Fecha:** 17/06/2026  
**Épica:** EP-08 - Administración del Sistema  
**Módulo:** M09  
**Sprint:** S1  
**Prioridad:** Alta  
**Story Points:** 3  

---

## 1. Descripción funcional

Como Administrador SIRD, necesito gestionar los feriados institucionales para que el sistema pueda calcular correctamente los plazos basados en días hábiles.

Esta configuración será usada posteriormente por el timer de 5 días hábiles del sistema.

---

## 2. Regla principal

El sistema debe permitir gestionar feriados institucionales de dos tipos:

```text
NACIONAL
REGIONAL_CUSCO
```

El sistema debe incluir obligatoriamente el feriado regional del Cusco:

```text
Inti Raymi - 24/06 - REGIONAL_CUSCO
```

Implementación inicial:

```text
nombre = Inti Raymi
fecha = 2026-06-24
tipo = REGIONAL_CUSCO
recurrente_anual = true
activo = true
```

Los feriados se usan como base para el cálculo de días hábiles, pero el uso real en el timer queda pendiente para HU-038.

---

## 3. Alcance implementado

| Área | Estado |
|---|---|
| Backend | Implementado |
| Base de datos | Implementado |
| Swagger | Implementado |
| Validaciones | Implementado |
| Listado de feriados | Implementado |
| Creación de feriados | Implementado |
| Actualización de feriados | Implementado |
| Activación/desactivación de feriados | Implementado |
| Registro inicial de Inti Raymi | Implementado |
| Uso real en timer de 5 días hábiles | Pendiente para HU-038 |
| Seguridad real con ADMIN_SIRD | Pendiente |
| Auditoría M06 | Pendiente |

---

## 4. Endpoint relacionado

```http
GET /api/v1/admin/feriados
POST /api/v1/admin/feriados
PUT /api/v1/admin/feriados/{feriadoId}
PATCH /api/v1/admin/feriados/{feriadoId}/estado
```

---

## 5. Validaciones

| Campo | Regla |
|---|---|
| `nombre` | Obligatorio |
| `nombre` | Mínimo 3 caracteres |
| `nombre` | Máximo 150 caracteres |
| `fecha` | Obligatoria |
| `fecha` | Formato `yyyy-MM-dd` |
| `tipo` | Obligatorio |
| `tipo` | Solo puede ser `NACIONAL` o `REGIONAL_CUSCO` |
| `recurrenteAnual` | Opcional, por defecto `false` |
| `activo` | Obligatorio para cambio de estado |
| `fecha` + `tipo` | No se permite duplicidad |

---

## 6. Casos de prueba

### Caso exitoso: listar feriados

Request:

```http
GET /api/v1/admin/feriados
```

Resultado esperado:

```text
HTTP 200
Listado de feriados obtenido correctamente.
```

### Caso exitoso: crear feriado

Request:

```json
{
  "nombre": "Día de prueba institucional",
  "fecha": "2026-07-15",
  "tipo": "NACIONAL",
  "recurrenteAnual": false
}
```

Resultado esperado:

```text
HTTP 200
Feriado institucional creado correctamente.
```

### Caso inválido: feriado duplicado

Request:

```json
{
  "nombre": "Inti Raymi duplicado",
  "fecha": "2026-06-24",
  "tipo": "REGIONAL_CUSCO",
  "recurrenteAnual": true
}
```

Resultado esperado:

```text
HTTP 409
ERROR_REGLA_NEGOCIO
```

### Caso exitoso: actualizar feriado

Request:

```json
{
  "nombre": "Inti Raymi",
  "fecha": "2026-06-24",
  "tipo": "REGIONAL_CUSCO",
  "recurrenteAnual": true
}
```

Resultado esperado:

```text
HTTP 200
Feriado institucional actualizado correctamente.
```

### Caso exitoso: desactivar feriado

Request:

```json
{
  "activo": false
}
```

Resultado esperado:

```text
HTTP 200
Estado del feriado institucional actualizado correctamente.
```

### Caso inválido: estado no enviado

Request:

```json
{}
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
| `V2__configuracion_jwt_y_feriados.sql` | Migración de configuración JWT y feriados |
| `TipoFeriado.java` | Enum de tipos de feriado |
| `FeriadoInstitucional.java` | Entidad JPA de feriado institucional |
| `FeriadoInstitucionalRepository.java` | Acceso a datos |
| `FeriadoInstitucionalService.java` | Contrato de servicio |
| `FeriadoInstitucionalServiceImpl.java` | Lógica de negocio |
| `FeriadoInstitucionalController.java` | Endpoint REST |
| `CrearFeriadoInstitucionalRequest.java` | Request DTO para creación |
| `ActualizarFeriadoInstitucionalRequest.java` | Request DTO para actualización |
| `CambiarEstadoFeriadoRequest.java` | Request DTO para activar/desactivar |
| `FeriadoInstitucionalResponse.java` | Response DTO |
| `M09-configuracion-jwt-feriados-openapi.md` | Documentación Swagger/OpenAPI |
| `HU-003-HU-004-configuracion-jwt-feriados.http` | Pruebas manuales HTTP |
| `probar-HU-003-HU-004.ps1` | Script automático de pruebas |

---

## 8. Definition of Done

| Criterio | Estado |
|---|---|
| Se pueden gestionar feriados institucionales | Cumplido |
| Se soporta tipo `NACIONAL` | Cumplido |
| Se soporta tipo `REGIONAL_CUSCO` | Cumplido |
| Se registra Inti Raymi 24/06 | Cumplido |
| Inti Raymi se registra como `REGIONAL_CUSCO` | Cumplido |
| Se evita duplicidad por fecha y tipo | Cumplido |
| Se permite activar/desactivar feriados | Cumplido |
| Persistencia en PostgreSQL | Cumplido |
| Migración Flyway versionada | Cumplido |
| Documentación Swagger | Cumplido |
| Uso real en timer de 5 días hábiles | Pendiente |
| Seguridad ADMIN_SIRD | Pendiente |
| Auditoría de cambio de configuración | Pendiente |
