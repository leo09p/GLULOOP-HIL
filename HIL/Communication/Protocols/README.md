
# Protocolos de comunicación

Esta carpeta contiene la documentación de las interfaces de comunicación utilizadas en la plataforma hardware-in-the-loop (HIL) del proyecto GLULOOP-HIL.

La arquitectura combina comunicación serial y WebSocket para intercambiar información entre el simulador UVA/Padova T1DM, la tarjeta Texas Instruments LAUNCHXL-F28379D y la aplicación Android GLULOOP.

## Comunicación serial entre UVA/Padova y LAUNCHXL-F28379D

La comunicación entre el simulador UVA/Padova T1DM y la tarjeta LAUNCHXL-F28379D se realiza mediante bloques de comunicación serial implementados en MATLAB/Simulink.

La siguiente figura presenta el esquema utilizado para intercambiar las señales entre ambos componentes.

![Esquema de comunicación serial](Comunicacion_Serial.png)

**Figura 1.** Esquema de comunicación serial implementado en Simulink.

En la configuración presentada se utiliza el puerto `COM17`, con una velocidad de transmisión de **115200 baudios**.

El esquema incluye bloques de recepción y transmisión serial, conversiones al tipo de dato `single` y agrupación de señales para su envío.

El puerto COM17 corresponde a la configuración del computador utilizado durante el desarrollo y puede variar según el equipo.

## Comunicación entre la tarjeta y GLULOOP

La comunicación con la aplicación Android GLULOOP se gestiona mediante el programa [`servidor_parametros.py`](../Python/servidor_parametros.py).

Este programa se ejecuta en el computador y actúa como intermediario entre la tarjeta LAUNCHXL-F28379D y la aplicación móvil.

Para ello, utiliza comunicación serial con la tarjeta y una conexión WebSocket con GLULOOP.

Esta arquitectura permite intercambiar las variables seleccionadas para su visualización, incluyendo glucosa, insulina e insulina activa estimada (IOB), así como los parámetros de configuración disponibles en la aplicación.

## Interfaces utilizadas

| Interfaz | Comunicación | Función |
|----------|---------------|---------|
| SCI_A | UVA/Padova ↔ LAUNCHXL-F28379D | Intercambio de información entre el simulador y el controlador. |
| SCI_B | LAUNCHXL-F28379D ↔ Python | Comunicación entre la tarjeta y el programa de Python. |
| WebSocket | Python ↔ GLULOOP | Intercambio de variables y parámetros con la aplicación Android. |

## Consideraciones

Para reproducir la comunicación es necesario configurar los puertos seriales y las conexiones de red según el computador y los dispositivos utilizados.

Los formatos de datos y parámetros de comunicación deben ser compatibles con las implementaciones correspondientes en Simulink, la tarjeta y la aplicación Android.
