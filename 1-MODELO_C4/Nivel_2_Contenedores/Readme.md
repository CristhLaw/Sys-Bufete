# C4 Nivel 2 – Contenedores

## Descripción

Este diagrama representa el **Nivel 2 del Modelo C4**, mostrando los
principales contenedores que conforman la arquitectura del sistema IURIS.
El sistema adopta una **arquitectura monolítica por capas**, donde todas
las funcionalidades del backend se ejecutan dentro de una única aplicación.

## Contenedores

### Aplicación Web

Cliente web desarrollado en Angular desde donde abogados, administradores
y personal del bufete realizan sus operaciones.

### Aplicación Móvil

Cliente móvil desarrollado en Flutter, utilizado principalmente por
clientes y gerentes para acceder a las funcionalidades del sistema.

### Backend IURIS

Aplicación central desarrollada con Spring Boot bajo una arquitectura
monolítica por capas (`controller → service → repository → model`).

Implementa:

- Lógica de negocio
- Seguridad
- Gestión de usuarios
- Casos legales
- Agenda
- Documentos
- Reportes
- Comunicaciones

### PostgreSQL

Base de datos única donde se almacena toda la información del sistema.

### Cloudinary

Servicio externo para almacenamiento de documentos legales.

### WebRTC

Servicio externo para videollamadas entre abogados y clientes.

## Relaciones

- Las aplicaciones Web y Móvil consumen la API REST del Backend.
- El Backend administra toda la lógica del negocio.
- PostgreSQL almacena toda la información del sistema.
- Cloudinary gestiona el almacenamiento de documentos.
- WebRTC proporciona el servicio de videollamadas.
