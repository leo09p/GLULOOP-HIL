
# Documentación del proyecto

Esta carpeta reúne la documentación técnica y académica relacionada con el desarrollo de **GLULOOP-HIL**, una plataforma de simulación hardware-in-the-loop (HIL) para la validación de estrategias de control en páncreas artificial.

Su propósito es facilitar la consulta de los documentos y recursos que complementan los archivos de implementación, los experimentos y los resultados disponibles en el repositorio.

## Documentación académica

### Trabajo de grado

**Título:** Plataforma de simulación hardware-in-the-loop para validación de estrategias de control en páncreas artificial.

**Autores:**
- Leonel Ricardo Araque Carreño.
- Paula Dayana Torres Mejía.

**Director:** José Jorge Carreño Zagarra, Ph. D.

**Institución:** Universidad Industrial de Santander (UIS), Escuela de Ingenierías Eléctrica, Electrónica y de Telecomunicaciones.

El trabajo de grado presenta los fundamentos teóricos, la metodología de diseño de los controladores, la implementación de la plataforma HIL y los resultados obtenidos durante su validación experimental.

La versión definitiva del documento podrá incorporarse a esta carpeta para facilitar su consulta.

## Documentación técnica de GLULOOP

GLULOOP es la aplicación Android desarrollada como interfaz de visualización y configuración de la plataforma HIL.

La documentación técnica de la aplicación comprende aspectos relacionados con su desarrollo, estructura, interfaz gráfica y mecanismos de comunicación con el sistema.

El código fuente y los recursos correspondientes se encuentran en la carpeta [Gluloop](../Gluloop/).

## Simulador UVA/Padova T1DM

El proyecto utiliza el **UVA/Padova Type 1 Diabetes Simulator (T1DMS), versión 3.2.1**, como entorno para evaluar las estrategias de regulación automática de glucosa.

Este simulador permite representar la dinámica glucosa-insulina de pacientes virtuales con diabetes mellitus tipo 1 y analizar la respuesta del sistema ante diferentes condiciones experimentales.

En GLULOOP-HIL se utilizó para evaluar los controladores ADRC y PID mediante simulaciones con pacientes virtuales adultos, así como para implementar los ensayos hardware-in-the-loop.

### Información y acceso al simulador

Los siguientes recursos permiten consultar información sobre UVA/Padova y su utilización:

| Recurso | Enlace |
|---------|--------|
| Sitio de referencia del simulador | [TEG Virginia](https://tegvirginia.com/) |
| Publicación técnica sobre UVA/Padova | [The UVA/PADOVA Type 1 Diabetes Simulator: New Features](https://doi.org/10.1177/1932296813514502) |
| Interfaz complementaria para el simulador | [UVAPadovaAPI](https://github.com/NeuroDiab/UVAPadovaAPI) |

La disponibilidad del simulador original y las condiciones para obtenerlo deben consultarse con sus desarrolladores o distribuidores autorizados.

El proyecto UVAPadovaAPI es un recurso complementario para trabajar con una instalación existente del simulador académico y no sustituye al software original.

### Integración con GLULOOP-HIL

Los controladores ADRC y PID disponibles en la carpeta [Controller](../Controller/) corresponden a subsistemas desarrollados en MATLAB/Simulink.

Para ejecutar las simulaciones es necesario disponer de una instalación autorizada de UVA/Padova e integrar los subsistemas de control dentro de su entorno.

En la implementación HIL, el simulador representa la dinámica fisiológica del paciente virtual, mientras que el controlador ADRC se ejecuta en la tarjeta Texas Instruments LAUNCHXL-F28379D.

**Nota:** el simulador UVA/Padova y sus componentes propietarios no se distribuyen en este repositorio.

## Documentación de herramientas

Durante el desarrollo de GLULOOP-HIL se utilizaron herramientas de simulación, programación y desarrollo de sistemas embebidos.

La documentación oficial de estas herramientas puede consultarse en los siguientes enlaces:

| Herramienta | Documentación |
|-------------|---------------|
| MATLAB/Simulink | [MathWorks Documentation](https://www.mathworks.com/help/) |
| Texas Instruments C2000 | [C2000 Real-Time Control MCUs](https://www.ti.com/microcontrollers-mcus-processors/c2000-real-time-control-mcus/overview.html) |
| Android Studio | [Android Developers](https://developer.android.com/studio) |
| Python | [Python Documentation](https://docs.python.org/3/) |

Estos recursos proporcionan información complementaria sobre el funcionamiento, la configuración y el desarrollo de los componentes utilizados en la plataforma.

## Organización del repositorio

La documentación específica de cada componente del proyecto se encuentra en las siguientes carpetas:

| Carpeta | Contenido |
|---------|-----------|
| [Controller](../Controller/) | Subsistemas de control ADRC y PID desarrollados en Simulink. |
| [Experiments](../Experiments/) | Configuración de los escenarios experimentales. |
| [Analysis](../Analysis/) | Procesamiento de señales, generación de gráficas y cálculo de métricas. |
| [HIL](../HIL/) | Implementación del controlador en hardware y comunicación entre componentes. |
| [Gluloop](../Gluloop/) | Aplicación Android, código fuente y recursos asociados. |
| [Results](../Results/) | Resultados de simulación y validación HIL. |
| [Appendices](../Appendices/) | Material complementario del proyecto. |

## Consideraciones

Los documentos y recursos de esta carpeta se proporcionan con fines académicos y de investigación.

Los manuales, herramientas y materiales de terceros están sujetos a sus respectivas licencias y condiciones de distribución. Cuando corresponde, se proporcionan enlaces a sus fuentes oficiales en lugar de incorporar copias al repositorio.

GLULOOP-HIL constituye una plataforma experimental para la evaluación de estrategias de control y no está destinada al uso clínico ni a la administración de insulina en pacientes reales.
