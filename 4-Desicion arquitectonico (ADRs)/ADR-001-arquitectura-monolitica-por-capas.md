# ADR-001: Adopción de arquitectura monolítica por capas

Estado: Aceptado
Fecha: 2026-07-02
Decisores: Equipo del proyecto IURIS (curso de Análisis y Diseño de Sistemas, UPeU)

## Contexto

IURIS es un sistema de gestión para un mini bufete de abogados, desarrollado
como proyecto académico del curso de Análisis y Diseño de Sistemas. El
sistema centraliza la gestión de usuarios, casos legales, agenda y
audiencias, documentos, comunicación con clientes y reportes del bufete.

Al definir el estilo arquitectónico del sistema, el equipo evaluó distintas
alternativas (ver sección "Alternativas consideradas") considerando el
alcance real del proyecto: una aplicación académica, con un solo equipo de
desarrollo, un solo repositorio y sin requisitos de escalamiento
independiente por dominio ni de aislamiento de fallos entre módulos.

## Decisión

Se decide **implementar IURIS como una aplicación monolítica organizada en
capas** (`controller → service → repository → model`), en un único
artefacto Spring Boot desplegado como proceso único, con una única base de
datos PostgreSQL compartida por todos los módulos funcionales.

Se toma como referencia arquitectónica el proyecto
`San_Martin_Backend-ImportaExportarRepuestosDesdeExcel`, que implementa
este estilo con un patrón CRUD genérico (`ICrudGenericoRepository`,
`ICrudGenericoServiceImp`), seguridad JWT y manejo centralizado de
excepciones.

Los dominios funcionales del sistema (Autenticación, Usuarios, Casos
Legales, Agenda y Audiencias, Documentos, Comunicación, Reportes) se
organizan como **módulos internos** (conjuntos de clases dentro de las
mismas capas compartidas), en lugar de aplicaciones independientes. La
comunicación entre dominios se resuelve siempre dentro del mismo proceso:

| Necesidad | Mecanismo adoptado |
|---|---|
| Exposición de la API hacia el frontend | Controladores REST expuestos directamente por la aplicación |
| Comunicación entre módulos (ej. Reportes necesita datos de Casos) | Inyección de dependencias e invocación directa de interfaces `Service` |
| Notificación ante cambios relevantes (ej. cambio de estado de un caso) | Patrón Observer (ver `3-PATRONES DE DISEÑO/Comportamiento/1-Patron Observer`) |
| Consistencia entre operaciones que afectan varios módulos | Transacciones locales `@Transactional` (consistencia inmediata) |
| Persistencia de todos los dominios | Una única base de datos PostgreSQL |

## Alternativas consideradas

**1. Arquitectura de microservicios** (un servicio independiente por
dominio, con su propia base de datos, comunicados mediante API Gateway,
llamadas síncronas y eventos asíncronos).
Se descartó por el sobrecosto operativo que implica (múltiples bases de
datos, orquestación, Gateway, observabilidad distribuida) frente a un
equipo pequeño y un alcance académico, sin requisitos reales de escalado
independiente por dominio. Este estilo aporta valor cuando distintos
equipos deben desplegar y escalar sus servicios de forma independiente,
lo cual no aplica al contexto actual del proyecto.

**2. Monolito modular sin separación estricta en capas** (organización por
dominio/feature, ej. `casos/`, `agenda/`, `documentos/`, cada uno con su
propio controller/service/repository interno).
Se descartó porque el estilo de referencia elegido (San Martín) usa capas
horizontales, y mantener esa consistencia facilita comparar y reutilizar
patrones ya probados en ese proyecto. Queda como alternativa válida a
futuro si el sistema creciera en complejidad.

**3. Arquitectura hexagonal (puertos y adaptadores).**
Se descartó por complejidad innecesaria para el alcance actual; el
patrón Adapter ya se aplica puntualmente donde aporta valor real (p. ej.
`CloudinaryAdapter` para el proveedor externo de almacenamiento), sin
necesidad de imponerlo a todo el sistema.

## Consecuencias

**Positivas:**

- Un solo artefacto para compilar, probar y desplegar; bajo costo
  operativo frente a un despliegue distribuido.
- Transacciones ACID nativas entre módulos (p. ej. abrir un caso y
  notificar al cliente ocurre en una sola transacción).
- Menor curva de aprendizaje para el equipo: no se requiere gestionar
  descubrimiento de servicios, balanceo de carga ni resiliencia entre
  procesos.
- Todas las reglas de negocio, casos de uso y entidades del dominio se
  cubren sin necesidad de coordinación entre servicios distribuidos.

**Negativas / trade-offs aceptados:**

- Se pierde la posibilidad de escalar un módulo de forma independiente
  (p. ej. escalar solo Documentos ante un pico de cargas de archivos).
- Un fallo o una fuga de memoria en un módulo puede afectar a toda la
  aplicación, al compartir el mismo proceso.
- Los despliegues no son independientes por dominio: cualquier cambio,
  por pequeño que sea, requiere reconstruir y desplegar todo el artefacto.
- Si el proyecto evolucionara hacia una escala mayor (más equipos, más
  tráfico, necesidad de aislar fallos), se debería reevaluar esta decisión
  documentando un nuevo ADR.

## Referencias

- `1-MODELO_C4/Nivel_1_Contexto/Contexto.puml` — diagrama de contexto del sistema
- `1-MODELO_C4/Nivel_2_Contenedores/Contenedores.puml` — diagrama de contenedores del monolito
- `1-MODELO_C4/Nivel_3_Componentes/Componentes.puml` — diagrama de componentes internos
- `3-PATRONES DE DISEÑO/Comportamiento/1-Patron Observer` — notificación interna entre módulos
- `3-PATRONES DE DISEÑO/Estructurales/1-Patron Adapter` — integración con Cloudinary
- Proyecto de referencia: `San_Martin_Backend-ImportaExportarRepuestosDesdeExcel`
