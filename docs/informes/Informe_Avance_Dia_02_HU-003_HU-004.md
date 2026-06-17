# Informe de Avance Backend SIRD — Día 2

**Proyecto:** Sistema Institucional de Repositorio Digital - SIRD  
**Institución:** Gobierno Regional del Cusco  
**Versión del informe:** v1.0  
**Fecha:** 17/06/2026  
**Responsable:** Yoshwa  
**Sprint:** S1  
**Módulo trabajado:** M09 - Administración del Sistema  
**Historias trabajadas:** HU-003, HU-004  

---

## 1. Resumen general

Durante el Día 2 se continuó con la implementación backend del proyecto SIRD, manteniendo como fuente principal de verdad los documentos oficiales:

- Product Backlog v11.3.
- Sprint Backlog v11.3.
- Épicas v11.3.
- Historias de Usuario v1.5.
- Definition of Done v11.3.

Se implementaron dos historias de usuario correspondientes al módulo M09 - Administración del Sistema:

- HU-003: Admin configura parámetros de seguridad JWT.
- HU-004: Admin gestiona feriados institucionales para el timer.

El desarrollo incluyó persistencia en PostgreSQL, migración versionada con Flyway, endpoints REST, validaciones backend, respuesta estándar, manejo centralizado de errores, documentación Swagger/OpenAPI, documentación funcional por historia y scripts de pruebas.

---

## 2. Historias de usuario trabajadas

| ID | Historia | Épica | Módulo | Sprint | Prioridad | Story Points | Estado |
|---|---|---|---|---|---|---:|---|
| HU-003 | Admin configura parámetros de seguridad JWT | EP-08 | M09 | S1 | Alta | 5 | Implementada |
| HU-004 | Admin gestiona feriados institucionales para el timer | EP-08 | M09 | S1 | Alta | 3 | Implementada |

---

## 3. Alcance implementado

| Área | Descripción | Estado |
|---|---|---|
| Backend | Endpoints REST para consulta y actualización de configuración JWT | Completado |
| Backend | Endpoints REST para gestión de feriados institucionales | Completado |
| Base de datos | Tabla `sird.configuracion_seguridad_jwt` | Completado |
| Base de datos | Tabla `sird.feriado_institucional` | Completado |
| Migraciones | Migración Flyway `V2__configuracion_jwt_y_feriados.sql` | Completado |
| Validaciones | Validación de rangos permitidos para parámetros JWT | Completado |
| Validaciones | Validación de tipos de feriado y duplicidad por fecha/tipo | Completado |
| Swagger | Endpoints visibles en Swagger UI | Completado |
| Manejo de errores | Uso de respuesta estándar de error con `trazaId` | Completado |
| Pruebas | Archivo `.http` y script PowerShell de pruebas | Completado |
| Seguridad | Configuración temporal para pruebas locales | Parcial |
| Auditoría M06 | Registro real de evento `CAMBIO_CONFIGURACION` | Pendiente |

---

## 4. Endpoints implementados

### 4.1 Consultar configuración JWT

```http
GET /api/v1/admin/configuracion/seguridad-jwt
```

#### Respuesta exitosa esperada

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

### 4.2 Actualizar configuración JWT

```http
PUT /api/v1/admin/configuracion/seguridad-jwt
```

#### Request body

```json
{
  "accessTokenHoras": 12,
  "refreshTokenDias": 30,
  "intentosFallidosMax": 5,
  "bloqueoMinutos": 20
}
```

#### Respuesta exitosa esperada

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

### 4.3 Listar feriados institucionales

```http
GET /api/v1/admin/feriados
```

#### Variante para listar solo activos

```http
GET /api/v1/admin/feriados?soloActivos=true
```

#### Respuesta exitosa esperada

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

### 4.4 Crear feriado institucional

```http
POST /api/v1/admin/feriados
```

#### Request body

```json
{
  "nombre": "Día de prueba institucional",
  "fecha": "2026-07-15",
  "tipo": "NACIONAL",
  "recurrenteAnual": false
}
```

#### Respuesta exitosa esperada

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

### 4.5 Actualizar feriado institucional

```http
PUT /api/v1/admin/feriados/{feriadoId}
```

#### Request body

```json
{
  "nombre": "Inti Raymi",
  "fecha": "2026-06-24",
  "tipo": "REGIONAL_CUSCO",
  "recurrenteAnual": true
}
```

### 4.6 Activar o desactivar feriado institucional

```http
PATCH /api/v1/admin/feriados/{feriadoId}/estado
```

#### Request body

```json
{
  "activo": true
}
```

---

## 5. Reglas de negocio implementadas

