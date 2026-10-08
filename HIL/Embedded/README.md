
# Implementación embebida

Esta carpeta está destinada a los archivos y la documentación relacionados con la ejecución del controlador ADRC sobre la tarjeta Texas Instruments LAUNCHXL-F28379D, basada en el microcontrolador TMS320F28379D de la familia C2000.

## Componentes implementados

La implementación embebida comprende:

- Filtro de Kalman.
- Observador de estado extendido (ESO).
- Ley de control ADRC.
- Caja supervisora.
- Estimación y restricciones de insulina activa (IOB).
- Saturación de la señal de insulina.

## Comunicación

La tarjeta utiliza la interfaz serial SCI_B para intercambiar información mediante UART.

La comunicación con la aplicación Android se realiza a través de un programa Python ejecutado en el computador, sin utilizar un módulo ESP32.

## Archivos

**PENDIENTE:** incorporar los archivos de implementación embebida que puedan distribuirse y documentar el procedimiento de configuración, compilación y ejecución sobre la tarjeta.

## Consideraciones

El modelo del controlador se documenta en [Controller/Simulink/](../../Controller/Simulink/).

La implementación corresponde a una plataforma experimental y no está validada para aplicaciones clínicas.
