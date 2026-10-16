package com.example.adrcmonitor.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.adrcmonitor.components.*


@Composable
fun ControlScreen(

    conectado: Boolean,
    idioma: String,

    // =========================================================
    // FUNCIÓN PARA ENVIAR LOS 3 PARÁMETROS
    // =========================================================

    onGuardarParametros: (
        Double,
        Double,
        Double
    ) -> Unit,

    // =========================================================
    // VALORES CONFIRMADOS POR LA TEXAS
    // =========================================================

    b0Confirmado: Double? = null,

    insulinaBasalConfirmada: Double? = null,

    glucosaBasalConfirmada: Double? = null
) {

    // =========================================================
    // PARÁMETROS PREPARADOS EN LA APP
    // =========================================================

    var b0 by remember {
        mutableStateOf("")
    }

    var glucosaBasal by remember {
        mutableStateOf("")
    }

    var insulinaBasal by remember {
        mutableStateOf("")
    }


    var mostrarEditor by remember {
        mutableStateOf(false)
    }


    // =========================================================
    // SABER SI YA EXISTE CONFIRMACIÓN DE LA TEXAS
    // =========================================================

    val parametrosConfirmados =

        b0Confirmado != null &&

                insulinaBasalConfirmada != null &&

                glucosaBasalConfirmada != null


    // =========================================================
    // FONDO
    // =========================================================

    val fondo = Brush.verticalGradient(

        listOf(

            Color(0xFFF6FAFF),

            Color(0xFFFBFAFF),

            Color(0xFFF7FAFF)
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

                .verticalScroll(
                    rememberScrollState()
                )

                .padding(16.dp)

        ) {


            // =====================================================
            // CABECERA
            // =====================================================

            CabeceraGLULOOP(

                subtitulo = tr(

                    idioma,

                    "Configuración del controlador ADRC",

                    "ADRC controller configuration"
                ),

                conectado = conectado,

                idioma = idioma
            )


            Spacer(
                Modifier.height(28.dp)
            )


            // =====================================================
            // CONFIGURACIÓN
            // =====================================================

            Text(

                text = tr(

                    idioma,

                    "Configuración del controlador",

                    "Controller configuration"
                ),

                fontSize = 20.sp,

                fontWeight = FontWeight.ExtraBold
            )


            Spacer(
                Modifier.height(5.dp)
            )


            Text(

                text = tr(

                    idioma,

                    "Configura los parámetros que serán enviados al controlador ADRC implementado en la F28379D.",

                    "Configure the parameters that will be sent to the ADRC controller implemented on the F28379D."
                ),

                fontSize = 11.sp,

                lineHeight = 16.sp,

                color =
                    MaterialTheme
                        .colorScheme
                        .onSurfaceVariant
            )


            Spacer(
                Modifier.height(17.dp)
            )


            // =====================================================
            // BOTÓN MODIFICAR
            // =====================================================

            Box(

                modifier = Modifier

                    .fillMaxWidth()

                    .height(58.dp)

                    .shadow(

                        elevation = 5.dp,

                        shape =
                            RoundedCornerShape(19.dp)
                    )

                    .background(

                        brush =
                            Brush.horizontalGradient(

                                listOf(

                                    GluloopAzul,

                                    GluloopMorado
                                )
                            ),

                        shape =
                            RoundedCornerShape(19.dp)
                    )

                    .clickable {

                        mostrarEditor = true
                    },

                contentAlignment =
                    Alignment.Center

            ) {


                Row(

                    verticalAlignment =
                        Alignment.CenterVertically

                ) {


                    Text(

                        text = "✎",

                        fontSize = 20.sp,

                        color = Color.White,

                        fontWeight =
                            FontWeight.Bold
                    )


                    Spacer(
                        Modifier.width(9.dp)
                    )


                    Text(

                        text = tr(

                            idioma,

                            "Modificar parámetros",

                            "Edit parameters"
                        ),

                        fontSize = 15.sp,

                        color = Color.White,

                        fontWeight =
                            FontWeight.Bold
                    )
                }
            }


            Spacer(
                Modifier.height(11.dp)
            )


            // =====================================================
            // ACLARACIÓN
            // =====================================================

            Surface(

                modifier =
                    Modifier.fillMaxWidth(),

                shape =
                    RoundedCornerShape(16.dp),

                color =
                    MaterialTheme
                        .colorScheme
                        .surface
                        .copy(alpha = 0.80f)

            ) {


                Row(

                    modifier =
                        Modifier.padding(13.dp),

                    verticalAlignment =
                        Alignment.CenterVertically

                ) {


                    Surface(

                        modifier =
                            Modifier.size(25.dp),

                        shape = CircleShape,

                        color =
                            GluloopAzul
                                .copy(alpha = 0.10f)

                    ) {


                        Box(

                            contentAlignment =
                                Alignment.Center

                        ) {


                            Text(

                                text = "i",

                                color =
                                    GluloopAzul,

                                fontSize = 12.sp,

                                fontWeight =
                                    FontWeight.Bold
                            )
                        }
                    }


                    Spacer(
                        Modifier.width(9.dp)
                    )


                    Text(

                        text = tr(

                            idioma,

                            "Al guardar, los parámetros se envían al hardware. El estado del controlador se actualiza únicamente cuando la F28379D devuelve la confirmación.",

                            "When saved, the parameters are sent to the hardware. Controller status is updated only when the F28379D returns confirmation."
                        ),

                        modifier =
                            Modifier.weight(1f),

                        fontSize = 10.sp,

                        lineHeight = 14.sp,

                        color =
                            MaterialTheme
                                .colorScheme
                                .onSurfaceVariant
                    )
                }
            }


            Spacer(
                Modifier.height(32.dp)
            )


            // =====================================================
            // ESTADO DEL CONTROLADOR
            // =====================================================

            Text(

                text = tr(

                    idioma,

                    "Estado del controlador",

                    "Controller status"
                ),

                fontSize = 20.sp,

                fontWeight =
                    FontWeight.ExtraBold
            )


            Spacer(
                Modifier.height(5.dp)
            )


            Text(

                text = tr(

                    idioma,

                    "Parámetros activos confirmados por la F28379D",

                    "Active parameters confirmed by the F28379D"
                ),

                fontSize = 11.sp,

                color =
                    MaterialTheme
                        .colorScheme
                        .onSurfaceVariant
            )


            Spacer(
                Modifier.height(13.dp)
            )


            Card(

                modifier = Modifier

                    .fillMaxWidth()

                    .shadow(

                        elevation = 5.dp,

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

                    modifier =
                        Modifier.padding(18.dp)

                ) {


                    // -------------------------------------------------
                    // ADRC
                    // -------------------------------------------------

                    Row(

                        modifier =
                            Modifier.fillMaxWidth(),

                        verticalAlignment =
                            Alignment.CenterVertically

                    ) {


                        Surface(

                            modifier =
                                Modifier.size(42.dp),

                            shape =
                                RoundedCornerShape(14.dp),

                            color =
                                GluloopAzul
                                    .copy(alpha = 0.09f)

                        ) {


                            Box(

                                contentAlignment =
                                    Alignment.Center

                            ) {


                                Text(

                                    text = "C",

                                    color =
                                        GluloopAzul,

                                    fontSize = 17.sp,

                                    fontWeight =
                                        FontWeight.ExtraBold
                                )
                            }
                        }


                        Spacer(
                            Modifier.width(11.dp)
                        )


                        Column(

                            modifier =
                                Modifier.weight(1f)

                        ) {


                            Text(

                                text = "ADRC",

                                fontSize = 17.sp,

                                fontWeight =
                                    FontWeight.ExtraBold
                            )


                            Text(

                                text = tr(

                                    idioma,

                                    "Controlador implementado en F28379D",

                                    "Controller implemented on F28379D"
                                ),

                                fontSize = 9.sp,

                                color =
                                    MaterialTheme
                                        .colorScheme
                                        .onSurfaceVariant
                            )
                        }


                        // =================================================
                        // ESTADO DE CONEXIÓN
                        // =================================================

                        Surface(

                            shape =
                                RoundedCornerShape(50.dp),

                            color =

                                if (conectado)

                                    GluloopVerde
                                        .copy(alpha = 0.09f)

                                else

                                    MaterialTheme
                                        .colorScheme
                                        .surfaceVariant

                        ) {


                            Row(

                                modifier =
                                    Modifier.padding(

                                        horizontal = 9.dp,

                                        vertical = 6.dp
                                    ),

                                verticalAlignment =
                                    Alignment.CenterVertically

                            ) {


                                Box(

                                    modifier = Modifier

                                        .size(7.dp)

                                        .background(

                                            if (conectado)

                                                GluloopVerde

                                            else

                                                MaterialTheme
                                                    .colorScheme
                                                    .outline,

                                            CircleShape
                                        )
                                )


                                Spacer(
                                    Modifier.width(5.dp)
                                )


                                Text(

                                    text =

                                        if (conectado) {

                                            tr(

                                                idioma,

                                                "EN LÍNEA",

                                                "ONLINE"
                                            )

                                        } else {

                                            tr(

                                                idioma,

                                                "SIN DATOS",

                                                "NO DATA"
                                            )
                                        },

                                    fontSize = 8.sp,

                                    fontWeight =
                                        FontWeight.Bold,

                                    color =

                                        if (conectado)

                                            GluloopVerde

                                        else

                                            MaterialTheme
                                                .colorScheme
                                                .outline
                                )
                            }
                        }
                    }


                    Spacer(
                        Modifier.height(17.dp)
                    )


                    HorizontalDivider()


                    // =================================================
                    // b0 CONFIRMADO
                    // =================================================

                    EstadoParametro(

                        nombre = "b₀",

                        valor =

                            if (b0Confirmado != null)

                                String.format(
                                    "%.6f",
                                    b0Confirmado
                                )

                            else

                                "--"
                    )


                    HorizontalDivider()


                    // =================================================
                    // GLUCOSA BASAL CONFIRMADA
                    // =================================================

                    EstadoParametro(

                        nombre = tr(

                            idioma,

                            "Glucosa basal",

                            "Basal glucose"
                        ),

                        valor =

                            if (
                                glucosaBasalConfirmada != null
                            )

                                String.format(
                                    "%.2f mg/dL",
                                    glucosaBasalConfirmada
                                )

                            else

                                "-- mg/dL"
                    )


                    HorizontalDivider()


                    // =================================================
                    // INSULINA BASAL CONFIRMADA
                    // =================================================

                    EstadoParametro(

                        nombre = tr(

                            idioma,

                            "Insulina basal",

                            "Basal insulin"
                        ),

                        valor =

                            if (
                                insulinaBasalConfirmada != null
                            )

                                String.format(
                                    "%.4f U/h",
                                    insulinaBasalConfirmada
                                )

                            else

                                "-- U/h"
                    )


                    Spacer(
                        Modifier.height(13.dp)
                    )


                    // =================================================
                    // CONFIRMACIÓN DE LA TEXAS
                    // =================================================

                    Surface(

                        modifier =
                            Modifier.fillMaxWidth(),

                        shape =
                            RoundedCornerShape(15.dp),

                        color =

                            if (parametrosConfirmados)

                                GluloopVerde
                                    .copy(alpha = 0.08f)

                            else

                                GluloopNaranja
                                    .copy(alpha = 0.06f)

                    ) {


                        Row(

                            modifier =
                                Modifier.padding(11.dp),

                            verticalAlignment =
                                Alignment.CenterVertically

                        ) {


                            Text(

                                text =

                                    if (parametrosConfirmados)

                                        "✓"

                                    else

                                        "◌",

                                color =

                                    if (parametrosConfirmados)

                                        GluloopVerde

                                    else

                                        GluloopNaranja,

                                fontSize = 17.sp,

                                fontWeight =
                                    FontWeight.Bold
                            )


                            Spacer(
                                Modifier.width(8.dp)
                            )


                            Text(

                                text =

                                    if (parametrosConfirmados) {

                                        tr(

                                            idioma,

                                            "Parámetros confirmados por la F28379D",

                                            "Parameters confirmed by the F28379D"
                                        )

                                    } else {

                                        tr(

                                            idioma,

                                            "Esperando parámetros confirmados por la F28379D",

                                            "Waiting for parameters confirmed by the F28379D"
                                        )
                                    },

                                modifier =
                                    Modifier.weight(1f),

                                fontSize = 10.sp,

                                lineHeight = 14.sp,

                                color =
                                    MaterialTheme
                                        .colorScheme
                                        .onSurfaceVariant
                            )
                        }
                    }
                }
            }


            Spacer(
                Modifier.height(30.dp)
            )
        }
    }


    // =========================================================
    // VENTANA PARA MODIFICAR
    // =========================================================

    if (mostrarEditor) {


        EditorParametrosControl(

            idioma = idioma,

            b0Actual = b0,

            glucosaBasalActual =
                glucosaBasal,

            insulinaBasalActual =
                insulinaBasal,


            cancelar = {

                mostrarEditor = false
            },


            guardar = {
                    nuevoB0,
                    nuevaGlucosaBasal,
                    nuevaInsulinaBasal ->


                // =====================================================
                // GUARDAR LOCALMENTE LO ESCRITO
                // =====================================================

                b0 =
                    nuevoB0

                glucosaBasal =
                    nuevaGlucosaBasal

                insulinaBasal =
                    nuevaInsulinaBasal


                // =====================================================
                // ENVIAR LOS 3 PARÁMETROS
                // =====================================================

                onGuardarParametros(

                    nuevoB0.toDouble(),

                    nuevaInsulinaBasal.toDouble(),

                    nuevaGlucosaBasal.toDouble()
                )


                // =====================================================
                // CERRAR VENTANA
                // =====================================================

                mostrarEditor = false
            }
        )
    }
}


