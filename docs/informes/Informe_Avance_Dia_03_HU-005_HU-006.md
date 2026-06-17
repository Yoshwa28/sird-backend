# Informe de Avance Backend SIRD — Día 3

**Proyecto:** Sistema Institucional de Repositorio Digital - SIRD  
**Institución:** Gobierno Regional del Cusco  
**Versión del informe:** v1.0  
**Fecha:** 17/06/2026  
**Responsable:** Yoshwa  
**Sprint:** S1  
**Módulo trabajado:** M09 - Administración del Sistema  
**Historias trabajadas:** HU-005, HU-006  

---

## 1. Resumen general

Durante el Día 3 se continuó con la implementación backend del proyecto SIRD, manteniendo como fuente principal de verdad los documentos oficiales:

- Product Backlog v11.3.
- Sprint Backlog v11.3.
- Épicas v11.3.
- Historias de Usuario v1.5.
- Definition of Done v11.3.

Se implementaron dos historias de usuario correspondientes al módulo M09 - Administración del Sistema:

- HU-005: Admin configura límites del Espacio Colaborativo.
- HU-006: Admin gestiona usuarios y 5 perfiles RBAC.

El desarrollo incluyó persistencia en PostgreSQL, migración versionada con Flyway, endpoints REST, validaciones backend, respuesta estándar, manejo centralizado de errores, documentación Swagger/OpenAPI, documentación funcional por historia, archivo de pruebas HTTP, script PowerShell de pruebas y generación de evidencia JSON.

Adicionalmente, se realizó limpieza de BOM UTF-8 en archivos Java, SQL, Markdown, HTTP, PowerShell y JSON para evitar errores de compilación, migración o lectura en el repositorio.

---

## 2. Historias de usuario trabajadas

| ID | Historia | Épica | Módulo | Sprint | Prioridad | Story Points | Estado |
|---|---|---|---|---|---|---:|---|
| HU-005 | Admin configura límites del Espacio Colaborativo | EP-08 | M09 | S1 | Alta | 3 | Implementada |
| HU-006 | Admin gestiona usuarios y 5 perfiles RBAC | EP-08 | M09 | S1 | Alta | 8 | Implementada |

---

## 3. Alcance implementado

| Área | Descripción | Estado |
|---|---|---|
| Backend | Endpoint REST para listar límites del Espacio Colaborativo | Completado |
| Backend | Endpoint REST para actualizar límite por tipo de Espacio Colaborativo | Completado |
| Backend | Endpoints REST para CRUD de usuarios SIRD | Completado |
| Backend | Endpoint REST para activar/desactivar usuarios | Completado |
| Backend | Endpoint REST para listar perfiles RBAC | Completado |
| Base de datos | Tabla `sird.configuracion_limite_espacio_colaborativo` | Completado |
| Base de datos | Tabla `sird.usuario_sird` | Completado |
| Migraciones | Migración Flyway `V3__limites_ec_y_usuarios_rbac.sql` | Completado |
| Validaciones | Validación de rango permitido 1 a 100 GB para límites EC | Completado |
| Validaciones | Validación de tipos `EXPEDIENTE`, `PLAN`, `PERFIL` | Completado |
| Validaciones | Validación de datos obligatorios de usuario | Completado |
| Validaciones | Validación de formato de correo | Completado |
| Validaciones | Validación de correo único | Completado |
| Validaciones | Validación de 5 perfiles RBAC permitidos | Completado |
| Swagger | Endpoints visibles en Swagger UI y OpenAPI exportado | Completado |
| Manejo de errores | Uso de respuesta estándar de error con `trazaId` | Completado |
| Pruebas | Archivo `.http` y script PowerShell de pruebas | Completado |
| Evidencia | Archivo JSON de evidencia generado | Completado |
| Seguridad | Configuración temporal para pruebas locales | Parcial |
| Auditoría M06 | Registro real de eventos administrativos | Pendiente |

---

## 4. Endpoints implementados

### 4.1 Listar límites del Espacio Colaborativo

```http
GET /api/v1/admin/configuracion/limites-ec
```

