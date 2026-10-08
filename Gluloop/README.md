
# GLULOOP — Aplicación Android

Esta carpeta reúne el código fuente y la documentación de GLULOOP, una aplicación Android desarrollada como parte de la plataforma GLULOOP-HIL.

La aplicación permite visualizar las variables transmitidas durante los ensayos Hardware-in-the-Loop (HIL) y proporciona una interfaz para configurar parámetros asociados al controlador.

## Funciones principales

- Visualización de la glucosa.
- Visualización de la administración de insulina.
- Seguimiento de la insulina activa estimada (IOB).
- Configuración de parámetros del controlador.
- Comunicación con el computador mediante WebSocket.

## Integración con la plataforma HIL

La tarjeta LAUNCHXL-F28379D utiliza la interfaz SCI_B para intercambiar información mediante UART con el programa Python ejecutado en el computador.

Python actúa como puente de comunicación con GLULOOP a través de WebSocket.

La interfaz SCI_A se utiliza de manera independiente para la comunicación entre el simulador UVA/Padova y la tarjeta.

## Organización

| Carpeta | Descripción |
|---|---|
| [Android/](Android/) | Código fuente y archivos del proyecto Android. |
| [Screenshots/](Screenshots/) | Capturas de pantalla de la interfaz de GLULOOP. |

## Alcance

GLULOOP es un prototipo experimental desarrollado con fines académicos y de investigación.

No constituye una aplicación médica validada ni está destinada a tomar decisiones clínicas o administrar insulina en personas.
