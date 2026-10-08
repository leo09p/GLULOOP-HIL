
# Proyecto Android de GLULOOP

Esta carpeta está destinada al código fuente de la aplicación Android GLULOOP.

La aplicación forma parte de la plataforma GLULOOP-HIL y proporciona una interfaz para visualizar las variables del sistema y configurar parámetros del controlador.

## Funcionalidades

La aplicación contempla las siguientes funciones:

- Visualización de los valores de glucosa recibidos.
- Representación de la administración de insulina.
- Visualización de la insulina activa estimada (IOB).
- Configuración de parámetros utilizados por el controlador.
- Conexión con el puente Python mediante WebSocket.

## Comunicación

GLULOOP se comunica con un servidor WebSocket ejecutado en el computador.

El servidor recibe información de la tarjeta LAUNCHXL-F28379D mediante la interfaz SCI_B y permite transmitir las variables a la aplicación.

El programa encargado de esta comunicación se documenta en [HIL/Communication/Python/](../../HIL/Communication/Python/).

## Archivos del proyecto

**PENDIENTE:** incorporar el proyecto Android y documentar su estructura.

## Requisitos

**PENDIENTE:** especificar:

- Versión de Android Studio utilizada.
- Versión del SDK de Android.
- Dependencias del proyecto.
- Versión mínima de Android compatible.

## Compilación y ejecución

1. Abrir Android Studio.
2. Seleccionar la opción para abrir un proyecto existente.
3. Seleccionar la carpeta del proyecto GLULOOP.
4. Esperar a que se sincronicen las dependencias.
5. Compilar la aplicación.
6. Ejecutarla en un dispositivo Android compatible.
7. Configurar la dirección IP y el puerto del servidor WebSocket para establecer la conexión.

**PENDIENTE:** verificar estos pasos con la versión definitiva del proyecto.

## Consideraciones

La aplicación requiere una conexión de red con el computador que ejecuta el puente Python para recibir información durante los ensayos HIL.

El software corresponde a un prototipo experimental y no está validado para uso clínico.
