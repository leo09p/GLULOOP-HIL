
# Identificación en lazo abierto

Esta carpeta documenta el procedimiento utilizado para identificar modelos simplificados de la dinámica glucosa-insulina de los diez pacientes adultos virtuales del simulador UVA/Padova T1DM.

## Procedimiento experimental

Para cada paciente se realizó un ensayo en lazo abierto bajo las siguientes condiciones:

| Parámetro | Configuración |
|---|---|
| Duración | 1440 minutos (24 horas) |
| Pacientes | 10 adultos virtuales |
| Ingesta de carbohidratos | Sin comidas |
| Entrada de identificación | Bolo de insulina de 1 U |
| Instante de aplicación | Minuto 100 |

Se registró la respuesta glucémica producida por la entrada de insulina para analizar la dinámica de cada paciente.

## Modelo identificado

Se utilizó un modelo de segundo orden con tiempo muerto (SOPTD, *Second-Order Plus Time Delay*), expresado como:

\[
G(s)=\frac{K e^{-\theta s}}{(T_1s+1)(T_2s+1)}
\]

donde:

- **K:** ganancia del modelo.
- **T1 y T2:** constantes de tiempo.
- **θ:** tiempo muerto.

El modelo proporciona una aproximación simplificada de la respuesta glucémica y no representa la totalidad de la dinámica fisiológica del simulador.

## Resultados

Las gráficas comparativas, los parámetros identificados y los porcentajes de ajuste se encuentran en [Results/Identification/](../../Results/Identification/).
