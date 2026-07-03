# ⚖️ IURIS — Sistema de Gestión para Bufete de Abogados

Sistema de gestión jurídica diseñado para centralizar la administración integral de un mini bufete de abogados: usuarios y roles, casos legales, agenda y audiencias, documentos, comunicación con clientes y reportes ejecutivos.

Proyecto académico del curso **Análisis y Diseño de Sistemas** — Universidad Peruana Unión (UPeU), Juliaca.

---

## 📋 Descripción

IURIS permite gestionar de manera eficiente la información jurídica y administrativa del bufete, facilitando el seguimiento de casos, la organización de documentos legales, el control de audiencias y la comunicación entre abogados, clientes y personal administrativo.

El sistema se implementa como una **aplicación monolítica organizada en capas** (`controller → service → repository → model`), en un único artefacto **Spring Boot** desplegado como proceso único, con una **única base de datos PostgreSQL** compartida por todos los módulos funcionales. Como proyecto de referencia arquitectónica se toma `San_Martin_Backend`, del cual se adoptan el patrón CRUD genérico, la seguridad JWT y el manejo centralizado de excepciones.

> El detalle de esta decisión, las alternativas evaluadas (microservicios, monolito modular por dominio, arquitectura hexagonal) y sus consecuencias están documentados en [`ADR-001`](./4-Desicion%20arquitectonico%20(ADRs)/ADR-001-arquitectura-monolitica-por-capas.md).

---

## 🛠️ Tecnologías Utilizadas

| Capa | Tecnología |
|---|---|
| Aplicación Web | Angular |
| Aplicación Móvil | Flutter |
| Backend | Spring Boot (Java 17) |
| Base de datos | PostgreSQL |
| Almacenamiento de documentos | Cloudinary |
| Videollamadas | WebRTC |
| Modelado | PlantUML, Draw.io, Arquitectura C4, UML |
| Control de versiones | Git & GitHub |

---

## 🏗️ Arquitectura y Modelado

### Modelo C4

| Nivel | Contenido |
|---|---|
| **Nivel 1 — Contexto** | Actores, Sistema IURIS y sistemas externos (Cloudinary, WebRTC) |
| **Nivel 2 — Contenedores** | App Web (Angular), App Móvil (Flutter), Backend IURIS (Spring Boot monolítico), PostgreSQL |
| **Nivel 3 — Componentes** | Controllers, Services, Repositories, Entidades, DTOs/Mappers, Seguridad (JWT), Configuración |

### UML

* Casos de Uso (general + por módulo)
* Diagrama de Clases (modelo de dominio completo)
* Diagramas de Secuencia
* Diagramas de Actividades
* Diagramas de Estado

---

## 📂 Estructura del Proyecto

