
# Documentación del proyecto

Esta carpeta reúne la documentación técnica y académica relacionada con el desarrollo de **GLULOOP-HIL**, una plataforma de simulación hardware-in-the-loop (HIL) para la validación de estrategias de control en páncreas artificial.

Su propósito es facilitar la consulta de los documentos que complementan los archivos de implementación, los experimentos y los resultados disponibles en el repositorio.

## Documentación académica

### Trabajo de grado

**Título:** Plataforma de simulación hardware-in-the-loop para validación de estrategias de control en páncreas artificial.

**Autores:**
- Leonel Ricardo Araque Carreño.
- Paula Dayana Torres Mejía.

**Director:** José Jorge Carreño Zagarra, Ph. D.

**Institución:** Universidad Industrial de Santander (UIS).

El documento del trabajo de grado presenta los fundamentos teóricos, la metodología, el diseño de los controladores, la implementación HIL y los resultados de validación experimental.

El archivo PDF podrá consultarse en esta carpeta una vez incorporada su versión definitiva.

## Documentación técnica de GLULOOP

Esta sección está destinada a la documentación del desarrollo de la aplicación Android GLULOOP, incluyendo su arquitectura, interfaz y mecanismos de comunicación con la plataforma HIL.

La documentación complementa el código fuente disponible en la carpeta [Gluloop](../Gluloop/).

## Documentación de herramientas

Las siguientes herramientas se utilizaron durante el desarrollo del proyecto. Sus sitios oficiales proporcionan información sobre instalación, configuración y funcionamiento.

| Herramienta | Documentación |
|-------------|---------------|
| MATLAB/Simulink | [Documentación oficial de MathWorks](https://www.mathworks.com/help/) |
| Texas Instruments C2000 | [Documentación y recursos de C2000](https://www.ti.com/microcontrollers-mcus-processors/c2000-real-time-control-mcus/overview.html) |
| Android Studio | [Documentación oficial de Android Developers](https://developer.android.com/studio) |
| Python | [Documentación oficial de Python](https://docs.python.org/3/) |

### Simulador UVA/Padova T1DM

El simulador UVA/Padova T1DM constituye el entorno utilizado para representar la dinámica glucosa-insulina de los pacientes virtuales.

Su utilización requiere acceso autorizado. Los archivos propietarios del simulador no se incluyen en este repositorio.

## Otras secciones del repositorio

La documentación específica de cada componente se encuentra en las siguientes carpetas:

- [Controller](../Controller/): implementación de los controladores ADRC y PID.
- [Experiments](../Experiments/): configuración de los escenarios experimentales.
- [Analysis](../Analysis/): procesamiento de señales y cálculo de métricas.
- [HIL](../HIL/): implementación en la tarjeta Texas Instruments y comunicación entre componentes.
- [Gluloop](../Gluloop/): código fuente y recursos de la aplicación Android.
- [Results](../Results/): resultados de simulación y validación HIL.

## Consideraciones

Los documentos incluidos en esta carpeta tienen fines académicos y de investigación.

Los manuales y materiales de terceros se referencian mediante enlaces oficiales cuando sus condiciones de distribución no permiten incorporarlos directamente al repositorio.

La plataforma GLULOOP-HIL es un prototipo experimental y no está destinada al uso clínico.
