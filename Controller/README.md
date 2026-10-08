
# Implementación de controladores

Esta carpeta contiene los modelos desarrollados en MATLAB/Simulink para la regulación automática de glucosa en el simulador UVA/Padova T1DM, como parte del proyecto **GLULOOP-HIL**.

Se incluyen dos estrategias de control: el controlador por rechazo activo de perturbaciones (ADRC), desarrollado como estrategia principal del proyecto, y el controlador proporcional-integral-derivativo (PID), implementado con fines comparativos.

## Organización

Los modelos y sus archivos de configuración se encuentran en la carpeta [Simulink](Simulink/).

```text
Controller/
├── README.md
└── Simulink/
    ├── Controlador_ADRC.slx
    ├── Controlador_PID.slx
    └── Inicializar_Parametros.m
```

Los nombres de los archivos son provisionales y deberán actualizarse según los archivos definitivos incorporados al repositorio.

## Archivos de implementación

| Archivo | Descripción |
|---------|-------------|
| `Controlador_ADRC.slx` | Modelo de Simulink correspondiente al controlador ADRC propuesto. |
| `Controlador_PID.slx` | Modelo de Simulink correspondiente al controlador PID utilizado para la comparación de desempeño. |
| `Inicializar_Parametros.m` | Script de MATLAB utilizado para configurar las variables y los parámetros necesarios para ejecutar las simulaciones. |

## Controlador ADRC

El controlador ADRC constituye la estrategia principal desarrollada en el proyecto para regular la concentración de glucosa mediante la administración automática de insulina.

Su implementación comprende los siguientes componentes:

- **Filtro de Kalman:** procesamiento de la señal de glucosa obtenida del sensor CGM.
- **Observador de estado extendido (ESO):** estimación de los estados del sistema y de la perturbación total.
- **Ley de control ADRC:** cálculo de la acción de control a partir de la referencia de glucosa y las estimaciones del observador.
- **Caja supervisora:** supervisión y ajuste de la acción de control según las condiciones del sistema.
- **Estimación de insulina activa (IOB):** mecanismo utilizado para considerar la insulina activa y aplicar restricciones a la administración.
- **Saturación de insulina:** limitación de la señal de administración dentro de los valores establecidos.

El controlador fue evaluado mediante simulaciones con pacientes virtuales del entorno UVA/Padova y posteriormente integrado en la plataforma hardware-in-the-loop (HIL).

## Controlador PID

El controlador PID se implementó como una estrategia de comparación para evaluar el desempeño del ADRC.

Su implementación se basó en un método descrito en la literatura científica y se incorporó al entorno de simulación para realizar pruebas bajo condiciones experimentales comparables.

La evaluación permite contrastar las respuestas glucémicas, las acciones de control y las métricas de desempeño obtenidas con ambas estrategias.

## Inicialización de parámetros

El archivo `Inicializar_Parametros.m` se utiliza para establecer las variables y los parámetros necesarios antes de ejecutar las simulaciones en MATLAB/Simulink.

Su función es preparar la configuración correspondiente al paciente virtual y a los componentes del sistema de control, incluyendo los parámetros requeridos por el modelo y el filtro de Kalman, según la implementación utilizada.

La ejecución del script permite disponer de las variables necesarias en el entorno de MATLAB antes de iniciar la simulación.

Los parámetros deben corresponder al paciente y a la estrategia de control seleccionados. Si los modelos ADRC y PID requieren configuraciones diferentes, estas deben establecerse de acuerdo con sus respectivos archivos de implementación.

## Ejecución de los modelos

Para ejecutar los controladores se requiere MATLAB/Simulink y acceso autorizado al simulador UVA/Padova T1DM.

El procedimiento general es el siguiente:

1. Abrir MATLAB y establecer la carpeta `Simulink/` como directorio de trabajo.
2. Ejecutar `Inicializar_Parametros.m`, cuando corresponda al modelo seleccionado.
3. Verificar los parámetros asociados al paciente virtual.
4. Abrir el modelo ADRC o PID en Simulink.
5. Configurar el escenario experimental correspondiente.
6. Ejecutar la simulación.
7. Exportar las señales necesarias para el análisis de resultados.

La configuración específica puede variar según el controlador y las dependencias utilizadas.

## Documentación relacionada

- [Experiments](../Experiments/): configuración de los escenarios experimentales.
- [Analysis](../Analysis/): procesamiento de señales, generación de gráficas y cálculo de métricas.
- [Results](../Results/): resultados de simulación y validación experimental.
- [HIL](../HIL/): implementación del controlador ADRC en la plataforma hardware-in-the-loop.

## Consideraciones

Los modelos se proporcionan con fines académicos y de investigación como parte del desarrollo de GLULOOP-HIL.

La ejecución de las simulaciones requiere las herramientas y dependencias correspondientes. El repositorio no distribuye archivos propietarios del simulador UVA/Padova T1DM.

La plataforma desarrollada constituye un prototipo experimental y no está destinada a la administración clínica de insulina.
