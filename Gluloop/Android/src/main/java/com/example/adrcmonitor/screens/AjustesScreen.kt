package com.example.adrcmonitor.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.adrcmonitor.components.*

@Composable
fun AjustesScreen(
    modoOscuro: Boolean,
    cambiarTema: () -> Unit,
    idioma: String,
    cambiarIdioma: (String) -> Unit
) {

    var menuIdioma by remember { mutableStateOf(false) }
    var acercaAbierto by remember { mutableStateOf(true) }

    val fondo = if (modoOscuro) {
        Brush.verticalGradient(
            listOf(
                MaterialTheme.colorScheme.background,
                MaterialTheme.colorScheme.background
            )
        )
    } else {
        Brush.verticalGradient(
            listOf(
                Color(0xFFEAF4FF),
                Color(0xFFF6F5FF),
                Color(0xFFF1F7FF)
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
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 18.dp, vertical = 16.dp)
        ) {

            // =================================================
            // CABECERA
            // =================================================

            CabeceraGLULOOP(
                subtitulo = tr(
                    idioma,
                    "Ajustes de la plataforma",
                    "Platform settings"
                ),
                idioma = idioma
            )

            Spacer(Modifier.height(24.dp))

            // =================================================
            // PREFERENCIAS
            // =================================================

            Text(
                text = tr(idioma, "Preferencias", "Preferences"),
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(Modifier.height(12.dp))

            // ---------------- IDIOMA ----------------

            Box {

                TarjetaAjuste(
                    onClick = { menuIdioma = true }
                ) {

                    IconoAjuste(
                        simbolo = "🌐",
                        color = GluloopMorado
                    )

                    Spacer(Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {

                        Text(
                            text = tr(idioma, "Idioma", "Language"),
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Text(
                            text = idioma,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                            .copy(alpha = 0.6f)
                    )
                }

                DropdownMenu(
                    expanded = menuIdioma,
                    onDismissRequest = { menuIdioma = false }
                ) {

                    DropdownMenuItem(
                        text = { Text("🇪🇸  Español") },
                        onClick = {
                            cambiarIdioma("Español")
                            menuIdioma = false
                        }
                    )

                    DropdownMenuItem(
                        text = { Text("🇬🇧  English") },
                        onClick = {
                            cambiarIdioma("English")
                            menuIdioma = false
                        }
                    )
                }
            }

            Spacer(Modifier.height(14.dp))

            // ---------------- TEMA ----------------

            TarjetaAjuste {

                IconoAjuste(
                    simbolo = if (modoOscuro) "🌙" else "☀️",
                    color = if (modoOscuro) GluloopMorado else Color(0xFFFFB300)
                )

                Spacer(Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {

                    Text(
                        text = tr(idioma, "Tema", "Theme"),
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Text(
                        text = if (modoOscuro)
                            tr(idioma, "Oscuro", "Dark")
                        else
                            tr(idioma, "Claro", "Light"),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Switch(
                    checked = modoOscuro,
                    onCheckedChange = { cambiarTema() },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = GluloopAzul,
                        uncheckedThumbColor = Color.White,
                        uncheckedTrackColor = Color(0xFFD0D5DD),
                        uncheckedBorderColor = Color.Transparent,
                        checkedBorderColor = Color.Transparent
                    )
                )
            }

            Spacer(Modifier.height(26.dp))

            // =================================================
            // INFORMACIÓN
            // =================================================

            Text(
                text = tr(idioma, "Información", "Information"),
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(Modifier.height(12.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        6.dp,
                        RoundedCornerShape(24.dp),
                        ambientColor = GluloopAzul.copy(alpha = 0.25f),
                        spotColor = GluloopAzul.copy(alpha = 0.25f)
                    )
                    .animateContentSize(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {

                Column(modifier = Modifier.padding(18.dp)) {

                    // ---------- ENCABEZADO DESPLEGABLE ----------

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { acercaAbierto = !acercaAbierto },
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = "∞",
                            style = TextStyle(
                                brush = Brush.linearGradient(
                                    listOf(GluloopAzul, GluloopMorado)
                                ),
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )

                        Spacer(Modifier.width(12.dp))

                        Text(
                            text = tr(
                                idioma,
                                "Acerca de GLULOOP",
                                "About GLULOOP"
                            ),
                            modifier = Modifier.weight(1f),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Icon(
                            imageVector = if (acercaAbierto)
                                Icons.Default.KeyboardArrowUp
                            else
                                Icons.Default.KeyboardArrowDown,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                                .copy(alpha = 0.6f)
                        )
                    }

                    // ---------- CONTENIDO ----------

                    AnimatedVisibility(visible = acercaAbierto) {

                        Column {

                            Spacer(Modifier.height(10.dp))

                            // Infinito grande con brillo
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(150.dp),
                                contentAlignment = Alignment.Center
                            ) {

                                // Resplandor
                                Box(
                                    modifier = Modifier
                                        .size(170.dp)
                                        .background(
                                            Brush.radialGradient(
                                                listOf(
                                                    GluloopAzul.copy(alpha = 0.22f),
                                                    Color.Transparent
                                                )
                                            ),
                                            CircleShape
                                        )
                                )

                                // Si tienes la imagen como recurso, reemplaza este
                                // Text por:
                                // Image(painterResource(R.drawable.infinito), null)
                                Text(
                                    text = "∞",
                                    style = TextStyle(
                                        brush = Brush.linearGradient(
                                            listOf(
                                                GluloopCeleste,
                                                GluloopAzul,
                                                GluloopMorado
                                            )
                                        ),
                                        fontSize = 150.sp,
                                        fontWeight = FontWeight.Bold,
                                        shadow = Shadow(
                                            color = GluloopAzul.copy(alpha = 0.55f),
                                            offset = Offset(0f, 8f),
                                            blurRadius = 40f
                                        )
                                    )
                                )
                            }

                            Spacer(Modifier.height(8.dp))

                            Text(
                                text = tr(
                                    idioma,
                                    "Plataforma experimental para supervisión y configuración de un sistema Hardware-in-the-Loop orientado a la evaluación de estrategias de control de glucosa.",
                                    "Experimental platform for monitoring and configuring a Hardware-in-the-Loop system for evaluating glucose control strategies."
                                ),
                                fontSize = 12.sp,
                                lineHeight = 18.sp,
                                color = MaterialTheme.colorScheme.onSurface
                                    .copy(alpha = 0.85f)
                            )

                            Spacer(Modifier.height(16.dp))

                            HorizontalDivider(
                                color = MaterialTheme.colorScheme.outline
                                    .copy(alpha = 0.15f)
                            )

                            InformacionApp(
                                nombre = tr(idioma, "Plataforma", "Platform"),
                                valor = "UVa/Padova + TI F28379D"
                            )

                            HorizontalDivider(
                                color = MaterialTheme.colorScheme.outline
                                    .copy(alpha = 0.15f)
                            )

                            InformacionApp(
                                nombre = tr(idioma, "Controlador", "Controller"),
                                valor = "ADRC"
                            )

                            HorizontalDivider(
                                color = MaterialTheme.colorScheme.outline
                                    .copy(alpha = 0.15f)
                            )

                            InformacionApp(
                                nombre = tr(idioma, "Modo", "Mode"),
                                valor = tr(
                                    idioma,
                                    "Experimental / Investigación",
                                    "Experimental / Research"
                                )
                            )

                            HorizontalDivider(
                                color = MaterialTheme.colorScheme.outline
                                    .copy(alpha = 0.15f)
                            )

                            InformacionApp(
                                nombre = tr(idioma, "Versión", "Version"),
                                valor = "1.0"
                            )

                            Spacer(Modifier.height(18.dp))

                            // Lema con línea curva
                            LemaGluloop(
                                texto = tr(
                                    idioma,
                                    "Control hoy para\nun mejor mañana",
                                    "Control today for\na better tomorrow"
                                )
                            )

                            Spacer(Modifier.height(6.dp))
                        }
                    }
                }
            }

            Spacer(Modifier.height(30.dp))
        }
    }
}

// ============================================================
// TARJETA BASE DE AJUSTE (Idioma / Tema)
// ============================================================

@Composable
private fun TarjetaAjuste(
    onClick: (() -> Unit)? = null,
    contenido: @Composable RowScope.() -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                5.dp,
                RoundedCornerShape(22.dp),
                ambientColor = GluloopAzul.copy(alpha = 0.20f),
                spotColor = GluloopAzul.copy(alpha = 0.20f)
            ),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .then(
                    if (onClick != null) Modifier.clickable { onClick() }
                    else Modifier
                )
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            content = contenido
        )
    }
}

// ============================================================
// ICONO CIRCULAR DE AJUSTE
// ============================================================

@Composable
private fun IconoAjuste(
    simbolo: String,
    color: Color
) {

    Box(
        modifier = Modifier
            .size(46.dp)
            .background(color.copy(alpha = 0.12f), CircleShape),
        contentAlignment = Alignment.Center
    ) {

        Text(
            text = simbolo,
            fontSize = 24.sp
        )
    }
}

// ============================================================
// FILA DE INFORMACIÓN
// ============================================================

@Composable
private fun InformacionApp(
    nombre: String,
    valor: String
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = nombre,
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(Modifier.width(15.dp))

        Text(
            text = valor,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.End,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

// ============================================================
// LEMA EN CURSIVA + LÍNEA CURVA
// ============================================================

@Composable
private fun LemaGluloop(
    texto: String
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 12.dp,
                bottom = 8.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = texto,
            fontFamily = FontFamily.Cursive,
            fontStyle = FontStyle.Italic,

            // MÁS GRANDE
            fontSize = 27.sp,
            lineHeight = 33.sp,

            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium,
            color = GluloopAzul
        )

        Spacer(
            modifier = Modifier.height(5.dp)
        )

        // Línea decorativa un poco más grande
        Canvas(
            modifier = Modifier
                .width(165.dp)
                .height(20.dp)
        ) {

            val path = Path().apply {

                moveTo(
                    0f,
                    size.height * 0.72f
                )

                cubicTo(
                    size.width * 0.28f,
                    size.height * 1.02f,

                    size.width * 0.60f,
                    size.height * 0.08f,

                    size.width,
                    size.height * 0.38f
                )
            }

            drawPath(
                path = path,

                brush = Brush.horizontalGradient(
                    listOf(
                        GluloopCeleste,
                        GluloopAzul,
                        GluloopMorado
                    )
                ),

                style = Stroke(
                    width = 3.5f,
                    cap = StrokeCap.Round
                )
            )
        }
    }
}