
# Protocolos experimentales

Esta carpeta contiene los archivos y la documentación asociados a la configuración de los experimentos realizados para evaluar el controlador ADRC de la plataforma GLULOOP-HIL.

Las pruebas se desarrollaron utilizando pacientes adultos virtuales del simulador UVA/Padova T1DM.

## Organización

| Carpeta | Descripción |
|---|---|
| [Scenarios/](Scenarios/) | Scripts de MATLAB para configurar los escenarios de alimentación utilizados durante las simulaciones. |

## Evaluación experimental

Se consideraron dos escenarios de alimentación, correspondientes a ingestas diarias de 100 y 130 gCH.

Cada escenario se evaluó durante 24 horas de tiempo simulado sobre diez pacientes adultos virtuales.

Adicionalmente, se realizaron tres ensayos Hardware-in-the-Loop (HIL):

- Paciente #3: 100 gCH/día.
- Paciente #8: 100 gCH/día.
- Paciente #10: 130 gCH/día.

Los resultados de las evaluaciones se encuentran en [Results/](../Results/).

## Consideraciones

El simulador UVA/Padova T1DM no se distribuye en este repositorio debido a sus condiciones de licencia.
