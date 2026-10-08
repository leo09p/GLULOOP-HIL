
# Comunicación de la plataforma HIL

Esta carpeta contiene la documentación y los archivos relacionados con el intercambio de información entre el simulador UVA/Padova, la tarjeta LAUNCHXL-F28379D y la aplicación Android GLULOOP.

## Interfaces de comunicación

### SCI_A — Comunicación entre simulador y controlador

La interfaz SCI_A se utiliza para intercambiar información entre el computador, donde se ejecuta MATLAB/Simulink con el simulador UVA/Padova, y la tarjeta LAUNCHXL-F28379D.

Esta comunicación permite integrar el modelo virtual del paciente con la ejecución del controlador sobre hardware.

### SCI_B — Comunicación con GLULOOP

La interfaz SCI_B se utiliza para transmitir variables desde la tarjeta mediante UART hacia un programa desarrollado en Python.

El programa Python recibe la información serial y la transmite mediante WebSocket a GLULOOP, donde se visualizan las variables del sistema.

## Organización

| Carpeta | Descripción |
|---|---|
| [Python/](Python/) | Programa utilizado como puente de comunicación entre SCI_B y GLULOOP. |
| [Protocols/](Protocols/) | Documentación de los formatos de mensajes y protocolos de comunicación. |

## Tecnologías utilizadas

- Interfaces SCI_A y SCI_B de la LAUNCHXL-F28379D.
- Comunicación serial UART.
- Python para el procesamiento y transmisión de datos.
- WebSocket para la comunicación con la aplicación Android.
