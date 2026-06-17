# HU-003 - Admin configura parámetros de seguridad JWT

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

Como Administrador SIRD, necesito configurar los parámetros de seguridad JWT para controlar la duración de tokens, refresh tokens, intentos fallidos y tiempo de bloqueo de usuarios.

Esta configuración servirá como base para el mecanismo de autenticación y control de sesiones del sistema SIRD.

---

## 2. Regla principal

El sistema debe permitir configurar cuatro parámetros de seguridad JWT:

| Parámetro | Rango permitido |
|---|---|
| Tiempo de vida del access token | 1 a 24 horas |
| Duración del refresh token | 1 a 90 días |
| Intentos fallidos máximos | 3 a 10 intentos |
| Duración del bloqueo | 5 a 60 minutos |

Valores iniciales implementados:

```text
access_token_horas = 8
refresh_token_dias = 7
intentos_fallidos_max = 5
bloqueo_minutos = 15
```

Cada vez que se actualizan los parámetros JWT, el sistema incrementa `versionSeguridad`.

Este campo sirve como base técnica para invalidar sesiones activas cuando se implemente la autenticación JWT completa.

---

## 3. Alcance implementado

| Área | Estado |
|---|---|
| Backend | Implementado |
| Base de datos | Implementado |
| Swagger | Implementado |
| Validaciones | Implementado |
| Incremento de `versionSeguridad` | Implementado |
| Invalidación real de sesiones activas | Pendiente para HU-035 |
| Seguridad real con ADMIN_SIRD | Pendiente |
| Auditoría M06 | Pendiente |

---

## 4. Endpoint relacionado

```http
GET /api/v1/admin/configuracion/seguridad-jwt
PUT /api/v1/admin/configuracion/seguridad-jwt
```

---

## 5. Validaciones

| Campo | Regla |
|---|---|
| `accessTokenHoras` | Obligatorio |
| `accessTokenHoras` | Mínimo 1 |
| `accessTokenHoras` | Máximo 24 |
| `refreshTokenDias` | Obligatorio |
| `refreshTokenDias` | Mínimo 1 |
| `refreshTokenDias` | Máximo 90 |
| `intentosFallidosMax` | Obligatorio |
| `intentosFallidosMax` | Mínimo 3 |
| `intentosFallidosMax` | Máximo 10 |
| `bloqueoMinutos` | Obligatorio |
| `bloqueoMinutos` | Mínimo 5 |
| `bloqueoMinutos` | Máximo 60 |

---

## 6. Casos de prueba

### Caso exitoso

Request:

```json
{
  "accessTokenHoras": 12,
  "refreshTokenDias": 30,
  "intentosFallidosMax": 5,
  "bloqueoMinutos": 20
}
```

Resultado esperado:

```text
HTTP 200
Configuración JWT actualizada correctamente.
```

### Caso inválido: access token mayor a 24 horas

Request:

```json
{
  "accessTokenHoras": 25,
  "refreshTokenDias": 30,
  "intentosFallidosMax": 5,
  "bloqueoMinutos": 20
}
```

Resultado esperado:

```text
HTTP 400
ERROR_VALIDACION
```

### Caso inválido: refresh token mayor a 90 días

Request:

```json
{
  "accessTokenHoras": 12,
  "refreshTokenDias": 91,
  "intentosFallidosMax": 5,
  "bloqueoMinutos": 20
}
```

Resultado esperado:

```text
HTTP 400
ERROR_VALIDACION
```

### Caso inválido: intentos fallidos menor a 3

Request:

```json
{
  "accessTokenHoras": 12,
  "refreshTokenDias": 30,
  "intentosFallidosMax": 2,
  "bloqueoMinutos": 20
}
```

Resultado esperado:

```text
HTTP 400
ERROR_VALIDACION
```

### Caso inválido: bloqueo menor a 5 minutos

Request:

```json
{
  "accessTokenHoras": 12,
  "refreshTokenDias": 30,
  "intentosFallidosMax": 5,
  "bloqueoMinutos": 4
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
| `V2__configuracion_jwt_y_feriados.sql` | Migración de configuración JWT y feriados |
| `ConfiguracionSeguridadJwt.java` | Entidad JPA de configuración JWT |
| `ConfiguracionSeguridadJwtRepository.java` | Acceso a datos |
| `ConfiguracionSeguridadJwtService.java` | Contrato de servicio |
| `ConfiguracionSeguridadJwtServiceImpl.java` | Lógica de negocio |
| `ConfiguracionSeguridadJwtController.java` | Endpoint REST |
| `ActualizarConfiguracionSeguridadJwtRequest.java` | Request DTO |
| `ConfiguracionSeguridadJwtResponse.java` | Response DTO |
| `M09-configuracion-jwt-feriados-openapi.md` | Documentación Swagger/OpenAPI |
| `HU-003-HU-004-configuracion-jwt-feriados.http` | Pruebas manuales HTTP |
| `probar-HU-003-HU-004.ps1` | Script automático de pruebas |

---

## 8. Definition of Done

| Criterio | Estado |
|---|---|
| Se puede configurar tiempo de vida del access token | Cumplido |
| Se puede configurar duración del refresh token | Cumplido |
| Se puede configurar intentos fallidos máximos | Cumplido |
| Se puede configurar duración del bloqueo | Cumplido |
| Se validan rangos definidos por la HU | Cumplido |
| Persistencia en PostgreSQL | Cumplido |
| Migración Flyway versionada | Cumplido |
| Documentación Swagger | Cumplido |
| Incremento de versión de seguridad | Cumplido |
| Invalidación real de sesiones activas | Pendiente |
| Seguridad ADMIN_SIRD | Pendiente |
| Auditoría de cambio de configuración | Pendiente |
