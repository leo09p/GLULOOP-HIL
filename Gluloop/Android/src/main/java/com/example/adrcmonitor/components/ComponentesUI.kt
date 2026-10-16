package com.example.adrcmonitor.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


// ============================================================
// PALETA GLULOOP
// ============================================================

val GluloopAzul = Color(0xFF1677FF)
val GluloopAzulOscuro = Color(0xFF163A8A)
val GluloopCeleste = Color(0xFF00B8F5)
val GluloopMorado = Color(0xFF7C4DFF)
val GluloopVerde = Color(0xFF00B96B)
val GluloopRojo = Color(0xFFFF3B3B)
val GluloopNaranja = Color(0xFFFF9800)


// ============================================================
// TRADUCCIÓN
// ============================================================

fun tr(
    idioma: String,
    espanol: String,
    ingles: String
): String {

    return if (idioma == "English") {
        ingles
    } else {
        espanol
    }
}


// ============================================================
// TÍTULO DE GRÁFICA
// ============================================================

@Composable
fun TituloGrafica(
    titulo: String,
    subtitulo: String
) {

    Text(
        text = titulo,
        fontSize = 17.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurface
    )

    Spacer(
        modifier = Modifier.height(2.dp)
    )

    Text(
        text = subtitulo,
        fontSize = 11.sp,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )

    Spacer(
        modifier = Modifier.height(9.dp)
    )
}


// ============================================================
// FILA DE PARÁMETRO
// ============================================================

@Composable
fun FilaParametro(
    nombre: String,
    valor: String
) {

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
            text = nombre,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.weight(1f)
        )

        Spacer(
            modifier = Modifier.width(12.dp)
        )

        Text(
            text = valor,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}


// ============================================================
// ESTADO DE GLUCOSA
// ============================================================

@Composable
fun EstadoGlucosa(
    glucosa: Double,
    estado: String,
    modoOscuro: Boolean
) {

    val fondo: Color
    val texto: Color

    when {

        glucosa == 0.0 -> {

            fondo =
                MaterialTheme.colorScheme.surfaceVariant

            texto =
                MaterialTheme.colorScheme.onSurfaceVariant
        }


        glucosa < 70.0 -> {

            fondo =
                GluloopNaranja.copy(
                    alpha =
                        if (modoOscuro) 0.22f
                        else 0.13f
                )

            texto =
                GluloopNaranja
        }


        glucosa > 180.0 -> {

            fondo =
                GluloopRojo.copy(
                    alpha =
                        if (modoOscuro) 0.22f
                        else 0.12f
                )

            texto =
                GluloopRojo
        }


        else -> {

            fondo =
                GluloopVerde.copy(
                    alpha =
                        if (modoOscuro) 0.23f
                        else 0.12f
                )

            texto =
                GluloopVerde
        }
    }


    Surface(
        shape = RoundedCornerShape(50.dp),
        color = fondo
    ) {

        Row(
            modifier = Modifier.padding(
                horizontal = 9.dp,
                vertical = 5.dp
            ),

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(7.dp)
                    .background(
                        texto,
                        CircleShape
                    )
            )

            Spacer(
                modifier = Modifier.width(5.dp)
            )

            Text(
                text = estado,
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold,
                color = texto
            )
        }
    }
}


// ============================================================
// TARJETA DE GLUCOSA
// ============================================================

