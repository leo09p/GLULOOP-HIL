
# Análisis de simulación

Esta carpeta contiene el script de MATLAB desarrollado para procesar y analizar los resultados obtenidos durante las simulaciones del controlador ADRC en la plataforma UVA/Padova T1DM.

El script permite generar gráficas, calcular métricas de desempeño y almacenar los resultados de manera organizada para cada paciente virtual.

## Archivo principal

**`Analisis_Simulacion.m`**

Script de MATLAB encargado de realizar automáticamente el análisis de las simulaciones.

> El nombre del archivo debe ajustarse al nombre definitivo del script incorporado al repositorio.

## Señales utilizadas

El análisis emplea cuatro señales exportadas desde MATLAB/Simulink:

| Archivo | Descripción |
|---------|-------------|
| `V.mat` | Glucosa fisiológica del paciente virtual. |
| `g.mat` | Medición de glucosa del sensor CGM. |
| `u.mat` | Salida directa del controlador ADRC. |
| `u_insu.mat` | Insulina administrada al paciente. |

Los archivos deben encontrarse en la carpeta de trabajo de MATLAB antes de ejecutar el script.

## Gráficas generadas

El script genera cuatro gráficas independientes:

1. Glucosa fisiológica (`V`).
2. Medición de glucosa CGM (`g`).
3. Salida directa del controlador ADRC (`u`).
4. Insulina administrada al paciente (`u_insu`).

Las gráficas se representan en un intervalo de 24 horas de tiempo simulado y se guardan en formatos `.png` (300 dpi) y `.fig`.

Las gráficas de glucosa incluyen los límites de 70 y 180 mg/dL.

## Métricas de desempeño

Las métricas glucémicas se calculan a partir de la señal CGM (`g`).

### Control glucémico

- **TIR (70–180 mg/dL):** tiempo en rango.
- **TAR (>180 mg/dL):** tiempo por encima del rango.
- **TBR (<70 mg/dL):** tiempo por debajo del rango.
- **TAR (>250 mg/dL):** tiempo por encima de 250 mg/dL.
- **TBR (<54 mg/dL):** tiempo por debajo de 54 mg/dL.
- Glucosa media, máxima y mínima.

### Desempeño del controlador

- **RMSE:** raíz del error cuadrático medio.
- **IAE:** integral del error absoluto.
- **ISE:** integral del error cuadrático.
- **ITAE:** integral del error absoluto ponderado por el tiempo.

Estas métricas se calculan respecto a una referencia de glucosa de 110 mg/dL, configurada en el script.

### Administración de insulina

- Esfuerzo de control, calculado a partir de la salida `u`.
- Insulina total administrada, calculada mediante la integración temporal de `u_insu`.

## Organización de resultados

El script crea automáticamente una carpeta identificada con el número del paciente o de la simulación.

La estructura generada es:

```text
Paciente_XX/
├── Graficas/
│   ├── 01_Glucosa_V.png
│   ├── 01_Glucosa_V.fig
│   ├── 02_CGM_g.png
│   ├── 02_CGM_g.fig
│   ├── 03_Salida_ADRC.png
│   ├── 03_Salida_ADRC.fig
│   ├── 04_Insulina_Aplicada.png
│   └── 04_Insulina_Aplicada.fig
│
├── Datos/
│   ├── Paciente_XX_datos.csv
│   └── Paciente_XX_datos.mat
│
└── Metricas/
    └── Paciente_XX_metricas.csv
```

El archivo `.mat` almacena las cuatro señales procesadas y las métricas calculadas. Los archivos `.csv` permiten consultar y procesar los resultados mediante otras herramientas.

## Ejecución

1. Abrir MATLAB.
2. Colocar los archivos `V.mat`, `g.mat`, `u.mat` y `u_insu.mat` en la carpeta de trabajo.
3. Abrir `Analisis_Simulacion.m`.
4. Configurar la variable `paciente` con el identificador correspondiente.
5. Configurar `carpeta_base` con la ubicación donde se guardarán los resultados.
6. Ejecutar el script.

Al finalizar, MATLAB mostrará las métricas en la ventana de comandos y guardará automáticamente los archivos generados.

## Relación con el repositorio

- [Controller](../Controller/): implementación del controlador ADRC.
- [Experiments](../Experiments/): configuración de los escenarios experimentales.
- [Results](../Results/): resultados de simulación organizados por paciente y escenario.

## Consideraciones

- El script está diseñado para señales exportadas desde Simulink como objetos `timeseries`.
- El tiempo de simulación se expresa originalmente en minutos y se convierte a horas para el análisis.
- Los resultados se limitan a las primeras 24 horas de tiempo simulado.
- El código no modifica las señales originales.
- No se incluyen archivos propietarios del simulador UVA/Padova.