| Regla | Historia | Estado |
|---|---|---|
| El tiempo de vida del access token debe estar entre 1 y 24 horas | HU-003 | Cumplido |
| La duración del refresh token debe estar entre 1 y 90 días | HU-003 | Cumplido |
| El máximo de intentos fallidos debe estar entre 3 y 10 | HU-003 | Cumplido |
| La duración del bloqueo debe estar entre 5 y 60 minutos | HU-003 | Cumplido |
| Al modificar configuración JWT se incrementa `versionSeguridad` | HU-003 | Cumplido parcial |
| Los tipos de feriado permitidos son `NACIONAL` y `REGIONAL_CUSCO` | HU-004 | Cumplido |
| El sistema registra obligatoriamente Inti Raymi el 24/06 | HU-004 | Cumplido |
| El feriado Inti Raymi se registra como `REGIONAL_CUSCO` | HU-004 | Cumplido |
| No se permite duplicar feriados con la misma fecha y tipo | HU-004 | Cumplido |
| Se permite activar y desactivar feriados institucionales | HU-004 | Cumplido |

---

## 6. Archivos creados o modificados

| Archivo | Propósito |
|---|---|
| `src/main/resources/db/migration/V2__configuracion_jwt_y_feriados.sql` | Migración de configuración JWT y feriados |
| `src/main/java/pe/gob/regioncusco/sird/administracion/entity/ConfiguracionSeguridadJwt.java` | Entidad JPA de configuración JWT |
| `src/main/java/pe/gob/regioncusco/sird/administracion/entity/TipoFeriado.java` | Enum de tipos de feriado |
| `src/main/java/pe/gob/regioncusco/sird/administracion/entity/FeriadoInstitucional.java` | Entidad JPA de feriados |
| `src/main/java/pe/gob/regioncusco/sird/administracion/repository/ConfiguracionSeguridadJwtRepository.java` | Repository JPA para configuración JWT |
| `src/main/java/pe/gob/regioncusco/sird/administracion/repository/FeriadoInstitucionalRepository.java` | Repository JPA para feriados |
| `src/main/java/pe/gob/regioncusco/sird/administracion/dto/ActualizarConfiguracionSeguridadJwtRequest.java` | DTO para actualizar configuración JWT |
| `src/main/java/pe/gob/regioncusco/sird/administracion/dto/ConfiguracionSeguridadJwtResponse.java` | DTO de respuesta de configuración JWT |
| `src/main/java/pe/gob/regioncusco/sird/administracion/dto/CrearFeriadoInstitucionalRequest.java` | DTO para crear feriado |
| `src/main/java/pe/gob/regioncusco/sird/administracion/dto/ActualizarFeriadoInstitucionalRequest.java` | DTO para actualizar feriado |
| `src/main/java/pe/gob/regioncusco/sird/administracion/dto/CambiarEstadoFeriadoRequest.java` | DTO para activar/desactivar feriado |
| `src/main/java/pe/gob/regioncusco/sird/administracion/dto/FeriadoInstitucionalResponse.java` | DTO de respuesta de feriados |
| `src/main/java/pe/gob/regioncusco/sird/administracion/service/ConfiguracionSeguridadJwtService.java` | Interfaz de servicio JWT |
| `src/main/java/pe/gob/regioncusco/sird/administracion/service/ConfiguracionSeguridadJwtServiceImpl.java` | Implementación de reglas JWT |
| `src/main/java/pe/gob/regioncusco/sird/administracion/service/FeriadoInstitucionalService.java` | Interfaz de servicio feriados |
| `src/main/java/pe/gob/regioncusco/sird/administracion/service/FeriadoInstitucionalServiceImpl.java` | Implementación de reglas de feriados |
| `src/main/java/pe/gob/regioncusco/sird/administracion/controller/ConfiguracionSeguridadJwtController.java` | Controller REST JWT |
| `src/main/java/pe/gob/regioncusco/sird/administracion/controller/FeriadoInstitucionalController.java` | Controller REST feriados |
| `docs/pruebas/HU-003-HU-004-configuracion-jwt-feriados.http` | Pruebas HTTP desde VS Code |
| `scripts/pruebas/probar-HU-003-HU-004.ps1` | Script automático de pruebas |
| `docs/swagger/M09-configuracion-jwt-feriados-openapi.md` | Documentación del contrato API |
| `docs/historias/HU-003-configuracion-parametros-jwt.md` | Documentación funcional de HU-003 |
| `docs/historias/HU-004-gestion-feriados-institucionales.md` | Documentación funcional de HU-004 |

---

## 7. Pruebas ejecutadas