@Composable
fun TarjetaGlucosa(
    glucosa: Double,
    estado: String,
    modoOscuro: Boolean,
    idioma: String,
    modifier: Modifier = Modifier
) {

    val fondo = if (modoOscuro) {

        Brush.verticalGradient(
            colors = listOf(
                Color(0xFF132D35),
                Color(0xFF10262D)
            )
        )

    } else {

        Brush.verticalGradient(
            colors = listOf(
                Color.White,
                Color(0xFFF3FFFC)
            )
        )
    }


    Card(
        modifier = modifier
            .height(180.dp)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(24.dp)
            ),

        shape = RoundedCornerShape(24.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(fondo)
        ) {

            // ------------------------------------------------
            // DECORACIÓN INFERIOR
            // ------------------------------------------------

            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(
                        x = 18.dp,
                        y = 25.dp
                    )
                    .size(100.dp)
                    .background(
                        GluloopVerde.copy(
                            alpha = 0.07f
                        ),
                        CircleShape
                    )
            )


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

                // --------------------------------------------
                // TÍTULO
                // --------------------------------------------

                Row(
                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    Surface(
                        modifier = Modifier.size(30.dp),
                        shape = CircleShape,
                        color =
                            GluloopRojo.copy(
                                alpha = 0.10f
                            )
                    ) {

                        Box(
                            contentAlignment =
                                Alignment.Center
                        ) {

                            Text(
                                text = "●",
                                color = GluloopRojo,
                                fontSize = 15.sp
                            )
                        }
                    }


                    Spacer(
                        modifier = Modifier.width(8.dp)
                    )


                    Text(
                        text = tr(
                            idioma,
                            "Glucosa",
                            "Glucose"
                        ),

                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }


                Spacer(
                    modifier = Modifier.height(12.dp)
                )


                // --------------------------------------------
                // VALOR
                // --------------------------------------------

                Row(
                    verticalAlignment =
                        Alignment.Bottom
                ) {

                    Text(
                        text =
                            if (glucosa == 0.0)
                                "--"
                            else
                                "%.1f".format(glucosa),

                        fontSize = 34.sp,
                        fontWeight = FontWeight.Bold,
                        color =
                            MaterialTheme.colorScheme.onSurface
                    )


                    Spacer(
                        modifier = Modifier.width(5.dp)
                    )


                    Text(
                        text = "mg/dL",
                        fontSize = 11.sp,
                        color =
                            MaterialTheme.colorScheme.onSurfaceVariant,

                        modifier =
                            Modifier.padding(
                                bottom = 5.dp
                            )
                    )
                }


                Spacer(
                    modifier = Modifier.height(10.dp)
                )


                EstadoGlucosa(
                    glucosa = glucosa,
                    estado = estado,
                    modoOscuro = modoOscuro
                )


                Spacer(
                    modifier = Modifier.weight(1f)
                )


                // --------------------------------------------
                // PEQUEÑA DECORACIÓN DE SEÑAL
                // --------------------------------------------

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(15.dp),

                    horizontalArrangement =
                        Arrangement.spacedBy(4.dp),

                    verticalAlignment =
                        Alignment.Bottom
                ) {

                    listOf(
                        4,
                        7,
                        5,
                        10,
                        7,
                        12,
                        8,
                        14,
                        9
                    ).forEach { altura ->

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(altura.dp)
                                .background(
                                    GluloopVerde.copy(
                                        alpha = 0.55f
                                    ),
                                    RoundedCornerShape(20.dp)
                                )
                        )
                    }
                }
            }
        }
    }
}


// ============================================================
// TARJETA DE INSULINA
// ============================================================

