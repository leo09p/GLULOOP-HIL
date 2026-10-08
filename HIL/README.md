
# Implementación Hardware-in-the-Loop (HIL)

Esta carpeta reúne los archivos y la documentación relacionados con la implementación Hardware-in-the-Loop (HIL) de la plataforma GLULOOP-HIL.

En esta configuración, el simulador UVA/Padova T1DM se ejecuta en un computador, mientras que el controlador ADRC y sus componentes asociados se ejecutan sobre la tarjeta Texas Instruments LAUNCHXL-F28379D.

La plataforma incorpora comunicación UART para intercambiar información con el computador y un puente desarrollado en Python para transmitir las variables a la aplicación Android GLULOOP.

## Arquitectura general

El sistema está compuesto por:

1. **Simulador UVA/Padova:** representa la dinámica glucosa-insulina de los pacientes virtuales.
2. **LAUNCHXL-F28379D:** ejecuta el controlador ADRC, el filtro de Kalman, el ESO y los mecanismos de supervisión y restricción de insulina.
3. **Comunicación UART:** permite el intercambio de información mediante la interfaz SCI_B de la tarjeta.
4. **Puente Python:** gestiona la comunicación serial y la transmisión de información mediante WebSocket.
5. **GLULOOP:** permite visualizar las variables del sistema desde una aplicación Android.

## Organización

| Carpeta | Descripción |
|---|---|
| [Embedded/](Embedded/) | Implementación del controlador sobre la tarjeta Texas Instruments. |
| [Communication/](Communication/) | Comunicación UART, puente Python y protocolos de intercambio de información. |

## Resultados

Los resultados de los ensayos HIL se encuentran en [Results/HIL/](../Results/HIL/).

## Alcance

La plataforma fue desarrollada para investigación y validación experimental utilizando pacientes virtuales.

No constituye un dispositivo médico ni está destinada a la administración clínica de insulina.
