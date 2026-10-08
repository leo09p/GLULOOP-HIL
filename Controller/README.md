## Archivos

| Archivo | Descripción |
|---|---|
| `GLULOOP_Controller.slx` | Modelo de Simulink que contiene el controlador ADRC, el filtro de Kalman, el ESO y los mecanismos de supervisión y restricción de insulina. |
| `Inicializar_Parametros.m` | Script de MATLAB utilizado para definir los parámetros y las matrices requeridas por los bloques del controlador. |

**Nota:** Los nombres de los archivos se actualizarán cuando se incorporen las versiones definitivas.

## Inicialización y ejecución

1. Abrir MATLAB y establecer esta carpeta como directorio de trabajo.
2. Ejecutar el script `Inicializar_Parametros.m` para cargar las variables necesarias.
3. Abrir `GLULOOP_Controller.slx` en Simulink.
4. Verificar que las matrices y los parámetros requeridos por los bloques estén definidos.
5. Configurar las entradas y salidas del controlador según el entorno de prueba.
6. Ejecutar el modelo.

**PENDIENTE:** verificar las dependencias del modelo, la versión de MATLAB/Simulink y las instrucciones definitivas de ejecución.