#### Respuesta exitosa obtenida

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
      "creadoEn": "2026-06-17T19:41:05.288826Z",
      "actualizadoEn": null
    },
    {
      "id": 2,
      "tipoEspacio": "PLAN",
      "limiteGb": 20,
      "activo": true,
      "creadoEn": "2026-06-17T19:41:05.288826Z",
      "actualizadoEn": null
    },
    {
      "id": 3,
      "tipoEspacio": "PERFIL",
      "limiteGb": 5,
      "activo": true,
      "creadoEn": "2026-06-17T19:41:05.288826Z",
      "actualizadoEn": null
    }
  ],
  "fechaHora": "2026-06-17T14:47:36.0623829-05:00"
}
```

### 4.2 Actualizar límite del Espacio Colaborativo por tipo

```http
PUT /api/v1/admin/configuracion/limites-ec/{tipoEspacio}
```

#### Request body ejecutado

```json
{
  "limiteGb": 25
}
```

#### Respuesta exitosa obtenida

```json
{
  "resultado": true,
  "mensaje": "Límite del Espacio Colaborativo actualizado correctamente.",
  "datos": {
    "id": 1,
    "tipoEspacio": "EXPEDIENTE",
    "limiteGb": 25,
    "activo": true,
    "creadoEn": "2026-06-17T19:41:05.288826Z",
    "actualizadoEn": null
  },
  "fechaHora": "2026-06-17T14:48:09.336665-05:00"
}
```

### 4.3 Validar límite inválido mayor a 100 GB

```http
PUT /api/v1/admin/configuracion/limites-ec/PLAN
```

#### Request body ejecutado

```json
{
  "limiteGb": 101
}
```

#### Resultado obtenido

```text
HTTP 400
```

### 4.4 Listar perfiles RBAC

```http
GET /api/v1/admin/perfiles
```

#### Respuesta exitosa obtenida

```json
{
  "resultado": true,
  "mensaje": "Listado de perfiles RBAC obtenido correctamente.",
  "datos": [
    "ADMIN_SIRD",
    "GERENTE_SUBGERENTE",
    "JEFE_DEPENDENCIA_PROYECTO",
    "ESPECIALISTA_RESPONSABLE",
    "TECNICO_ADMINISTRATIVO"
  ],
  "fechaHora": "2026-06-17T14:48:19.5433233-05:00"
}
```

### 4.5 Listar usuarios SIRD

```http
GET /api/v1/admin/usuarios
```

#### Respuesta exitosa obtenida

```json
{
  "resultado": true,
  "mensaje": "Listado de usuarios obtenido correctamente.",
  "datos": [
    {
      "id": 1,
      "nombres": "Administrador",
      "apellidos": "SIRD",
      "correo": "admin.sird@regioncusco.gob.pe",
      "cargo": "Administrador del Sistema",
      "unidad": "Oficina de Tecnologías de la Información",
      "perfil": "ADMIN_SIRD",
      "activo": true,
      "creadoEn": "2026-06-17T19:41:05.288826Z",
      "actualizadoEn": null
    }
  ],
  "fechaHora": "2026-06-17T14:48:25.0542133-05:00"
}
```

### 4.6 Crear usuario SIRD

```http
POST /api/v1/admin/usuarios
```

#### Request body ejecutado

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

#### Respuesta exitosa obtenida

```json
{
  "resultado": true,
  "mensaje": "Usuario SIRD creado correctamente.",
  "datos": {
    "id": 2,
    "nombres": "Juan",
    "apellidos": "Puma Quispe",
    "correo": "juan.puma@regioncusco.gob.pe",
    "cargo": "Especialista Documental",
    "unidad": "Gerencia Regional de Planeamiento",
    "perfil": "ESPECIALISTA_RESPONSABLE",
    "activo": true,
    "creadoEn": "2026-06-17T14:48:40.8813521-05:00",
    "actualizadoEn": null
  },
  "fechaHora": "2026-06-17T14:48:40.9008568-05:00"
}
```

### 4.7 Validar usuario duplicado por correo

```http
POST /api/v1/admin/usuarios
```

#### Resultado obtenido

```text
HTTP 409
```

### 4.8 Obtener usuario por ID

```http
GET /api/v1/admin/usuarios/{usuarioId}
```

### 4.9 Actualizar usuario

```http
PUT /api/v1/admin/usuarios/{usuarioId}
```

### 4.10 Activar o desactivar usuario

```http
PATCH /api/v1/admin/usuarios/{usuarioId}/estado
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
| Los tipos de Espacio Colaborativo permitidos son `EXPEDIENTE`, `PLAN` y `PERFIL` | HU-005 | Cumplido |
| El límite mínimo permitido es 1 GB | HU-005 | Cumplido |
| El límite máximo permitido es 100 GB | HU-005 | Cumplido |
| Se inicializan límites por defecto para `EXPEDIENTE`, `PLAN` y `PERFIL` | HU-005 | Cumplido |
| Se permite actualizar el límite por tipo de Espacio Colaborativo | HU-005 | Cumplido |
| El sistema gestiona usuarios con nombres, apellidos, correo, cargo, unidad y perfil | HU-006 | Cumplido |
| Los perfiles permitidos son exactamente 5 perfiles RBAC | HU-006 | Cumplido |
| El usuario inicial `ADMIN_SIRD` se registra por migración | HU-006 | Cumplido |
| El correo de usuario debe ser único | HU-006 | Cumplido |
| El correo se normaliza a minúsculas | HU-006 | Cumplido |
| Se permite activar y desactivar usuarios | HU-006 | Cumplido |
| La creación duplicada de usuario retorna HTTP 409 | HU-006 | Cumplido |

