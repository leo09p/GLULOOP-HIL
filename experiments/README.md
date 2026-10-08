
# Experimentos

Esta carpeta reúne los archivos y procedimientos utilizados para configurar los experimentos desarrollados en el proyecto **GLULOOP-HIL**, empleando el simulador UVA/Padova T1DM.

Los experimentos comprenden la identificación de la dinámica glucosa-insulina en lazo abierto y la evaluación del sistema bajo dos escenarios de alimentación.

## 1. Identificación en lazo abierto

La identificación se realizó utilizando los diez pacientes adultos virtuales del simulador UVA/Padova.

Para cada paciente se configuró un experimento de 24 horas de tiempo simulado, sin ingesta de alimentos y con la administración de un bolo de insulina de 1 U en el minuto 100.

Las respuestas obtenidas se utilizaron para identificar modelos aproximados de segundo orden con tiempo muerto (SOPTD), empleados posteriormente en el diseño del controlador.

**Archivo:** `Identificacion_Lazo_Abierto.m` (pendiente de incorporar o sustituir por la documentación del procedimiento).

Los parámetros identificados y las gráficas correspondientes se encuentran en [Results/Identification](../Results/Identification/).

## 2. Escenarios de alimentación

Para evaluar el desempeño del controlador ADRC se definieron dos escenarios de alimentación, con cargas totales de 100 y 130 gramos de carbohidratos por día (gCH/día).

Ambos escenarios consideran tres comidas distribuidas durante un periodo de 24 horas de tiempo simulado.

| Hora | Escenario 100 gCH | Escenario 130 gCH |
|------|-------------------|-------------------|
| 06:00 | 30 gCH | 40 gCH |
| 13:00 | 40 gCH | 50 gCH |
| 19:00 | 30 gCH | 40 gCH |
| **Total** | **100 gCH** | **130 gCH** |

### Archivos

- `Escenario_100gCH.m`: configuración del escenario de 100 gCH/día.
- `Escenario_130gCH.m`: configuración del escenario de 130 gCH/día.

Los nombres de los archivos se actualizarán según las versiones definitivas de los scripts.

## 3. Evaluación experimental

Los escenarios de alimentación se utilizaron para evaluar la respuesta del controlador ante perturbaciones asociadas a la ingesta de carbohidratos.

Las simulaciones se realizaron con los diez pacientes adultos virtuales, considerando ambos escenarios y manteniendo la configuración del controlador definida para cada paciente.

Los datos, las gráficas y las métricas obtenidas se encuentran disponibles en [Results/Simulation](../Results/Simulation/).

## 4. Consideraciones

- Los experimentos requieren acceso autorizado al simulador UVA/Padova T1DM.
- Esta carpeta contiene únicamente archivos de configuración y documentación propios del proyecto.
- No se distribuyen archivos propietarios del simulador.
- Los archivos de implementación del controlador se encuentran en [Controller](../Controller/).
- Los resultados de los experimentos se almacenan en [Results](../Results/).
