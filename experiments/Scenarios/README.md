
# Escenarios de alimentación

Esta carpeta contiene los scripts de MATLAB utilizados para configurar los escenarios de alimentación considerados en la evaluación del controlador ADRC.

Los escenarios se definieron mediante tres comidas distribuidas durante un periodo de 24 horas de tiempo simulado.

## Escenarios evaluados

| Parámetro | Escenario 100 gCH | Escenario 130 gCH |
|---|---|---|
| Duración | 1440 min | 1440 min |
| Pacientes evaluados en simulación | 10 adultos | 10 adultos |
| Desayuno (06:00) | 30 gCH | 40 gCH |
| Almuerzo (13:00) | 40 gCH | 50 gCH |
| Cena (19:00) | 30 gCH | 40 gCH |
| **Total diario** | **100 gCH** | **130 gCH** |

## Archivos

| Archivo | Descripción |
|---|---|
| `Escenario_100gCH.m` | Script destinado a configurar el escenario de 100 gCH/día. |
| `Escenario_130gCH.m` | Script destinado a configurar el escenario de 130 gCH/día. |

**Nota:** Los nombres se actualizarán cuando se incorporen los archivos definitivos.

## Objetivo de los escenarios

Los dos escenarios permiten evaluar la respuesta del controlador ante perturbaciones alimentarias de diferente magnitud.

El escenario de 130 gCH/día representa la mayor carga de carbohidratos considerada en el estudio.

## Ejecución

Los scripts se utilizan junto con el entorno de simulación correspondiente.

**PENDIENTE:** documentar las instrucciones de ejecución y las variables que configura cada archivo, una vez incorporadas sus versiones definitivas.

## Resultados

- [Resultados de simulación](../../Results/Simulation/)
- [Resultados HIL](../../Results/HIL/)

Los ensayos HIL se realizaron con los pacientes #3 y #8 para 100 gCH/día y con el paciente #10 para 130 gCH/día.

## Consideraciones

Los scripts publicados deben contener únicamente código que pueda distribuirse sin incluir componentes propietarios del simulador UVA/Padova.
