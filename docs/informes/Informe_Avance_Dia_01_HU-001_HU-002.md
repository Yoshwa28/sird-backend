# Informe de Avance Backend SIRD — Día 1

**Proyecto:** Sistema Institucional de Repositorio Digital - SIRD  
**Institución:** Gobierno Regional del Cusco  
**Versión del informe:** v1.0  
**Fecha:** 17/06/2026  
**Responsable:** Yoshwa  
**Sprint:** S1  
**Módulo trabajado:** M09 - Administración del Sistema  
**Historias trabajadas:** HU-001, HU-002  

---

## 1. Resumen general

Durante el Día 1 se inició la implementación backend del proyecto SIRD, tomando como fuente principal de verdad los documentos oficiales:

- Product Backlog v11.3.
- Sprint Backlog v11.3.
- Épicas v11.3.
- Historias de Usuario v1.5.
- Definition of Done v11.3.

Se implementaron dos historias de usuario correspondientes al módulo M09 - Administración del Sistema:

- HU-001: Admin configura período de retención TRD.
- HU-002: Admin configura clasificación por defecto.

El desarrollo incluyó persistencia en PostgreSQL, migración versionada con Flyway, endpoints REST, validaciones backend, respuesta estándar, manejo centralizado de errores y documentación inicial con Swagger/OpenAPI.

---

## 2. Historias de usuario trabajadas

| ID | Historia | Épica | Módulo | Sprint | Prioridad | Story Points | Estado |
|---|---|---|---|---|---|---:|---|
| HU-001 | Admin configura período de retención TRD | EP-08 | M09 | S1 | Alta | 5 | Implementada |
| HU-002 | Admin configura clasificación por defecto | EP-08 | M09 | S1 | Alta | 5 | Implementada |

---

## 3. Alcance implementado

| Área | Descripción | Estado |
|---|---|---|
| Backend | Endpoints REST para consulta y actualización de configuración documental | Completado |
| Base de datos | Tabla `sird.configuracion_documental` | Completado |
| Migraciones | Migración Flyway `V1__crear_esquema_y_configuracion_documental.sql` | Completado |
| Validaciones | Validación de período TRD y clasificación por defecto | Completado |
| Swagger | Endpoints visibles en Swagger UI | Completado |
| Manejo de errores | Respuesta estándar de error con `trazaId` | Completado |
| Seguridad | Configuración temporal para pruebas locales | Parcial |
| Auditoría M06 | Registro real de evento `CAMBIO_CONFIGURACION` | Pendiente |

---

## 4. Endpoints implementados

### 4.1 Consultar configuración documental

```http
GET /api/v1/admin/configuracion/documental
```

#### Respuesta exitosa esperada

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

### 4.2 Actualizar configuración documental

```http
PUT /api/v1/admin/configuracion/documental
```

#### Request body

```json
{
  "periodoRetencionAnios": 7,
  "clasificacionPorDefecto": "PUBLICO"
}
```

#### Respuesta exitosa esperada

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

## 5. Reglas de negocio implementadas

| Regla | Historia | Estado |
|---|---|---|
| El período de retención TRD tiene valor por defecto de 5 años | HU-001 | Cumplido |
| El período de retención TRD es obligatorio | HU-001 | Cumplido |
| El período de retención TRD debe ser mínimo 1 año | HU-001 | Cumplido |
| El período de retención TRD no debe superar 50 años | HU-001 | Cumplido |
| La clasificación por defecto es obligatoria | HU-002 | Cumplido |
| Solo `PUBLICO` puede ser clasificación por defecto | HU-002 | Cumplido |
| Solo `INTERNO` puede ser clasificación por defecto | HU-002 | Cumplido |
| `CONFIDENCIAL` no puede ser clasificación por defecto | HU-002 | Cumplido |
| `RESTRINGIDO` no puede ser clasificación por defecto | HU-002 | Cumplido |

---

## 6. Archivos creados o modificados

