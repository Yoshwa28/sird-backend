# HU-006 - Admin gestiona usuarios y 5 perfiles RBAC

**Proyecto:** SIRD  
**Versión:** v1.0  
**Fecha:** 17/06/2026  
**Épica:** EP-08 - Administración del Sistema  
**Módulo:** M09  
**Sprint:** S1  
**Prioridad:** Alta  
**Story Points:** 8  

---

## 1. Descripción funcional

Como Administrador SIRD, necesito gestionar usuarios y perfiles RBAC para controlar el acceso al sistema según las responsabilidades institucionales.

---

## 2. Regla principal

El sistema debe permitir gestionar usuarios con los siguientes datos:

| Campo | Descripción |
|---|---|
| nombres | Nombres del usuario |
| apellidos | Apellidos del usuario |
| correo | Correo institucional |
| cargo | Cargo institucional |
| unidad | Unidad orgánica |
| perfil | Perfil RBAC asignado |
| activo | Estado del usuario |

Los 5 perfiles orgánicos permitidos son:

```text
ADMIN_SIRD
GERENTE_SUBGERENTE
JEFE_DEPENDENCIA_PROYECTO
ESPECIALISTA_RESPONSABLE
TECNICO_ADMINISTRATIVO
```

---

## 3. Alcance implementado

| Área | Estado |
|---|---|
| Backend | Implementado |
| Base de datos | Implementado |
| Swagger | Implementado |
| Validaciones | Implementado |
| CRUD de usuarios | Implementado |
| Listado de perfiles | Implementado |
| Activación/desactivación de usuarios | Implementado |
| Validación de correo único | Implementado |
| Seguridad real con JWT/RBAC | Pendiente para HU-035/HU-006 fase seguridad |
| Auditoría M06 | Pendiente |

---

## 4. Endpoint relacionado

```http
GET /api/v1/admin/usuarios
GET /api/v1/admin/usuarios/{usuarioId}
POST /api/v1/admin/usuarios
PUT /api/v1/admin/usuarios/{usuarioId}
PATCH /api/v1/admin/usuarios/{usuarioId}/estado
GET /api/v1/admin/perfiles
```

---

## 5. Validaciones

| Campo | Regla |
|---|---|
| `nombres` | Obligatorio, 2 a 100 caracteres |
| `apellidos` | Obligatorio, 2 a 100 caracteres |
| `correo` | Obligatorio, formato email, único |
| `cargo` | Obligatorio, 2 a 120 caracteres |
| `unidad` | Obligatorio, 2 a 150 caracteres |
| `perfil` | Obligatorio |
| `perfil` | Solo uno de los 5 perfiles permitidos |
| `activo` | Obligatorio para cambio de estado |

---

## 6. Casos de prueba

### Caso exitoso: crear usuario

Request:

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

Resultado esperado:

```text
HTTP 200
Usuario SIRD creado correctamente.
```

### Caso inválido: correo duplicado

Request:

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

Resultado esperado:

```text
HTTP 400
ERROR_REGLA_NEGOCIO
```

### Caso exitoso: activar/desactivar usuario

Request:

```json
{
  "activo": false
}
```

Resultado esperado:

```text
HTTP 200
Estado del usuario SIRD actualizado correctamente.
```

---

## 7. Archivos implementados

| Archivo | Propósito |
|---|---|
| `V3__limites_ec_y_usuarios_rbac.sql` | Migración de límites EC y usuarios RBAC |
| `PerfilUsuario.java` | Enum de perfiles RBAC |
| `UsuarioSird.java` | Entidad JPA |
| `UsuarioSirdRepository.java` | Acceso a datos |
| `CrearUsuarioSirdRequest.java` | DTO de creación |
| `ActualizarUsuarioSirdRequest.java` | DTO de actualización |
| `CambiarEstadoUsuarioRequest.java` | DTO de activación/desactivación |
| `UsuarioSirdResponse.java` | DTO de respuesta |
| `UsuarioSirdService.java` | Contrato de servicio |
| `UsuarioSirdServiceImpl.java` | Lógica de negocio |
| `UsuarioSirdController.java` | Endpoint REST |

---

## 8. Definition of Done

| Criterio | Estado |
|---|---|
| CRUD de usuarios implementado | Cumplido |
| Se registran nombres, cargo, unidad y perfil | Cumplido |
| Se soportan 5 perfiles RBAC | Cumplido |
| Se valida correo único | Cumplido |
| Se permite activar/desactivar usuario | Cumplido |
| Persistencia en PostgreSQL | Cumplido |
| Migración Flyway versionada | Cumplido |
| Documentación Swagger | Cumplido |
| Seguridad JWT/RBAC real | Pendiente |
| Auditoría de cambios de usuario | Pendiente |