| Caso | Método | Endpoint | Resultado esperado | Estado |
|---|---|---|---|---|
| Consultar configuración JWT | GET | `/api/v1/admin/configuracion/seguridad-jwt` | HTTP 200 | Pendiente de evidencia |
| Actualizar configuración JWT válida | PUT | `/api/v1/admin/configuracion/seguridad-jwt` | HTTP 200 | Pendiente de evidencia |
| Access token mayor a 24 horas | PUT | `/api/v1/admin/configuracion/seguridad-jwt` | HTTP 400 | Pendiente de evidencia |
| Refresh token mayor a 90 días | PUT | `/api/v1/admin/configuracion/seguridad-jwt` | HTTP 400 | Pendiente de evidencia |
| Listar feriados | GET | `/api/v1/admin/feriados` | HTTP 200 | Pendiente de evidencia |
| Listar feriados activos | GET | `/api/v1/admin/feriados?soloActivos=true` | HTTP 200 | Pendiente de evidencia |
| Crear feriado válido | POST | `/api/v1/admin/feriados` | HTTP 200 | Pendiente de evidencia |
| Crear feriado duplicado | POST | `/api/v1/admin/feriados` | HTTP 409 | Pendiente de evidencia |
| Actualizar feriado | PUT | `/api/v1/admin/feriados/{feriadoId}` | HTTP 200 | Pendiente de evidencia |
| Activar/desactivar feriado | PATCH | `/api/v1/admin/feriados/{feriadoId}/estado` | HTTP 200 | Pendiente de evidencia |

---

## 8. Evidencias esperadas

| Evidencia | Ubicación sugerida |
|---|---|
| Resultado JSON de pruebas PowerShell | `docs/evidencias/HU-003-HU-004_FECHA.json` |
| Contrato OpenAPI exportado | `docs/swagger/openapi/sird-openapi-dia-02.json` |
| Evidencia de compilación Maven | `docs/evidencias/mvn-compile-dia-02.txt` |
| Evidencia de health check | `docs/evidencias/health-dia-02.json` |
| Captura de Swagger con endpoints | `docs/evidencias/swagger-HU-003-HU-004.png` |
| Captura de consola Spring Boot | `docs/evidencias/backend-running-dia-02.png` |

---

## 9. Definition of Done

### HU-003

| Criterio | Estado |
|---|---|
| El Admin puede configurar tiempo de vida del access token | Cumplido |
| El Admin puede configurar duración del refresh token | Cumplido |
| El Admin puede configurar cantidad de intentos fallidos | Cumplido |
| El Admin puede configurar duración del bloqueo | Cumplido |
| Se validan los rangos definidos por la HU | Cumplido |
| Persistencia en PostgreSQL | Cumplido |
| Migración Flyway versionada | Cumplido |
| Endpoint documentado | Cumplido |
| Incremento de versión de seguridad para invalidación futura | Cumplido parcial |
| Invalidación real de sesiones activas | Pendiente |
| Seguridad con perfil Admin SIRD | Pendiente |
| Auditoría M06 `CAMBIO_CONFIGURACION` | Pendiente |

### HU-004

| Criterio | Estado |
|---|---|
| El Admin puede gestionar feriados institucionales | Cumplido |
| Se soporta tipo `NACIONAL` | Cumplido |
| Se soporta tipo `REGIONAL_CUSCO` | Cumplido |
| Se registra Inti Raymi 24/06 como feriado regional del Cusco | Cumplido |
| Se evita duplicidad por fecha y tipo | Cumplido |
| Se permite activar y desactivar feriados | Cumplido |
| Persistencia en PostgreSQL | Cumplido |
| Migración Flyway versionada | Cumplido |
| Endpoint documentado | Cumplido |
| Uso real en timer de 5 días hábiles | Pendiente |
| Seguridad con perfil Admin SIRD | Pendiente |
| Auditoría M06 `CAMBIO_CONFIGURACION` | Pendiente |

---

## 10. Pendientes técnicos

| Pendiente | Relación | Motivo |
|---|---|---|
| Invalidación real de sesiones JWT | HU-035 | Aún no existe autenticación JWT completa |
| Seguridad real con RBAC | HU-006, HU-035 | Actualmente los endpoints están liberados para pruebas |
| Auditoría M06 | HU-040 | Se integrará cuando exista el módulo de auditoría |
| Uso de feriados para cálculo de días hábiles | HU-038 | Se usará en el timer de solicitudes restringidas |
| Pruebas automatizadas JUnit | Transversal | Se agregarán progresivamente |

---

## 11. Riesgos

| Riesgo | Nivel | Mitigación |
|---|---|---|
| Endpoints administrativos temporalmente sin seguridad | Medio | Restringir al implementar JWT/RBAC |
| Invalidación de sesiones aún no real | Medio | Se implementó `versionSeguridad` como base técnica |
| Feriados recurrentes pueden requerir lógica adicional por año | Medio | Se agregó campo `recurrenteAnual` |
| Auditoría aún no implementada | Medio | Registrar deuda técnica y atender en HU-040 |

---

## 12. Próximos pasos

Para el siguiente día de desarrollo se recomienda avanzar con:

- HU-005: Admin configura límites del Espacio Colaborativo.
- HU-006: Admin gestiona usuarios y 5 perfiles RBAC.

Estas historias continúan dentro del módulo M09 y preparan la base para límites operativos del Espacio Colaborativo y control de acceso basado en roles.
