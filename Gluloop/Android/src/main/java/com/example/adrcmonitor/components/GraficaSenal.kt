package com.example.adrcmonitor.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas

@Composable
fun GraficaSenal(
    datos: List<Double>,
    minY: Double,
    maxY: Double,
    nivelesY: List<Double>,
    ventanaMinutos: Int,
    modifier: Modifier = Modifier,
    zonaObjetivo: Pair<Double, Double>? = null,
    idioma: String = "Español",
    tipo: TipoGrafica = TipoGrafica.GENERICA
) {

    val colorCurva = when (tipo) {
        TipoGrafica.GLUCOSA -> Color(0xFF079A8B)
        TipoGrafica.INSULINA -> Color(0xFF1976D2)
        TipoGrafica.GENERICA -> MaterialTheme.colorScheme.primary
    }

    val colorRelleno = when (tipo) {
        TipoGrafica.GLUCOSA ->
            Color(0xFF26A69A).copy(alpha = 0.13f)

        TipoGrafica.INSULINA ->
            Color(0xFF42A5F5).copy(alpha = 0.13f)

        TipoGrafica.GENERICA ->
            MaterialTheme.colorScheme.primary.copy(alpha = 0.10f)
    }

    val colorGrid =
        MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.65f)

    val colorTexto =
        MaterialTheme.colorScheme.onSurfaceVariant

    val colorRango =
        Color(0xFF4CAF50).copy(alpha = 0.10f)

    /*
     * Dos modos:
     *
     * ventanaMinutos <= 60:
     *     Monitoreo en tiempo real.
     *
     * ventanaMinutos > 60:
     *     Ensayo completo.
     *
     * Para Ensayo:
     * índice 0    = minuto 0
     * índice 720  = minuto 720
     * índice 1440 = minuto 1440 = 24 h
     */
    val esEnsayo24h = ventanaMinutos > 60

    Canvas(modifier = modifier) {

        val izq = 48f
        val der = 10f
        val arriba = 10f
        val abajo = 30f

        val ancho = size.width - izq - der
        val alto = size.height - arriba - abajo

        // -----------------------------------------------------
        // CONVERSIÓN Y
        // -----------------------------------------------------

        fun y(valor: Double): Float {

            val porcentaje =
                (valor.coerceIn(minY, maxY) - minY) /
                        (maxY - minY)

            return arriba +
                    alto -
                    (porcentaje * alto).toFloat()
        }

        // -----------------------------------------------------
        // CONVERSIÓN X
        // -----------------------------------------------------

        fun x(minuto: Int): Float {

            val maximoX =
                if (esEnsayo24h) {
                    // 0 ... 1440 minutos
                    1500f
                } else {
                    // Ventana de monitoreo
                    (ventanaMinutos - 1)
                        .coerceAtLeast(1)
                        .toFloat()
                }

            val posicion =
                minuto.toFloat()
                    .coerceIn(0f, maximoX)

            return izq +
                    (posicion / maximoX) *
                    ancho
        }

        // -----------------------------------------------------
        // ZONA OBJETIVO
        // -----------------------------------------------------

        zonaObjetivo?.let { zona ->

            drawRect(
                color = colorRango,
                topLeft = Offset(
                    izq,
                    y(zona.second)
                ),
                size = Size(
                    ancho,
                    y(zona.first) - y(zona.second)
                )
            )
        }

        // -----------------------------------------------------
        // TEXTO
        // -----------------------------------------------------

        val paint = android.graphics.Paint().apply {

            color = android.graphics.Color.rgb(
                (colorTexto.red * 255).toInt(),
                (colorTexto.green * 255).toInt(),
                (colorTexto.blue * 255).toInt()
            )

            textSize = 24f
            isAntiAlias = true
        }

        // -----------------------------------------------------
        // LÍNEAS HORIZONTALES
        // -----------------------------------------------------

        nivelesY.forEach { nivel ->

            val yy = y(nivel)

            drawLine(
                color = colorGrid,
                start = Offset(izq, yy),
                end = Offset(izq + ancho, yy),
                strokeWidth = 1.5f
            )

            val texto =
                if (nivel % 1.0 == 0.0) {
                    nivel.toInt().toString()
                } else {
                    "%.1f".format(nivel)
                }

            drawContext.canvas.nativeCanvas.drawText(
                texto,
                2f,
                yy + 7f,
                paint
            )
        }

        // -----------------------------------------------------
        // EJE X
        // -----------------------------------------------------

        if (esEnsayo24h) {

            // =============================================
            // ENSAYO: TIEMPO SIMULADO 0 - 24 h
            // =============================================

            val marcas =
                listOf(
                    0 to "00h",
                    360 to "06h",
                    720 to "12h",
                    1080 to "18h",
                    1440 to "24h"
                )

            marcas.forEach { (minuto, etiqueta) ->

                val xx = x(minuto)

                drawLine(
                    color = colorGrid.copy(alpha = 0.40f),
                    start = Offset(xx, arriba),
                    end = Offset(xx, arriba + alto),
                    strokeWidth = 1f
                )

                val desplazamiento =
                    when (minuto) {
                        0 -> 0f
                        1440 -> 38f
                        else -> 20f
                    }

                drawContext.canvas.nativeCanvas.drawText(
                    etiqueta,
                    xx - desplazamiento,
                    size.height - 2f,
                    paint
                )
            }

        } else {

            // =============================================
            // MONITOREO: ÚLTIMOS 60 DATOS
            // =============================================

            val marcas =
                listOf(
                    0 to "-60m",
                    15 to "-45m",
                    30 to "-30m",
                    45 to "-15m",
                    59 to if (idioma == "English") "now" else "ahora"
                )

            marcas.forEach { (indice, etiqueta) ->

                val xx = x(indice)

                drawLine(
                    color = colorGrid.copy(alpha = 0.40f),
                    start = Offset(xx, arriba),
                    end = Offset(xx, arriba + alto),
                    strokeWidth = 1f
                )

                val desplazamiento =
                    when (etiqueta) {
                        "ahora", "now" -> 42f
                        else -> 20f
                    }

                drawContext.canvas.nativeCanvas.drawText(
                    etiqueta,
                    xx - desplazamiento,
                    size.height - 2f,
                    paint
                )
            }
        }

        // -----------------------------------------------------
        // DATOS A REPRESENTAR
        // -----------------------------------------------------

        val datosVisibles =
            if (esEnsayo24h) {

                /*
                 * Ensayo completo:
                 *
                 * máximo 1441 muestras:s
                 * índices 0 ... 1440.
                 *
                 * NO depende del tiempo real.
                 */
                datos.take(1501)

            } else {

                /*
                 * Monitoreo:
                 * solamente las últimas muestras.
                 */
                datos.takeLast(
                    ventanaMinutos.coerceAtLeast(2)
                )
            }

        // -----------------------------------------------------
        // CURVA
        // -----------------------------------------------------

        if (datosVisibles.size >= 2) {

            val pathCurva = Path()

            datosVisibles.forEachIndexed { indice, valor ->

                if (indice == 0) {

                    pathCurva.moveTo(
                        x(indice),
                        y(valor)
                    )

                } else {

                    pathCurva.lineTo(
                        x(indice),
                        y(valor)
                    )
                }
            }

            // -------------------------------------------------
            // RELLENO
            // -------------------------------------------------

            val pathRelleno = Path()

            datosVisibles.forEachIndexed { indice, valor ->

                if (indice == 0) {

                    pathRelleno.moveTo(
                        x(indice),
                        arriba + alto
                    )

                    pathRelleno.lineTo(
                        x(indice),
                        y(valor)
                    )

                } else {

                    pathRelleno.lineTo(
                        x(indice),
                        y(valor)
                    )
                }
            }

            val ultimoIndice =
                datosVisibles.lastIndex

            pathRelleno.lineTo(
                x(ultimoIndice),
                arriba + alto
            )

            pathRelleno.close()

            drawPath(
                path = pathRelleno,
                color = colorRelleno
            )

            // -------------------------------------------------
            // CURVA PRINCIPAL
            // -------------------------------------------------

            drawPath(
                path = pathCurva,
                color = colorCurva,
                style = Stroke(width = 4f)
            )

            // -------------------------------------------------
            // ÚLTIMO DATO
            // -------------------------------------------------

            val ultimoValor =
                datosVisibles.last()

            drawCircle(
                color = colorCurva.copy(alpha = 0.20f),
                radius = 13f,
                center = Offset(
                    x(ultimoIndice),
                    y(ultimoValor)
                )
            )

            drawCircle(
                color = colorCurva,
                radius = 6.5f,
                center = Offset(
                    x(ultimoIndice),
                    y(ultimoValor)
                )
            )
        }
    }
}


/*
 * Permite utilizar colores diferentes dependiendo
 * de la señal que estemos representando.
 */
enum class TipoGrafica {
    GLUCOSA,
    INSULINA,
    GENERICA
}