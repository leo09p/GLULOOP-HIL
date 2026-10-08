# Resultados de simulación

Esta carpeta contiene los resultados de la evaluación del controlador ADRC utilizando los diez pacientes adultos virtuales del simulador UVA/Padova T1DM.

Se consideraron dos escenarios de ingesta diaria de carbohidratos, con una duración de 24 horas de tiempo simulado por ensayo.

## Escenarios evaluados

| Escenario | Desayuno (06:00) | Almuerzo (13:00) | Cena (19:00) |
|---|---:|---:|---:|
| [100gCH/](100gCH/) | 30 gCH | 40 gCH | 30 gCH |
| [130gCH/](130gCH/) | 40 gCH | 50 gCH | 40 gCH |

Cada escenario contiene los resultados individuales de los diez pacientes, organizados en carpetas de datos, gráficas y métricas.

## Variables y métricas

Los resultados permiten analizar la evolución de la glucosa, la administración de insulina y las variables registradas durante los ensayos.

Se consideran indicadores de control glucémico:

- **TIR:** tiempo en rango de 70–180 mg/dL.
- **TAR:** tiempo por encima de 180 mg/dL.
- **TBR:** tiempo por debajo de 70 mg/dL.

También se incluyen métricas de desempeño del controlador, como RMSE, IAE, ISE e ITAE.

## Resultados consolidados

La carpeta [Summary/](Summary/) reúne las figuras y resultados generales que permiten comparar el comportamiento de los pacientes en ambos escenarios.

Los scripts de procesamiento y generación de métricas se organizan en [Analysis/](../../Analysis/).
