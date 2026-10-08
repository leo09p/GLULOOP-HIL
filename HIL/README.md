
# Implementación Hardware-in-the-Loop (HIL)

Esta carpeta contiene los archivos relacionados con la implementación hardware-in-the-loop (HIL) del controlador ADRC desarrollado para el proyecto GLULOOP-HIL.

La plataforma permite ejecutar el algoritmo de control en una tarjeta de desarrollo Texas Instruments LAUNCHXL-F28379D, mientras que el modelo fisiológico del paciente virtual se ejecuta en el simulador UVA/Padova T1DM desde un computador.

## Arquitectura de la plataforma

La implementación comprende los siguientes componentes:

- **Simulador UVA/Padova T1DM:** representa la dinámica fisiológica del paciente virtual y permite evaluar la respuesta glucémica ante la administración de insulina.
- **LAUNCHXL-F28379D:** ejecuta el algoritmo ADRC, el observador de estado extendido (ESO), el filtro de Kalman y los mecanismos de supervisión y restricción de insulina.
- **Computador:** ejecuta el simulador y el programa de Python encargado de intercambiar información con la aplicación móvil.
- **GLULOOP:** aplicación Android desarrollada para visualizar las variables del sistema y configurar los parámetros disponibles.

La comunicación entre el simulador y la tarjeta se realiza mediante una interfaz serial, mientras que el intercambio de información con GLULOOP se gestiona mediante Python y WebSocket.

## Organización

| Carpeta | Contenido |
|---------|-----------|
| [Embedded](Embedded/) | Archivos relacionados con la implementación del controlador ADRC en la tarjeta LAUNCHXL-F28379D. |
| [Communication/Python](Communication/Python/) | Script de Python utilizado para gestionar el intercambio de información con la aplicación Android. |
| [Communication/Protocols](Communication/Protocols/) | Documentación de las interfaces y los protocolos de comunicación empleados en la plataforma. |

## Funcionamiento general

Durante la ejecución HIL, el simulador UVA/Padova proporciona la información glucémica del paciente virtual al controlador implementado en la tarjeta LAUNCHXL-F28379D.

A partir de esta información, el controlador calcula la acción de control y aplica los mecanismos de supervisión establecidos. La señal de insulina resultante se devuelve al entorno de simulación para actualizar la respuesta del paciente virtual.

Paralelamente, el sistema de comunicación permite transmitir las variables seleccionadas hacia GLULOOP para su visualización en un dispositivo Android.

## Requisitos generales

Para reproducir la implementación HIL se requiere:

- MATLAB/Simulink y acceso autorizado al simulador UVA/Padova T1DM.
- Tarjeta Texas Instruments LAUNCHXL-F28379D.
- Herramientas de desarrollo compatibles con la tarjeta.
- Python y las dependencias utilizadas por el programa de comunicación.
- Dispositivo Android compatible con GLULOOP.
- Conexiones de comunicación correspondientes entre los componentes.

## Consideraciones

Los archivos incluidos en esta sección corresponden a la implementación experimental del sistema HIL y no contienen el simulador UVA/Padova T1DM.

La plataforma fue desarrollada con fines académicos y de investigación. No está destinada a la administración clínica de insulina.

## Documentación relacionada

- [Controller](../Controller/): subsistemas de control ADRC y PID.
- [Experiments](../Experiments/): escenarios experimentales.
- [Results](../Results/): resultados de simulación y validación HIL.
- [Gluloop](../Gluloop/): aplicación Android.
