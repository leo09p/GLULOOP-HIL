package com.example.adrcmonitor.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.adrcmonitor.components.*
import java.util.Locale


@Composable
fun EnsayoScreen(
    conectado: Boolean,
    glucosa: List<Double>,
    insulina: List<Double>,
    idioma: String
) {

    // ========================================================
    // DATOS DEL ENSAYO
    //
    // 1 muestra = 1 minuto simulado
    //
    // muestra 1    -> minuto 0
    // muestra 361  -> minuto 360  -> 06 h
    // muestra 721  -> minuto 720  -> 12 h
    // muestra 1081 -> minuto 1080 -> 18 h
    // muestra 1441 -> minuto 1440 -> 24 h
    //
    // La duración REAL de la simulación no importa.
    // ========================================================

    val muestrasEnsayo =
        maxOf(glucosa.size, insulina.size)
            .coerceAtMost(1501)

    val minutosEnsayo =
        (muestrasEnsayo - 1)
            .coerceAtLeast(0)
            .coerceAtMost(1440)

    val progreso =
        minutosEnsayo / 1440f


    // Solo utilizar las muestras correspondientes
    // al ensayo completo de 24 horas.

    val glucosaEnsayo =
        glucosa
            .take(1501)
            .filter { it > 0.0 }

    val insulinaEnsayo =
        insulina.take(1501)


    // ========================================================
    // MÉTRICAS
    // ========================================================

    val glucosaMaxima =
        glucosaEnsayo.maxOrNull()

    val glucosaMinima =
        glucosaEnsayo.minOrNull()

    val tiempoEnRango =
        porcentaje(glucosaEnsayo) {
            it in 70.0..180.0
        }

    val tiempoSobreRango =
        porcentaje(glucosaEnsayo) {
            it > 180.0
        }

    val tiempoBajoRango =
        porcentaje(glucosaEnsayo) {
            it < 70.0
        }


    // ========================================================
    // FONDO
    // ========================================================

    val fondo = Brush.verticalGradient(
        listOf(
            Color(0xFFF5FBFF),
            Color(0xFFFAF8FF),
            Color(0xFFF4FAFF)
        )
    )


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(fondo)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {

            // =================================================
            // CABECERA
            // =================================================

            CabeceraGLULOOP(
                subtitulo = tr(
                    idioma,
                    "Ensayo HIL · seguimiento de 24 horas",
                    "HIL trial · 24-hour tracking"
                ),
                conectado = conectado,
                idioma = idioma
            )

            Spacer(Modifier.height(20.dp))


            // =================================================
            // ESTADO DEL ENSAYO
            // =================================================

            TarjetaEstadoEnsayo(
                conectado = conectado,
                minutos = minutosEnsayo,
                progreso = progreso,
                idioma = idioma
            )

            Spacer(Modifier.height(16.dp))


            // =================================================
            // GRÁFICA DE GLUCOSA
            // =================================================

            TarjetaGraficaEnsayo(
                titulo = tr(
                    idioma,
                    "Glucosa · 24 horas",
                    "Glucose · 24 hours"
                ),
                subtitulo = tr(
                    idioma,
                    "Respuesta glucémica durante el ensayo",
                    "Glycemic response during the trial"
                )
            ) {

                GraficaSenal(
                    datos = glucosaEnsayo,
                    minY = 40.0,
                    maxY = 220.0,
                    nivelesY = listOf(
                        40.0,
                        70.0,
                        110.0,
                        180.0,
                        220.0
                    ),
                    ventanaMinutos = 1440,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(205.dp),
                    zonaObjetivo = 70.0 to 180.0,
                    idioma = idioma,
                    tipo = TipoGrafica.GLUCOSA
                )
            }

            Spacer(Modifier.height(16.dp))


            // =================================================
            // GRÁFICA DE INSULINA
            // =================================================

            TarjetaGraficaEnsayo(
                titulo = tr(
                    idioma,
                    "Insulina · 24 horas",
                    "Insulin · 24 hours"
                ),
                subtitulo = tr(
                    idioma,
                    "Acción del controlador durante el ensayo",
                    "Controller action during the trial"
                )
            ) {

                GraficaSenal(
                    datos = insulinaEnsayo,
                    minY = 0.0,
                    maxY = 5.0,
                    nivelesY = listOf(
                        0.0,
                        1.0,
                        2.5,
                        4.0,
                        5.0
                    ),
                    ventanaMinutos = 1440,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(205.dp),
                    idioma = idioma,
                    tipo = TipoGrafica.INSULINA
                )
            }

            Spacer(Modifier.height(23.dp))


            // =================================================
            // RESULTADOS
            // =================================================

            Text(
                text = tr(
                    idioma,
                    "Resultados del ensayo",
                    "Trial results"
                ),
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(Modifier.height(3.dp))

            Text(
                text = tr(
                    idioma,
                    "Métricas glucémicas obtenidas durante el ensayo HIL",
                    "Glycemic metrics obtained during the HIL trial"
                ),
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(Modifier.height(13.dp))


            // =================================================
            // GLUCOSA MÁXIMA + MÍNIMA
            // =================================================

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                TarjetaMetrica(
                    titulo = tr(
                        idioma,
                        "Glucosa máxima",
                        "Maximum glucose"
                    ),
                    descripcion = tr(
                        idioma,
                        "Mayor glucosa registrada",
                        "Highest glucose recorded"
                    ),
                    valor = glucosaMaxima?.let {
                        "%.0f".format(it)
                    } ?: "--",
                    unidad = "mg/dL",
                    simbolo = "↑",
                    color = GluloopRojo,
                    modifier = Modifier.weight(1f)
                )

                TarjetaMetrica(
                    titulo = tr(
                        idioma,
                        "Glucosa mínima",
                        "Minimum glucose"
                    ),
                    descripcion = tr(
                        idioma,
                        "Menor glucosa registrada",
                        "Lowest glucose recorded"
                    ),
                    valor = glucosaMinima?.let {
                        "%.0f".format(it)
                    } ?: "--",
                    unidad = "mg/dL",
                    simbolo = "↓",
                    color = GluloopNaranja,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.height(10.dp))


            // =================================================
            // TIEMPO EN RANGO
            // =================================================

            TarjetaMetricaHorizontal(
                titulo = tr(
                    idioma,
                    "Tiempo en rango",
                    "Time in range"
                ),
                descripcion = tr(
                    idioma,
                    "Porcentaje del ensayo con glucosa entre 70 y 180 mg/dL",
                    "Percentage of trial with glucose between 70 and 180 mg/dL"
                ),
                valor = tiempoEnRango?.let {
                    "%.1f".format(it)
                } ?: "--",
                unidad = "%",
                simbolo = "✓",
                color = GluloopVerde
            )

            Spacer(Modifier.height(10.dp))


            // =================================================
            // SOBRE RANGO + BAJO RANGO
            // =================================================

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                TarjetaMetrica(
                    titulo = tr(
                        idioma,
                        "Tiempo sobre rango",
                        "Time above range"
                    ),
                    descripcion = tr(
                        idioma,
                        "Glucosa mayor a 180 mg/dL",
                        "Glucose above 180 mg/dL"
                    ),
                    valor = tiempoSobreRango?.let {
                        "%.1f".format(it)
                    } ?: "--",
                    unidad = "%",
                    simbolo = "↑",
                    color = GluloopRojo,
                    modifier = Modifier.weight(1f)
                )

                TarjetaMetrica(
                    titulo = tr(
                        idioma,
                        "Tiempo bajo rango",
                        "Time below range"
                    ),
                    descripcion = tr(
                        idioma,
                        "Glucosa menor a 70 mg/dL",
                        "Glucose below 70 mg/dL"
                    ),
                    valor = tiempoBajoRango?.let {
                        "%.1f".format(it)
                    } ?: "--",
                    unidad = "%",
                    simbolo = "↓",
                    color = GluloopNaranja,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.height(14.dp))


            // =================================================
            // NOTA INFERIOR
            // =================================================

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                color = GluloopMorado.copy(alpha = 0.07f)
            ) {

                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Surface(
                        modifier = Modifier.size(38.dp),
                        shape = CircleShape,
                        color = GluloopMorado.copy(alpha = 0.12f)
                    ) {

                        Box(
                            contentAlignment = Alignment.Center
                        ) {

                            Text(
                                text = "✦",
                                fontSize = 19.sp,
                                color = GluloopMorado
                            )
                        }
                    }

                    Spacer(Modifier.width(11.dp))

                    Column {

                        Text(
                            text = tr(
                                idioma,
                                "Ensayo en curso",
                                "Trial in progress"
                            ),
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = tr(
                                idioma,
                                "Las métricas se actualizan con los datos recibidos del sistema HIL.",
                                "Metrics update with data received from the HIL system."
                            ),
                            fontSize = 10.sp,
                            color =
                                MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(Modifier.height(30.dp))
        }
    }
}


// ============================================================
// TARJETA: ESTADO DEL ENSAYO
// ============================================================

@Composable
private fun TarjetaEstadoEnsayo(
    conectado: Boolean,
    minutos: Int,
    progreso: Float,
    idioma: String
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                6.dp,
                RoundedCornerShape(24.dp)
            ),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {

        Column(
            modifier = Modifier.padding(17.dp)
        ) {

            Text(
                text = tr(
                    idioma,
                    "Estado del ensayo",
                    "Trial status"
                ),
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(10.dp))

            FilaParametro(
                nombre = tr(
                    idioma,
                    "Controlador",
                    "Controller"
                ),
                valor = "ADRC"
            )

            HorizontalDivider()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 11.dp),
                horizontalArrangement =
                    Arrangement.SpaceBetween,
                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Text(
                    text = tr(
                        idioma,
                        "Estado",
                        "Status"
                    ),
                    color =
                        MaterialTheme.colorScheme.onSurfaceVariant
                )

                Row(
                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    Box(
                        modifier = Modifier
                            .size(9.dp)
                            .background(
                                if (conectado)
                                    GluloopVerde
                                else
                                    MaterialTheme.colorScheme.outline,
                                CircleShape
                            )
                    )

                    Spacer(Modifier.width(6.dp))

                    Text(
                        text =
                            if (conectado)
                                tr(
                                    idioma,
                                    "Ejecutando",
                                    "Running"
                                )
                            else
                                tr(
                                    idioma,
                                    "Sin conexión",
                                    "Offline"
                                ),
                        fontWeight = FontWeight.Bold,
                        color =
                            if (conectado)
                                GluloopVerde
                            else
                                MaterialTheme.colorScheme.outline
                    )
                }
            }

            HorizontalDivider()

            FilaParametro(
                nombre = tr(
                    idioma,
                    "Tiempo simulado",
                    "Simulated time"
                ),
                valor =
                    "${formatearTiempo(minutos)} / 24:00 h"
            )

            Spacer(Modifier.height(7.dp))

            LinearProgressIndicator(
                progress = { progreso },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = GluloopMorado,
                trackColor =
                    GluloopAzul.copy(alpha = 0.10f)
            )

            Spacer(Modifier.height(5.dp))

            Text(
                text =
                    "${(progreso * 100).toInt()}%",
                modifier =
                    Modifier.align(Alignment.End),
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = GluloopMorado
            )
        }
    }
}