| Archivo | Propósito |
|---|---|
| `src/main/resources/db/migration/V1__crear_esquema_y_configuracion_documental.sql` | Migración inicial de configuración documental |
| `src/main/java/pe/gob/regioncusco/sird/shared/api/RespuestaApi.java` | Envoltorio estándar de respuesta exitosa |
| `src/main/java/pe/gob/regioncusco/sird/shared/api/ErrorDetalle.java` | Detalle de errores por campo |
| `src/main/java/pe/gob/regioncusco/sird/shared/api/RespuestaErrorApi.java` | Envoltorio estándar de respuesta de error |
| `src/main/java/pe/gob/regioncusco/sird/shared/exception/GlobalExceptionHandler.java` | Manejo global de excepciones |
| `src/main/java/pe/gob/regioncusco/sird/shared/config/OpenApiConfig.java` | Configuración general de Swagger/OpenAPI |
| `src/main/java/pe/gob/regioncusco/sird/shared/security/SecurityConfig.java` | Seguridad temporal para pruebas locales |
| `src/main/java/pe/gob/regioncusco/sird/administracion/entity/ClasificacionDocumento.java` | Enum de clasificación documental |
| `src/main/java/pe/gob/regioncusco/sird/administracion/entity/ConfiguracionDocumental.java` | Entidad JPA de configuración documental |
| `src/main/java/pe/gob/regioncusco/sird/administracion/repository/ConfiguracionDocumentalRepository.java` | Repository JPA |
| `src/main/java/pe/gob/regioncusco/sird/administracion/dto/ActualizarConfiguracionDocumentalRequest.java` | DTO de actualización |
| `src/main/java/pe/gob/regioncusco/sird/administracion/dto/ConfiguracionDocumentalResponse.java` | DTO de respuesta |
| `src/main/java/pe/gob/regioncusco/sird/administracion/service/ConfiguracionDocumentalService.java` | Interfaz de servicio |
| `src/main/java/pe/gob/regioncusco/sird/administracion/service/ConfiguracionDocumentalServiceImpl.java` | Implementación de reglas de negocio |
| `src/main/java/pe/gob/regioncusco/sird/administracion/controller/ConfiguracionDocumentalController.java` | Controller REST |
| `docs/pruebas/HU-001-HU-002-configuracion-documental.http` | Pruebas HTTP desde VS Code |
| `scripts/pruebas/probar-HU-001-HU-002.ps1` | Script automático de pruebas |
| `docs/swagger/M09-configuracion-documental-openapi.md` | Documentación del contrato API |
| `docs/historias/HU-001-configuracion-retencion-trd.md` | Documentación funcional de HU-001 |
| `docs/historias/HU-002-clasificacion-por-defecto.md` | Documentación funcional de HU-002 |

---

## 7. Pruebas ejecutadas

| Caso | Método | Endpoint | Resultado esperado | Estado |
|---|---|---|---|---|
| Consultar configuración inicial | GET | `/api/v1/admin/configuracion/documental` | HTTP 200 | Pendiente de evidencia |
| Actualizar configuración válida | PUT | `/api/v1/admin/configuracion/documental` | HTTP 200 | Pendiente de evidencia |
| Período menor a 1 | PUT | `/api/v1/admin/configuracion/documental` | HTTP 400 | Pendiente de evidencia |
| Clasificación `RESTRINGIDO` no permitida | PUT | `/api/v1/admin/configuracion/documental` | HTTP 400 | Pendiente de evidencia |

---

## 8. Evidencias esperadas

| Evidencia | Ubicación sugerida |
|---|---|
| Resultado JSON de pruebas PowerShell | `docs/evidencias/HU-001-HU-002_FECHA.json` |
| Captura de Swagger con endpoints | `docs/evidencias/swagger-HU-001-HU-002.png` |
| Captura de consola Spring Boot | `docs/evidencias/backend-running.png` |
| Captura de Docker con PostgreSQL activo | `docs/evidencias/docker-running.png` |

---

## 9. Definition of Done

### HU-001

| Criterio | Estado |
|---|---|
| El Admin puede configurar período de retención TRD | Cumplido |
| Default inicial de 5 años | Cumplido |
| Persistencia en PostgreSQL | Cumplido |
| Migración Flyway versionada | Cumplido |
| Endpoint documentado | Cumplido |
| Validaciones backend | Cumplido |
| Seguridad con perfil Admin SIRD | Pendiente |
| Auditoría M06 `CAMBIO_CONFIGURACION` | Pendiente |

### HU-002

| Criterio | Estado |
|---|---|
| El Admin puede configurar clasificación por defecto | Cumplido |
| `PUBLICO` permitido como valor default | Cumplido |
| `INTERNO` permitido como valor default | Cumplido |
| `CONFIDENCIAL` rechazado como default | Cumplido |
| `RESTRINGIDO` rechazado como default | Cumplido |
| Persistencia en PostgreSQL | Cumplido |
| Endpoint documentado | Cumplido |
| Aplicación real en ingesta A-1 | Pendiente |
| Seguridad con perfil Admin SIRD | Pendiente |
| Auditoría M06 `CAMBIO_CONFIGURACION` | Pendiente |

---

## 10. Pendientes técnicos

| Pendiente | Relación | Motivo |
|---|---|---|
| Seguridad real con JWT y RBAC | HU-003, HU-006, HU-035 | Actualmente los endpoints están liberados para pruebas |
| Auditoría M06 | HU-040 | Se integrará cuando exista el módulo de auditoría |
| Aplicación de clasificación default en ingesta | HU-008, HU-009 | Se usará cuando se implemente contrato A-1 |
| Pruebas automatizadas JUnit | Transversal | Se agregarán progresivamente |

---

## 11. Riesgos

| Riesgo | Nivel | Mitigación |
|---|---|---|
| Endpoints administrativos temporalmente sin seguridad | Medio | Restringir en historias de seguridad |
| Reglas TRD pueden cambiar | Bajo | Mantener configuración parametrizable |
| Auditoría aún no implementada | Medio | Registrar deuda técnica y atender en HU-040 |

---

## 12. Próximos pasos

Para el siguiente día de desarrollo se recomienda avanzar con:

- HU-003: Admin configura parámetros de seguridad JWT.
- HU-004: Admin gestiona feriados institucionales para el timer.

Estas historias continúan dentro del módulo M09 y preparan la base para seguridad JWT y cálculo de días hábiles.
