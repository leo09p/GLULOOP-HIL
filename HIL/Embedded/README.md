
## Interfaces de comunicación

La tarjeta LAUNCHXL-F28379D utiliza dos interfaces seriales para integrar el controlador con los demás componentes de la plataforma:

- **SCI_A:** comunicación con MATLAB/Simulink en el computador, donde se ejecuta el simulador UVA/Padova.
- **SCI_B:** comunicación UART utilizada para transmitir variables hacia el programa Python, encargado de establecer el enlace con GLULOOP.

Esta separación permite utilizar una interfaz para el intercambio de información necesario durante la simulación HIL y otra para la comunicación con la aplicación móvil.