@Composable
fun TarjetaInsulina(
    insulina: Double,
    hayDatos: Boolean,
    modoOscuro: Boolean,
    idioma: String,
    modifier: Modifier = Modifier
) {

    val fondo = if (modoOscuro) {

        Brush.verticalGradient(
            colors = listOf(
                Color(0xFF142A43),
                Color(0xFF111F34)
            )
        )

    } else {

        Brush.verticalGradient(
            colors = listOf(
                Color.White,
                Color(0xFFF2F7FF)
            )
        )
    }


    Card(
        modifier = modifier
            .height(180.dp)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(24.dp)
            ),

        shape = RoundedCornerShape(24.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(fondo)
        ) {

            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(
                        x = 18.dp,
                        y = 25.dp
                    )
                    .size(100.dp)
                    .background(
                        GluloopAzul.copy(
                            alpha = 0.07f
                        ),
                        CircleShape
                    )
            )


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

                Row(
                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    Surface(
                        modifier = Modifier.size(30.dp),
                        shape = CircleShape,
                        color =
                            GluloopAzul.copy(
                                alpha = 0.10f
                            )
                    ) {

                        Box(
                            contentAlignment =
                                Alignment.Center
                        ) {

                            Text(
                                text = "✦",
                                color = GluloopAzul,
                                fontSize = 16.sp
                            )
                        }
                    }


                    Spacer(
                        modifier = Modifier.width(8.dp)
                    )


                    Text(
                        text = tr(
                            idioma,
                            "Insulina",
                            "Insulin"
                        ),

                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }


                Spacer(
                    modifier = Modifier.height(12.dp)
                )


                Row(
                    verticalAlignment =
                        Alignment.Bottom
                ) {

                    Text(
                        text =
                            if (!hayDatos)
                                "--"
                            else
                                "%.2f".format(insulina),

                        fontSize = 34.sp,
                        fontWeight = FontWeight.Bold,
                        color =
                            MaterialTheme.colorScheme.onSurface
                    )


                    Spacer(
                        modifier = Modifier.width(5.dp)
                    )


                    Text(
                        text = "U/h",
                        fontSize = 11.sp,
                        color =
                            MaterialTheme.colorScheme.onSurfaceVariant,

                        modifier =
                            Modifier.padding(
                                bottom = 5.dp
                            )
                    )
                }


                Spacer(
                    modifier = Modifier.height(10.dp)
                )


                Surface(
                    shape =
                        RoundedCornerShape(50.dp),

                    color =
                        GluloopAzul.copy(
                            alpha =
                                if (modoOscuro)
                                    0.22f
                                else
                                    0.10f
                        )
                ) {

                    Text(
                        text = tr(
                            idioma,
                            "● Administración",
                            "● Delivery"
                        ),

                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = GluloopAzul,

                        modifier =
                            Modifier.padding(
                                horizontal = 9.dp,
                                vertical = 5.dp
                            )
                    )
                }


                Spacer(
                    modifier = Modifier.weight(1f)
                )


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(15.dp),

                    horizontalArrangement =
                        Arrangement.spacedBy(4.dp),

                    verticalAlignment =
                        Alignment.Bottom
                ) {

                    listOf(
                        3,
                        5,
                        8,
                        12,
                        7,
                        14,
                        10,
                        13,
                        9
                    ).forEach { altura ->

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(altura.dp)
                                .background(
                                    GluloopAzul.copy(
                                        alpha = 0.55f
                                    ),
                                    RoundedCornerShape(20.dp)
                                )
                        )
                    }
                }
            }
        }
    }
}


// ============================================================
// TARJETA IOB
// ============================================================

@Composable
fun TarjetaIOB(
    iob: Double?,
    idioma: String,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 5.dp,
                shape = RoundedCornerShape(22.dp)
            ),

        shape = RoundedCornerShape(22.dp),

        colors = CardDefaults.cardColors(
            containerColor =
                MaterialTheme.colorScheme.surface
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(17.dp),

            horizontalArrangement =
                Arrangement.SpaceBetween,

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Row(
                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Surface(
                    modifier = Modifier.size(40.dp),
                    shape = RoundedCornerShape(13.dp),
                    color =
                        GluloopAzul.copy(
                            alpha = 0.10f
                        )
                ) {

                    Box(
                        contentAlignment =
                            Alignment.Center
                    ) {

                        Text(
                            text = "◆",
                            color = GluloopAzul,
                            fontSize = 19.sp
                        )
                    }
                }


                Spacer(
                    modifier = Modifier.width(12.dp)
                )


                Column {

                    Text(
                        text = "IOB",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )


                    Text(
                        text = tr(
                            idioma,
                            "Insulina activa",
                            "Active insulin"
                        ),

                        fontSize = 11.sp,

                        color =
                            MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }


            Text(
                text =
                    iob?.let {
                        "%.2f U".format(it)
                    } ?: "-- U",

                fontSize = 23.sp,
                fontWeight = FontWeight.Bold,
                color = GluloopAzulOscuro
            )
        }
    }
}