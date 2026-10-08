
# Implementación Hardware-in-the-Loop (HIL)

Esta carpeta reúne los archivos y la documentación relacionados con la implementación Hardware-in-the-Loop (HIL) de la plataforma GLULOOP-HIL.

En esta configuración, el simulador UVA/Padova T1DM se ejecuta en un computador, mientras que el controlador ADRC y sus componentes asociados se ejecutan sobre la tarjeta Texas Instruments LAUNCHXL-F28379D.

## Arquitectura de la plataforma

La comunicación se realiza mediante dos interfaces seriales independientes de la tarjeta:

- **SCI_A:** permite el intercambio de información entre MATLAB/Simulink, donde se ejecuta el simulador UVA/Padova, y el controlador implementado en la LAUNCHXL-F28379D.
- **SCI_B:** utiliza comunicación UART para transmitir información desde la tarjeta hacia el programa Python ejecutado en el computador, que actúa como puente con la aplicación Android GLULOOP.

Python utiliza WebSocket para establecer la comunicación con la aplicación móvil.

## Componentes principales

1. **UVA/Padova:** simulador de pacientes virtuales con diabetes tipo 1.
2. **LAUNCHXL-F28379D:** plataforma embebida encargada de ejecutar el controlador ADRC, el filtro de Kalman, el ESO y los mecanismos de supervisión y restricción de insulina.
3. **SCI_A:** interfaz de comunicación entre el simulador y la tarjeta.
4. **SCI_B:** interfaz UART utilizada para la transmisión de variables hacia el puente Python.
5. **Python:** programa intermediario entre la comunicación serial y GLULOOP.
6. **GLULOOP:** aplicación Android para visualizar las variables del sistema.

## Organización

| Carpeta | Descripción |
|---|---|
| [Embedded/](Embedded/) | Implementación del controlador sobre la tarjeta Texas Instruments. |
| [Communication/](Communication/) | Comunicación mediante SCI_A, SCI_B, Python y WebSocket. |

## Resultados

Los resultados de las pruebas HIL se encuentran en [Results/HIL/](../Results/HIL/).

## Alcance

La plataforma fue desarrollada para investigación y validación experimental con pacientes virtuales. No constituye un dispositivo médico ni está destinada a la administración clínica de insulina.
