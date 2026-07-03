# Patrón Observer — Módulo de Casos Legales (IURIS)

## Categoría

Patrón de diseño **de comportamiento**.

## Problema que resuelve

Cuando el estado de un `CasoLegal` cambia (por ejemplo, de `ACTIVO` a `EN_AUDIENCIA` o a `CERRADO`), otros módulos del sistema necesitan enterarse y reaccionar:

- **Comunicación** debe notificar al cliente sobre el cambio.
- **Auditoría** debe dejar registro del evento en el log del sistema.

El módulo de Casos Legales no debe conocer ni invocar directamente a esos módulos: eso generaría un acoplamiento fuerte, difícil de mantener y de extender (cada vez que se agregue un nuevo interesado en los cambios de un caso, habría que modificar `CasoLegal`).

## Solución aplicada

Se implementa el patrón **Observer**: `CasoLegal` actúa como **sujeto observado** y expone una lista de interesados (`observadores`) sin conocer sus implementaciones concretas, solo su contrato (`IObservador`). Cuando el estado del caso cambia, `CasoLegal` notifica a todos los observadores registrados, y cada uno decide de forma autónoma cómo reaccionar.

## Estructura

### Interfaces

| Interfaz | Responsabilidad |
|---|---|
| `IObservador` | Contrato de quien quiere ser notificado. Declara `actualizar(evento, origen)`. |
| `ISujeto` | Contrato de quien es observado. Declara `agregarObservador`, `removerObservador` y `notificarObservadores`. |

### Clases

| Clase | Rol | Responsabilidad |
|---|---|---|
| `CasoLegal` | Sujeto concreto (`implements ISujeto`) | Mantiene la lista de observadores y su propio estado (`id`, `estado`). Al ejecutar `cambiarEstado(nuevoEstado)`, actualiza el estado y dispara `notificarObservadores("CASO_ACTUALIZADO")`. |
| `NotificacionObserver` | Observador concreto (`implements IObservador`) | Al recibir `actualizar()`, simula el envío de una notificación al cliente sobre el caso afectado. |
| `AuditoriaObserver` | Observador concreto (`implements IObservador`) | Al recibir `actualizar()`, simula el registro del evento en el log de auditoría. |

### Relación

- Un `CasoLegal` puede tener **0 a muchos** observadores (`"1" o-- "0..*" IObservador`).
- `NotificacionObserver` y `AuditoriaObserver` implementan `IObservador`.
- `CasoLegal` implementa `ISujeto`.

## Flujo de ejecución

1. Se crea un `CasoLegal` (en producción, esto ocurre al registrar o cargar un caso desde la base de datos).
2. Se registran sus observadores — en IURIS esto se hace **por inyección de dependencias al arrancar la aplicación**, no manualmente como en el ejemplo de consola.
3. Al invocar `cambiarEstado(nuevoEstado)`, el caso actualiza su estado y notifica a todos los observadores registrados.
4. Cada observador ejecuta su propia lógica (`NotificacionObserver` → avisa al cliente; `AuditoriaObserver` → registra en auditoría), sin que `CasoLegal` tenga conocimiento de esos módulos.

El archivo `Observer.java` incluye además un `main()` de demostración: crea un caso, le registra ambos observadores y ejecuta dos cambios de estado (`EN_AUDIENCIA` y `CERRADO`) para mostrar cómo ambos observadores reaccionan a cada notificación.

## Relación con la arquitectura del sistema

Este patrón es la implementación concreta de un mecanismo definido en `ADR-001 — Adopción de arquitectura monolítica por capas`, específicamente la fila de la tabla de decisión:

> *Notificación ante cambios relevantes (ej. cambio de estado de un caso) → Patrón Observer*

Dentro de una arquitectura monolítica por capas, todos los módulos comparten el mismo proceso; Observer permite que la comunicación entre módulos (Casos Legales → Comunicación / Auditoría) se resuelva de forma desacoplada, sin llamadas directas entre servicios ni dependencias cruzadas explícitas.

## Ventajas de esta implementación

- **Desacoplamiento:** `CasoLegal` no conoce las clases concretas `NotificacionObserver` ni `AuditoriaObserver`, solo la interfaz `IObservador`.
- **Extensibilidad:** agregar un nuevo interesado (por ejemplo, un futuro `EstadisticasObserver`) no requiere modificar `CasoLegal`, solo registrar el nuevo observador.
- **Responsabilidad única:** cada observador encapsula su propia lógica de reacción, sin mezclarla con la lógica de negocio del caso.

## Archivos de esta carpeta

| Archivo | Contenido |
|---|---|
| `Observer.puml` | Diagrama de clases UML del patrón (PlantUML) |
| `Observer.java` | Implementación de referencia en Java, con `main()` de demostración |


## Diagrama Observer

![Diagrama Observer](https://www.plantuml.com/plantuml/png/pLJDYjim6BphAOQSaZOnxTMKiANj8R3zeQ4FyAtqnVqMMGgIRDZ-F59F-mXviShUjTTkjeTI2WKVFgFvPaR3e8kOAAIcjYf9iemFbCBfrk7zLUJGSi06Rq-tfh4UXl6Aee-uv8eiHontdpxkzYkbn2KE1zACtTqU6H_mLG5FGJerPEKBXIMtx98liKz1NBM63rAnAxFMDUkqAj5wCUhxZ6_VN7Fo0rSLidHuq5ZwSIodsWDNjnIuzYt_uPBpIGwYfreSVxd0nB2s56EVJfyDf3vQhZCupcwmWP_GbRYKc5vC76nxZ9XyubtL_t1CP4R9_pE54Q-flX8ARthFIzTmw-_ctqRsxbv1YtTZvQRf_NsVHgsNZP6KCVJlX1uQi7Y-WDzii7XM54yMC_fop1LRzMW0HV5jErrHPzRF81FfxcYedL9PYv7zVaxm1umeXTonEEiZbe-PMIkS9xT2P0IkSdy3EHopFY3kXhMMxlKmdByZEyDE2qMG1OMCrDG1BO6EjjFfm0LUSoJjZvOwN3s-DWhpzwPZjJuEY7WFyNNZ1YxS35vFFsYDA0wEDKSnP1Hw0air6ShB4bYdFB1BWS4sCnQAdK6NcBh8KrFRdm00)
---