
## Función del programa

El programa Python establece un puente de comunicación entre la interfaz SCI_B de la tarjeta LAUNCHXL-F28379D y la aplicación Android GLULOOP.

Su función consiste en:

1. Recibir los mensajes transmitidos mediante UART desde SCI_B.
2. Procesar la información recibida.
3. Transmitir las variables hacia GLULOOP mediante WebSocket.
4. Gestionar el intercambio de información de configuración, según las funciones implementadas.

La comunicación SCI_A entre el simulador y la tarjeta se utiliza de manera independiente y no forma parte de este puente Python.
