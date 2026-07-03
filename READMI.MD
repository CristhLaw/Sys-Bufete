# IURIS — Sistema de Gestión para Bufete de Abogados

Proyecto académico del curso **Análisis y Diseño de Sistemas** (Universidad Peruana Unión — UPeU, Juliaca).

IURIS centraliza la gestión integral de un mini bufete de abogados: usuarios y roles, casos legales, agenda y audiencias, documentos, comunicación con clientes y reportes ejecutivos del bufete.

## Arquitectura

El sistema se implementa como una **aplicación monolítica organizada en capas** (`controller → service → repository → model`), en un único artefacto **Spring Boot** desplegado como proceso único, con una **única base de datos PostgreSQL** compartida por todos los módulos funcionales.

Los dominios funcionales (Autenticación, Usuarios, Casos Legales, Agenda y Audiencias, Documentos, Comunicación, Reportes) se organizan como módulos internos dentro de las mismas capas compartidas, en lugar de servicios independientes. Como proyecto de referencia arquitectónica se toma `San_Martin_Backend-ImportaExportarRepuestosDesdeExcel`, del cual se adoptan el patrón CRUD genérico, la seguridad JWT y el manejo centralizado de excepciones.

> El detalle de esta decisión, las alternativas evaluadas (microservicios, monolito modular por dominio, arquitectura hexagonal) y sus consecuencias están documentados en [`ADR-001`](./4-Desicion%20arquitectonico%20(ADRs)/ADR-001-arquitectura-monolitica-por-capas.md).

### Stack tecnológico

| Capa | Tecnología |
|---|---|
| Aplicación Web | Angular |
| Aplicación Móvil | Flutter |
| Backend | Spring Boot (Java 17) |
| Base de datos | PostgreSQL |
| Almacenamiento de documentos | Cloudinary |
| Videollamadas | WebRTC |

### Actores del sistema

Cliente · Abogado Principal · Abogado Asistente · Secretaria · Administrador · Gerente/Socio

## Estructura del repositorio

```
Sys-Bufete/
├── 1-MODELO_C4/                     Modelo C4 del sistema (Niveles 1-3)
│   ├── Nivel_1_Contexto/            Actores, sistema y servicios externos
│   ├── Nivel_2_Contenedores/        Web, Móvil, Backend, PostgreSQL
│   └── Nivel_3_Componentes/         Controllers, Services, Repositories, Seguridad...
│
├── 2-MODELO_UML/
│   ├── Comportamiento/
│   │   ├── 1-Casos_Uso/             Diagrama general + 4 diagramas por módulo (M1-M4)
│   │   │   └── Especificaciones/    Especificaciones detalladas de casos de uso clave
│   │   ├── 2-Secuencia/             Diagramas de secuencia (DS01-DS08)
│   │   ├── 3-Actividades/           Diagramas de actividades (AD01-AD08)
│   │   └── 4-Estado/                Diagramas de estado (DE01-DE06)
│   └── Estructural/
│       └── 1-Clases/                Diagrama de clases — modelo de dominio completo
│
├── 3-PATRONES DE DISEÑO/            Observer, Strategy, Singleton, Adapter
│
├── 4-Desicion arquitectonico (ADRs)/
│   └── ADR-001-...md                Adopción de arquitectura monolítica por capas
│
├── src/main/java/com/LexTech/       Código fuente (scaffold inicial)
└── pom.xml                          Configuración Maven
```

## Modelo C4

| Nivel | Contenido |
|---|---|
| **Nivel 1 — Contexto** | Actores, Sistema IURIS y sistemas externos (Cloudinary, WebRTC) |
| **Nivel 2 — Contenedores** | App Web (Angular), App Móvil (Flutter), Backend IURIS (Spring Boot monolítico), PostgreSQL |
| **Nivel 3 — Componentes** | Controllers, Services, Repositories, Entidades, DTOs/Mappers, Seguridad (JWT), Configuración |

## Modelo UML

**Casos de uso** — organizados en un diagrama general (`CU00`) y cuatro diagramas por módulo funcional:

| Módulo | Alcance |
|---|---|
| M1 — Autenticación y Perfil | Iniciar/cerrar sesión, recuperar y restablecer contraseña |
| M2 — Casos y Reportes | Solicitar consulta, registrar caso, asignar abogado, actualizar/cerrar caso, reportes |
| M3 — Documentos y Comunicación | Subir/consultar/descargar documento, mensajería, videollamada |
| M4 — Agenda y Audiencias | Registrar audiencia, programar/reprogramar/cancelar cita |

**Diagramas de comportamiento** — cada flujo cuenta con su diagrama de secuencia (`DS`) y su diagrama de actividades (`AD`) equivalente: Iniciar Sesión, Registrar Caso, Asignar Abogado, Subir Documento, Generar Reporte, Actualizar Estado del Caso, Programar Audiencia, Realizar Videollamada.

**Diagramas de estado** — ciclo de vida de las entidades principales: Caso Legal, Audiencia, Documento, Cita, Mensaje, Reporte.

**Diagrama de clases** — modelo de dominio completo con las entidades `Cliente`, `Abogado`, `Administrador`, `Caso`, `Expediente`, `Audiencia`, `Cita`, `Documento`, `Resolución`, `ActuaciónProcesal`, `Pago`, `Mensaje`, `Conversación`, `Notificación`, `Videollamada`, `Rol`, `Permiso`, `Sede`, `EspecialidadLegal`, `AccesoDocumento`, `Auditoría`, `ConfiguraciónSistema`, junto con sus enumeraciones asociadas (`EstadoCaso`, `EstadoCita`, `EstadoDocumento`, `EstadoAudiencia`, `EstadoPago`, `RolUsuario`, `TipoDocumento`, `TipoMensaje`, `TipoNotificacion`, `TipoActuacion`).

## Patrones de diseño aplicados

| Patrón | Categoría | Uso en IURIS |
|---|---|---|
| **Observer** | Comportamiento | Notificación interna entre módulos ante cambios relevantes (p. ej. cambio de estado de un caso) |
| **Strategy** | Comportamiento | Selección intercambiable de algoritmos/comportamientos de negocio |
| **Singleton** | Creacional | Instancia única de recursos compartidos del sistema |
| **Adapter** | Estructural | Integración con el proveedor externo de almacenamiento (`CloudinaryAdapter`) |

## Decisiones de arquitectura (ADRs)

| ADR | Título | Estado |
|---|---|---|
| [ADR-001](./4-Desicion%20arquitectonico%20(ADRs)/ADR-001-arquitectura-monolitica-por-capas.md) | Adopción de arquitectura monolítica por capas | Aceptado |

## Estado actual del proyecto

El repositorio contiene actualmente el **modelado y la documentación completa** del sistema (C4, UML, patrones de diseño, ADR). El código fuente en `src/` corresponde aún a un scaffold inicial de Maven/IntelliJ, pendiente de implementación siguiendo la arquitectura documentada.

## Requisitos

- Java 17
- Maven
- PostgreSQL