// ============================================================
// CONTENEDOR PARA GRÁFICAS
// ============================================================

@Composable
private fun TarjetaGraficaEnsayo(
    titulo: String,
    subtitulo: String,
    contenido: @Composable () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                6.dp,
                RoundedCornerShape(24.dp)
            ),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {

        Column(
            modifier = Modifier.padding(15.dp)
        ) {

            TituloGrafica(
                titulo = titulo,
                subtitulo = subtitulo
            )

            contenido()
        }
    }
}


// ============================================================
// TARJETA DE MÉTRICA
// ============================================================

@Composable
private fun TarjetaMetrica(
    titulo: String,
    descripcion: String,
    valor: String,
    unidad: String,
    simbolo: String,
    color: Color,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier
            .height(170.dp)
            .shadow(
                5.dp,
                RoundedCornerShape(24.dp)
            ),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor =
                MaterialTheme.colorScheme.surface
        )
    ) {

        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(
                        x = 27.dp,
                        y = (-27).dp
                    )
                    .size(90.dp)
                    .background(
                        color.copy(alpha = 0.07f),
                        CircleShape
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                horizontalAlignment =
                    Alignment.CenterHorizontally,
                verticalArrangement =
                    Arrangement.Center
            ) {

                Surface(
                    modifier = Modifier.size(38.dp),
                    shape = CircleShape,
                    color = color.copy(alpha = 0.12f)
                ) {

                    Box(
                        contentAlignment = Alignment.Center
                    ) {

                        Text(
                            text = simbolo,
                            color = color,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(Modifier.height(5.dp))

                Text(
                    text = titulo,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(2.dp))

                Text(
                    text = descripcion,
                    fontSize = 9.sp,
                    textAlign = TextAlign.Center,
                    color =
                        MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(Modifier.height(5.dp))

                Text(
                    text = valor,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = color
                )

                Text(
                    text = unidad,
                    fontSize = 10.sp,
                    color =
                        MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}


// ============================================================
// TARJETA PRINCIPAL: TIEMPO EN RANGO
// ============================================================

@Composable
private fun TarjetaMetricaHorizontal(
    titulo: String,
    descripcion: String,
    valor: String,
    unidad: String,
    simbolo: String,
    color: Color
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(125.dp)
            .shadow(
                6.dp,
                RoundedCornerShape(24.dp)
            ),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor =
                MaterialTheme.colorScheme.surface
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Surface(
                modifier = Modifier.size(48.dp),
                shape = CircleShape,
                color = color.copy(alpha = 0.12f)
            ) {

                Box(
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = simbolo,
                        color = color,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = titulo,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(3.dp))

                Text(
                    text = descripcion,
                    fontSize = 10.sp,
                    lineHeight = 13.sp,
                    color =
                        MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(Modifier.width(8.dp))

            Column(
                horizontalAlignment = Alignment.End
            ) {

                Text(
                    text = valor,
                    fontSize = 31.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = color
                )

                Text(
                    text = unidad,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = color
                )
            }
        }
    }
}


// ============================================================
// CALCULAR PORCENTAJE
// ============================================================

private fun porcentaje(
    datos: List<Double>,
    condicion: (Double) -> Boolean
): Double? {

    if (datos.isEmpty()) {
        return null
    }

    val cantidad =
        datos.count(condicion)

    return 100.0 *
            cantidad /
            datos.size
}


// ============================================================
// FORMATEAR TIEMPO SIMULADO
// ============================================================

private fun formatearTiempo(
    minutos: Int
): String {

    val horas =
        minutos / 60

    val minutosRestantes =
        minutos % 60

    return String.format(
        Locale.getDefault(),
        "%02d:%02d",
        horas,
        minutosRestantes
    )
}