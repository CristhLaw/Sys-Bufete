# C4 Nivel 3 – Componentes del Backend

## Descripción

Este diagrama representa el **Nivel 3 del Modelo C4**, mostrando la organización interna del contenedor **Backend IURIS**.

La aplicación sigue una **arquitectura monolítica por capas**, donde cada componente posee una responsabilidad claramente definida y colabora con los demás para implementar la lógica del negocio.

## Componentes

### Controllers

Reciben las solicitudes HTTP provenientes de las aplicaciones cliente y delegan el procesamiento a la capa de servicios.

### Services

Implementan la lógica de negocio del sistema, coordinando las operaciones necesarias para atender cada proceso.

### Repositories

Gestionan el acceso a la base de datos mediante Spring Data JPA.

### Entidades

Representan el modelo de dominio del sistema y corresponden a las tablas de la base de datos.

### DTOs y Mappers

Permiten transportar información entre las capas y transformar entidades en objetos de transferencia de datos.

### Seguridad

Administra la autenticación, autorización y protección de los recursos mediante Spring Security y JWT.

### Configuración

Centraliza la configuración general del sistema, incluyendo CORS, documentación de la API, inyección de dependencias y otros componentes transversales.

## Sistemas Externos

### PostgreSQL

Base de datos única del sistema.

### Cloudinary

Servicio utilizado para almacenar y recuperar documentos legales.

### WebRTC

Servicio empleado para la gestión de videollamadas entre clientes y abogados.

## Flujo General

1. El cliente envía una solicitud HTTP.
2. El Controller recibe la petición.
3. La capa de Seguridad valida la autenticación y autorización.
4. El Controller delega la operación al Service correspondiente.
5. El Service aplica la lógica de negocio.
6. El Repository accede a la base de datos cuando es necesario.
7. El Service interactúa con Cloudinary o WebRTC si el proceso lo requiere.
8. La respuesta es transformada mediante DTOs y enviada al cliente.

## Responsabilidades

| Componente | Responsabilidad |
|------------|-----------------|
| Controllers | Gestionar solicitudes HTTP |
| Services | Implementar la lógica de negocio |
| Repositories | Acceder a la base de datos |
| Entidades | Representar el dominio del sistema |
| DTOs y Mappers | Transferencia y conversión de datos |
| Seguridad | Autenticación y autorización |
| Configuración | Configuración global de la aplicación |

## Relación con el Modelo C4

- **Nivel 1:** Presenta el contexto general del sistema.
- **Nivel 2:** Describe los contenedores principales (Web, Móvil, Backend y Base de Datos).
- **Nivel 3:** Detalla la estructura interna del Backend IURIS.
- **Nivel 4:** Profundiza en la implementación de cada componente mediante diagramas de código.