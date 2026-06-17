# HU-005 - Admin configura límites del Espacio Colaborativo

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

Como Administrador SIRD, necesito configurar los límites de almacenamiento del Espacio Colaborativo para controlar el uso máximo permitido según el tipo de espacio.

---

## 2. Regla principal

El sistema debe permitir configurar límites por tipo de Espacio Colaborativo:

```text
EXPEDIENTE
PLAN
PERFIL
```

El rango permitido es:

```text
1 GB a 100 GB
```

Valores iniciales implementados:

| Tipo | Límite inicial |
|---|---:|
| EXPEDIENTE | 10 GB |
| PLAN | 20 GB |
| PERFIL | 5 GB |

---

## 3. Alcance implementado

| Área | Estado |
|---|---|
| Backend | Implementado |
| Base de datos | Implementado |
| Swagger | Implementado |
| Validaciones | Implementado |
| Persistencia PostgreSQL | Implementado |
| Migración Flyway | Implementado |
| Aplicación real en Espacio Colaborativo | Pendiente para HU-010/HU-011/HU-021 |
| Seguridad real con ADMIN_SIRD | Pendiente |
| Auditoría M06 | Pendiente |

---

## 4. Endpoint relacionado

```http
GET /api/v1/admin/configuracion/limites-ec
PUT /api/v1/admin/configuracion/limites-ec/{tipoEspacio}
```

---

## 5. Validaciones

| Campo | Regla |
|---|---|
| `tipoEspacio` | Debe ser `EXPEDIENTE`, `PLAN` o `PERFIL` |
| `limiteGb` | Obligatorio |
| `limiteGb` | Mínimo 1 |
| `limiteGb` | Máximo 100 |

---

## 6. Casos de prueba

### Caso exitoso

Request:

```json
{
  "limiteGb": 25
}
```

Resultado esperado:

```text
HTTP 200
Límite del Espacio Colaborativo actualizado correctamente.
```

### Caso inválido

Request:

```json
{
  "limiteGb": 101
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
| `V3__limites_ec_y_usuarios_rbac.sql` | Migración de límites EC y usuarios RBAC |
| `TipoEspacioColaborativo.java` | Enum de tipos de espacio |
| `ConfiguracionLimiteEspacioColaborativo.java` | Entidad JPA |
| `ConfiguracionLimiteEspacioColaborativoRepository.java` | Acceso a datos |
| `ActualizarLimiteEspacioColaborativoRequest.java` | DTO de actualización |
| `LimiteEspacioColaborativoResponse.java` | DTO de respuesta |
| `ConfiguracionLimiteEspacioColaborativoService.java` | Contrato de servicio |
| `ConfiguracionLimiteEspacioColaborativoServiceImpl.java` | Lógica de negocio |
| `ConfiguracionLimiteEspacioColaborativoController.java` | Endpoint REST |

---

## 8. Definition of Done

| Criterio | Estado |
|---|---|
| Se pueden configurar límites por tipo de EC | Cumplido |
| Se soporta tipo `EXPEDIENTE` | Cumplido |
| Se soporta tipo `PLAN` | Cumplido |
| Se soporta tipo `PERFIL` | Cumplido |
| Se valida rango 1 a 100 GB | Cumplido |
| Persistencia en PostgreSQL | Cumplido |
| Migración Flyway versionada | Cumplido |
| Documentación Swagger | Cumplido |
| Aplicación real en EC | Pendiente |
| Seguridad ADMIN_SIRD | Pendiente |
| Auditoría de cambio de configuración | Pendiente |
