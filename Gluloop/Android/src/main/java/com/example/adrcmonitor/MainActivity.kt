package com.example.adrcmonitor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.adrcmonitor.screens.AjustesScreen
import com.example.adrcmonitor.screens.ControlScreen
import com.example.adrcmonitor.screens.EnsayoScreen
import com.example.adrcmonitor.screens.MonitoreoScreen
import okhttp3.*
import org.json.JSONObject
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit


// ============================================================
// PANTALLAS PRINCIPALES
// ============================================================

enum class Pantalla {
    MONITOREO,
    CONTROL,
    ENSAYO,
    AJUSTES
}


// ============================================================
// MAIN ACTIVITY
// ============================================================

class MainActivity : ComponentActivity() {

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)

        setContent {
            AppGLULOOP()
        }
    }
}


// ============================================================
// APLICACIÓN PRINCIPAL
// ============================================================

@Composable
fun AppGLULOOP() {

    // ========================================================
    // NAVEGACIÓN
    // ========================================================

    var pantallaActual by remember {
        mutableStateOf(Pantalla.MONITOREO)
    }


    // ========================================================
    // AJUSTES
    // ========================================================

    var modoOscuro by remember {
        mutableStateOf(false)
    }

    var idioma by remember {
        mutableStateOf("Español")
    }


    // ========================================================
    // TELEMETRÍA
    // ========================================================

    var glucosa by remember {
        mutableDoubleStateOf(0.0)
    }

    var insulina by remember {
        mutableDoubleStateOf(0.0)
    }

    /*
     * El IOB es nullable porque todavía el controlador
     * puede no estar enviándolo.
     *
     * Android NO calcula el IOB.
     */
    var iob by remember {
        mutableStateOf<Double?>(null)
    }


    var conectado by remember {
        mutableStateOf(false)
    }


    var ultimaLectura by remember {
        mutableStateOf("--")
    }


    // ========================================================
    // HISTORIALES
    // ========================================================

    val datosGlucosa =
        remember {
            mutableStateListOf<Double>()
        }

    val datosInsulina =
        remember {
            mutableStateListOf<Double>()
        }


    // ========================================================
    // PARÁMETROS CONFIRMADOS POR LA F28379D
    //
    // Estos valores SOLO se actualizan cuando Python recibe
    // la respuesta que volvió desde la Texas.
    // ========================================================

    var b0Confirmado by remember {
        mutableStateOf<Double?>(null)
    }

    var insulinaBasalConfirmada by remember {
        mutableStateOf<Double?>(null)
    }

    var glucosaBasalConfirmada by remember {
        mutableStateOf<Double?>(null)
    }


    // ========================================================
    // REFERENCIA AL WEBSOCKET
    //
    // La necesitamos para que ControlScreen pueda enviar
    // parámetros usando la conexión ya existente.
    // ========================================================

    var webSocketActual by remember {
        mutableStateOf<WebSocket?>(null)
    }


    // ========================================================
    // WEBSOCKET
    //
    // IMPORTANTE:
    // Por ahora permanece dentro de MainActivity.
    //
    // NO estamos creando todavía WebSocketManager.kt.
    // ========================================================

    DisposableEffect(Unit) {

        val cliente = OkHttpClient.Builder()

            .readTimeout(
                0,
                TimeUnit.MILLISECONDS
            )

            .build()


        val solicitud =
            Request.Builder()

                // ------------------------------------------------
                // MANTENEMOS LA DIRECCIÓN ACTUAL DEL SERVIDOR
                // ------------------------------------------------

                .url(
                    "ws://10.14.90.195:8765"
                )

                .build()


        val listener =
            object : WebSocketListener() {


                // ================================================
                // CONEXIÓN ABIERTA
                // ================================================

                override fun onOpen(
                    webSocket: WebSocket,
                    response: Response
                ) {

                    conectado = true

                    // Guardamos la conexión activa.
                    webSocketActual = webSocket
                }


                // ================================================
                // MENSAJE RECIBIDO
                // ================================================

                override fun onMessage(
                    webSocket: WebSocket,
                    text: String
                ) {

                    try {

                        val json =
                            JSONObject(text)


                        // =========================================
                        // IDENTIFICAR TIPO DE MENSAJE
                        // =========================================

                        when (
                            json.optString("tipo")
                        ) {


                            // =====================================
                            // TELEMETRÍA
                            // =====================================

                            "telemetria" -> {


                                // ---------------------------------
                                // GLUCOSA
                                // ---------------------------------

                                if (
                                    json.has("glucosa")
                                ) {

                                    val nuevaGlucosa =
                                        json.getDouble(
                                            "glucosa"
                                        )

                                    glucosa =
                                        nuevaGlucosa

                                    datosGlucosa.add(
                                        nuevaGlucosa
                                    )
                                }


                                // ---------------------------------
                                // INSULINA
                                // ---------------------------------

                                if (
                                    json.has("insulina")
                                ) {

                                    val nuevaInsulina =
                                        json.getDouble(
                                            "insulina"
                                        )

                                    insulina =
                                        nuevaInsulina

                                    datosInsulina.add(
                                        nuevaInsulina
                                    )
                                }


                                // ---------------------------------
                                // IOB
                                //
                                // Solo se actualiza si Python/Texas
                                // lo envían realmente.
                                // ---------------------------------

                                if (
                                    json.has("iob") &&
                                    !json.isNull("iob")
                                ) {

                                    iob =
                                        json.getDouble(
                                            "iob"
                                        )
                                }


                                // ---------------------------------
                                // HORA DE LA ÚLTIMA LECTURA
                                // ---------------------------------

                                ultimaLectura =
                                    LocalTime
                                        .now()
                                        .format(
                                            DateTimeFormatter
                                                .ofPattern(
                                                    "HH:mm:ss"
                                                )
                                        )
                            }


                            // =====================================
                            // PARÁMETROS CONFIRMADOS POR TEXAS
                            // =====================================

                            "parametros_confirmados" -> {


                                // ---------------------------------
                                // b0
                                // ---------------------------------

                                if (
                                    json.has("b0") &&
                                    !json.isNull("b0")
                                ) {

                                    b0Confirmado =
                                        json.getDouble(
                                            "b0"
                                        )
                                }


                                // ---------------------------------
                                // INSULINA BASAL
                                // ---------------------------------

                                if (
                                    json.has(
                                        "insulina_basal"
                                    ) &&
                                    !json.isNull(
                                        "insulina_basal"
                                    )
                                ) {

                                    insulinaBasalConfirmada =
                                        json.getDouble(
                                            "insulina_basal"
                                        )
                                }


                                // ---------------------------------
                                // GLUCOSA BASAL
                                // ---------------------------------

                                if (
                                    json.has(
                                        "glucosa_basal"
                                    ) &&
                                    !json.isNull(
                                        "glucosa_basal"
                                    )
                                ) {

                                    glucosaBasalConfirmada =
                                        json.getDouble(
                                            "glucosa_basal"
                                        )
                                }
                            }
                        }


                    } catch (
                        e: Exception
                    ) {

                        e.printStackTrace()
                    }
                }


                // ================================================
                // CONEXIÓN CERRADA
                // ================================================

                override fun onClosed(
                    webSocket: WebSocket,
                    code: Int,
                    reason: String
                ) {

                    conectado = false

                    webSocketActual = null
                }


                // ================================================
                // ERROR DE CONEXIÓN
                // ================================================

                override fun onFailure(
                    webSocket: WebSocket,
                    t: Throwable,
                    response: Response?
                ) {

                    conectado = false

                    webSocketActual = null

                    t.printStackTrace()
                }
            }


        // ====================================================
        // INICIAR WEBSOCKET
        // ====================================================

        val webSocket =
            cliente.newWebSocket(
                solicitud,
                listener
            )


        // ====================================================
        // CERRAR AL DESTRUIR EL COMPOSABLE
        // ====================================================

        onDispose {

            webSocketActual = null

            webSocket.close(
                1000,
                "GLULOOP cerrado"
            )

            cliente
                .dispatcher
                .executorService
                .shutdown()
        }
    }


    // ========================================================
    // TEMA
    // ========================================================

    MaterialTheme(
        colorScheme =
            if (modoOscuro)
                darkColorScheme()
            else
                lightColorScheme()
    ) {


        // ====================================================
        // ESTRUCTURA PRINCIPAL
        // ====================================================

        Scaffold(

            // =================================================
            // BARRA INFERIOR
            // =================================================

            bottomBar = {

                NavigationBar {


                    // -----------------------------------------
                    // MONITOREO
                    // -----------------------------------------

                    NavigationBarItem(

                        selected =
                            pantallaActual ==
                                    Pantalla.MONITOREO,

                        onClick = {
                            pantallaActual =
                                Pantalla.MONITOREO
                        },

                        icon = {

                            Icon(
                                imageVector =
                                    Icons.Default.Home,

                                contentDescription =
                                    null
                            )
                        },

                        label = {

                            Text(
                                if (idioma == "English")
                                    "Monitor"
                                else
                                    "Monitoreo"
                            )
                        }
                    )


                    // -----------------------------------------
                    // CONTROL
                    //
                    // Edit = configuración del controlador.
                    // -----------------------------------------

                    NavigationBarItem(

                        selected =
                            pantallaActual ==
                                    Pantalla.CONTROL,

                        onClick = {
                            pantallaActual =
                                Pantalla.CONTROL
                        },

                        icon = {

                            Icon(
                                imageVector =
                                    Icons.Default.Edit,

                                contentDescription =
                                    null
                            )
                        },

                        label = {

                            Text(
                                if (idioma == "English")
                                    "Control"
                                else
                                    "Control"
                            )
                        }
                    )


                    // -----------------------------------------
                    // ENSAYO
                    // -----------------------------------------

                    NavigationBarItem(

                        selected =
                            pantallaActual ==
                                    Pantalla.ENSAYO,

                        onClick = {
                            pantallaActual =
                                Pantalla.ENSAYO
                        },

                        icon = {

                            Icon(
                                imageVector =
                                    Icons.Default.List,

                                contentDescription =
                                    null
                            )
                        },

                        label = {

                            Text(
                                if (idioma == "English")
                                    "Trial"
                                else
                                    "Ensayo"
                            )
                        }
                    )


                    // -----------------------------------------
                    // AJUSTES
                    // -----------------------------------------

                    NavigationBarItem(

                        selected =
                            pantallaActual ==
                                    Pantalla.AJUSTES,

                        onClick = {
                            pantallaActual =
                                Pantalla.AJUSTES
                        },

                        icon = {

                            Icon(
                                imageVector =
                                    Icons.Default.Settings,

                                contentDescription =
                                    null
                            )
                        },

                        label = {

                            Text(
                                if (idioma == "English")
                                    "Settings"
                                else
                                    "Ajustes"
                            )
                        }
                    )
                }
            }

        ) { paddingValues ->


            // =================================================
            // CONTENIDO DE CADA PANTALLA
            // =================================================

            Surface(
                modifier =
                    Modifier.padding(
                        paddingValues
                    )
            ) {

                when (
                    pantallaActual
                ) {


                    // =========================================
                    // MONITOREO
                    // =========================================

                    Pantalla.MONITOREO -> {

                        MonitoreoScreen(

                            glucosa =
                                glucosa,

                            insulina =
                                insulina,

                            iob =
                                iob,

                            conectado =
                                conectado,

                            datosGlucosa =
                                datosGlucosa,

                            datosInsulina =
                                datosInsulina,

                            ultimaLectura =
                                ultimaLectura,

                            modoOscuro =
                                modoOscuro,

                            idioma =
                                idioma
                        )
                    }


                    // =========================================
                    // CONTROL
                    // =========================================

                    Pantalla.CONTROL -> {

                        ControlScreen(

                            conectado =
                                conectado,

                            idioma =
                                idioma,


                            // =================================
                            // ENVIAR PARÁMETROS
                            // =================================

                            onGuardarParametros = {
                                    b0,
                                    insulinaBasal,
                                    glucosaBasal ->


                                // -----------------------------
                                // Antes de enviar una nueva
                                // configuración, quitamos la
                                // confirmación anterior.
                                // -----------------------------

                                b0Confirmado =
                                    null

                                insulinaBasalConfirmada =
                                    null

                                glucosaBasalConfirmada =
                                    null


                                // -----------------------------
                                // CREAR JSON
                                // -----------------------------

                                val mensaje =
                                    JSONObject()


                                mensaje.put(
                                    "tipo",
                                    "parametros"
                                )

                                mensaje.put(
                                    "b0",
                                    b0
                                )

                                mensaje.put(
                                    "insulina_basal",
                                    insulinaBasal
                                )

                                mensaje.put(
                                    "glucosa_basal",
                                    glucosaBasal
                                )


                                // -----------------------------
                                // ENVIAR A PYTHON
                                // -----------------------------

                                webSocketActual?.send(
                                    mensaje.toString()
                                )
                            },


                            // =================================
                            // VALORES CONFIRMADOS
                            // =================================

                            b0Confirmado =
                                b0Confirmado,

                            insulinaBasalConfirmada =
                                insulinaBasalConfirmada,

                            glucosaBasalConfirmada =
                                glucosaBasalConfirmada
                        )
                    }


                    // =========================================
                    // ENSAYO
                    // =========================================

                    Pantalla.ENSAYO -> {

                        EnsayoScreen(

                            conectado =
                                conectado,

                            glucosa =
                                datosGlucosa,

                            insulina =
                                datosInsulina,

                            idioma =
                                idioma
                        )
                    }


                    // =========================================
                    // AJUSTES
                    // =========================================

                    Pantalla.AJUSTES -> {

                        AjustesScreen(

                            modoOscuro =
                                modoOscuro,

                            cambiarTema = {
                                modoOscuro =
                                    !modoOscuro
                            },

                            idioma =
                                idioma,

                            cambiarIdioma = {
                                    nuevoIdioma ->

                                idioma =
                                    nuevoIdioma
                            }
                        )
                    }
                }
            }
        }
    }
}