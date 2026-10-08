
# Controlador ADRC en Simulink

Esta carpeta contiene la implementación del controlador basado en control activo con rechazo de perturbaciones (ADRC), desarrollado en MATLAB/Simulink para la plataforma GLULOOP-HIL.

El modelo integra los componentes de estimación, control y supervisión utilizados para calcular la administración de insulina a partir de las señales del sistema.

## Componentes del controlador

La implementación comprende los siguientes elementos:

1. **Filtro de Kalman:** procesamiento y estimación de la señal de glucosa utilizada por el controlador.
2. **Observador de estado extendido (ESO):** estimación de los estados del sistema y de la perturbación total.
3. **Controlador ADRC:** cálculo de la acción de control a partir de la referencia de glucosa y los estados estimados.
4. **Caja supervisora:** aplicación de condiciones adicionales para modificar o restringir la acción de control.
5. **Estimación y restricciones de IOB:** seguimiento de la insulina activa y limitación de la administración según las condiciones establecidas.
6. **Saturación final:** aplicación de los límites definidos para la señal de insulina.

## Archivos

| Archivo | Descripción |
|---|---|
| `GLULOOP_Controller.slx` | Modelo de Simulink que contiene el subsistema completo del controlador. |
| `Inicializar_Parametros.m` | Script de MATLAB que define los parámetros y matrices necesarios para configurar los bloques del modelo. |

**Nota:** Los nombres de los archivos son provisionales y se actualizarán cuando se incorporen las versiones definitivas.

## Configuración de parámetros

Algunos componentes del controlador utilizan variables definidas externamente al modelo de Simulink.

Entre ellas se encuentran:

- Matrices y parámetros del filtro de Kalman.
- Ganancias y parámetros del observador ESO.
- Ganancias del controlador ADRC.
- Parámetros del modelo asociados a cada paciente.
- Valores basales de glucosa e insulina.
- Parámetros de supervisión y restricciones de insulina.

El archivo de inicialización permite cargar las variables requeridas por los bloques de Simulink antes de ejecutar el modelo.

## Inicialización y ejecución

1. Abrir MATLAB y establecer esta carpeta como directorio de trabajo.
2. Ejecutar el archivo `Inicializar_Parametros.m`.
3. Abrir el modelo `GLULOOP_Controller.slx` en Simulink.
4. Verificar que las variables y matrices requeridas por los bloques se encuentren definidas.
5. Configurar las entradas y salidas del controlador de acuerdo con el entorno de evaluación.
6. Ejecutar el modelo.

**PENDIENTE:** verificar las dependencias del modelo, la versión de MATLAB/Simulink utilizada y el procedimiento definitivo de ejecución.

## Consideraciones de uso

El controlador fue desarrollado para su evaluación con pacientes virtuales del simulador UVA/Padova T1DM y su integración en una plataforma Hardware-in-the-Loop (HIL).

El archivo publicado debe contener únicamente los componentes del controlador que puedan distribuirse, sin incluir el simulador UVA/Padova ni sus archivos propietarios.

La implementación corresponde a un prototipo de investigación y no está destinada a la administración clínica de insulina.