---

## 6. Archivos creados o modificados

| Archivo | Propósito |
|---|---|
| `src/main/resources/db/migration/V3__limites_ec_y_usuarios_rbac.sql` | Migración de límites EC y usuarios RBAC |
| `src/main/java/pe/gob/regioncusco/sird/administracion/entity/TipoEspacioColaborativo.java` | Enum de tipos de Espacio Colaborativo |
| `src/main/java/pe/gob/regioncusco/sird/administracion/entity/PerfilUsuario.java` | Enum de perfiles RBAC |
| `src/main/java/pe/gob/regioncusco/sird/administracion/entity/ConfiguracionLimiteEspacioColaborativo.java` | Entidad JPA de límites EC |
| `src/main/java/pe/gob/regioncusco/sird/administracion/entity/UsuarioSird.java` | Entidad JPA de usuario SIRD |
| `src/main/java/pe/gob/regioncusco/sird/administracion/repository/ConfiguracionLimiteEspacioColaborativoRepository.java` | Repository JPA para límites EC |
| `src/main/java/pe/gob/regioncusco/sird/administracion/repository/UsuarioSirdRepository.java` | Repository JPA para usuarios |
| `src/main/java/pe/gob/regioncusco/sird/administracion/dto/ActualizarLimiteEspacioColaborativoRequest.java` | DTO para actualizar límite EC |
| `src/main/java/pe/gob/regioncusco/sird/administracion/dto/LimiteEspacioColaborativoResponse.java` | DTO de respuesta de límites EC |
| `src/main/java/pe/gob/regioncusco/sird/administracion/dto/CrearUsuarioSirdRequest.java` | DTO para crear usuario |
| `src/main/java/pe/gob/regioncusco/sird/administracion/dto/ActualizarUsuarioSirdRequest.java` | DTO para actualizar usuario |
| `src/main/java/pe/gob/regioncusco/sird/administracion/dto/CambiarEstadoUsuarioRequest.java` | DTO para activar/desactivar usuario |
| `src/main/java/pe/gob/regioncusco/sird/administracion/dto/UsuarioSirdResponse.java` | DTO de respuesta de usuario |
| `src/main/java/pe/gob/regioncusco/sird/administracion/service/ConfiguracionLimiteEspacioColaborativoService.java` | Interfaz de servicio para límites EC |
| `src/main/java/pe/gob/regioncusco/sird/administracion/service/ConfiguracionLimiteEspacioColaborativoServiceImpl.java` | Implementación de reglas de límites EC |
| `src/main/java/pe/gob/regioncusco/sird/administracion/service/UsuarioSirdService.java` | Interfaz de servicio para usuarios |
| `src/main/java/pe/gob/regioncusco/sird/administracion/service/UsuarioSirdServiceImpl.java` | Implementación de reglas de usuarios |
| `src/main/java/pe/gob/regioncusco/sird/administracion/controller/ConfiguracionLimiteEspacioColaborativoController.java` | Controller REST para límites EC |
| `src/main/java/pe/gob/regioncusco/sird/administracion/controller/UsuarioSirdController.java` | Controller REST para usuarios y perfiles |
| `docs/pruebas/HU-005-HU-006-limites-ec-usuarios-rbac.http` | Pruebas HTTP desde VS Code |
| `scripts/pruebas/probar-HU-005-HU-006.ps1` | Script automático de pruebas |
| `docs/swagger/M09-limites-ec-usuarios-rbac-openapi.md` | Documentación del contrato API |
| `docs/swagger/openapi/sird-openapi-dia-03.json` | Exportación OpenAPI JSON del Día 3 |
| `docs/historias/HU-005-configuracion-limites-espacio-colaborativo.md` | Documentación funcional de HU-005 |
| `docs/historias/HU-006-gestion-usuarios-perfiles-rbac.md` | Documentación funcional de HU-006 |
| `docs/evidencias/HU-005-HU-006_20260617_144933.json` | Evidencia JSON de pruebas automáticas |
| `docs/informes/Informe_Avance_Dia_03_HU-005_HU-006.md` | Informe de avance del Día 3 |