// =============================================================
// FILA DEL ESTADO
// =============================================================

@Composable
private fun EstadoParametro(

    nombre: String,

    valor: String

) {


    Row(

        modifier = Modifier

            .fillMaxWidth()

            .padding(
                vertical = 13.dp
            ),

        horizontalArrangement =
            Arrangement.SpaceBetween,

        verticalAlignment =
            Alignment.CenterVertically

    ) {


        Text(

            text = nombre,

            fontSize = 12.sp,

            color =
                MaterialTheme
                    .colorScheme
                    .onSurfaceVariant
        )


        Spacer(
            Modifier.width(15.dp)
        )


        Text(

            text = valor,

            fontSize = 13.sp,

            fontWeight =
                FontWeight.Bold,

            color =
                MaterialTheme
                    .colorScheme
                    .onSurface
        )
    }
}


// =============================================================
// DIÁLOGO
// =============================================================

@Composable
private fun EditorParametrosControl(

    idioma: String,

    b0Actual: String,

    glucosaBasalActual: String,

    insulinaBasalActual: String,

    cancelar: () -> Unit,

    guardar: (
        String,
        String,
        String
    ) -> Unit

) {


    var nuevoB0 by remember(
        b0Actual
    ) {

        mutableStateOf(
            b0Actual
        )
    }


    var nuevaGlucosaBasal by remember(
        glucosaBasalActual
    ) {

        mutableStateOf(
            glucosaBasalActual
        )
    }


    var nuevaInsulinaBasal by remember(
        insulinaBasalActual
    ) {

        mutableStateOf(
            insulinaBasalActual
        )
    }


    // =========================================================
    // VALIDAR LOS 3 CAMPOS
    // =========================================================

    val valoresValidos =

        nuevoB0.toDoubleOrNull() != null &&

                nuevaGlucosaBasal
                    .toDoubleOrNull() != null &&

                nuevaInsulinaBasal
                    .toDoubleOrNull() != null


    AlertDialog(

        onDismissRequest =
            cancelar,


        title = {


            Column {


                Text(

                    text = tr(

                        idioma,

                        "Modificar parámetros",

                        "Edit parameters"
                    ),

                    fontSize = 20.sp,

                    fontWeight =
                        FontWeight.ExtraBold
                )


                Spacer(
                    Modifier.height(3.dp)
                )


                Text(

                    text = tr(

                        idioma,

                        "Configuración personalizada del controlador",

                        "Custom controller configuration"
                    ),

                    fontSize = 10.sp,

                    color =
                        MaterialTheme
                            .colorScheme
                            .onSurfaceVariant
                )
            }
        },


        text = {


            Column {


                // =====================================================
                // b0
                // =====================================================

                CampoParametroControl(

                    etiqueta = "b₀",

                    valor =
                        nuevoB0,

                    ejemplo =
                        "-0.078",

                    onCambio = {

                        nuevoB0 = it
                    }
                )


                Spacer(
                    Modifier.height(10.dp)
                )


                // =====================================================
                // GLUCOSA BASAL
                // =====================================================

                CampoParametroControl(

                    etiqueta = tr(

                        idioma,

                        "Glucosa basal [mg/dL]",

                        "Basal glucose [mg/dL]"
                    ),

                    valor =
                        nuevaGlucosaBasal,

                    ejemplo =
                        "119.4",

                    onCambio = {

                        nuevaGlucosaBasal = it
                    }
                )


                Spacer(
                    Modifier.height(10.dp)
                )


                // =====================================================
                // INSULINA BASAL
                // =====================================================

                CampoParametroControl(

                    etiqueta = tr(

                        idioma,

                        "Insulina basal [U/h]",

                        "Basal insulin [U/h]"
                    ),

                    valor =
                        nuevaInsulinaBasal,

                    ejemplo =
                        "0.85",

                    onCambio = {

                        nuevaInsulinaBasal = it
                    }
                )
            }
        },


        confirmButton = {


            Button(

                onClick = {


                    guardar(

                        nuevoB0,

                        nuevaGlucosaBasal,

                        nuevaInsulinaBasal
                    )
                },

                enabled =
                    valoresValidos

            ) {


                Text(

                    tr(

                        idioma,

                        "Guardar",

                        "Save"
                    )
                )
            }
        },


        dismissButton = {


            TextButton(

                onClick =
                    cancelar

            ) {


                Text(

                    tr(

                        idioma,

                        "Cancelar",

                        "Cancel"
                    )
                )
            }
        },


        shape =
            RoundedCornerShape(25.dp)
    )
}


// =============================================================
// CAMPO NUMÉRICO
// =============================================================

@Composable
private fun CampoParametroControl(

    etiqueta: String,

    valor: String,

    ejemplo: String,

    onCambio: (String) -> Unit

) {


    OutlinedTextField(

        value =
            valor,


        onValueChange = { entrada ->


            val texto =
                entrada.replace(
                    ',',
                    '.'
                )


            if (

                texto.isEmpty() ||

                texto == "-" ||

                texto.matches(

                    Regex(
                        "-?\\d*(\\.\\d*)?"
                    )
                )

            ) {

                onCambio(
                    texto
                )
            }
        },


        label = {

            Text(
                etiqueta
            )
        },


        placeholder = {

            Text(
                ejemplo
            )
        },


        singleLine = true,


        shape =
            RoundedCornerShape(14.dp),


        modifier =
            Modifier.fillMaxWidth()
    )
}