```text
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

---

# 👥 Actores del Sistema

## Cliente
* Consultar información de sus casos.
* Revisar documentos autorizados.
* Consultar audiencias programadas.
* Comunicarse con su abogado asignado.

## Abogado Principal
* Gestionar casos legales.
* Asignar abogados asistentes.
* Registrar audiencias y actuaciones procesales.
* Generar reportes de casos.

## Abogado Asistente
* Dar seguimiento a expedientes asignados.
* Administrar documentos jurídicos.
* Actualizar el estado de casos.

## Secretaria
* Programar y reprogramar audiencias y citas.
* Gestionar la agenda del bufete.
* Apoyar en la organización documental.

## Administrador
* Gestionar usuarios y roles.
* Gestionar permisos de acceso.
* Supervisar el funcionamiento general del sistema.

## Gerente / Socio
* Consultar reportes ejecutivos del bufete.
* Supervisar el desempeño general de los casos.

---

# 📌 Casos de Uso

Organizados en un diagrama general (`CU00`) y cuatro diagramas por módulo funcional:

| Módulo | Alcance |
|---|---|
| **M1 — Autenticación y Perfil** | Iniciar/cerrar sesión, recuperar y restablecer contraseña |
| **M2 — Casos y Reportes** | Solicitar consulta, registrar caso, asignar abogado, actualizar/cerrar caso, reportes |
| **M3 — Documentos y Comunicación** | Subir/consultar/descargar documento, mensajería, videollamada |
| **M4 — Agenda y Audiencias** | Registrar audiencia, programar/reprogramar/cancelar cita |
# 📌 Casos de Uso

## Diagrama General

![Diagrama General](https://www.plantuml.com/plantuml/png/VPRDZjis4CVlUeh1NfeSqjhojv4kid62OWy15Z4MFJFKhCA68WsI2kAsUPW-G0z5RxtwnJe3YTO7bUp1Yvdv3Vdd33_qoWVXGbtfJEDJW631gV9ZW48vb45Pa_bFofo44nMSXFmaIZo6iqRWi32bnXvGUvJ28_oH0Rmck7IsDiN1QklWruyg8FkjAz0rljUQeEnhbWaPo7pqjN3AjjP1ApG1M-lkWor54MCthGaFJXcfJaBVWF0HkZh7z9rNFl2WVVhgREaZIeU1X8WuTL4fGumZsCKXVq77EV0J7ArKbWU4rfTbhNHuyUBFs-kgnkAQQ5pNMCnaT3XW9tJe7-WTXgBgWJExjX9kZZnz9U3-yVtzyOOQcK5iEvMnPcbA2gak_neummEw9wNXsRlvSsO1uBe5RWwFipdS6oMLSE3HSmfN2JYGe3cyHrcVq14ihGcE0fT_H4p8nmdv5Wxet7XeyXFvTQWwbgEHJMfdaseN9A5aJZYeczB4wHS9l8Htjb1FIgRmCe5Nj3CydoM2dTNe8xfAbjL7Jy9T_gemE1iJyZHXtKz0LodVqUk4ti316b_hGBIe2nMik_mj8hz9QdSGD1oSgJ8dwm9wAbu-MRnrhtYosVrnaUi4tiJYfV0cWRVzaKbE9BS9kUFpNN8XH7DS8heRe_CPtCbG2wr-9vhgHp0KgBi9c4covd7FzR5vKkISMcAGETUKZORcbAunwNPU93bFoWWZ5JeRinP9rXBUVc6wIuliCcdb6olhYahD_OI3hMgZvFL4BAQQEb_3iVwW71GnDQvvdQ2zZPNYcmJVmXlqKhXo2jyc-0xUci-AMEAy-0qZcJGsdz5kyITh2Xp154hWES7j7lYi2hHQYqeK8YRCqwkaH5C8gjzTNIYaxWW-4Sl9ukMzVIiY7eNd2Rw01sTB9oh2fGfNSf6GIpwDa-moOLTm44QY7e4hNXc_kp_0p_m7mvjz3z9Mb_yC20Ysi1wqzV6Myqr6rZuUz98q5_h0fbkOd3dyU0iSX3syV8bVQ8hYzhQRTF1EjSCEVEtWYqdlSjAxclBcqzxrf7VJ2UmUs7syf_ZmY6OND88xfbMmJbsRn4LjvNvnWTWO5eWRD8YafKi-V_PGeW_SlEjbqw1JdntDzVcDm6uwm1ST_xJ2Pi322D1rWLFAddNfv-wxw_aUF9QrgXHPTgYmdMOsilEHlHxPcv6zpVYdbRdvbip11RYFtvfmZlfRQT4VfnhQsv6z6zhvV2HjDxJpqLA9PsShVJVT-lWKVx-zUTvzwatQupCLlVJJxiey7mheHKQxyXNzgolzFm00)
---


# 📊 Diagrama de Clases


![Diagrama General](https://www.plantuml.com/plantuml/png/ZLZTRjiu5hxFKn3P6tUs2UfasdI3GJ6AB2S273kmdLpiJN4i7IkSKgH0KkbETkU1zcAlzX7wOaFIj49J2Z95aKZdvpl_XrH-LHgaRcjsfBvHte24cfGD9V_u512uA55XGyu_14G9_9ibVlnm5EhY5bgcfuBhETH8Rf0zeQO513Bg4KhndVAAR84f33WRABvLKhIyJ0KJalnjwlu54WsKfTKy2sXEeH2IeoIXsucKullwaHRVE2f5ngSzfhSolXfFpyvZfetXJO4LVKOoFaX1mS1O-_SH8LT2bYWznld5pnS_Jpjgu9UXzMqPMWU_brZftncIZM2b8IHQIxffDLfEP2DW1Zx-qVFobr_kLGkI2lNboyxZk03Pv-buUXN7Ciu-9gP3XW1dM05x5IsPVihI6EqikvWOhuVGahQaMaWAho5E9zbLbiM8oVdPf_FFXuZ8sxfVbKyVBYQVhmQHFqulfWv3KysGhAZIM0F9xvVvYfoG2OLAWY6KI5862XKPtJxSlJiwQWmKL4YECsCI9HHKS5J7phZpOYcOpxkZ4PBEyco-pjnpShMuJYQBtVFaDf_dg_NI49Q6Ofpn49dIK8eKr1uYITVvmquhctzDxYS6CSsJFI5RtMNBtLkQBPUTWMLwap_Ojn1yJHkH5Bfrddly276LBlFr3cE-MEVJF4tIV35tX4a-owzVP9VPQZ6xxxYBzN8n50hLu4tTBHVNo-GscNX_5_DfllIl5YwPvV_icCaypMRsBOHC2kjvrppxB1lLpetRnUJGwsoMcsHtxzRiwdwstfcPBQxp-SlJrsaoc-K-Qty45T-F4_cThB1g9IsXj8KtWh1HMaAX_NxmagGtWiJq5Yt99Pa9NlbtBkgDH6M8AorfHuO66QEbw36m1ifYOcFCVXUol07r6FCqCjmABcAw50OcwDUzuKBJ9ojy9GH3uA7P1vHqImie-_mj5e-GIdHzvI8iW4r0uvhMw6LQfcajJ05HYM6H4_VhxmHQZLmRItBa3EzTVzz5kgUy2nsngWNK6mhopikDm3Px0C1RV890-qdGqglkHEcpqKQvfMnKWXRgaYmsls6X3sKhr7DNkLJK3KCjHk_seBvTVJEaZ2BNEDmC9QTnMKegqRLul_2DH0K1ESoO4emML8DC1LScqJ0gKHMIDXOg2DUk5Q_oXFAMAajGrlaPLTfpe49U6YXgO5nXesAzDnqlAYX5NbgE0xR2Xv4dEvdXoDtw5AbWM57GhOHUkAh1WWApOuQA1FxP7Ab6SBfXsEj33QP5uKOum8b1g84TPggG23BxLuEbAyoe01N6ySBnyj9uQCTRTcjpX9tCNl4zYNkbaxwJea05R6yUfS6oMGyj6m2nKIYV_8aIDwXFRBTP_dDoik-icEpJVh2xhb5f-kF_dBWZt6oje3_jEHEUxV6Q2klbXEFzGdNBUjIW-_gLrMw310TTk4II1gLhYA3fGdvAKKeSu3QI2hUL3I_d6YkKqLRPI2f7NKg3SepUIFxXTjZvFTh54OOHAIXQyR3LWhu9-ono25ZHCZl7IekrqC2CnrqdXmCMGFpb0WOgKLL-QvzjsyHaofK6Nb286JLeb1ISsPZdwfHpMj1mpySxlf9OXLC5-wU1uOcNBqS9qguYHZb6swj2VMzF6dY1xizZdCiU-o-aLDF6n7TufNgh_NroElc1FfPe5tY_5YVHGefR6Kkx_mSFhUX8Cd9lXkSTVUKePsq5ygt1DcQwIuu6zKeuXU1RAkjUE0Nm0faXryBUI8Q7Kc8ZHIN1ghkJVJfmN4L9S4liU7nCVZBRylZ3wQbv3EV2UgJDCv8lHZhWX3e_7G-qp6LN6wDgRAMiKpJnVjah-YtgZsmhwtUs4nYKhL1fOyd8lWfgaIxzqHr23elP04kqSA0Ykw6Sn2rAQW0DP72U-EltZ_-1kGsl9Jp3XhgJ-k1eURcanrzxmms7N5kCn3IUXZBSE24xZxJ0tvRYBuEt9_49c92e4cutcNrwHM1k6alqcBHPI5hXy7pV23ayEczEiTXekzrkg79Hi94F--K6wmd1dSKlFzDnvh-_34I53ufnwVUdhIWN6eaM3H5Rqdq7xBvvkwyQ81lpUcheDoZHdGMhV7uzoxwkawjPTdg4l2GMoCE18lEnHH5r8nGjxCN3U4y9ibqxApIFjAOStXinPE6CQNjz__5V7g8wfTDJYpeVcuT0tflS2Ar5ROFmjuaP48aCd6qxpsGYYhOs6jQwuLDU2DdO8Y4d3CW3IkLapqB332lx8cdrQFCxz0Leo8UNAqCOd0ulVF1t0sjgP-FeL_EZhTcV)
---



# 🧩 Patrones de Diseño Aplicados

| Patrón | Categoría | Uso en IURIS |
|---|---|---|
| **Observer** | Comportamiento | Notificación interna entre módulos ante cambios relevantes (p. ej. cambio de estado de un caso) |
| **Strategy** | Comportamiento | Selección intercambiable de algoritmos/comportamientos de negocio |
| **Singleton** | Creacional | Instancia única de recursos compartidos del sistema |
| **Adapter** | Estructural | Integración con el proveedor externo de almacenamiento (`CloudinaryAdapter`) |

---

# 🗂️ Decisiones de Arquitectura (ADRs)

| ADR | Título | Estado |
|---|---|---|
| [ADR-001](./4-Desicion%20arquitectonico%20(ADRs)/ADR-001-arquitectura-monolitica-por-capas.md) | Adopción de arquitectura monolítica por capas | ✅ Aceptado |

---

# 🎯 Objetivos del Sistema

* Centralizar la información jurídica del bufete.
* Optimizar la gestión de expedientes y casos legales.
* Reducir errores administrativos.
* Mejorar el seguimiento y control de audiencias.
* Facilitar la comunicación entre abogados y clientes.
* Garantizar la seguridad y confidencialidad de la información.

---

# 🚀 Estado del Proyecto

📌 Fase actual: **Modelado y Documentación completa**

* [x] Contexto del sistema
* [x] Identificación de actores
* [x] Casos de uso
* [x] Modelo C4 (Niveles 1-3)
* [x] Diagrama de clases (modelo de dominio)
* [x] Diagramas de secuencia y actividades
* [x] Diagramas de estado
* [x] Patrones de diseño
* [x] ADR de arquitectura
* [ ] Implementación backend
* [ ] Implementación frontend
* [ ] Pruebas del sistema

> El código fuente en `src/` corresponde aún a un scaffold inicial de Maven, pendiente de implementación siguiendo la arquitectura documentada.

---

## 📦 Requisitos

* Java 17
* Maven
* PostgreSQL

---

# 👨‍💻 Equipo de Desarrollo

Proyecto académico de Ingeniería de Sistemas — UPeU