---

## 7. Pruebas ejecutadas

| Caso | Método | Endpoint | Resultado esperado | Estado |
|---|---|---|---|---|
| Listar límites EC | GET | `/api/v1/admin/configuracion/limites-ec` | HTTP 200 | Ejecutado correctamente |
| Actualizar límite EC válido | PUT | `/api/v1/admin/configuracion/limites-ec/EXPEDIENTE` | HTTP 200 | Ejecutado correctamente |
| Límite EC mayor a 100 | PUT | `/api/v1/admin/configuracion/limites-ec/PLAN` | HTTP 400 | Ejecutado correctamente |
| Listar perfiles RBAC | GET | `/api/v1/admin/perfiles` | HTTP 200 | Ejecutado correctamente |
| Listar usuarios | GET | `/api/v1/admin/usuarios` | HTTP 200 | Ejecutado correctamente |
| Crear usuario válido | POST | `/api/v1/admin/usuarios` | HTTP 200 | Ejecutado correctamente |
| Crear usuario duplicado | POST | `/api/v1/admin/usuarios` | HTTP 409 | Ejecutado correctamente |
| Ejecutar script automático PowerShell | PS1 | `scripts/pruebas/probar-HU-005-HU-006.ps1` | Generar JSON | Ejecutado correctamente |
| Exportar OpenAPI Día 3 | GET | `/v3/api-docs` | Archivo JSON | Ejecutado correctamente |
| Limpieza de BOM en documentación y evidencias | PowerShell | `docs`, `scripts` | Sin BOM UTF-8 | Ejecutado correctamente |

---

## 8. Evidencias generadas

| Evidencia | Ubicación |
|---|---|
| Resultado JSON de pruebas PowerShell | `docs/evidencias/HU-005-HU-006_20260617_144933.json` |
| Contrato OpenAPI exportado | `docs/swagger/openapi/sird-openapi-dia-03.json` |
| Documentación del contrato API | `docs/swagger/M09-limites-ec-usuarios-rbac-openapi.md` |
| Pruebas manuales HTTP | `docs/pruebas/HU-005-HU-006-limites-ec-usuarios-rbac.http` |
| Script de pruebas automáticas | `scripts/pruebas/probar-HU-005-HU-006.ps1` |
| Documentación funcional HU-005 | `docs/historias/HU-005-configuracion-limites-espacio-colaborativo.md` |
| Documentación funcional HU-006 | `docs/historias/HU-006-gestion-usuarios-perfiles-rbac.md` |
| Evidencia de compilación Maven | Consola: `mvn clean compile` con `BUILD SUCCESS` |
| Evidencia de pruebas manuales | Consola PowerShell con respuestas HTTP 200, 400 y 409 |
| Evidencia de limpieza BOM | Consola PowerShell con archivos procesados |

---

## 9. Definition of Done

### HU-005

