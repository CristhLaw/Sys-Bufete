# C4 Nivel 1 – Contexto del Sistema IURIS

## Descripción

Este diagrama representa el **Nivel 1 del Modelo C4 (Contexto del Sistema)** para **IURIS**, un sistema de gestión para un mini bufete de abogados.

Su propósito es mostrar la interacción entre los diferentes actores del sistema, las aplicaciones cliente, el sistema principal y los servicios externos con los que se integra. Este nivel ofrece una visión general del entorno donde opera el sistema, sin detallar su implementación interna.

## Objetivo

- Identificar los actores que interactúan con el sistema.
- Mostrar los puntos de acceso al sistema.
- Representar las integraciones con servicios externos.
- Delimitar el alcance funcional del sistema dentro de su entorno.

## Arquitectura

El sistema está diseñado bajo una **arquitectura monolítica por capas**, donde todas las funcionalidades se encuentran centralizadas en una única aplicación backend desarrollada con Spring Boot.

Las aplicaciones cliente (Web y Móvil) consumen los servicios del backend mediante una API REST, mientras que el backend se integra con servicios externos para el almacenamiento de documentos y la gestión de videollamadas.

## Actores

- Cliente
- Abogado Principal
- Abogado Asistente
- Secretaria
- Administrador
- Gerente / Socio

## Aplicaciones Cliente

- Aplicación Web (Angular)
- Aplicación Móvil (Flutter)

## Sistema Principal

- Backend IURIS (Spring Boot)
- Arquitectura Monolítica por Capas

## Sistemas Externos

- Cloudinary (Almacenamiento de documentos)
- WebRTC (Servicio de videollamadas)

## Relaciones

- Los usuarios interactúan con el sistema mediante la aplicación web o la aplicación móvil.
- Ambas aplicaciones consumen la API REST expuesta por el Backend IURIS.
- El backend gestiona la lógica de negocio y la comunicación con los servicios externos.
- Cloudinary se utiliza para almacenar y recuperar documentos legales.
- WebRTC proporciona el servicio de videollamadas entre clientes y abogados.

## Alcance

Este diagrama corresponde únicamente al **Nivel 1 del Modelo C4**, por lo que no representa la organización interna del backend, las capas de la aplicación, la base de datos ni los componentes del sistema. Estos elementos se describen en los niveles posteriores del Modelo C4.

## Archivo relacionado

- `Contexto.puml`