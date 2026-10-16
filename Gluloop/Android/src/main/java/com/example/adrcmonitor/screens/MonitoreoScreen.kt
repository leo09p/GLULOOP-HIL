package com.example.adrcmonitor.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.adrcmonitor.components.*


@Composable
fun MonitoreoScreen(
    glucosa: Double,
    insulina: Double,
    iob: Double?,
    conectado: Boolean,
    datosGlucosa: List<Double>,
    datosInsulina: List<Double>,
    ultimaLectura: String,
    modoOscuro: Boolean,
    idioma: String
) {

    // ========================================================
    // ESTADO DE GLUCOSA
    // ========================================================

    val estadoGlucosa = when {

        glucosa == 0.0 ->
            tr(
                idioma,
                "Esperando datos",
                "Waiting"
            )

        glucosa < 70.0 ->
            tr(
                idioma,
                "Glucosa baja",
                "Low"
            )

        glucosa > 180.0 ->
            tr(
                idioma,
                "Glucosa alta",
                "High"
            )

        else ->
            tr(
                idioma,
                "En rango",
                "In range"
            )
    }


    // ========================================================
    // DATOS DE LA ÚLTIMA HORA
    //
    // Por ahora conservamos 60 muestras.
    // Luego lo convertiremos en una ventana temporal real.
    // ========================================================

    val glucosaVentana =
        datosGlucosa.takeLast(60)

    val insulinaVentana =
        datosInsulina.takeLast(60)


    // ========================================================
    // FONDO
    // ========================================================

    val fondo = if (modoOscuro) {

        Brush.verticalGradient(
            colors = listOf(
                MaterialTheme.colorScheme.background,
                Color(0xFF101827)
            )
        )

    } else {

        Brush.verticalGradient(
            colors = listOf(
                Color(0xFFF5FBFF),
                Color(0xFFF9F8FF),
                Color(0xFFF4FAFF)
            )
        )
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(fondo)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(
                    rememberScrollState()
                )
                .padding(
                    horizontal = 16.dp,
                    vertical = 15.dp
                )
        ) {

            // =================================================
            // CABECERA
            // =================================================

            CabeceraGLULOOP(
                subtitulo = tr(
                    idioma,
                    "Monitoreo HIL en tiempo real",
                    "Real-time HIL monitoring"
                ),
                conectado = conectado,
                idioma = idioma
            )


            Spacer(
                modifier = Modifier.height(20.dp)
            )


            // =================================================
            // GLUCOSA + INSULINA
            // =================================================

            Row(
                modifier = Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.spacedBy(12.dp)
            ) {

                TarjetaGlucosa(
                    glucosa = glucosa,
                    estado = estadoGlucosa,
                    modoOscuro = modoOscuro,
                    idioma = idioma,
                    modifier = Modifier.weight(1f)
                )


                TarjetaInsulina(
                    insulina = insulina,
                    hayDatos =
                        datosInsulina.isNotEmpty(),
                    modoOscuro = modoOscuro,
                    idioma = idioma,
                    modifier = Modifier.weight(1f)
                )
            }


            // =================================================
            // GLUCOSA ÚLTIMA HORA
            // =================================================

            Spacer(
                modifier = Modifier.height(18.dp)
            )


            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 7.dp,
                        shape =
                            RoundedCornerShape(24.dp)
                    ),

                shape =
                    RoundedCornerShape(24.dp),

                colors =
                    CardDefaults.cardColors(
                        containerColor =
                            MaterialTheme
                                .colorScheme
                                .surface
                    )
            ) {

                Column(
                    modifier = Modifier.padding(
                        15.dp
                    )
                ) {

                    TituloGrafica(
                        titulo = tr(
                            idioma,
                            "Glucosa · Última hora",
                            "Glucose · Last hour"
                        ),

                        subtitulo = tr(
                            idioma,
                            "Respuesta glucémica del sistema HIL",
                            "Glycemic response of the HIL system"
                        )
                    )


                    GraficaSenal(
                        datos =
                            glucosaVentana,

                        minY = 40.0,
                        maxY = 220.0,

                        nivelesY =
                            listOf(
                                40.0,
                                70.0,
                                110.0,
                                180.0,
                                220.0
                            ),

                        ventanaMinutos = 60,

                        modifier = Modifier
                            .fillMaxWidth()
                            .height(205.dp),

                        zonaObjetivo =
                            70.0 to 180.0,

                        idioma = idioma,

                        tipo =
                            TipoGrafica.GLUCOSA
                    )
                }
            }


            // =================================================
            // INSULINA ÚLTIMA HORA
            // =================================================

            Spacer(
                modifier = Modifier.height(16.dp)
            )


            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 7.dp,
                        shape =
                            RoundedCornerShape(24.dp)
                    ),

                shape =
                    RoundedCornerShape(24.dp),

                colors =
                    CardDefaults.cardColors(
                        containerColor =
                            MaterialTheme
                                .colorScheme
                                .surface
                    )
            ) {

                Column(
                    modifier = Modifier.padding(
                        15.dp
                    )
                ) {

                    TituloGrafica(
                        titulo = tr(
                            idioma,
                            "Insulina · Última hora",
                            "Insulin · Last hour"
                        ),

                        subtitulo = tr(
                            idioma,
                            "Tasa administrada por el controlador",
                            "Controller delivery rate"
                        )
                    )


                    GraficaSenal(
                        datos =
                            insulinaVentana,

                        minY = 0.0,
                        maxY = 5.0,

                        nivelesY =
                            listOf(
                                0.0,
                                1.0,
                                2.5,
                                4.0,
                                5.0
                            ),

                        ventanaMinutos = 60,

                        modifier = Modifier
                            .fillMaxWidth()
                            .height(205.dp),

                        idioma = idioma,

                        tipo =
                            TipoGrafica.INSULINA
                    )
                }
            }


            // =================================================
            // IOB
            // =================================================

            Spacer(
                modifier = Modifier.height(16.dp)
            )


            TarjetaIOB(
                iob = iob,
                idioma = idioma
            )


            // =================================================
            // ÚLTIMA LECTURA
            // =================================================

            Spacer(
                modifier = Modifier.height(12.dp)
            )


            Card(
                modifier = Modifier.fillMaxWidth(),

                shape =
                    RoundedCornerShape(18.dp),

                colors =
                    CardDefaults.cardColors(
                        containerColor =
                            GluloopAzul.copy(
                                alpha = 0.06f
                            )
                    )
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 15.dp,
                            vertical = 11.dp
                        ),

                    horizontalArrangement =
                        Arrangement.SpaceBetween
                ) {

                    Text(
                        text = tr(
                            idioma,
                            "Última lectura",
                            "Last reading"
                        ),

                        style =
                            MaterialTheme
                                .typography
                                .bodySmall,

                        color =
                            MaterialTheme
                                .colorScheme
                                .onSurfaceVariant
                    )


                    Text(
                        text = ultimaLectura,

                        style =
                            MaterialTheme
                                .typography
                                .bodySmall,

                        color =
                            GluloopAzul,

                        fontWeight =
                            androidx.compose.ui.text.font
                                .FontWeight.Bold
                    )
                }
            }


            Spacer(
                modifier = Modifier.height(25.dp)
            )
        }
    }
}