| Criterio | Estado |
|---|---|
| El Admin puede configurar límites del Espacio Colaborativo | Cumplido |
| Se soporta tipo `EXPEDIENTE` | Cumplido |
| Se soporta tipo `PLAN` | Cumplido |
| Se soporta tipo `PERFIL` | Cumplido |
| Se valida rango de 1 a 100 GB | Cumplido |
| Persistencia en PostgreSQL | Cumplido |
| Migración Flyway versionada | Cumplido |
| Endpoint documentado | Cumplido |
| Pruebas manuales ejecutadas | Cumplido |
| Evidencia JSON generada | Cumplido |
| Aplicación real de límite en creación/subida del EC | Pendiente |
| Seguridad con perfil Admin SIRD | Pendiente |
| Auditoría M06 `CAMBIO_CONFIGURACION` | Pendiente |

### HU-006

| Criterio | Estado |
|---|---|
| El Admin puede gestionar usuarios | Cumplido |
| Se registran nombres del usuario | Cumplido |
| Se registra cargo del usuario | Cumplido |
| Se registra unidad del usuario | Cumplido |
| Se asigna perfil RBAC | Cumplido |
| Se soportan 5 perfiles RBAC | Cumplido |
| Se valida correo único | Cumplido |
| Se permite activar y desactivar usuarios | Cumplido |
| Persistencia en PostgreSQL | Cumplido |
| Migración Flyway versionada | Cumplido |
| Endpoint documentado | Cumplido |
| Pruebas manuales ejecutadas | Cumplido |
| Evidencia JSON generada | Cumplido |
| Seguridad JWT/RBAC real | Pendiente |
| Auditoría M06 `GESTION_USUARIO` | Pendiente |

---

## 10. Pendientes técnicos

| Pendiente | Relación | Motivo |
|---|---|---|
| Aplicar límites reales al Espacio Colaborativo | HU-010, HU-011, HU-021 | La configuración queda lista, pero el flujo operativo EC aún no está implementado |
| Seguridad real con RBAC | HU-006, HU-035 | Actualmente los endpoints están liberados para pruebas locales |
| Autenticación JWT real | HU-003, HU-035 | Los parámetros JWT ya existen, pero falta integración completa de autenticación |
| Auditoría M06 | HU-040 | Se integrará cuando exista el módulo de auditoría |
| Registro de eventos `CAMBIO_CONFIGURACION` | HU-040 | Debe auditar cambios de límites EC |
| Registro de eventos `GESTION_USUARIO` | HU-040 | Debe auditar creación, actualización y cambio de estado de usuarios |
| Pruebas automatizadas JUnit | Transversal | Se agregarán progresivamente |
| Capturas visuales de Swagger y consola | Evidencia documental | Pueden adjuntarse al cierre del sprint |

---

## 11. Riesgos

| Riesgo | Nivel | Mitigación |
|---|---|---|
| Endpoints administrativos temporalmente sin seguridad | Medio | Restringir al implementar JWT/RBAC |
| Límites EC aún no aplicados al almacenamiento real | Medio | Integrar con HU-010, HU-011 y HU-021 |
| Usuarios creados por pruebas pueden generar duplicidad al reejecutar scripts | Bajo | El caso duplicado se considera esperado con HTTP 409 |
| Perfiles RBAC podrían requerir permisos más granulares | Medio | Mantener los 5 perfiles base y evolucionar a matriz de permisos si el backlog lo requiere |
| Auditoría aún no implementada | Medio | Registrar deuda técnica y atender en HU-040 |
| Manejo temporal de seguridad puede diferir del flujo final | Medio | Alinear con HU-035 al implementar autenticación/autorización |

---

## 12. Próximos pasos

Para el siguiente día de desarrollo se recomienda avanzar con:

- HU-007: Admin gestiona API Keys de sistemas institucionales.
- HU-008: Sistema recibe documento vía contrato A-1.

Estas historias continúan el Sprint S1 y conectan la administración del sistema con la interoperabilidad institucional y la ingesta documental inicial.

HU-007 permitirá registrar y administrar API Keys para sistemas institucionales externos, mientras que HU-008 habilitará la recepción inicial de documentos mediante el contrato A-1.
