
# Protocolos experimentales

Esta carpeta reúne la documentación de los procedimientos utilizados para la identificación de la dinámica glucosa-insulina y la evaluación del controlador ADRC desarrollado para la plataforma GLULOOP-HIL.

Los experimentos se realizaron utilizando pacientes adultos virtuales del simulador UVA/Padova T1DM.

## Organización

| Carpeta | Descripción |
|---|---|
| [Open-loop/](Open-loop/) | Procedimiento de identificación de modelos dinámicos mediante ensayos en lazo abierto. |
| [Scenarios/](Scenarios/) | Definición de los escenarios de alimentación utilizados para evaluar el controlador. |

## Etapas experimentales

El desarrollo experimental comprende:

1. Identificación de la dinámica glucosa-insulina de los pacientes virtuales.
2. Evaluación del controlador ADRC mediante simulaciones de 24 horas.
3. Validación de la implementación sobre la plataforma Hardware-in-the-Loop (HIL).

Los resultados de estas etapas se encuentran en [Results/](../Results/).

## Consideraciones

Los protocolos se documentan para facilitar la comprensión de las condiciones experimentales y la interpretación de los resultados.

El simulador UVA/Padova no se distribuye en este repositorio debido a sus condiciones de licencia.
