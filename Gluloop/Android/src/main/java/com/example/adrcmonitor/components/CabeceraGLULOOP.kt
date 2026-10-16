package com.example.adrcmonitor.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CabeceraGLULOOP(
    subtitulo: String,
    conectado: Boolean? = null,
    idioma: String = "Español"
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 2.dp,
                bottom = 2.dp
            )
    ) {

        // =====================================================
        // LOGO + LIVE / OFFLINE
        // =====================================================

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // =================================================
            // LOGO
            // =================================================

            Column(
                modifier = Modifier.wrapContentWidth()
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    // GLU
                    Text(
                        text = "GLU",
                        fontSize = 23.sp,
                        lineHeight = 23.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = (-0.7).sp,
                        color = Color(0xFF071B4B)
                    )

                    // L
                    Text(
                        text = "L",
                        fontSize = 23.sp,
                        lineHeight = 23.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = (-0.7).sp,
                        color = Color(0xFF1677FF)
                    )

                    // OO
                    Text(
                        text = "OO",
                        fontSize = 23.sp,
                        lineHeight = 23.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = (-0.7).sp,
                        color = Color(0xFF2589F5)
                    )

                    // P
                    Text(
                        text = "P",
                        fontSize = 23.sp,
                        lineHeight = 23.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = (-0.7).sp,
                        color = Color(0xFF7657EE)
                    )
                }

                // =================================================
                // ONDA
                // Empieza debajo de la L
                // Termina debajo de la P
                // =================================================

                Row {

                    // Antes estaba en 62.dp.
                    // Ahora la movemos hacia la izquierda.
                    Spacer(
                        modifier = Modifier.width(48.dp)
                    )

                    Canvas(
                        modifier = Modifier
                            .width(82.dp)
                            .height(10.dp)
                    ) {

                        val path = Path()

                        // Inicio
                        path.moveTo(
                            0f,
                            size.height * 0.55f
                        )

                        // =========================================
                        // PRIMERA ONDA
                        // =========================================

                        path.cubicTo(
                            size.width * 0.10f,
                            size.height * 0.05f,

                            size.width * 0.22f,
                            size.height * 0.05f,

                            size.width * 0.34f,
                            size.height * 0.50f
                        )

                        // =========================================
                        // BAJADA CENTRAL
                        // =========================================

                        path.cubicTo(
                            size.width * 0.46f,
                            size.height * 0.95f,

                            size.width * 0.58f,
                            size.height * 0.95f,

                            size.width * 0.69f,
                            size.height * 0.48f
                        )

                        // =========================================
                        // SUBIDA FINAL
                        // =========================================

                        path.cubicTo(
                            size.width * 0.79f,
                            size.height * 0.05f,

                            size.width * 0.89f,
                            size.height * 0.08f,

                            size.width,
                            size.height * 0.42f
                        )

                        drawPath(
                            path = path,

                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color(0xFF31C8F5),
                                    Color(0xFF1677FF),
                                    Color(0xFF7657EE)
                                )
                            ),

                            style = Stroke(
                                width = 1.8.dp.toPx()
                            )
                        )
                    }
                }
            }

            // =================================================
            // EMPUJAR ESTADO A LA DERECHA
            // =================================================

            Spacer(
                modifier = Modifier.weight(1f)
            )

            if (conectado != null) {

                Spacer(
                    modifier = Modifier.width(12.dp)
                )

                EstadoConexionCompacto(
                    conectado = conectado,
                    idioma = idioma
                )
            }
        }

        // =====================================================
        // SUBTÍTULO
        // =====================================================

        Spacer(
            modifier = Modifier.height(5.dp)
        )

        Text(
            text = subtitulo,
            fontSize = 11.sp,
            lineHeight = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}


// =============================================================
// LIVE / OFFLINE
// =============================================================

@Composable
private fun EstadoConexionCompacto(
    conectado: Boolean,
    idioma: String
) {

    val texto = if (conectado) {
        "LIVE"
    } else {
        tr(
            idioma,
            "OFFLINE",
            "OFFLINE"
        )
    }

    val fondo = if (conectado) {

        Brush.horizontalGradient(
            colors = listOf(
                Color(0xFF00B96B),
                Color(0xFF078A78)
            )
        )

    } else {

        Brush.horizontalGradient(
            colors = listOf(
                Color(0xFF9299A8),
                Color(0xFF747C8C)
            )
        )
    }

    Row(
        modifier = Modifier
            .background(
                brush = fondo,
                shape = RoundedCornerShape(50.dp)
            )
            .padding(
                horizontal = 8.dp,
                vertical = 4.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // Punto de conexión
        Box(
            modifier = Modifier
                .size(5.dp)
                .background(
                    color = if (conectado) {
                        Color(0xFF2AF49B)
                    } else {
                        Color(0xFFD8DCE5)
                    },
                    shape = CircleShape
                )
        )

        Spacer(
            modifier = Modifier.width(4.dp)
        )

        Text(
            text = texto,
            color = Color.White,
            fontSize = 7.5.sp,
            lineHeight = 8.sp,
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = 0.25.sp
        )
    }
}