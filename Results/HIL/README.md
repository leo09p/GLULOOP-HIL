# Resultados Hardware-in-the-Loop (HIL)

Esta carpeta reúne los resultados obtenidos durante la validación experimental del controlador ADRC mediante la plataforma Hardware-in-the-Loop (HIL).

Durante los ensayos, el simulador UVA/Padova T1DM se ejecutó en el computador, mientras que el controlador se implementó sobre la tarjeta **Texas Instruments LAUNCHXL-F28379D**.

La plataforma también se integró con la aplicación Android GLULOOP para visualizar las variables transmitidas durante las pruebas.

## Ensayos realizados

Se realizaron tres ensayos HIL, distribuidos de la siguiente manera:

| Paciente | Escenario | Duración simulada |
|---|---|---|
| Adulto #3 | 100 gCH/día | 24 h |
| Adulto #8 | 100 gCH/día | 24 h |
| Adulto #10 | 130 gCH/día | 24 h |

## Resultados disponibles

Para cada ensayo se incluyen:

- Gráficas obtenidas mediante MATLAB.
- Capturas de las variables visualizadas en GLULOOP.
- Resultados asociados a la respuesta glucémica y la administración de insulina.

Las gráficas de MATLAB y las capturas de GLULOOP corresponden al mismo ensayo experimental, por lo que no representan pruebas independientes.

## Organización

Los resultados se distribuyen en tres carpetas:

- **Patient_03_100gCH:** resultados del paciente #3.
- **Patient_08_100gCH:** resultados del paciente #8.
- **Patient_10_130gCH:** resultados del paciente #10.

## Alcance de la validación

La implementación HIL permite evaluar la ejecución del controlador sobre hardware externo y la comunicación entre los componentes de la plataforma.

Los ensayos se realizaron utilizando pacientes virtuales y no constituyen pruebas clínicas ni validación para la administración de insulina en personas.
